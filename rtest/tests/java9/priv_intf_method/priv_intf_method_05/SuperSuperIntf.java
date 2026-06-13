public interface SuperSuperIntf {
    private void helperSuperSuper(){
        System.out.println("in private");
    }
    default void concreteMethodSuperSuper(){
         helperSuperSuper();
    }
}