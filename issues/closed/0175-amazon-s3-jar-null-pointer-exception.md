# Amazon S3 jar - Null Pointer Exception

**Status:** resolved

## Description: ##
A large test project using amazon s3 (aws-java-sdk-s3-1.9.8.jar) does not compile with ExtendJ. A NPE is thrown.
To reproduce the NPE I assembled a little jar containing the core types involved in this issue:

```
#!java
import com.amazonaws.services.s3.AmazonS3Client;

public class AmazonS3ClientExample {
	public static void main(String[] args) {
		AmazonS3Client amazonS3Client = new AmazonS3Client();
	}
}
```

## Types of aws-java-sdk-s3.jar ##

```
#!java
package com.amazonaws.services.s3;

public class AmazonS3Client {

	public AmazonS3Client() {
		this(new AWSCredentialsProviderChain(new InstanceProfileCredentialsProvider()) {
			public Object getCredentials() {
				return new Object();
			}
		});
	}

	public AmazonS3Client(AWSCredentialsProviderChain credentialsProviderChain) {
		super();
	}
}
```


```
#!java
package com.amazonaws.services.s3;

public interface AWSCredentialsProvider {

}
```


```
#!java
package com.amazonaws.services.s3;

public class AWSCredentialsProviderChain {
	public AWSCredentialsProviderChain(AWSCredentialsProvider... credentialsProviders) {
		super();
	}

	public Object getCredentials() {
		return null;
	}

}
```

```
#!java
package com.amazonaws.services.s3;

public class InstanceProfileCredentialsProvider implements
		AWSCredentialsProvider {

}
```

## Example call: ##

```
#!text
java -jar extendj.jar -cp lib/aws-java-sdk-s3.jar src/AmazonS3ClientExample.java
```

## Hints: ##
```
#!text
java5/frontend/BytecodeDescriptor.jrag:208
	lastIndex evaluates to -1
java5/frontend/BytecodeDescriptor.jrag:209
	ParameterDeclaration p = (ParameterDeclaration)parameterList.getChildNoTransform(lastIndex); -> p evaluates to null
java5/frontend/BytecodeDescriptor.jrag:213
	p.getModifiersNoTransform() causes NPE
```

## Stacktrace: ##
```
#!text
java.lang.NullPointerException
        at org.extendj.ast.MethodInfo.bodyDecl(MethodInfo.java:97)
        at org.extendj.ast.BytecodeParser.parseMethods(BytecodeParser.java:190)
        at org.extendj.ast.BytecodeParser.parse(BytecodeParser.java:86)
        at org.extendj.ast.BytecodeParser.parse(BytecodeParser.java:54)
        at org.extendj.ast.Attributes$TypeAttributes.innerClasses(Attributes.java:413)
        at org.extendj.ast.Attributes$TypeAttributes.processAttribute(Attributes.java:316)
        at org.extendj.ast.Attributes.attributes(Attributes.java:69)
        at org.extendj.ast.Attributes$TypeAttributes.<init>(Attributes.java:306)
        at org.extendj.ast.BytecodeParser.parse(BytecodeParser.java:89)
        at org.extendj.ast.Program$1.read(Program.java:53)
        at org.extendj.ast.BytecodeClassSource.parseCompilationUnit(BytecodeClassSource.java:47)
        at org.extendj.ast.ClassPath.getCompilationUnit(ClassPath.java:213)
        at org.extendj.ast.Program.getCompilationUnit(Program.java:712)
        at org.extendj.ast.Program.getLibCompilationUnit(Program.java:1338)
        at org.extendj.ast.Program.lookupLibraryType(Program.java:340)
        at org.extendj.ast.Program.lookupType_compute(Program.java:1309)
        at org.extendj.ast.Program.lookupType(Program.java:1288)
        at org.extendj.ast.Program.Define_lookupType(Program.java:1914)
        at org.extendj.ast.ASTNode.Define_lookupType(ASTNode.java:1980)
        at org.extendj.ast.Expr.lookupType(Expr.java:1437)
        at org.extendj.ast.TypeAccess.decls(TypeAccess.java:328)
        at org.extendj.ast.TypeAccess.refined_TypeScopePropagation_TypeAccess_decl(TypeAccess.java:293)
        at org.extendj.ast.TypeAccess.decl(TypeAccess.java:342)
        at org.extendj.ast.TypeAccess.collect_contributors_CompilationUnit_problems(TypeAccess.java:577)
        at org.extendj.ast.ASTNode.collect_contributors_CompilationUnit_problems(ASTNode.java:1044)
        at org.extendj.ast.SingleTypeImportDecl.collect_contributors_CompilationUnit_problems(SingleTypeImportDecl.java:335)
        at org.extendj.ast.ASTNode.collect_contributors_CompilationUnit_problems(ASTNode.java:1044)
        at org.extendj.ast.ASTNode.collect_contributors_CompilationUnit_problems(ASTNode.java:1044)
        at org.extendj.ast.CompilationUnit.collect_contributors_CompilationUnit_problems(CompilationUnit.java:1417)
        at org.extendj.ast.CompilationUnit.survey_CompilationUnit_problems(CompilationUnit.java:600)
        at org.extendj.ast.CompilationUnit.problems_compute(CompilationUnit.java:1392)
        at org.extendj.ast.CompilationUnit.problems(CompilationUnit.java:1375)
        at org.extendj.ast.CompilationUnit.errors(CompilationUnit.java:690)
        at org.extendj.ast.Frontend.processCompilationUnit(Frontend.java:211)
        at org.extendj.JavaCompiler.processCompilationUnit(JavaCompiler.java:107)
        at org.extendj.ast.Frontend.run(Frontend.java:136)
        at org.extendj.JavaCompiler.run(JavaCompiler.java:101)
        at org.extendj.JavaCompiler.main(JavaCompiler.java:61)
        at org.jastadd.extendj.JavaCompiler.main(JavaCompiler.java:39)
```

