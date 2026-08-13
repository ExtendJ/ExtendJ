// SPDX-License-Identifier: BSD-3-Clause
package org.extendj.ast;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * A type bound set used for type inference as described by JLS SE8 §18.
 * Collects bounds on inference variables.
 * As long as the bound false is not in the set, the types can be resolved
 * with capture conversion.
 *
 * <p>Type variable and inference variable is used interchangeably in comments below.
 *
 * <p>Constraints formulas (JLS SE8 §18.1.2):
 * <ul>
 * <li>{@code ‹expr → T›} - expr compatible in loose invocation context with target type T</li>
 * <li>{@code ‹S → T›} - S is compatible in loose invocation context with type T</li>
 * <li>{@code ‹S <: T›} - reference type S is a subtype of a reference type T</li>
 * <li>{@code ‹S <= T›} - type argument S is contained by a type argument T (§4.5.1)</li>
 * <li>{@code ‹S = T›} - type (argument) S is the same as a type (argument) T (§4.3.4)</li>
 * <li>{@code ‹lambda → throws T›} - checked exceptions thrown by the body of the lambda derived from function type T.</li>
 * <li>{@code ‹method → throws T›} - checked exceptions thrown by the method reference derived from function T.</li>
 * </ul>
 *
 * <p>Transitivity rules for pairs of bounds on a shared inference variable (§18.3.1):
 * <ul>
 * <li>{@code α = S} and {@code α = T} imply {@code ‹S = T›}</li>
 * <li>{@code α = S} and {@code α <: T} imply {@code ‹S <: T›}</li>
 * <li>{@code α = S} and {@code T <: α} imply {@code ‹T <: S›}</li>
 * <li>{@code S <: α} and {@code α <: T} imply {@code ‹S <: T›}</li>
 * </ul>
 */
public class BoundSet {
  static final boolean DEBUG = !System.getProperty("extendj.debug.inf", "").isEmpty();

  /** The bound set is satisfiable only if the false constraint has not been added. */
  public boolean satisfiable = true;

  /** The invocation this bound set infers. */
  private final Expr context;

  private ArrayList<Bound> newBounds = new ArrayList<>();
  private boolean incorporating = false;

  /**
   * The set of reduced bounds for an inference variable.
   * Produced from constraint formulas.
   */
  static class VariableBounds {
    /** Lower type bounds. */
    public final Collection<TypeDecl> lower;

    /** Upper type bounds. */
    public final Collection<TypeDecl> upper;

    /** Equal type bounds. */
    public final Collection<TypeDecl> equal;

    /**
     * Whether the bound {@code throws α} was added for the inference variable (§18.1.3).
     *
     * <p>Make inference prefer an unchecked exception type instantiation (§18.4).
     */
    public boolean hasThrowsBound = false;

    public CaptureBound captureBound = null;

    /**
     * The instantiated type to use as type argument.
     *
     * <p>This is {@code null} until the variable has been instantiated.
     */
    public TypeDecl inst;

    /** This is a fresh synthetic variable created during resolution. */
    public boolean fresh = false;

    /** Sentinel set from looking up a missing/external variable. */
    static final VariableBounds EMPTY = VariableBounds.empty();

    VariableBounds() {
      // Insertion ordered so that resolution does not depend on identity hash codes:
      // the bounds drive the dependency traversal and the lub/glb argument order.
      this(new LinkedHashSet<>(), new LinkedHashSet<>(), new LinkedHashSet<>());
    }

    VariableBounds(VariableBounds that) {
      this.lower = new LinkedHashSet<>(that.lower);
      this.upper = new LinkedHashSet<>(that.upper);
      this.equal = new LinkedHashSet<>(that.equal);
      this.hasThrowsBound = that.hasThrowsBound;
      this.inst = that.inst;
      this.fresh = that.fresh;
    }

    private VariableBounds(Collection<TypeDecl> lower, Collection<TypeDecl> upper,
        Collection<TypeDecl> equal) {
      this.lower = lower;
      this.upper = upper;
      this.equal = equal;
    }

    /** Create an immutable empty constraint set. */
    static VariableBounds empty() {
      return new VariableBounds(Collections.<TypeDecl>emptySet(),
          Collections.<TypeDecl>emptySet(), Collections.<TypeDecl>emptySet());
    }
  }

  /** Inference variables whose instantiations are to be derived from this bound set. */
  private Collection<TypeVariable> variables;

  /**
   * Inference variables incorporated from other bound sets during merging
   * (§18.3). They participate in resolution but are not result type arguments.
   */
  private Collection<TypeVariable> auxiliaryVariables;

  /** The constraint sets of the inference variables in this set. */
  protected Map<TypeVariable, VariableBounds> map;

  protected Collection<CaptureBound> captureBounds;

  VariableBounds lookup(TypeDecl v) {
    return map.getOrDefault(v, VariableBounds.EMPTY);
  }

  public boolean rawAccess = false;

  /**
   * Whether unchecked conversion was necessary for the method to be applicable (§18.5.1).
   */
  public boolean uncheckedConversion = false;

  /**
   * A constraint that could not be reduced because it mentions a type variable
   * not local to this bound set (an inference variable of an enclosing bound set).
   */
  private static class DeferredConstraint {
    static final int SUBTYPE = 0;
    static final int EQUAL = 1;
    static final int CONTAINED = 2;
    final int kind;
    final TypeDecl S;
    final TypeDecl T;

    DeferredConstraint(int kind, TypeDecl S, TypeDecl T) {
      this.kind = kind;
      this.S = S;
      this.T = T;
    }
  }

  /** Constraints deferred to the enclosing bound set (§18.5.2). */
  private ArrayList<DeferredConstraint> deferred = new ArrayList<>(0);

  public BoundSet(Expr context) {
    this.context = context;
    variables = new ArrayList<>();
    auxiliaryVariables = new HashSet<>();
    map = new HashMap<>();
    captureBounds = new HashSet<>();
  }

  public void addTypeVariable(TypeVariable T) {
    if (!variables.contains(T)) {
      variables.add(T);
      map.put(T, new VariableBounds());
    }
  }

  /** Add the bound {@code throws α} for an inference variable (§18.1.3).  */
  public void addThrowsBound(TypeVariable alpha) {
    VariableBounds set = lookup(alpha);
    if (set != VariableBounds.EMPTY) {
      set.hasThrowsBound = true;
    }
  }

  /**
   * Register an inference variable incorporated from another bound set so that it
   * participates in resolution without becoming a result type argument.
   */
  private void addAuxiliaryVariable(TypeVariable T) {
    if (!variables.contains(T) && !auxiliaryVariables.contains(T)) {
      auxiliaryVariables.add(T);
      map.put(T, new VariableBounds());
    }
  }

  /**
   * Test whether this bound set infers the inference variables {@code vars}.
   */
  public boolean infersVariables(Iterable<? extends TypeVariable> vars) {
    for (TypeVariable v : vars) {
      if (variables.contains(v)) {
        return true;
      }
    }
    return false;
  }

  /** All inference variables of this bound set, both result and auxiliary. */
  private Collection<TypeVariable> allVariables() {
    if (auxiliaryVariables.isEmpty()) {
      return variables;
    }
    ArrayList<TypeVariable> all = new ArrayList<>(variables.size() + auxiliaryVariables.size());
    all.addAll(variables);
    all.addAll(auxiliaryVariables);
    return all;
  }

  /** Lift the bound set of a nested invocation into this set (§18.5.2). */
  public void incorporateBounds(BoundSet set) {
    if (!set.satisfiable) {
      // If lifted bounds contain ‹false› then this set includes ‹false›
      satisfiable = false;
      return;
    }
    if (set.rawAccess) {
      rawAccess = true;
    }
    if (set.uncheckedConversion) {
      uncheckedConversion = true;
    }
    for (TypeVariable v : set.variables) {
      addAuxiliaryVariable(v);
    }
    for (TypeVariable v : set.auxiliaryVariables) {
      addAuxiliaryVariable(v);
    }
    for (Map.Entry<TypeVariable, VariableBounds> entry : set.map.entrySet()) {
      TypeVariable v = entry.getKey();
      VariableBounds incoming = entry.getValue();
      if (incoming.hasThrowsBound) {
        lookup(v).hasThrowsBound = true;
      }
      if (incoming.captureBound != null) {
        lookup(v).captureBound = incoming.captureBound;
      }
      for (TypeDecl T : incoming.equal) {
        constraintEqual(v, T);
      }
      for (TypeDecl T : incoming.upper) {
        constraintSubtype(v, T);
      }
      for (TypeDecl T : incoming.lower) {
        constraintSubtype(T, v);
      }
    }
    captureBounds.addAll(set.captureBounds);
    // Apply the deferred constraints of the lifted set within this set
    // where previously non-local type variables may be inference variables.
    for (DeferredConstraint dc : set.deferred) {
      if (dc.kind == DeferredConstraint.SUBTYPE) {
        constraintSubtype(dc.S, dc.T);
      } else if (dc.kind == DeferredConstraint.CONTAINED) {
        constraintContainedIn(dc.S, dc.T);
      } else {
        constraintEqual(dc.S, dc.T);
      }
    }
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append("BoundSet {");
    String sep = " ";
    for (TypeVariable T : variables) {
      VariableBounds set = lookup(T);
      for (TypeDecl U : set.lower) {
        sep = "\n";
        str.append("\n  " + T.fullName() + " :> " + U.fullName());
      }
      for (TypeDecl U : set.upper) {
        sep = "\n";
        str.append("\n  " + T.fullName() + " <: " + U.fullName());
      }
      for (TypeDecl U : set.equal) {
        sep = "\n";
        str.append("\n  " + T.fullName() + " = " + U.fullName());
      }
    }
    for (CaptureBound cap : captureBounds) {
        str.append("\n  " + cap);
    }
    str.append(sep + "}");
    return str.toString();
  }

