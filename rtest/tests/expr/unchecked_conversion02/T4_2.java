
class T4_2 {
    
    class MyClass<T> { }

    @SuppressWarnings("unchecked")
    void foo() {
        MyClass raw = new MyClass();
        MyClass<Integer> generic = raw;
    }

}
