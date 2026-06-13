// .result=COMPILE_PASS

/*
    Initializer can be a non denotable type, here an anonymous class
*/
public class Test {
    public static void main (String[] args) {
        var d = new Object();
         // d has the type of the anonymous class
    }

}