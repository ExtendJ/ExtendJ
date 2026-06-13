public interface SuperIntf {
    private void helperSuper(){
        System.out.println("in private");
    }
    default void concreteMethodSuper(){
         helperSuper();
    }
}