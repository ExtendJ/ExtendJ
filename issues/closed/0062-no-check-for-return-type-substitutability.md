# No check for return-type substitutability

**Status:** invalid

According to [JLSv7 8.4.8.3](http://docs.oracle.com/javase/specs/jls/se7/html/jls-8.html#jls-8.4.8.3) and [JLSv7 8.5.4](http://docs.oracle.com/javase/specs/jls/se7/html/jls-8.html#jls-8.4.5) an overriding method must be return-type substitutable for the method it is overriding. JastAddJ does not appear to check this currently.

Test case (should fail to compile):

    class A {
            void m() { }
    }

    class Test extends A {
            /* Test.m() not return-type substitutable for A.m() */
            int m() {
                    return -1;
            }
    }

## Comments

### Jesper Öqvist - 2014-03-03

Cannot reproduce the issue in JastAddJ 7.1.1-152-g02dab28 Java SE 7