  private java.util.List<TypeVariable> addEdge(TypeVariable alpha, TypeVariable beta, Stack<TypeVariable> stack,
      Map<TypeVariable, Integer> index, Map<TypeVariable, Integer> lowlink) {
    // Add dependency edge alpha -> beta
    VariableBounds set = lookup(beta);
    if (hasInstantiation(set)) {
      // Variable already instantiated - ignore it.
      return null;
    }
    if (!index.containsKey(beta)) {
      java.util.List<TypeVariable> scc = connect(beta, stack, index, lowlink);
      lowlink.put(alpha, Math.min(lowlink.get(alpha), lowlink.get(beta)));
      return scc;
    }
    lowlink.put(alpha, Math.min(lowlink.get(alpha), index.get(beta)));
    return null;
  }

  private java.util.List<TypeVariable> addEdge(TypeVariable alpha, TypeDecl T, Stack<TypeVariable> stack,
      Map<TypeVariable, Integer> index, Map<TypeVariable, Integer> lowlink) {
    if (T instanceof TypeVariable) {
      return addEdge(alpha, (TypeVariable) T, stack, index, lowlink);
    }
    if (T instanceof ParTypeDecl) {
      for (TypeDecl arg : ((ParTypeDecl) T).getParameterization().args) {
        java.util.List<TypeVariable> scc = addEdge(alpha, arg, stack, index, lowlink);
        if (scc != null) return scc;
      }
    }
    if (T instanceof WildcardExtendsType) {
      return addEdge(alpha, ((WildcardExtendsType) T).extendsType(), stack, index, lowlink);
    }
    if (T instanceof WildcardSuperType) {
      return addEdge(alpha, ((WildcardSuperType) T).superType(), stack, index, lowlink);
    }
    if (T.isArrayDecl()) {
      return addEdge(alpha, T.componentType(), stack, index, lowlink);
    }
    return null;
  }

  private java.util.List<TypeVariable> connect(TypeVariable alpha, Stack<TypeVariable> stack,
      Map<TypeVariable, Integer> index, Map<TypeVariable, Integer> lowlink) {
    // Modified Tarjan SCC algorithm which stops at the first SCC.
    // We terminate when leaving the first node v that has index[v] == lowlink[v].
    // The first SCC found is a sink.
    // We do not need the on-stack test of Tarjan because we stop at the first SCC.
    if (index.containsKey(alpha)) return null;
    int i = index.size() + 1;
    index.put(alpha, Integer.valueOf(i));
    lowlink.put(alpha, Integer.valueOf(i));
    stack.push(alpha);
    java.util.List<TypeVariable> scc;

    // Iterate dependencies.
    VariableBounds set = lookup(alpha);
    if (set.captureBound != null) {
      // A variable on the left-hand side of a capture bound depends
      // on every other variable mentioned in the bound and its other
      // bounds do not contribute dependencies (§18.4).
      for (TypeVariable beta : set.captureBound.lhs) {
        if (beta != alpha) {
          scc = addEdge(alpha, beta, stack, index, lowlink);
          if (scc != null) return scc;
        }
      }
      for (TypeDecl beta : set.captureBound.rhs) {
        scc = addEdge(alpha, beta, stack, index, lowlink);
        if (scc != null) return scc;
      }
    } else {
      // TODO(joqvist): how to handle the capture variable dependency inversion rule?
      for (TypeDecl T : set.equal) {
        // alpha depends on T (unless capture)
        scc = addEdge(alpha, T, stack, index, lowlink);
        if (scc != null) return scc;
      }
      for (TypeDecl T : set.upper) {
        // alpha depends on T (unless capture)
        scc = addEdge(alpha, T, stack, index, lowlink);
        if (scc != null) return scc;
      }
      for (TypeDecl T : set.lower) {
        // alpha depends on T (unless capture)
        scc = addEdge(alpha, T, stack, index, lowlink);
        if (scc != null) return scc;
      }
    }

    // If v is a root node, pop the stack and generate an SCC
    if (Objects.equals(lowlink.get(alpha), index.get(alpha))) {
      // Insertion ordered: the SCC determines the order in which the fresh variables
      // of the second instantiation attempt are created and instantiated.
      scc = new ArrayList<>();
      while (true) {
        TypeVariable w = stack.pop();
        scc.add(w);
        if (w == alpha) break;
      }
      return scc;
    } else {
      return null;
    }
  }

  /** Resolve the inference variables to instantiations (§18.4). */
  public boolean resolve() {
    return resolve(this::allVariables);
  }

  /** Resolve the given inference variables and the variables they depend on. */
  public boolean resolve(Collection<TypeVariable> targets) {
    return resolve(() -> targets);
  }

  private boolean resolve(Supplier<Collection<TypeVariable>> variables) {
    // §18.4 specifies that variables are instantiated in an iterative fashion
    // where in each step a subset S ⊂ V is chosen and instantiated as a unit
    // where S is minimal non-empty sink SCC of uninstantiated inference
    // variables under inference variable dependencies.
    if (!satisfiable) return false;
    Stack<TypeVariable> stack = new Stack<>();
    Map<TypeVariable, Integer> index = new HashMap<>();
    Map<TypeVariable, Integer> lowlink = new HashMap<>();
    boolean change = true;
    if (DEBUG) System.err.println("Resolve start: " + this);
    while (satisfiable && change) {
      change = false;
      // Incorporation during instantiation can add auxiliary variables to the set.
      for (TypeVariable u : variables.get()) {
        VariableBounds set = lookup(u);
        if (hasInstantiation(set)) {
          // Variable already instantiated - ignore it.
          continue;
        }
        stack.clear();
        index.clear();
        lowlink.clear();
        java.util.List<TypeVariable> sink = connect(u, stack, index, lowlink);
        if (DEBUG) System.err.println("sink: " + sink);
        assert sink != null; // Must be non-null by the fact that at least u is not instantiated.
        java.util.List<TypeVariable> filtered = sink.stream()
            .filter(it -> lookup(it).captureBound == null)
            .collect(Collectors.toList());
        boolean captureBlocked = filtered.size() != sink.size();
        if (!filtered.isEmpty()) {
          sink = filtered;
        }
        if (!captureBlocked) {
          // Instantiate the variables. First attempt.
          LinkedList<Bound> bounds = new LinkedList<>();
          ArrayList<VariableBounds> saved = new ArrayList<>(sink.size()); // May need to rollback constraints.
          for (TypeVariable alpha : sink) {
            saved.add(new VariableBounds(lookup(alpha)));
          }
          for (TypeVariable alpha : sink) {
            instantiate(alpha, bounds);
          }
          if (satisfiable) {
            // Incorporate the bounds from instantiation.
            for (Bound bound : bounds) {
              change = true;
              addEqualBound(bound.alpha, bound.type);
              if (!satisfiable) break;
              VariableBounds as = lookup(bound.alpha);
              if (as.inst == null) {
                satisfiable = false;
              }
            }
          }
          if (satisfiable) {
            // Instantiation success on first attempt. Continue to next α.
            continue;
          }
          // Rollback saved constraints in preparation for the second instantiation attempt.
          for (int i = 0; i < saved.size(); ++i) {
            map.put(sink.get(i), saved.get(i));
          }
          satisfiable = true;
        }
        if (satisfiable) {
          // Run a second instantiation attempt according to §18.4.
          // We create new type variables Y1, ..., Yn whose bounds are the lub/glb of the original
          // variables in sink and if they are not internally inconsistent then assign
          // αi = Yi for each i ∈ {1, ..., n}.
          change = true;
          instantiateFresh(new ArrayList<>(sink));
        }
        if (!satisfiable) break;
      }
      if (DEBUG) System.err.println("after: " + this);
    }
    return satisfiable;
  }

  enum ConstraintKind {
    COMPATIBILITY,  // ‹expr → T›
    CHECKED_THROWS, // ‹expr → throws T›
  };

  /**
   * A constraint formula on an argument expression to be reduced in dependency order.
   */
  public static class Constraint {
    final ConstraintKind kind;
    final Expr expr;

    /** The target type, substituted as the input variables are instantiated. */
    TypeDecl T;

    Constraint(ConstraintKind kind, Expr expr, TypeDecl T) {
      this.kind = kind;
      this.expr = expr;
      this.T = T;
    }

    /** Collect variables that must be instantiated before this constraint can be reduced. */
    void collectInputVars(BoundSet bounds, Collection<TypeVariable> result) {
      expr.constraintInputVariables(bounds, T, kind == ConstraintKind.CHECKED_THROWS, result);
    }

