# signature() attribute has wrong value for ConstructorDecl (fix provided)

**Status:** resolved

Example code:
```
#!java

class Node {
    Node(String name) {}
}
```
The value of the signature attribute for the Node-constructor is Node(AST.ParameterDeclaration) instead of the correct one Node(java.lang.String).

It is a regression after commit  8dbee0d, after which ASTNode.toString() does not pretty print AST nodes any more.

The fix is to replace the [line 116 in java4/frontend/LookupConstructor.jrag](https://bitbucket.org/jastadd/jastaddj/annotate/bc3a3b02e4fe946cbf4ebd3ce79ec4332b289102/java4/frontend/LookupConstructor.jrag?at=master#cl-116) with this one:
```
#!java
s.append(getParameter(i).type().typeName());
```

## Comments

### Jesper Öqvist - 2015-02-07

Thank you for spotting this! I am sorry I did not respond to this earlier - I have had email notifications disabled for bitbucket so I did not notice this issue until just now. I am testing this fix and will hopefully push the commit soon!

### Jesper Öqvist - 2015-02-07

Fixed incorrect signature for ConstructorDecl

fixes #71 (bitbucket)


→ <<cset 461d0454516d>>
