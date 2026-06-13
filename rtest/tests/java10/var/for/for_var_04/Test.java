// .result=EXEC_PASS

/*
 * Index variables declared in traditional for loops
 */
public class Test {
    public static void main(String[] args) {
        for(var i = func(); i <= 2; i++){
            System.out.println(i);
        }
    }

    public static int func(){
        return 1;
    }
}