    /** Collect variables of the target type that this constraint can constrain. */
    void collectOutputVars(BoundSet bounds, Collection<TypeVariable> result) {
      Collection<TypeVariable> input = new LinkedHashSet<>();
      collectInputVars(bounds, input);
      Collection<TypeVariable> mentioned = new LinkedHashSet<>();
      bounds.collectMentionedVars(T, mentioned);
      mentioned.removeAll(input);
      result.addAll(mentioned);
    }

    void reduce(BoundSet bounds) {
      switch (kind) {
        case CHECKED_THROWS:
          bounds.constraintCheckedThrows(expr, T);
          break;
        case COMPATIBILITY:
          if (expr instanceof LambdaExpr && ((LambdaExpr) expr).isImplicit()
              && expr.hasProperParameterTypes(T)) {
            // The target type gives the lambda proper parameter types, so its body can
            // be typed against a ground target type.
            bounds.constraintExprCompat(((LambdaExpr) expr).groundedLambda(T), T);
          } else {
            bounds.constraintExprCompat(expr, T);
          }
          break;
      }
    }
  }

  public static Constraint compatibilityConstraint(Expr expr, TypeDecl T) {
    return new Constraint(ConstraintKind.COMPATIBILITY, expr, T);
  }

  public static Constraint checkedThrowsConstraint(Expr expr, TypeDecl T) {
    return new Constraint(ConstraintKind.CHECKED_THROWS, expr, T);
  }

  /** Collect the inference variables of this bound set that are mentioned by {@code T}. */
  public void collectMentionedVars(TypeDecl T, Collection<TypeVariable> result) {
    if (isInferenceVariable(T)) {
      result.add((TypeVariable) T);
    } else if (T instanceof ParTypeDecl && !T.isRawType()) {
      for (TypeDecl arg : ((ParTypeDecl) T).getParameterization().args) {
        collectMentionedVars(arg, result);
      }
    } else if (T instanceof WildcardExtendsType) {
      collectMentionedVars(((WildcardExtendsType) T).extendsType(), result);
    } else if (T instanceof WildcardSuperType) {
      collectMentionedVars(((WildcardSuperType) T).superType(), result);
    } else if (T.isArrayDecl()) {
      collectMentionedVars(T.componentType(), result);
    } else if (T instanceof GLBType) {
      GLBType glb = (GLBType) T;
      for (int i = 0; i < glb.getNumTypeBound(); ++i) {
        collectMentionedVars(glb.getTypeBound(i).type(), result);
      }
    }
  }

  /**
   * Reduce the constraint formulas of an invocation in dependency order (§18.5.2).
   */
  public void reduceOrdered(java.util.List<Constraint> constraints) {
    // Fixpoint constraint reduction, choosing constraints whose input
    // variables cannot influence output variables of other constraints.
    ArrayList<Constraint> pending = new ArrayList<>(constraints);
    while (satisfiable && !pending.isEmpty()) {
      ArrayList<Constraint> selected = select(pending);
      pending.removeAll(selected);
      Collection<TypeVariable> input = new LinkedHashSet<>();
      for (Constraint constraint : selected) {
        constraint.collectInputVars(this, input);
      }
      if (!input.isEmpty() && !resolve(input)) {
        return;
      }
      Map<TypeVariable, TypeDecl> theta = instantiations();
      if (!theta.isEmpty()) {
        for (Constraint constraint : selected) {
          constraint.T = constraint.T.substituted(theta);
        }
        for (Constraint constraint : pending) {
          constraint.T = constraint.T.substituted(theta);
        }
      }
      for (Constraint constraint : selected) {
        constraint.reduce(this);
        if (!satisfiable) {
          return;
        }
      }
    }
  }

  /**
   * Select constraints to reduce in the next round of reduction.
   */
  private ArrayList<Constraint> select(ArrayList<Constraint> pending) {
    ArrayList<Collection<TypeVariable>> input = new ArrayList<>(pending.size());
    ArrayList<Collection<TypeVariable>> output = new ArrayList<>(pending.size());
    for (Constraint constraint : pending) {
      Collection<TypeVariable> in = new LinkedHashSet<>();
      Collection<TypeVariable> out = new LinkedHashSet<>();
      constraint.collectInputVars(this, in);
      constraint.collectOutputVars(this, out);
      input.add(in);
      output.add(out);
    }
    Map<TypeVariable, TypeVariable> forest = influenceGroups();
    ArrayList<Constraint> selected = new ArrayList<>(pending.size());
    for (int i = 0; i < pending.size(); ++i) {
      boolean independent = true;
      for (int j = 0; independent && j < pending.size(); ++j) {
        if (i == j) {
          continue;
        }
influence:
        for (TypeVariable alpha : input.get(i)) {
          // If alpha is already instantiated it becomes a singleton root in the forest.
          // Note that identical variables should still compare as dependent
          // regardless of instantiation state. See test ti/cycle_01f.
          TypeVariable root = find(forest, alpha);
          for (TypeVariable beta : output.get(j)) {
            if (find(forest, beta) == root) {
              independent = false;
              break influence;
            }
          }
        }
      }
      if (independent) {
        selected.add(pending.get(i));
      }
    }
    if (selected.isEmpty()) {
      // Remaining constraints form a dependency cycle, pick one using tie breaker.
      Constraint first = pending.get(0);
      for (Constraint constraint : pending) {
        if (tieBreak(constraint, first)) {
          first = constraint;
        }
      }
      selected.add(first);
    }
    return selected;
  }

  /** Tie breaker for mutually dependent constraint ordering. */
  private static boolean tieBreak(Constraint constraint, Constraint other) {
    if (constraint.kind != other.kind) {
      return constraint.kind == ConstraintKind.COMPATIBILITY;
    }
    return constraint.expr.getStart() < other.expr.getStart();
  }

  private TypeVariable find(Map<TypeVariable, TypeVariable> forest, TypeVariable x) {
    if (!forest.containsKey(x)) {
      forest.put(x, x);
      return x;
    }
    TypeVariable parent = forest.get(x);
    while (parent != x) {
      x = parent;
      parent = forest.get(parent);
    }
    return parent;
  }

  private void union(Map<TypeVariable, TypeVariable> forest, TypeVariable x, TypeVariable y) {
    x = find(forest, x);
    y = find(forest, y);
    if (x == y) return;
    forest.put(y, x);
  }

  /**
   * Group the uninstantiated inference variables so that two variables are in the
   * same group when one can influence the other (§18.5.2).
   *
   * <p>An inference variable can influence another when either depends on the
   * resolution of the other (§18.4).
   *
   * @return a disjoint set forest (union-find data structure)
   */
  private Map<TypeVariable, TypeVariable> influenceGroups() {
    Map<TypeVariable, TypeVariable> forest = new LinkedHashMap<>();
    for (TypeVariable alpha : allVariables()) {
      if (hasInstantiation(alpha)) {
        continue;
      }
      Collection<TypeVariable> mentioned = new LinkedHashSet<>();
      VariableBounds set = lookup(alpha);
      if (set.captureBound != null) {
        mentioned.addAll(set.captureBound.lhs);
        collectMentionedVars(set.captureBound.baseType, mentioned);
      } else {
        for (TypeDecl bound : set.equal) {
          collectMentionedVars(bound, mentioned);
        }
        for (TypeDecl bound : set.upper) {
          collectMentionedVars(bound, mentioned);
        }
        for (TypeDecl bound : set.lower) {
          collectMentionedVars(bound, mentioned);
        }
      }
      find(forest, alpha);
      for (TypeVariable beta : mentioned) {
        if (hasInstantiation(beta)) continue;
        union(forest, alpha, beta);
      }
    }
    return forest;
  }

  /** The instantiations of the inference variables that have been resolved. */
  private Map<TypeVariable, TypeDecl> instantiations() {
    Map<TypeVariable, TypeDecl> theta = new LinkedHashMap<>();
    for (TypeVariable alpha : allVariables()) {
      VariableBounds set = lookup(alpha);
      if (set != VariableBounds.EMPTY && set.inst != null) {
        theta.put(alpha, set.inst);
      }
    }
    return theta;
  }

  /** Test if an inference variable has an instantiation.  */
  private boolean hasInstantiation(TypeVariable alpha) {
    return hasInstantiation(lookup(alpha));
  }

  /**
   * Test if the constriant set comes from a variable that has an instantiation or
   * is not part of this bound set.
   */
  private boolean hasInstantiation(VariableBounds set) {
    if (set == VariableBounds.EMPTY || set.inst != null) return true;
    for (TypeDecl bound : set.equal) {
      TypeDecl proper = properBound(bound);
      if (proper != null) {
        // We can cache the instantiation in set.inst.
        set.inst = proper;
        return true;
      }
    }
    return false;
  }

