# Remove addEnclosingVariables

**Status:** resolved

The `addEnclosingVariables` methods should be removed from JastAddJ since they are being used to alter the AST in a potentially unsafe manner.

The fields added by `addEnclosingVariables` could instead be added using NTAs.

## Comments

### Jesper Öqvist - 2013-11-04

Change to task

### Jesper Öqvist - 2016-02-16

After some work on this I found a better approach to removing the `addEnclosingVariables` transformation: just generate the extra constructor parameters/arguments/fields during code generation. There is no need to use NTAs to store the synthetic AST elements since it is fairly straightforward to detect during code generation which extra bytecode elements should be generated.

### Jesper Öqvist - 2016-02-16

Remove enclosing variables transformation

Anonymous and local classes that use variables from an enclosing variable scope
need to have the variables implicitly passed through their constructors. This
used to be achieved by imperative transformation of the AST before code
generation. The AST was traversed and each constructor, class declaration, and
constructor access was tested to see if it needed enclosing variables. The
fields, parameters or arguments used for the enclosing variables were then
added in the corresponding AST nodes using the addEnclosingVariables() method.

This transformation was problematic because it modified the AST and could
affect the correctness of attribute values, for example it affected the
constructor lookup since modifying a constructor access would result in the
decl() attribute needing to point to another target constructor.

The enclosing variables transformation has been replaced by implicitly
generating the needed fields, parameters, and formal arguments during code
generation instead. This removes the dependence of the constructor access
decl() attribute on the transformation.

This refactoring required a few other changes:

* Added the method Access.emitLoadVariable(CodeGeneration, Variable) to
  unify loading variables/fields/parameters.
* Lambda expressions no longer clear their body after generating the class
  representation using the toClass() NTA.
* Changed VarAccess.createLoadQualifier(CodeGeneration) to
  Access.createLoadQualifier(CodeGeneration, Variable).
* Changed VarAccess.createLoadQualifier(CodeGeneration) to
  Access.createLoadQualifier(CodeGeneration, Variable).
* Added VarAccess.requiresAccessor() attribute declaration to Access.
* Added VarAccess.fieldQualifierType() attribute declaration to Access.
* Added helper method Access.emitCheckCast(CodeGeneration, Variable).

fixes #29 (bitbucket)


→ <<cset f1875d6bf5ca>>
