# Recursive finally embedding

**Status:** resolved

This test case provokes a stack overflow exception in the nta-finally branch:

````
class Test {
        void m () {
                try {
                } finally {
                        return;
                }
        }
}
````

This is caused by the return-statement thinking that the finally block is it's enclosing finally block (it has none). This can be fixed by improving the collectFinally method.