  /**
   * Compute a candidate instantiation for the inference variable {@code alpha}
   * from its bounds (§18.4). If a candidate instantiation is found then it is
   * added to the {@code bounds} list.
   */
  private void instantiate(TypeVariable alpha, LinkedList<Bound> bounds) {
    // An equality bound to a proper type gives a direct instantiation and takes precedence.
    VariableBounds set = lookup(alpha);
    // If αi has lower bounds, the instantiation is their least upper bound.
    if (!set.lower.isEmpty()) {
      ArrayList<TypeDecl> lower = properBounds(set.lower);
      if (!lower.isEmpty()) {
        TypeDecl lub = leastUpperBound(alpha, lower);
        if (lub.isUnknown()) {
          satisfiable = false;
          return;
        }
        bounds.add(new Bound(Bound.Kind.EQUAL, alpha, lub));
        return;
      }
    }
    // If we have the bound throws αi, then incorporate αi = RuntimeException.
    if (set.hasThrowsBound) {
      boolean useRTE = true;
      for (TypeDecl upper : set.upper) {
        if (upper != alpha.typeException()
            && upper != alpha.typeObject()
            && upper != alpha.typeThrowable()) {
          useRTE = false;
        }
      }
      if (useRTE) {
        bounds.add(new Bound(Bound.Kind.EQUAL, alpha, alpha.typeRuntimeException()));
        return;
      }
    }
    // Otherwise, if α has upper bounds, the instantiation is their greatest lower bound (§5.1.10).
    if (!set.upper.isEmpty()) {
      ArrayList<TypeDecl> upper = properBounds(set.upper);
      if (!upper.isEmpty()) {
        TypeDecl glb = greatestLowerBound(upper);
        // An ill-formed intersection (e.g. of two unrelated classes) has no
        // greatest lower bound, so the variable cannot be instantiated and the
        // bound set is unsatisfiable (§5.1.10, §18.4).
        if (glb.isUnknown()) {
          satisfiable = false;
          return;
        }
        bounds.add(new Bound(Bound.Kind.EQUAL, alpha, glb));
      }
    }
  }

  /**
   * The lower bound of the fresh type variable that replaces {@code alpha} in the
   * second instantiation attempt (§18.4).
   */
  private TypeDecl freshLowerBound(TypeVariable alpha) {
    VariableBounds set = lookup(alpha);
    if (set.lower.isEmpty()) {
      return null;
    }
    ArrayList<TypeDecl> lower = properBounds(set.lower);
    if (lower.isEmpty()) {
      return null;
    }
    TypeDecl lub = leastUpperBound(alpha, lower);
    if (lub.isUnknown()) {
      // An ill-formed upper bound has no instantiation and the bound set is unsatisfiable.
      satisfiable = false;
      return null;
    }
    return lub;
  }

  /**
   * The upper bound of the fresh type variable that replaces {@code alpha} in the
   * second instantiation attempt (§18.4).
   */
  private TypeDecl freshUpperBound(TypeVariable alpha) {
    VariableBounds set = lookup(alpha);
    if (set.upper.isEmpty()) {
      return null;
    }
    ArrayList<TypeDecl> upper = new ArrayList<>(set.upper.size());
    for (TypeDecl u : set.upper) {
      upper.add(substituted(u));
    }
    TypeDecl glb = greatestLowerBound(upper);
    if (glb.isUnknown()) {
      // An ill-formed lower bound has no instantiation and the bound set is unsatisfiable.
      satisfiable = false;
      return null;
    }
    return glb;
  }

  /**
   * Second instantiation attempt of §18.4. Replace the inference variables
   * {@code vars} of a sink SCC by fresh type variables {@code Y1, ..., Yn} whose bounds are
   * derived from the bounds of {@code α1, ..., αn} and instantiate {@code αi = Yi}.
   */
  private void instantiateFresh(ArrayList<TypeVariable> vars) {
    int n = vars.size();
    ArrayList<TypeDecl> lowerBounds = new ArrayList<>(n);
    ArrayList<TypeDecl> upperBounds = new ArrayList<>(n);
    for (TypeVariable alpha : vars) {
      lowerBounds.add(freshLowerBound(alpha));
      upperBounds.add(freshUpperBound(alpha));
    }
    if (!satisfiable) return;
    for (int i = 0; i < n; ++i) {
      TypeDecl lower = lowerBounds.get(i);
      TypeDecl upper = upperBounds.get(i);
      if (lower != null && upper != null && isProperType(upper) && !lower.subtype(upper)) {
        // The fresh variables must have well-formed bounds (§18.4).
        satisfiable = false;
        return;
      }
    }
    List<FreshVariable> fresh = context.lookupFreshVars(
        context, new ArrayList<>(vars), lowerBounds, upperBounds);
    for (int i = 0; i < n; ++i) {
      FreshVariable Yi = fresh.getChild(i);
      VariableBounds cs = new VariableBounds();
      cs.fresh = true;
      cs.inst = Yi;
      map.put(Yi, cs);
      auxiliaryVariables.add(Yi);
      lookup(vars.get(i)).inst = Yi;
    }
    for (int i = 0; i < n && satisfiable; ++i) {
      addEqualBound(vars.get(i), fresh.getChild(i));
    }
  }

  /**
   * Resolve {@code bound} to a proper type: the bound itself if it is already
   * proper, or the instantiation of an inference variable bound. Returns
   * {@code null} if the bound still mentions an uninstantiated inference variable.
   */
  private TypeDecl properBound(TypeDecl bound) {
    if (!bound.involvesTypeParameters()) {
      return bound;
    }
    if (isInferenceVariable(bound)) {
      return lookup(bound).inst;
    }
    if (bound instanceof TypeVariable) {
      // A type variable that is not an inference variable of this set is a
      // proper type instantiation.
      // TODO(joqvist): is this true?
      return bound;
    }
    // Substitute the instantiations of inference variables mentioned inside a
    // parameterized, array or wildcard bound type, e.g. resolve S<α> to S<String>
    // once α is instantiated. Returns null if any mentioned variable is not yet
    // instantiated or the structure is not supported.
    if (bound instanceof WildcardExtendsType) {
      TypeDecl b = properBound(((WildcardExtendsType) bound).extendsType());
      return b == null ? null : b.asWildcardExtends();
    }
    if (bound instanceof WildcardSuperType) {
      TypeDecl b = properBound(((WildcardSuperType) bound).superType());
      return b == null ? null : b.asWildcardSuper();
    }
    if (bound.isArrayDecl()) {
      TypeDecl c = properBound(bound.componentType());
      return c == null ? null : c.arrayType();
    }
    if (bound instanceof ParTypeDecl && !bound.isRawType()) {
      ParTypeDecl pt = (ParTypeDecl) bound;
      TypeDecl generic = pt.genericDecl();
      if (!(generic instanceof GenericTypeDecl)) {
        return null;
      }
      java.util.List<TypeDecl> args = pt.getParameterization().args;
      ArrayList<TypeDecl> properArgs = new ArrayList<TypeDecl>(args.size());
      for (TypeDecl arg : args) {
        TypeDecl proper = properBound(arg);
        if (proper == null) {
          return null;
        }
        properArgs.add(proper);
      }
      return ((GenericTypeDecl) generic).lookupParTypeDecl(properArgs);
    }
    return null;
  }

  /**
   * Substitute the instantiations of the inference variables mentioned in
   * {@code T}, or return {@code T} if some mentioned variable is still
   * uninstantiated.
   */
  private TypeDecl substituted(TypeDecl T) {
    TypeDecl proper = properBound(T);
    return proper == null ? T : proper;
  }

  /**
   * Resolve every bound to a proper type, or return {@code null} if any of them
   * still mentions an uninstantiated inference variable.
   */
  private ArrayList<TypeDecl> properBounds(Collection<TypeDecl> bounds) {
    ArrayList<TypeDecl> result = new ArrayList<TypeDecl>(bounds.size());
    for (TypeDecl bound : bounds) {
      TypeDecl proper = properBound(bound);
      if (proper != null) {
        result.add(proper);
      }
    }
    return result;
  }

  private static TypeDecl leastUpperBound(TypeVariable alpha, ArrayList<TypeDecl> bounds) {
    if (bounds.size() == 1) {
      return bounds.get(0);
    }
    return alpha.lookupLUBType(bounds).lub();
  }

  private static TypeDecl greatestLowerBound(ArrayList<TypeDecl> bounds) {
    if (bounds.size() == 1) {
      return bounds.get(0);
    }
    return GLBTypeFactory.glb(bounds);
  }

  /** Computes the parameterized supertypes of some type.  */
  protected static Collection<ParTypeDecl> parameterizedSupertypes(TypeDecl type) {
    // TODO(joqvist): this should be an attribute of TypeDecl instead.
    Collection<ParTypeDecl> result = new HashSet<>();
    addParameterizedSupertypes(type, new HashSet<>(), result);
    return result;
  }

  protected static void addParameterizedSupertypes(TypeDecl type,
      Collection<TypeDecl> processed,
      Collection<ParTypeDecl> result) {
    // TODO(joqvist): this should be an attribute of TypeDecl instead.
    if (!processed.contains(type)) {
      processed.add(type);
      if (type.isParameterizedType()) {
        result.add((ParTypeDecl) type);
      }
      for (TypeDecl typeDecl : type.directSupertypes()) {
        addParameterizedSupertypes(typeDecl, processed, result);
      }
    }
  }

  /**
   * Gives the inferred type arguments.
   */
  public Collection<TypeDecl> typeArguments() {
    Collection<TypeDecl> list = new ArrayList<>(variables.size());
    for (TypeVariable T : variables) {
      list.add(lookup(T).inst);
    }
    return list;
  }

