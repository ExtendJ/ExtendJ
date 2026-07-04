# Report compile error when a source file does not declare an expected type

**Status:** duplicate

Javac reports an error if it finds a which does not provide a specific reference type X when it should.

For example, if we have these two files in the same directory:

    /* Test.java */
    public class Test {
            public static void main(String[] args) {
                    System.out.println("" + new X());
            }
    }


    /* X.java */
    class Y { }

Javac will report this error:

    Test.java:4: error: cannot access X
                    System.out.println("" + new X());
                                                ^
      bad source file: ./X.java
        file does not contain class X
        Please remove or make sure it appears in the correct subdirectory of the sourcepath.
    1 error

JastAddJ on the other hand just ignores the problem if it can find the type `X` in an imported package. It would be good if JastAddJ reports the same type of error as Javac.

## Comments

### Jesper Öqvist - 2016-02-24

Duplicate of #11.
