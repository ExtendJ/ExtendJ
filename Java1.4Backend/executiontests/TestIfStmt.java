public class TestIfStmt {
    public static void main(String[] args) {
        boolean z = false;
        if (z)
            {z = false;}
        else z = true;
        if (z) System.out.println("ok1");
       
        if (z) z = false;
        if (!z) System.out.println("ok2");
        
        if (z) {}
        else z = true;
        if (z) System.out.println("ok3");
        
        if(z);else;
        if (z) System.out.println("ok4");
     
    }
}
