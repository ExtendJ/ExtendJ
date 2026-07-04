# MethodAccess.decl() inconsistency

In the following example extracted from the freemind 0.9.0 sources, the call to decl() on "getRowCount" access can return either p.AttributeTableModel.getRowCount() or javax.swing.table.TableModel.getRowCount() :
```
#!java

package p;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
interface AttributeTableModel extends TableModel {
    int getRowCount();
}

abstract class AttributeTableModelDecoratorAdapter extends AbstractTableModel implements AttributeTableModel {
}
public class View {

    private AttributeTableModelDecoratorAdapter currentAttributeTableModel;

    private void startEditingTable() {
        currentAttributeTableModel.getRowCount();
    }
}

```

For info, I tried to minimalize even further the above example as follow to remove any library dependencies but then the inconsistency is not observable.

```
#!java
package p;
interface TableModel {
    int m();
}
abstract class AbstractTableModel implements TableModel {}
interface AttributeTableModel extends TableModel {
    int m();
}
abstract class C extends AbstractTableModel implements AttributeTableModel {
}

class D {
    C c;
    void n(){
        c.m();
    }
}
```
Edit : comment edited  with test that reproduces the inconsistency

## Comments

### Jesper Öqvist - 2016-02-27

I have tried to reproduce this problem, but I was able to correctly compile the example code you provided. I'm not sure why we get different results.

Have you used the regression test suite for ExtendJ? The Git repository is here: https://bitbucket.org/extendj/regression-tests

It would be very useful if you can give your examples as test cases for the regression test suite, because then I can run it exactly the same way.

For example I did the following to try to reproduce the bug with your example code:

* `cd regression-tests`
* `ln -s ../extendj/extendj.jar extendj.jar` -- creates a link to the latest `extendj.jar` to be used by the test framework.
* `mkdir tests/name/method_01p` -- the path does not really matter, the test framework scans all folders under the `test/` directory for test cases.
* Then I pasted the code into the file `tests/name/method_01p/Test.java` -- the `Test.java` file makes the test framework recognize it as a test file that will be compiled and the executed. Adding a comment `// .result=COMPILE_PASS` makes the test skip executing the compiled code.
* `ant -Dtest=name/method_01p`  -- this runs only the test in the path `tests/name/method_01p`

### Loïc Girault - 2016-02-28

Hello. The inconsistency of MethodAccess.decl() value returned is not a  problem  for compilation but it is for my static analysis. As such I do not know if you will consider it a bug.

Anyway here is a test that reproduce the inconsistency  :
```
#!java
package tests.extendj;

import org.extendj.JavaCompiler;
import org.extendj.ast.*;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

class TestFrontend extends JavaCompiler {

        Program getProgam(){
           return program;
        }
}

public class TestJavaMethodAccess {
        MethodAccess searchMethodAccess(ASTNode<?> n, String name){
                if(n instanceof MethodAccess && ((MethodAccess)n ).name().equals(name))
                        return (MethodAccess) n;

                for(int i=0; i<n.getNumChild(); i++){
                        MethodAccess ma = searchMethodAccess(n.getChild(i), name);
                        if(ma!= null) return ma;
                }

                return null;
        }
        String fullName(MethodDecl md){
            return md.hostType().fullName() + "." + md.fullSignature();
        }

        String compileAccessMethodAndBuildFullName(String filePath, String methodName){
            TestFrontend jc = new TestFrontend();
            String[] args= {filePath};
            jc.run(args);
            Program p = jc.getProgam();
            assertTrue(p != null);
            MethodAccess ma =searchMethodAccess(p, methodName);
            assertTrue(ma.name().equals(methodName));
            MethodDecl md = ma.decl();
            return fullName(md);
        }

        @Test
        public void testMethodAccess() {
            String p = "<path to Test.java>";
            String m = "getRowCount";

            String fn0 = compileAccessMethodAndBuildFullName(p, m);

            for(int i=0; i<10; i++){
                assertTrue(fn0.equals(compileAccessMethodAndBuildFullName(p, m)));
            }

       }
}
```
To run it I modified the build.xml :

 * in the javac task of the build target, i've added <pathelement path="${extendj.jar}"/> in the classpath

 * I've written the following task

```
#!xml

	<target name="javaMethodAccess" depends="build">
		<junit fork="yes" showoutput="yes">
			<classpath>
				<pathelement path="lib/junit-${junit.version}.jar"/>
				<pathelement path="${extendj.jar}"/>
			</classpath>
			<test name="tests.extendj.TestJavaMethodAccess" todir="${test-reports.dir}"/>
		</junit>
	</target>
```

### Jesper Öqvist - 2016-03-04

Thank you! Your test code above helped a lot, and I can reproduce the bug now!

I think the reason that the result of `MethodAccess.decl()` varies is because it picks the first result from an unsorted set of candidate declarations. There is probably a rule in the Java Language Specification that ExtendJ does not follow correctly in this case. I will see if I can fix it.

### Loïc Girault - 2016-03-04

You're welcome. Happy to help ^_^

### Jesper Öqvist - 2016-03-04

I am not sure that there is a good solution to this. The `MethodAccess.decl()` attribute has to return a single declaration, unless the MethodAccess is undefined and then it returns `UnknownType.unknown()`. In this case there are two most specific method declarations: `p.AttributeTableModel.getRowCount()`, and `javax.swing.table.TableModel.getRowCount()`. I can't see that either of these declarations is more correct than the other, and there is no good solution to sort the declarations.

If you want to find out if a method is declared in multiple places you could use the `MethodAccess.decls()` attribute which returns all applicable declarations.

### Loïc Girault - 2016-03-04

I arrived at the same conclusion. As I said in a previous answer, this inconsistency is not a problem for the compilation. It's just weird to stumble upon a case where different results can be computed and concurrency does not seems to be the origin of it. Now I'm using ``` MethodAccess.decls() ``` and it suits me perfectly !

Again, thank you for your time. Furthermore, this make me discover your test suite and gave me the opportunity to build the test to exhibit the stackoverflow problem with the fullname of a type variable declared in a generic method (issue #136).
