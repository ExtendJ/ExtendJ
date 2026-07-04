# Lambda expression - StackOverflowError

**Status:** resolved

The following java8 example causes a java.lang.StackOverflowError:

```
#!java
public class LambdaExpr01<T> {

	public LambdaExpr01(LambdaExprDataProvider<T> t) {}

	public static void main(String[] args) {
		LambdaExpr01<Object> a = new LambdaExpr01<>(
        		() -> LambdaExprObjectProducer.produce(Object.class)
        	);
	}
}

class LambdaExprObjectProducer {
	static <T> T produce(Class<T> clazz) {
		T t = null;
		return t;
	}
}

interface LambdaExprDataProvider<T> {
    T getData();
}
```
## Stacktrace ##

```
#!text
Encountered error while processing src/LambdaExpr01.java
Fatal exception:
java.lang.StackOverflowError
        at org.extendj.ast.Block.getStmtListNoTransform(Block.java:289)
        at org.extendj.ast.Block.Define_lookupType(Block.java:888)
        at org.extendj.ast.ASTNode.Define_lookupType(ASTNode.java:2012)
        at org.extendj.ast.Expr.lookupType(Expr.java:1927)
        at org.extendj.ast.ClassInstanceExpr.Define_lookupType(ClassInstanceExpr.java:1817)
        at org.extendj.ast.ASTNode.Define_lookupType(ASTNode.java:2012)
        at org.extendj.ast.Expr.lookupType(Expr.java:1927)
        at org.extendj.ast.TypeAccess.decls(TypeAccess.java:377)
        at org.extendj.ast.TypeAccess.refined_TypeScopePropagation_TypeAccess_decl(TypeAccess.java:341)
        at org.extendj.ast.TypeAccess.decl(TypeAccess.java:390)
        at org.extendj.ast.TypeAccess.type(TypeAccess.java:532)
        at org.extendj.ast.DiamondAccess.type_compute(DiamondAccess.java:389)
        at org.extendj.ast.DiamondAccess.type(DiamondAccess.java:377)
        at org.extendj.ast.ClassInstanceExpr.decls_compute(ClassInstanceExpr.java:1025)
        at org.extendj.ast.ClassInstanceExpr.decls(ClassInstanceExpr.java:1013)
        at org.extendj.ast.ClassInstanceExpr.decl_compute(ClassInstanceExpr.java:1063)
        at org.extendj.ast.ClassInstanceExpr.decl(ClassInstanceExpr.java:1051)
        at org.extendj.ast.ClassInstanceExpr.Define_targetType(ClassInstanceExpr.java:1995)
        at org.extendj.ast.ASTNode.Define_targetType(ASTNode.java:3332)
        at org.extendj.ast.Expr.targetType(Expr.java:2096)
        at org.extendj.ast.LambdaExpr.targetInterface_compute(LambdaExpr.java:1021)
        at org.extendj.ast.LambdaExpr.targetInterface(LambdaExpr.java:1009)
        at org.extendj.ast.LambdaExpr.type_compute(LambdaExpr.java:1068)
        at org.extendj.ast.LambdaExpr.type(LambdaExpr.java:1052)
        at org.extendj.ast.Expr.computeConstraints(Expr.java:225)
        at org.extendj.ast.Expr.inferTypeArguments_compute(Expr.java:1243)
        at org.extendj.ast.Expr.inferTypeArguments(Expr.java:1228)
        at org.extendj.ast.DiamondAccess.potentiallyApplicable(DiamondAccess.java:118)
        at org.extendj.ast.DiamondAccess.potentiallyApplicable(DiamondAccess.java:90)
        at org.extendj.ast.DiamondAccess.chooseConstructor(DiamondAccess.java:69)
        at org.extendj.ast.DiamondAccess.type_compute(DiamondAccess.java:408)
        at org.extendj.ast.DiamondAccess.type(DiamondAccess.java:377)
 		...
```

## Comments

### Jesper Öqvist - 2016-07-25

The StackOverflowError is caused by lambda type analysis being co-circular with Diamond type inference. To break the loop one of the attributes in the cycle needs to be declared circular.

### Jesper Öqvist - 2016-07-25

Make LambdaExpr.type() circular to fix eval loop

LambdaExpr and Diamond had mutually circular type inference/analysis
causing an infinite loop during type analysis.

fixes #176 (bitbucket)


→ <<cset 641e69280e86>>
