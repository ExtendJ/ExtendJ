public interface Intf {
    default void helper(){
        System.out.println("in private");
    }
    default void concreteMethod(){
         helper();
    }
}