  /** Expression compatibility in a loose invocation context with type T: {@code <Expr → T>}. */
  public void constraintExprCompat(Expr expr, TypeDecl T) {
    if (expr instanceof GroundedLambda) {
      // A lambda pinned to a ground target type (§18.5.2): its parameter types
      // come from the ground target, so the body can be typed without depending on
      // the enclosing invocation's resolution. Reduce the pinned lambda directly.
      constraintLambdaCompat(((GroundedLambda) expr).getLambda(), T);
      return;
    }
    if (isProperType(T)) {
      // If T is a proper type, the constraint reduces to true if the expression
      // is compatible in a loose invocation context with T (§5.3), and false
      // otherwise.
      if (!expr.compatibleLooseContext(substituted(T))) {
        satisfiable = false;
      }
      return;
    }
    if (!expr.isPolyExpression()) {
      // Otherwise, if the expression is a standalone expression (§15.2) of type
      // S, the constraint reduces to ‹S → T›.
      // The type of an expression is capture converted (§6.5.6.1, §15.11.1,
      // §15.12.2.6), which ExtendJ does not do in Expr.type(), so the capture
      // conversion is applied here where the type enters the inference.
      constraintTypeCompat(expr.capturedType(), T);
      return;
    }
    // Otherwise, the expression is a poly expression (§15.2). The result depends
    // on the form of the expression:
    if (expr instanceof ParExpr) {
      // If the expression is a parenthesized expression of the form
      // ( Expression' ), the constraint reduces to ‹Expression' → T›.
      constraintExprCompat(((ParExpr) expr).getExpr(), T);
      return;
    }
    if (expr instanceof MethodAccess || expr instanceof ClassInstanceExpr
        || expr instanceof Dot) {
      // A class instance creation or method invocation reduces to the bound set
      // B3 of the nested invocation targeting T (§18.5.2, and §15.9.3 for the
      // "method" of a class instance creation).
      // B3 is computed without resolving the nested invocation and lifted into
      // this bound set; constraints in B3 that mention the inference variables of
      // T (which are not local to B3) are deferred and replayed here, so the
      // nested and enclosing variables are solved together at resolution time.
      if (!expr.reduceInvocationBounds(this, T)) {
        satisfiable = false;
      }
      return;
    }
    if (expr instanceof ConditionalExpr) {
      // If the expression is a conditional expression of the form e1 ? e2 : e3,
      // the constraint reduces to two constraint formulas, ‹e2 → T› and ‹e3 → T›.
      ConditionalExpr cond = (ConditionalExpr) expr;
      constraintExprCompat(cond.getTrueExpr(), T);
      constraintExprCompat(cond.getFalseExpr(), T);
      return;
    }
    // If the expression is a lambda expression or a method reference expression,
    // the result is specified below.
    if (expr instanceof LambdaExpr) {
      constraintLambdaCompat((LambdaExpr) expr, T);
      return;
    }
    if (expr instanceof MethodReference) {
      constraintReferenceCompat((MethodReference) expr, T);
      return;
    }
    if (expr instanceof ConstructorReference) {
      constraintReferenceCompat((ConstructorReference) expr, T);
      return;
    }
  }

  /**
   * Reduce a lambda expression compatibility constraint ‹LambdaExpression → T›
   * where T mentions inference variables (§18.2.1).
   */
  private void constraintLambdaCompat(LambdaExpr expr, TypeDecl T) {
    // If T is not a functional interface type (§9.8), the constraint reduces to false.
    if (!T.hasFunctionDescriptor()) {
      satisfiable = false;
      return;
    }
    // TODO(joqvist): FunctionDescriptor fd = expr.groundTargetType(T);
    MethodDecl function = T.functionDescriptor().method;
    LambdaParameters lambdaParams = expr.getLambdaParameters();
    boolean explicitlyTyped = lambdaParams instanceof DeclaredLambdaParameters;
    // If the number of lambda parameters differs from the function type's, the
    // constraint reduces to false.
    int numParams = explicitlyTyped
        ? ((DeclaredLambdaParameters) lambdaParams).getNumParameter()
        : ((InferredLambdaParameters) lambdaParams).getNumParameter();
    if (numParams != function.getNumParameter()) {
      satisfiable = false;
      return;
    }
    if (explicitlyTyped) {
      // For an explicitly typed lambda, each lambda parameter type is the same
      // as the corresponding function type parameter type: ‹P_i = Q_i›. The
      // declared parameters are read directly from the lambda (rather than via a
      // detached tree copy) so their type accesses resolve in the enclosing scope.
      List<ParameterDeclaration> declared =
          ((DeclaredLambdaParameters) lambdaParams).getParameterList();
      for (int i = 0; i < function.getNumParameter(); i++) {
        TypeDecl P = declared.getChild(i).type();
        TypeDecl Q = function.getParameter(i).type();
        constraintEqual(P, Q);
      }
    } else {
      // For an implicitly typed lambda, the constraint reduces to false if any
      // of the target function parameter types is not a proper type.
      for (int i = 0; i < function.getNumParameter(); i++) {
        if (function.getParameter(i).type().involvesTypeParameters()) {
          satisfiable = false;
          return;
        }
      }
    }
    TypeDecl R = function.type();
    if (R.isVoid()) {
      // A void result places no compatibility constraint on the body.
      return;
    }
    // The result type is a (non-void) type R, so each result expression e of the
    // lambda body yields a constraint ‹e → R›.
    for (Expr result : lambdaResultExpressions(expr.getLambdaBody())) {
      constraintExprCompat(result, R);
    }
  }

  /** The result expressions of a lambda body (§15.27.2). */
  private static Collection<Expr> lambdaResultExpressions(LambdaBody body) {
    if (body instanceof ExprLambdaBody) {
      return Collections.singleton(((ExprLambdaBody) body).getExpr());
    } else if (body instanceof BlockLambdaBody) {
      Collection<Expr> result = new ArrayList<Expr>();
      for (ReturnStmt ret : ((BlockLambdaBody) body).lambdaReturns()) {
        if (ret.hasResult()) {
          result.add(ret.getResult());
        }
      }
      return result;
    }
    return Collections.<Expr>emptySet();
  }

  /**
   * Reduce a method reference compatibility constraint ‹MethodReference → T›
   * where T mentions inference variables (§18.2.1).
   */
  private void constraintReferenceCompat(MethodReference expr, TypeDecl T) {
    if (!T.hasFunctionDescriptor()) {
      satisfiable = false;
      return;
    }
    FunctionDescriptor fd = T.functionDescriptor();
    if (isProperType(T)) {
      // No inference variables in T: a plain congruence check is sufficient.
      if (!expr.congruentTo(fd)) {
        satisfiable = false;
      }
      return;
    }
    // T mentions inference variables. The descriptor parameter types identify the
    // referenced method; reduce the constraint ‹R' → R› where R' is the referenced
    // method's result and R is the descriptor result type (§18.2.1).
    TypeDecl ftype = fd.method.type();
    if (ftype.isVoid()) {
      return;
    }
    TypeDecl referencedResult = expr.invocationType(fd);
    if (referencedResult.isVoid()) {
      satisfiable = false;
      return;
    }
    if (!referencedResult.isUnknown()) {
      constraintTypeCompat(referencedResult, ftype);
      return;
    }
    MethodAccess invoc = expr.implicitInvocation(fd);
    if (invoc == null || !invoc.reduceInvocationBounds(this, ftype)) {
      satisfiable = false;
    }
  }

  /**
   * Reduce a constructor reference compatibility constraint
   * ‹ConstructorReference → T› where T mentions inference variables (§18.2.1).
   */
  private void constraintReferenceCompat(ConstructorReference expr, TypeDecl T) {
    if (!T.hasFunctionDescriptor()) {
      satisfiable = false;
      return;
    }
    FunctionDescriptor fd = T.functionDescriptor();
    if (isProperType(T)) {
      // No inference variables in T: a plain congruence check is sufficient.
      if (!expr.congruentTo(fd)) {
        satisfiable = false;
      }
      return;
    }
    // T mentions inference variables. Reduce the constraint ‹R' → R› where R' is
    // the constructed type and R is the descriptor result type (§18.2.1).
    TypeDecl descriptorResult = fd.method.type();
    if (descriptorResult.isVoid()) {
      return;
    }
    TypeDecl referencedResult = expr.invocationType(fd);
    if (referencedResult.isUnknown() || referencedResult.isVoid()) {
      satisfiable = false;
      return;
    }
    constraintTypeCompat(referencedResult, descriptorResult);
  }

