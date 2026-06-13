// .result=EXEC_PASS

/*
    Variable being declared as a var can have same name as a var in a different name space
*/

public class Test {
    int a;
    public Test(int x){
        a = x;
        P p = new P();
        var z = p.z; // Not the z variable being referenced
        System.out.println("z = " + z);

    }
    public static void main (String[] args) {
        Test test = new Test(2);
    }

     class P{
        int z = 1;

    }

}