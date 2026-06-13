// The type Object is not compatible with T extends Object
// See https://bitbucket.org/jastadd/jastaddj/issue/105/object-is-not-compatible-with-type
// .result=COMPILE_FAIL
class Test<T> {
    T _() {
        return new Object();
    }
}