  /**
   * A type T is a proper type if it does not involve unresolved inference variables.
   */
  private boolean isProperType(TypeDecl T) {
    if (T instanceof TypeVariable) {
      // NOTE(joqvist): The definition of proper types is a bit vague:
      // JLS SE8 §18.1 says that proper types excludes types that mention inference variables.
      // However, for this to work one would have to consider inference variables that have been instantiated
      // to be replaced in all occurrences with their instantiated type. We do not replace
      // inference variables, instead we check if they have been instantiated to a proper type.
      VariableBounds cs = map.get(T);
      if (cs == null) {
        // A capture variable outside this bound set is a proper type.
        return T instanceof CaptureVariable;
      }
      return cs.fresh || (cs.inst != null && isProperType(cs.inst)); // NOTE(joqvist): is this recursion guaranteed bounded?
    }
    if (T instanceof ArrayDecl) {
      return isProperType(((ArrayDecl) T).componentType());
    }
    if (T instanceof ParTypeDecl) {
      for (TypeDecl arg : ((ParTypeDecl) T).getParameterization().args) {
        if (!isProperType(arg)) {
          return false;
        }
      }
      return true;
    }
    if (T instanceof WildcardExtendsType) {
      return isProperType(((WildcardExtendsType) T).extendsType());
    }
    if (T instanceof WildcardSuperType) {
      return isProperType(((WildcardSuperType) T).superType());
    }
    return true;
  }

  /** Test if {@code T} is one of the inference variables of this bound set. */
  public boolean isInferenceVariable(TypeDecl T) {
    if (!(T instanceof TypeVariable)) return false;
    VariableBounds cs = map.get((TypeVariable) T);
    return cs != null && !cs.fresh;
  }

  /**
   * Test if {@code S} is compatible with {@code T} in a loose invocation context (§5.3).
   */
  private static boolean looseInvocationCompatible(TypeDecl S, TypeDecl T) {
    return S.methodInvocationConversionTo(T) || S.boxed().withinBounds(T);
  }

  /**
   * Reduce a type compatibility constraint {@code ‹S → T›} (§18.2.2).
   */
  public void constraintTypeCompat(TypeDecl S, TypeDecl T) {
    // If S and T are proper types, the constraint reduces to true if S is
    // compatible in a loose invocation context with T (§5.3), and false
    // otherwise.
    if (isProperType(S) && isProperType(T)) {
      if (!looseInvocationCompatible(substituted(S), substituted(T))) {
        satisfiable = false;
      }
      return;
    }
    // Otherwise, if S is a primitive type, let S' be the result of applying boxing
    // conversion to S. The constraint reduces to ‹S' → T›.
    if (S.isUnboxedPrimitive()) {
      constraintTypeCompat(S.boxed(), T);
      return;
    }
    // Otherwise, if T is a primitive type, let T' be the result of applying boxing
    // conversion to T. The constraint reduces to ‹S = T'›.
    if (T.isUnboxedPrimitive()) {
      constraintEqual(S, T.boxed());
      return;
    }
    // TODO(joqvist): the cases where T is a parameterized or array type whose
    // only supertype of S is raw reduce directly to true (unchecked conversion).
    // Falling through to the subtype rule below records the raw access instead.
    // Otherwise, the constraint reduces to ‹S <: T›.
    constraintSubtype(S, T);
  }

  /**
   * Reduce a subtyping constraint {@code ‹S <: T›} (§18.2.3).
   */
  public void constraintSubtype(TypeDecl S, TypeDecl T) {
    // If S and T are proper types, the constraint reduces to true if S is a
    // subtype of T (§4.10), and false otherwise.
    if (isProperType(S) && isProperType(T)) {
      if (!substituted(S).subtype(substituted(T))) {
        satisfiable = false;
      }
      return;
    }
    // Otherwise, if S is the null type, the constraint reduces to true.
    if (S.isNull()) {
      return;
    }
    // Otherwise, if T is the null type, the constraint reduces to false.
    if (T.isNull()) {
      satisfiable = false;
      return;
    }
    // Otherwise, if S is an inference variable α, the constraint reduces to α <: T.
    if (isInferenceVariable(S)) {
      addUpperBound(S, T);
      return;
    }
    // Otherwise, if T is an inference variable α, the constraint reduces to S <: α.
    if (isInferenceVariable(T)) {
      addLowerBound(T, S);
      return;
    }
    // Otherwise, the constraint is reduced according to the form of T.
    if (T.isArrayDecl()) {
      // If T is an array type T'[]: if S is an array type S'[] (or a type variable
      // with such an upper bound) then the constraint reduces to ‹S' <: T'› if both
      // component types are reference types, and to ‹S' = T'› otherwise.
      TypeDecl Tc = T.componentType();
      if (S.isArrayDecl()) {
        TypeDecl Sc = S.componentType();
        // Both component types reference types: reduce to subtyping; otherwise to
        // equality. isUnboxedPrimitive distinguishes a genuine primitive component
        // (e.g. int[]) from a boxed wrapper (e.g. Long[], a reference type).
        if (Sc.isUnboxedPrimitive() || Tc.isUnboxedPrimitive()) {
          constraintEqual(Sc, Tc);
        } else {
          constraintSubtype(Sc, Tc);
        }
      } else {
        // S is not an array type (inference variables are handled above).
        // TODO(joqvist): also allow S to have array type upper bound (§18.2.3)
        satisfiable = false;
      }
      return;
    }
    if (T instanceof ParTypeDecl && !T.isRawType()) {
      // T is a parameterized type G<A1, ..., Tn>.
      ParTypeDecl PT = (ParTypeDecl) T;
      TypeDecl G = PT.genericDecl();
      // Among supertypes of S find U = G<B1, ..., Bn>.
      ParTypeDecl U = parameterizedSupertype(S, G);
      if (U == null) {
        if (G instanceof GenericTypeDecl
            && S.subtype(((GenericTypeDecl) G).rawType())) {
          // TODO(joqvist): this part deviates from JLS
          // If S only has G as a raw supertype the access is unchecked,
          // In Java 8, unchecked conversion is necessary for applicability only
          // when it is needed to reduce an argument compatibility constraint
          // ‹ei → Fi› (§18.5.1). A subtyping constraint derived by incorporating a
          // pair of bounds (§18.3.1) does not count. A raw argument reaching a type
          // parameter's declared bound is not counted as unchecked conversion.
          //
          // Java 9 extended the unchecked determination to the bound as well, so from
          // Java 9 onwards such an invocation is unchecked and its invocation type
          // is erased (§18.5.2).
          if (!incorporating || ASTNode.JAVA_VERSION >= 9) {
            rawAccess = true;
          }
        } else {
          // otherwise reduce to ‹false›.
          satisfiable = false;
        }
        return;
      }
      java.util.List<TypeDecl> sArgs = U.getParameterization().args;
      java.util.List<TypeDecl> tArgs = PT.getParameterization().args;
      for (int i = 0; i < tArgs.size(); i++) {
        constraintContainedIn(sArgs.get(i), tArgs.get(i));
      }
      return;
    }
    if (T instanceof TypeVariable) {
      // T is a type variable that is not an inference variable of this bound set.
      // TODO(joqvist): handle intersection-type T (§18.2.3).
      deferred.add(new DeferredConstraint(DeferredConstraint.SUBTYPE, S, T));
      return;
    }
    // T is a raw or non-generic class or interface type.
    // The constraint holds only if a supertype of S is T.
    if (!S.erasure().subtype(T)) {
      satisfiable = false;
    }
  }

  /**
   * Reduce a type containment constraint {@code ‹S <= T›}: type argument S is
   * contained by type argument T (§18.2.3).
   */
  public void constraintContainedIn(TypeDecl S, TypeDecl T) {
    if (!T.isWildcard()) {
      // If T is a type: ‹S = T› if S is a type, false if S is a wildcard.
      if (S.isWildcard()) {
        // Capture conversion is applied to the types entering the inference, not
        // to the arguments of a nested parameterized type, so a wildcard S here
        // reduces to false.
        if (!isInferenceVariable(T) && T instanceof TypeVariable) {
          // T may be an inference variable of an enclosing bound set into which this
          // set is lifted (§18.5.2); defer the containment so it is reduced there.
          deferred.add(new DeferredConstraint(DeferredConstraint.CONTAINED, S, T));
        } else {
          satisfiable = false;
        }
      } else {
        constraintEqual(S, T);
      }
      return;
    }
    if (T.isUnboundedWildcard()) {
      // If T is the unbound wildcard, the constraint reduces to true.
      return;
    }
    if (T instanceof WildcardExtendsType) {
      // T is ‹? extends T'›.
      TypeDecl Tp = ((WildcardExtendsType) T).extendsType();
      if (!S.isWildcard()) {
        constraintSubtype(S, Tp);
      } else if (S.isUnboundedWildcard()) {
        constraintSubtype(T.typeObject(), Tp);
      } else if (S instanceof WildcardExtendsType) {
        constraintSubtype(((WildcardExtendsType) S).extendsType(), Tp);
      } else { // S is ‹? super S'›.
        constraintEqual(T.typeObject(), Tp);
      }
      return;
    }
    if (T instanceof WildcardSuperType) {
      // T is ‹? super T'›.
      TypeDecl Tp = ((WildcardSuperType) T).superType();
      if (!S.isWildcard()) {
        constraintSubtype(Tp, S);
      } else if (S instanceof WildcardSuperType) {
        constraintSubtype(Tp, ((WildcardSuperType) S).superType());
      } else {
        satisfiable = false;
      }
    }
  }

