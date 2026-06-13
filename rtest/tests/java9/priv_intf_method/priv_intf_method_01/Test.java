// Test if we can have a private interface method
// .result=COMPILE_PASS
public interface Test {
    private void helper(){
        System.out.println("in private");
    }

    default void concreteMethod(){
         helper();
    }
}