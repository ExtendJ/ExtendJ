package org.extendj.ast;

import java.util.*;

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
  /**
   * Whether the bound set is still satisfiable
   * (the false constraint has not been added).
   */
  public boolean satisfiable = true;

  /** Set of type bounds for inferred type variables. */
  static class ConstraintSet {
    /** Lower type bounds. */
    public Collection<TypeDecl> lower = new HashSet<>(4);

    /** Upper type bounds. */
    public Collection<TypeDecl> upper = new HashSet<>(4);

    /** Equal type bounds. */
    public Collection<TypeDecl> equal = new HashSet<>(4);

    /**
     * Whether the bound {@code throws α} was added for the inference variable
     * (§18.1.3).
     *
     * <p>Make inference prefer an unchecked exception type instantiation (§18.4).
     */
    public boolean hasThrowsBound = false;

    /**
     * The captured type to use as type argument.
     *
     * <p>This is {@code null} before inference starts and if no type matches the bounds.
     */
    public TypeDecl capture;
  }

  static final ConstraintSet EMPTY_CONSTRAINT_SET = new ConstraintSet();

  /**
   * Inference variables whose instantiations are the inferred type arguments of
   * this bound set.
   */
  private Collection<TypeVariable> variables;

  /**
   * Inference variables incorporated from other bound sets during merging
   * (§18.3). They participate in resolution but are not result type arguments.
   */
  private Collection<TypeVariable> auxiliaryVariables;

  protected Map<TypeVariable, ConstraintSet> map;

  ConstraintSet lookup(TypeDecl v) {
    return map.getOrDefault(v, EMPTY_CONSTRAINT_SET);
  }

  public boolean rawAccess = false;

  /**
   * Whether unchecked conversion was necessary for the method to be
   * applicable (§18.5.1). The invocation type is then the erasure of the
   * method type (§18.5.2), so the inferred type arguments are not used.
   */
  public boolean uncheckedConversion = false;

  /**
   * A constraint that could not be reduced because it mentions a type variable
   * not local to this bound set (an inference variable of an enclosing bound set).
   * It is deferred and replayed when this bound set is lifted into the enclosing
   * one (§18.5.2). */
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
  private java.util.List<DeferredConstraint> deferred = new java.util.ArrayList<DeferredConstraint>(0);

  public BoundSet() {
    variables = new ArrayList<>(4);
    auxiliaryVariables = new ArrayList<>(0);
    map = new HashMap<>();
  }

  public void addTypeVariable(TypeVariable T) {
    if (!variables.contains(T)) {
      variables.add(T);
      map.put(T, new ConstraintSet());
    }
  }

  /**
   * Register an inference variable incorporated from another bound set so that it
   * participates in resolution without becoming a result type argument.
   */
  private void addAuxiliaryVariable(TypeVariable T) {
    if (!variables.contains(T) && !auxiliaryVariables.contains(T)) {
      auxiliaryVariables.add(T);
      map.put(T, new ConstraintSet());
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
    for (TypeVariable var : set.variables) {
      addAuxiliaryVariable(var);
    }
    for (TypeVariable var : set.auxiliaryVariables) {
      addAuxiliaryVariable(var);
    }
    for (Map.Entry<TypeVariable, ConstraintSet> entry : set.map.entrySet()) {
      TypeVariable var = entry.getKey();
      ConstraintSet incoming = entry.getValue();
      if (incoming.hasThrowsBound) {
        lookup(var).hasThrowsBound = true;
      }
      for (TypeDecl T : incoming.equal) {
        constraintEqual(var, T);
      }
      for (TypeDecl T : incoming.upper) {
        constraintSubtype(var, T);
      }
      for (TypeDecl T : incoming.lower) {
        constraintSubtype(T, var);
      }
    }
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

  public String toString() {
    StringBuilder str = new StringBuilder();
    for (TypeVariable T : variables) {
      ConstraintSet set = lookup(T);
      for (TypeDecl U : set.lower) {
        if (str.length() > 0) {
          str.append("\n");
        }
        str.append(T.fullName() + " :> " + U.fullName());
      }
      for (TypeDecl U : set.upper) {
        if (str.length() > 0) {
          str.append("\n");
        }
        str.append(T.fullName() + " <: " + U.fullName());
      }
      for (TypeDecl U : set.equal) {
        if (str.length() > 0) {
          str.append("\n");
        }
        str.append(T.fullName() + " = " + U.fullName());
      }
    }
    return str.toString();
  }

  /**
   * Incorporate the bounds to a fixed point, deriving the constraints implied by
   * pairs of bounds on a shared inference variable (§18.3.1).
   *
   * <p>An equality between two inference variables is recorded symmetrically so
   * that rules propagate bounds in both directions.
   */
  private void incorporate() {
    int previousSize = -1;
    while (satisfiable) {
      int size = totalBoundCount();
      if (size == previousSize) {
        break;
      }
      previousSize = size;
      ArrayList<TypeVariable> vars = new ArrayList<TypeVariable>(allVariables());
      for (TypeVariable alpha : vars) {
        ConstraintSet set = lookup(alpha);
        // Snapshot each bound collection (mutated by calls below)
        ArrayList<TypeDecl> equal = new ArrayList<TypeDecl>(set.equal);
        ArrayList<TypeDecl> upper = new ArrayList<TypeDecl>(set.upper);
        ArrayList<TypeDecl> lower = new ArrayList<TypeDecl>(set.lower);
        for (TypeDecl S : equal) {
          if (isInferenceVariable(S)) {
            addEqualBound(S, alpha);    // α = β implies β = α
          }
          for (TypeDecl T : equal) {
            if (S != T) {
              constraintEqual(S, T);    // α = S, α = T  ⟹  ‹S = T›
            }
          }
          for (TypeDecl T : upper) {
            constraintSubtype(S, T);    // α = S, α <: T ⟹  ‹S <: T›
          }
          for (TypeDecl T : lower) {
            constraintSubtype(T, S);    // α = S, T <: α ⟹  ‹T <: S›
          }
          if (!satisfiable) {
            return;
          }
        }
        for (TypeDecl S : lower) {
          for (TypeDecl T : upper) {
            constraintSubtype(S, T);    // S <: α, α <: T ⟹  ‹S <: T›
          }
        }
        if (!satisfiable) {
          return;
        }
      }
    }
  }

  /** Total number of recorded bounds, used to detect incorporation fixed point. */
  private int totalBoundCount() {
    int count = 0;
    for (ConstraintSet set : map.values()) {
      count += set.equal.size() + set.upper.size() + set.lower.size();
    }
    return count;
  }

  /** Resolve the inference variables to instantiations (§18.4). */
  public boolean resolve() {
    // §18.4 specifies that variables are instantiated in an iterative fashion
    // where in each step a subset S ⊂ V is chosen and instantiated as a unit
    // where S is minimal non-empty sink SCC of uninstantiated inference
    // variables under inference variable dependencies.
    // We first select a set S and then incorporate bounds related to variables in S.
    incorporate();
    if (!satisfiable) {
      return false;
    }
    Collection<TypeVariable> all = allVariables();
    boolean change = true;
    while (change) {
      change = false;
      for (TypeVariable alpha : all) {
        ConstraintSet set = lookup(alpha);
        if (set.capture != null) {
          continue;
        }
        TypeDecl instantiation = instantiate(alpha, set);
        if (!satisfiable) {
          return false;
        }
        if (instantiation != null) {
          set.capture = instantiation;
          change = true;
        }
      }
    }
    // Instantiate any remaining variable to its declared first bound (typically
    // Object), matching the behavior when no constraints were recorded.
    for (TypeVariable alpha : all) {
      ConstraintSet set = lookup(alpha);
      if (set.capture == null) {
        set.capture = alpha.firstBound().type();
      }
    }
    checkDeferred();
    return satisfiable;
  }

  /** Strictly evaluate the deferred constraints (§18.5.2).
   */
  private void checkDeferred() {
    for (DeferredConstraint dc : deferred) {
      if (!satisfiable) {
        return;
      }
      TypeDecl S = properBound(dc.S);
      TypeDecl T = properBound(dc.T);
      if (S == null || T == null) {
        // Still mentions an uninstantiated inference variable; leave it deferred.
        continue;
      }
      if (dc.kind == DeferredConstraint.EQUAL
          && (isCaptureVariable(dc.S) || isCaptureVariable(dc.T))
          && isResolvedProperType(S) && isResolvedProperType(T)) {
        // A capture variable equated to a proper type is an over-constrained inference variable.
        // E.g., ‹capture = String› from a Box<?> argument and a conflicting Box<String> target.
        // Two resolved proper types are equal only if they are the same type (§18.2.4).
        if (S != T) {
          satisfiable = false;
        }
      }
    }
  }

  /**
   * Compute a candidate instantiation for the inference variable {@code alpha}
   * from its bounds (§18.4), or {@code null} if it cannot yet be resolved because
   * a bound still mentions an uninstantiated inference variable.
   */
  private TypeDecl instantiate(TypeVariable alpha, ConstraintSet set) {
    // An equality bound gives a direct instantiation and takes precedence (§18.4).
    for (TypeDecl bound : set.equal) {
      TypeDecl proper = properBound(bound);
      if (proper != null) {
        return proper;
      }
    }
    // An equality bound to a non-variable type that is not yet proper (e.g. S<β>
    // with β uninstantiated) must be deferred rather than falling back to the
    // lower/upper bounds.
    for (TypeDecl bound : set.equal) {
      if (!isInferenceVariable(bound)) {
        return null;
      }
    }
    // Otherwise, if α has lower bounds, the instantiation is their least upper bound (§4.10.4).
    if (!set.lower.isEmpty()) {
      ArrayList<TypeDecl> lower = properBounds(set.lower);
      return lower == null ? null : leastUpperBound(alpha, lower);
    }
    // Otherwise, if α has upper bounds, the instantiation is their greatest lower bound (§5.1.10).
    if (!set.upper.isEmpty()) {
      ArrayList<TypeDecl> upper = properBounds(set.upper);
      if (upper == null) {
        return null;
      }
      TypeDecl glb = greatestLowerBound(upper);
      // An ill-formed intersection (e.g. of two unrelated classes) has no
      // greatest lower bound, so the variable cannot be instantiated and the
      // bound set is unsatisfiable (§5.1.10, §18.4).
      if (glb.isUnknown()) {
        satisfiable = false;
        return null;
      }
      return glb;
    }
    return null;
  }

  /**
   * Resolve {@code bound} to a proper type: the bound itself if it is already
   * proper, or the instantiation of an inference variable bound. Returns
   * {@code null} if the bound still mentions an uninstantiated inference variable.
   */
  private TypeDecl properBound(TypeDecl bound) {
    // This is the substitution that isProperType() assumes has already happened, so the
    // test here does not rely on isProperType().
    if (!bound.involvesTypeParameters()) {
      return bound;
    }
    if (isInferenceVariable(bound)) {
      return lookup(bound).capture;
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
   * Resolve every bound to a proper type, or return {@code null} if any of them
   * still mentions an uninstantiated inference variable.
   */
  private ArrayList<TypeDecl> properBounds(Collection<TypeDecl> bounds) {
    ArrayList<TypeDecl> result = new ArrayList<TypeDecl>(bounds.size());
    for (TypeDecl bound : bounds) {
      TypeDecl proper = properBound(bound);
      if (proper == null) {
        return null;
      }
      result.add(proper);
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

  /**
   * Computes the direct supertypes of a type.
   */
  protected static Collection<TypeDecl> directSupertypes(TypeDecl T) {
    // TODO(joqvist): this should be an attribute of TypeDecl instead.
    if (T instanceof ClassDecl) {
      ClassDecl type = (ClassDecl) T;
      Collection<TypeDecl> set = new HashSet<TypeDecl>();
      if (type.hasSuperclass()) {
        set.add(type.superclass());
      }
      for (int i = 0; i < type.getNumImplements(); i++) {
        set.add(type.getImplements(i).type());
      }
      return set;
    } else if (T instanceof InterfaceDecl) {
      InterfaceDecl type = (InterfaceDecl) T;
      Collection<TypeDecl> set = new HashSet<TypeDecl>();
      for (int i = 0; i < type.getNumSuperInterface(); i++) {
        set.add(type.getSuperInterface(i).type());
      }
      return set;
    } else if (T instanceof TypeVariable) {
      TypeVariable type = (TypeVariable) T;
      Collection<TypeDecl> set = new HashSet<TypeDecl>();
      for (int i = 0; i < type.getNumTypeBound(); i++) {
        set.add(type.getTypeBound(i).type());
      }
      return set;
    } else {
      throw new Error(String.format(
            "Operation not supported for %s, %s",
            T.fullName(), T.getClass().getName()));
    }
  }

  /** Computes the parameterized supertypes of some type.  */
  protected static Collection<ParTypeDecl> parameterizedSupertypes(TypeDecl type) {
    // TODO(joqvist): this should be an attribute of TypeDecl instead.
    Collection<ParTypeDecl> result = new HashSet<ParTypeDecl>();
    addParameterizedSupertypes(type, new HashSet<TypeDecl>(), result);
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
      for (TypeDecl typeDecl : directSupertypes(type)) {
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
      list.add(lookup(T).capture);
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
      if (!expr.compatibleLooseContext(T)) {
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
      TypeDecl cap = lookup(T).capture;
      return cap != null && isProperType(cap); // NOTE(joqvist): is this recursion guaranteed bounded?
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

  /** Whether {@code T} is one of the inference variables of this bound set. */
  private boolean isInferenceVariable(TypeDecl T) {
    return T instanceof TypeVariable && map.containsKey(T);
  }

  /**
   * Whether S is compatible with T in a loose invocation context (§5.3), used to
   * decide the proper-type case of a type compatibility constraint.
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
      if (!looseInvocationCompatible(S, T)) {
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
      if (!S.subtype(T)) {
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
          rawAccess = true;
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
      if (S != T) {
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
   * Whether {@code T} is a resolved proper type for the purpose of the deferred
   * equality check: a concrete (non-type-variable) type or a wildcard capture
   * variable (§5.1.10), which denotes a fixed captured type. A declared type
   * parameter is not resolved. In a deferred constraint it is an unresolved
   * placeholder for an inference variable.
   */
  private static boolean isResolvedProperType(TypeDecl T) {
    return !(T instanceof TypeVariable) || isCaptureVariable(T);
  }

  /** Whether {@code T} is a wildcard capture variable (§5.1.10). */
  private static boolean isCaptureVariable(TypeDecl T) {
    return T instanceof CaptureVariable;
  }

  /**
   * Checked exception constraint {@code ‹Expression →throws T›} (§18.5.2).
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
        // TODO(joqvist): This constraint is reduced too eagerly now.
        // We need dependency-based constraint resolution.
        return;
      }
      constraintCheckedThrows(lambda, T);
    } else if (expr instanceof MethodReference) {
      MethodReference ref = (MethodReference) expr;
      if (!ref.isExact() && !ref.hasProperParameterTypes(T)) {
        // TODO(joqvist): This constraint is reduced too eagerly now.
        // We need dependency-based constraint resolution.
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
      // The function type's return type is neither void nor a proper type.
      // §18.2.5 reduces the constraint to false in this case, but we
      // cannot do this because we have reduced constraints eagerly.
      // TODO(joqvist): we need dependency-based constraint resolution.
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

  /** Whether {@code type} is a subtype of some type in {@code types}. */
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
      // The function type's return type is neither void nor a proper type.
      // §18.2.5 reduces the constraint to false in this case, but we
      // cannot do this because we have reduced constraints eagerly.
      // TODO(joqvist): we need dependency-based constraint resolution.
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
   * Add the equality bound {@code α = T} where {@code S} is the inference
   * variable α and {@code T} is a proper type or another inference variable (§18.1.3).
   */
  private void addEqualBound(TypeDecl S, TypeDecl T) {
    if (S == T) {
      return;
    }
    ConstraintSet set = lookup(S);
    set.equal.add(T);
  }

  /**
   * Add the subtype bound {@code α <: T} where {@code alpha} is the inference
   * variable α (§18.1.3).
   */
  private void addUpperBound(TypeDecl alpha, TypeDecl T) {
    if (alpha == T) {
      return;
    }
    lookup(alpha).upper.add(T);
  }

  /**
   * Add the supertype bound {@code α >: S}, recorded as the lower bound S of the
   * inference variable α (§18.1.3).
   */
  private void addLowerBound(TypeDecl alpha, TypeDecl S) {
    if (alpha == S) {
      return;
    }
    lookup(alpha).lower.add(S);
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