## Attachments

- [extendj-amazon-s3.zip](../attachments/175/extendj-amazon-s3.zip) (uploaded by Jörg Hagemann)

## Comments

### Jesper Öqvist - 2016-07-25

This error is caused by the bytecode parser assuming that a constructor of an inner class always has the enclosing class as the first parameter. The bytecode parser then skips the first parameter and builds a parameter list out of the remaining parameters. However, if the constructor is declared `ACC_VARARGS`, then the bytecode parser will access the last parameter to check the type of the variable arity argument, which in this case leads to a null pointer returned from `parameterList.getChild()` when it tries to access one-before-the-end of an empty list.

To fix this the condition determining when to skip the first parameter of a bytecode method needs to be fixed to remove the false positive.

Here is the bytecode for the constructor causing the error:

```
  com.amazonaws.services.s3.AmazonS3Client$1(com.amazonaws.services.s3.AWSCredentialsProvider...);
    descriptor: ([Lcom/amazonaws/services/s3/AWSCredentialsProvider;)V
    flags: ACC_VARARGS
    Code:
      stack=2, locals=2, args_size=2
         0: aload_0
         1: aload_1
         2: invokespecial #1                  // Method com/amazonaws/services/s3/AWSCredentialsProviderChain."<init>":([Lcom/amazonaws/services/s3/AWSCredentialsProvider;)V
         5: return
      LineNumberTable:
        line 6: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       6     0  this   Lcom/amazonaws/services/s3/AmazonS3Client$1;
            0       6     1    x0   [Lcom/amazonaws/services/s3/AWSCredentialsProvider;
```

### Jesper Öqvist - 2016-07-25

It is interesting that creating the anonymous instance inside the explicit constructor call (`this();`) causes the anonymous class to become static, while if you duplicate the same code in a non-static context it will be generated as a non-static anonymous class. I believe ExtendJ has the same behaviour. Seems a little wasteful though since the anonymous class never accesses enclosing scope.

### Jesper Öqvist - 2016-07-25

Since this is only affecting anonymous classes, which are not accessible from source, it would be okay to keep stripping the first parameter and just do a `lastIndex != -1` check.

ExtendJ could also safely ignore parsing anonymous classes.

### Jesper Öqvist - 2016-07-25

For non-anonymous static inner classes the bytecode parser sets `isInnerClass = false`.

### Jesper Öqvist - 2016-07-25

A more minimal test case for this issue:

In the Jar: Enclosing.java:
```java
package pkg;

public class Enclosing {
  public Enclosing() {
    this(new VarArgsConstructor() { }); // Static anonymous class.
  }

  Enclosing(Object o) {
  }
}

class VarArgsConstructor {
  VarArgsConstructor(String... args) {
  }
}
```

Source code: Test.java:
```java
import pkg.Enclosing;

class Test extends Enclosing {
}
```

### Jesper Öqvist - 2016-07-25

Fix NPE in BC parse: static anon class w/ varargs

Fix an NPE when parsing bytecode for a static anonymous class with a
varargs constructor with only one parameter (variable arity parameter).

In the future we should avoid parsing bytecode for anonymous classes
entirely.

fixes #175 (bitbucket)


→ <<cset 117904ba777e>>
