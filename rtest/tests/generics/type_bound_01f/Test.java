// Test illegal type bound
// https://bitbucket.org/jastadd/jastaddj/issue/63/array-types-in-bounds
// .result=COMPILE_FAIL
class Test<U extends Object[]> {
}
