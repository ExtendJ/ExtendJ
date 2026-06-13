public interface Intf {
    private void helper(){
        System.out.println("in private");
    }
    default void concreteMethod(){
         helper();
    }
}