  /**
   * Reduce an equality constraint {@code ‹S = T›} where S and T are types, type
   * arguments, and/or inference variables (§18.2.4).
   */
  public void constraintEqual(TypeDecl S, TypeDecl T) {
    // Type-argument equality: at least one operand is a wildcard.
    if (S.isWildcard() || T.isWildcard()) {
      if (S.isUnboundedWildcard() && T.isUnboundedWildcard()) {
        // ‹? = ?› reduces to true.
      } else if (S instanceof WildcardExtendsType && T instanceof WildcardExtendsType) {
        constraintEqual(((WildcardExtendsType) S).extendsType(),
            ((WildcardExtendsType) T).extendsType());
      } else if (S instanceof WildcardSuperType && T instanceof WildcardSuperType) {
        constraintEqual(((WildcardSuperType) S).superType(),
            ((WildcardSuperType) T).superType());
      } else {
        satisfiable = false;
      }
      return;
    }
    // If S and T are proper types, the constraint reduces to true if S is the same
    // as T (§4.3.4), and false otherwise.
    if (isProperType(S) && isProperType(T)) {
      if (substituted(S) != substituted(T)) {
        satisfiable = false;
      }
      return;
    }
    // Otherwise, if S or T is the null type, the constraint reduces to false.
    if (S.isNull() || T.isNull()) {
      satisfiable = false;
      return;
    }
    // Otherwise, if S is an inference variable α, and T is not a primitive type,
    // the constraint reduces to the bound α = T. The genuine-primitive test
    // isUnboxedPrimitive is used rather than isPrimitive, which in ExtendJ is also
    // true for the boxed wrapper types (a valid type argument such as Integer).
    if (isInferenceVariable(S) && !T.isUnboxedPrimitive()) {
      addEqualBound(S, T);
      return;
    }
    // Otherwise, if T is an inference variable α, and S is not a primitive type,
    // the constraint reduces to the bound S = α.
    if (isInferenceVariable(T) && !S.isUnboxedPrimitive()) {
      addEqualBound(T, S);
      return;
    }
    // Otherwise, if S and T are class or interface types with the same erasure,
    // the constraint reduces to equality of the corresponding type arguments.
    if (S instanceof ParTypeDecl && !S.isRawType()
        && T instanceof ParTypeDecl && !T.isRawType()
        && ((ParTypeDecl) S).genericDecl() == ((ParTypeDecl) T).genericDecl()) {
      java.util.List<TypeDecl> sArgs = ((ParTypeDecl) S).getParameterization().args;
      java.util.List<TypeDecl> tArgs = ((ParTypeDecl) T).getParameterization().args;
      for (int i = 0; i < sArgs.size(); i++) {
        constraintEqual(sArgs.get(i), tArgs.get(i));
      }
      return;
    }
    // Otherwise, if S and T are array types, S'[] and T'[], the constraint reduces
    // to ‹S' = T'›.
    if (S.isArrayDecl() && T.isArrayDecl()) {
      constraintEqual(S.componentType(), T.componentType());
      return;
    }
    // One side is a type variable that is not an inference variable of this bound
    // set. It may be an inference variable of an enclosing bound set into which this
    // set will be lifted (§18.5.2); defer the constraint so it can be reduced
    // there. A genuine (non-inference) type variable is handled leniently when the
    // deferred constraint is eventually replayed at the outermost set.
    if (S instanceof TypeVariable || T instanceof TypeVariable) {
      deferred.add(new DeferredConstraint(DeferredConstraint.EQUAL, S, T));
      return;
    }
    // Otherwise, the constraint reduces to false.
    satisfiable = false;
  }

  /**
   * Checked exception constraint {@code ‹Expression → throws T›} (§18.5.2).
   */
  public void constraintCheckedThrows(Expr expr, TypeDecl T) {
    if (expr instanceof ParExpr) {
      constraintCheckedThrows(((ParExpr) expr).getExpr(), T);
    } else if (expr instanceof ConditionalExpr) {
      ConditionalExpr cond = (ConditionalExpr) expr;
      constraintCheckedThrows(cond.getTrueExpr(), T);
      constraintCheckedThrows(cond.getFalseExpr(), T);
    } else if (expr instanceof LambdaExpr) {
      LambdaExpr lambda = (LambdaExpr) expr;
      if (lambda.isImplicit() && !lambda.hasProperParameterTypes(T)) {
        // The lambda body still cannot be typed.
        // TODO(joqvist): set satisfiable = false?
        return;
      }
      constraintCheckedThrows(lambda, T);
    } else if (expr instanceof MethodReference) {
      MethodReference ref = (MethodReference) expr;
      if (!ref.isExact() && !ref.hasProperParameterTypes(T)) {
        // The method reference still cannot be resolved.
        // TODO(joqvist): set satisfiable = false?
        return;
      }
      constraintCheckedThrows(ref, T);
    }
  }

  /**
   * Checked exception bound {@code ‹LambdaExpr →throws T›} (§18.2.5).
   */
  public void constraintCheckedThrows(LambdaExpr expr, TypeDecl T) {
    // If T is not a functional interface type, the constraint reduces to false.
    if (!T.hasFunctionDescriptor()) {
      satisfiable = false;
      return;
    }
    FunctionDescriptor fd = T.functionDescriptor();
    MethodDecl function = fd.method;
    if (!isProperType(function.type())) {
      // TODO(joqvist): §18.2.5 reduces to false here. We do not because the return type
      // might still become proper after further constraint reduction. Fix me?
      return;
    }
    // Grounded lambda used here to get the thrown types with substitution θ.
    LambdaBody body = expr.isImplicit()
        ? expr.groundedLambda(T).getLambda().getLambdaBody()
        : expr.getLambdaBody();
    // TODO(joqvist): replace collectExceptions() with a collection attribute.
    Collection<TypeDecl> thrown = new HashSet<TypeDecl>();
    body.collectExceptions(thrown);
    ArrayList<TypeDecl> thrownTypes = new ArrayList<TypeDecl>();
    for (TypeDecl X : thrown) {
      if (X.isCheckedException() && body.reachedException(X)) {
        thrownTypes.add(X);
      }
    }
    reduceCheckedThrows(thrownTypes, fd.throwsList);
  }

  /**
   * Reduce a checked exception constraint given the checked exception types
   * {@code thrownTypes} that the lambda body or referenced method can throw,
   * checked against the target function type's throws clause (§18.2.5).
   */
  private void reduceCheckedThrows(Collection<TypeDecl> thrownTypes, Collection<TypeDecl> throwsList) {
    // Split types in the function type's throws clause into two sets (proper/not proper types):
    ArrayList<TypeDecl> properThrows = new ArrayList<TypeDecl>();
    ArrayList<TypeDecl> nonProperThrows = new ArrayList<TypeDecl>();
    for (TypeDecl thrownType : throwsList) {
      if (isProperType(thrownType)) {
        properThrows.add(thrownType);
      } else {
        nonProperThrows.add(thrownType);
      }
    }
    for (TypeDecl X : thrownTypes) {
      if (!subtypeOfAny(X, properThrows)) {
        if (nonProperThrows.isEmpty()) {
          satisfiable = false;
          return;
        }
        for (TypeDecl E : nonProperThrows) {
          constraintSubtype(X, E);
        }
      }
    }
    for (TypeDecl E : nonProperThrows) {
      if (isInferenceVariable(E)) {
        lookup(E).hasThrowsBound = true;
      }
    }
  }

