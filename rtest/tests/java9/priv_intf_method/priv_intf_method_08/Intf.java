public interface Intf {
    private static void helper(){
        System.out.println("in private");
    }
    default void concreteMethod(){
         helper();
    }
}