// .result=COMPILE_FAIL

/*
    Initializer cannot be a lambda function
*/
public class Test {
    public static void main (String[] args) {
        var f = () -> "hello"; // Illegal: lambda not in an assignment context

    }

}