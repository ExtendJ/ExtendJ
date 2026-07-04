# Fix error messages or revert changed semantics of ASTNode.toString()

**Status:** resolved

In commit [39f7c6a], the semantics of `ASTNode.toString()` was changed. Previously it pretty-printed the subtree rooted at the node, but now it only prints information about the node.

Many error messages in JastAddJ rely on the prettyprinting semantics, so either they have to be updated, or the semantics should be reverted back to the previous one.

For example, there are error messages in `typecheck.jrag` that call `getExpr()` and `getTypeAccess()`, and there are many other cases. Some error messages also call `this`which has the same effect.

I (Görel) think it might be a good idea to revert the semantics since this change might affect many users of JastAddJ. They might have written their own error messages, and they might have tests that check error message output.

## Comments

### Jesper Öqvist - 2014-01-02

The reason for the change of `ASTNode.toString()` in JastAddJ was that the pretty printing often leads to problems during debugging - inspecting nodes in a debugger causes `toString` to be called and that in turn causes many different method calls which often lead to additional breakpoints triggering.

I also think it is important to have a separate interface for pretty printing other than `toString` because `toString` is used in so many different contexts for different reasons.

### Jesper Öqvist - 2014-01-08

The new tracing feature in JastAdd2 also had problems due to `toString` pretty printing.

Either way, the commit f518c1e12452ee7ebb175afacbc628e717e07e5c fixes the error messages (replaced explicit and implicit calls to toString by prettyPrint).
