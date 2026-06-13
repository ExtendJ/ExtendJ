public interface SuperIntf extends SuperSuperIntf{
    private void helperSuper(){
        System.out.println("in private");
    }
    default void concreteMethodSuper(){
         concreteMethodSuperSuper();
    }
}
