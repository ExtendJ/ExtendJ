public class TestCmpPrint {
    public static void main(String[] args) {
        int i=3,j=7;
        boolean b = i<j;
	System.out.println(b);
        Object o = null;
        b = i<=j; System.out.println(b);
        b = i==j; System.out.println(b);
        b = i!=j; System.out.println(b);
        b = i>=j; System.out.println(b);
        b = i<j;  System.out.println(b);
        b = o == null; System.out.println(b);
        b = o != null; System.out.println(b);
        b = o == o; System.out.println(b);
        b = o != o; System.out.println(b);
    }
}
