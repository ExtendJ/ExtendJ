public interface Intf extends SuperIntf{
    private void helper(){
        System.out.println("in private");
    }
    default void concreteMethod(){
         concreteMethodSuper();
    }
}