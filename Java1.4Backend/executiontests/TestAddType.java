public class TestAddType {
    public static void main(String[] args) {
        byte b = 1;
        char c = 33;
        short s = 3;
        int i = 4;
        long l = 5l;
        double d = 6.1;
        float f = 7.2f;
        
        b = (byte)(b+b);
	
        c = (char)(c+c);
        s = (short)(s+s);
        i = i+i;
        l = l+l;
        d = d+d;
        f = f+f;
	
        System.out.println(b);
        System.out.println(c);
        System.out.println(s);
        System.out.println(i);
        System.out.println(l);
        System.out.println(d);
        System.out.println(f);
    }
}
