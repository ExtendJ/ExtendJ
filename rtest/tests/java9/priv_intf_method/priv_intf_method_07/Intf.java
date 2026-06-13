public interface Intf {
    static void helper(){
        System.out.println("in private");
    }
    default void concreteMethod(){
         helper();
    }
}