  /** Test if {@code type} is a subtype of some type in {@code types}. */
  private static boolean subtypeOfAny(TypeDecl type, Collection<TypeDecl> types) {
    for (TypeDecl T : types) {
      if (type.subtype(T)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checked exception bound {@code ‹MethodReference →throws T›} (§18.2.5).
   */
  public void constraintCheckedThrows(MethodReference expr, TypeDecl T) {
    // If T is not a functional interface type, the constraint reduces to false.
    if (!T.hasFunctionDescriptor()) {
      satisfiable = false;
      return;
    }
    FunctionDescriptor fd = T.functionDescriptor();
    MethodDecl function = fd.method;
    if (function.isUnknown()) {
      // Target method not identified in this functional interface.
      // Error reported elsewhere.
      // TODO(joqvist): should we set satisfiable = false here?
      return;
    }
    if (!isProperType(function.type())) {
      // TODO(joqvist): §18.2.5 reduces to false here. We do not because the return type
      // might still become proper after further constraint reduction. Fix me?
      return;
    }
    MethodDecl invoked = expr.compileTimeDeclaration(fd);
    if (invoked.isUnknown()) {
      // No unique invoked method was found. The incompatible method reference
      // is instead reported by the compatibility constraint.
      return;
    }
    ArrayList<TypeDecl> thrownTypes = new ArrayList<TypeDecl>();
    for (int i = 0; i < invoked.getNumException(); i++) {
      TypeDecl X = invoked.getException(i).type();
      if (X.isCheckedException()) {
        thrownTypes.add(X);
      }
    }
    reduceCheckedThrows(thrownTypes, fd.throwsList);
  }

  /**
   * Incorporate a single bound, deriving constraints implied by
   * pairs of bounds on a shared inference variable (§18.3.1).
   *
   * <p>An equality between two inference variables is recorded symmetrically so
   * that rules propagate bounds in both directions.
   */
  private boolean incorporate(Bound bound) {
    TypeVariable alpha = bound.alpha;
    TypeDecl S = bound.type;
    switch (bound.kind) {
      case EQUAL:
        {
        VariableBounds set = lookup(bound.alpha);
        if (isInferenceVariable(S)) {
          addEqualBound(S, alpha);    // reflexivity
        }
        for (TypeDecl T : set.equal) {
          if (S != T) {
            constraintEqual(S, T);    // α = S, α = T  ⟹  ‹S = T›
          }
        }
        for (TypeDecl T : set.upper) {
          constraintSubtype(S, T);    // α = S, α <: T ⟹  ‹S <: T›
        }
        for (TypeDecl T : set.lower) {
          constraintSubtype(T, S);    // α = S, T <: α ⟹  ‹T <: S›
        }
        if (!satisfiable) {
          return false;
        }
        }
        break;
      case UPPER:
        {
        VariableBounds set = lookup(bound.alpha);
        for (TypeDecl T : set.equal) {
          constraintSubtype(T, S);    // α = T, α <: S  ⟹  ‹T <: S›
        }
        for (TypeDecl T : set.lower) {
          constraintSubtype(T, S);    // T <: α, α <: S ⟹  ‹T <: S›
        }
        }
        break;
      case LOWER:
        {
        VariableBounds set = lookup(bound.alpha);
        for (TypeDecl T : set.equal) {
          constraintSubtype(S, T);    // α = T, S <: α  ⟹  ‹S <: T›
        }
        for (TypeDecl T : set.upper) {
          constraintSubtype(S, T);    // S <: α, α <: T ⟹  ‹S <: T›
        }
        }
        break;
      case CAPTURE:
        {
          // Incorporate bounds involving capture conversion (§18.3.2)
          CaptureBound cap = (CaptureBound) bound;
          GenericTypeDecl original = (GenericTypeDecl) ((ParTypeDecl) cap.baseType).genericDecl();
          Map<TypeVariable, TypeDecl> theta = new HashMap<>();
          for (int i = 0; i < original.getNumTypeParameter(); ++i) {
            TypeVariable Pi = original.getTypeParameter(i);
            TypeDecl ai = cap.lhs.get(i);
            theta.put(Pi, ai);
          }
          for (int i = 0; i < cap.lhs.size(); ++i) {
            TypeDecl ai = cap.lhs.get(i);
            TypeDecl Ai = cap.rhs.get(i);
            TypeVariable Pi = original.getTypeParameter(i);
            TypeDecl Bi = Pi.firstBound().type();
            VariableBounds aiBounds = lookup(ai);
            if (Ai instanceof WildcardType) {
              for (TypeDecl R : aiBounds.equal) {
                if (isProperType(R)) {
                  satisfiable = false; // αi = R implies the bound false
                  break;
                }
              }
              for (TypeDecl R : aiBounds.upper) {
                if (isProperType(R)) {
                  constraintSubtype(Bi.substituted(theta), R); // αi <: R implies ‹Bi θ <: R›
                }
              }
              for (TypeDecl R : aiBounds.lower) {
                if (isProperType(R)) {
                  satisfiable = false; // R <: αi implies the bound false
                  break;
                }
              }
            } else if (Ai instanceof WildcardExtendsType) {
              for (TypeDecl R : aiBounds.equal) {
                if (isProperType(R)) {
                  satisfiable = false; // αi = R implies the bound false
                  break;
                }
              }
              TypeDecl T = ((WildcardExtendsType) Ai).extendsType();
              for (TypeDecl R : aiBounds.upper) {
                if (isProperType(R)) {
                  if (Bi == Bi.typeObject()) {
                    constraintSubtype(T, R); // If Bi is Object, αi <: R implies ‹T <: R›
                  }
                  if (T == T.typeObject()) {
                    constraintSubtype(Bi.substituted(theta), R); // If T is Object, αi <: R implies ‹Bi θ <: R›
                  }
                }
              }
              for (TypeDecl R : aiBounds.lower) {
                if (isProperType(R)) {
                  satisfiable = false; // R <: αi implies the bound false
                  break;
                }
              }
            } else if (Ai instanceof WildcardSuperType) {
              TypeDecl T = ((WildcardSuperType) Ai).superType();
              for (TypeDecl R : aiBounds.equal) {
                if (isProperType(R)) {
                  satisfiable = false; // αi = R implies the bound false
                  break;
                }
              }
              for (TypeDecl R : aiBounds.upper) {
                if (isProperType(R)) {
                  constraintSubtype(Bi.substituted(theta), R); // αi <: R implies ‹Bi θ <: R›
                }
              }
              for (TypeDecl R : aiBounds.lower) {
                if (isProperType(R)) {
                  constraintSubtype(R, T); // R <: αi implies ‹R <: T›
                }
              }
            }
          }
          break;
        }
    }
    return satisfiable;
  }

  private void addBound(Bound bound) {
    if (incorporating) {
      // Recursive case.
      newBounds.add(bound);
      return;
    }

    // Start a new fixpoint bound incorporation loop.
    incorporating = true;
    try {
      newBounds.clear();
      ArrayList<Bound> back = new ArrayList<>();
      incorporate(bound);
      do {
        ArrayList<Bound> swap = newBounds;
        newBounds = back;
        newBounds.clear();
        back = swap;
        for (Bound b : back) {
          if (!incorporate(b)) {
            break;
          }
        }
      } while (satisfiable && !newBounds.isEmpty());
    } finally {
      incorporating = false;
    }
  }

  /**
   * Add the equality bound {@code α = T} where {@code S} is the inference
   * variable α and {@code T} is a proper type or another inference variable (§18.1.3).
   */
  private void addEqualBound(TypeDecl S, TypeDecl T) {
    if (S == T) return;
    VariableBounds set = lookup(S);
    if (set.equal.add(T)) {
      if (isProperType(T)) {
        // As soon as we add an equality bound to a proper type T
        // we have an instantiation of the inference variable (§18.1.3).
        set.inst = properBound(T);
      }
      addBound(new Bound(Bound.Kind.EQUAL, (TypeVariable) S, T));
    }
  }

  /**
   * Add the subtype bound {@code α <: T} where {@code alpha} is the inference
   * variable α (§18.1.3).
   */
  private void addUpperBound(TypeDecl alpha, TypeDecl T) {
    if (alpha == T) return;
    if (lookup(alpha).upper.add(T)) {
      addBound(new Bound(Bound.Kind.UPPER, (TypeVariable) alpha, T));
    }
  }

  /**
   * Add the supertype bound {@code α >: S}, recorded as the lower bound S of the
   * inference variable α (§18.1.3).
   */
  private void addLowerBound(TypeDecl alpha, TypeDecl S) {
    if (alpha == S) return;
    if (lookup(alpha).lower.add(S)) {
      addBound(new Bound(Bound.Kind.LOWER, (TypeVariable) alpha, S));
    }
  }

  /**
   * The capture bound must be fully constructed before this call because its
   * identity is content-based.
   */
  private void addCaptureBound(CaptureBound cap) {
    if (captureBounds.add(cap)) {
      addBound(cap);
    }
  }

  /**
   * Incorporate the constraint formula {@code ‹G<β1, ..., βn> → T›} and
   * add the capture bound {@code G<β1, ...., βn> = capture(G<A1, ...., An>)}.
   */
  public void maybeCaptureBound(TypeDecl R, TypeDecl T) {
    Parameterization par = ((ParTypeDecl) R).getParameterization();
    // Create a parameterization of G with fresh variables β1, ..., βn.
    // If there are no wildcards fall back to normal type compatibility constraint.
    boolean haveWildcard = false;
    for (TypeDecl arg : par.args) {
      if (arg.isWildcard()) {
        haveWildcard = true;
      }
    }
    if (!haveWildcard) {
      constraintTypeCompat(R, T);
      return;
    }
    CaptureBound cap = new CaptureBound(R);
    ArrayList<TypeDecl> fresh = new ArrayList<>();
    List<FreshVariable> freshVars = context.lookupFreshVars(
        context, new ArrayList<>(par.args), new ArrayList<>(), new ArrayList<>());
    for (int i = 0; i < par.args.size(); ++i) {
      TypeDecl arg = par.args.get(i);
      FreshVariable beta = freshVars.getChild(i);
      VariableBounds cs = new VariableBounds();
      cs.captureBound = cap;
      map.put(beta, cs);
      auxiliaryVariables.add(beta);
      fresh.add(beta);
      if (!arg.isWildcard()) {
        addEqualBound(beta, arg); // §18.3.2
      }
      cap.lhs.add(beta);
      cap.rhs.add(arg);
    }
    TypeDecl G = ((GenericTypeDecl) ((ParTypeDecl) R).genericDecl()).lookupParTypeDecl(fresh);
    constraintTypeCompat(G, T);
    addCaptureBound(cap);
  }

  /**
   * The parameterized supertype of {@code type} whose generic declaration is
   * {@code genericDecl}, or {@code null} if there is none.
   */
  private static ParTypeDecl parameterizedSupertype(TypeDecl type, TypeDecl genericDecl) {
    for (ParTypeDecl sup : parameterizedSupertypes(type)) {
      if (sup.genericDecl() == genericDecl) {
        return sup;
      }
    }
    return null;
  }
}
