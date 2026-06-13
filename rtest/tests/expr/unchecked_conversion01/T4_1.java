
class T4_1 {
    
    class MyClass<T> { }

    void foo() {
        MyClass raw = new MyClass();
        MyClass<Integer> generic = raw;
    }

}
