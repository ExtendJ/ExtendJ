public class TestUnsignedString {
  public static void main(String[] args) {
    /*for(int i = 0; i < 34; i++)
      System.out.println(lshift(1, i));
    for(int i = 0; i < 34; i++)
      System.out.println(urshift(lshift(1, 31), i));*/
    System.out.println(toUnsignedString(0xffffffff, 4));
  }
   private static int lshift(int val, int shift) {
    	int result = val;
    	for (int i = 0; i < shift; i++)
    		result = result * 2;
    	return result;
    }
    
/*    private static int urshift(int val, int shift) {
    	int result = val;
    	for (int i = 0; i < shift; i++)
    		result = result / 2;
    	return result;
    }*/
    private static int urshift(int val, int shift) {
    	int result = val;
      if(shift > 0) {
    	  boolean negative = (val & 0x80000000) != 0;
    	  result = val & 0x7fffffff;
        result = (result / 2) | (negative ? 0x40000000 : 0);
      }
    	for (int i = 1; i < shift; i++)
    		result = result / 2;
    	return result;
    }
      private static String toUnsignedString(int i, int shift) {
    	char[] buf = new char[32];
    	int charPos = 32;
    	int radix = lshift(1, shift);
    	//int radix = 1 << shift;
    	int mask = radix - 1;
    	do {
    		buf[--charPos] = digitsHack(i & mask);
    		//buf[--charPos] = digits[i & mask];
    		i = urshift(i, shift);
    		//i >>>= shift;
        System.out.println(i);
    	} while (i != 0);
    	
    	return new String(buf, charPos, (32 - charPos));
    }
        final static char[] digits = {
          	'0' , '1' , '2' , '3' , '4' , '5' ,
          	'6' , '7' , '8' , '9' , 'a' , 'b' ,
          	'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
          	'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
          	'o' , 'p' , 'q' , 'r' , 's' , 't' ,
          	'u' , 'v' , 'w' , 'x' , 'y' , 'z'
      };
    private static char digitsHack(int i) {
        if(i==0) return '0';
        if(i==1) return '1';
        if(i==2) return '2';
        if(i==3) return '3';
        if(i==4) return '4';
        if(i==5) return '5';
        if(i==6) return '6';
        if(i==7) return '7';
        if(i==8) return '8';
        if(i==9) return '9';
        if(i==10) return 'a';
        if(i==11) return 'b';
        if(i==12) return 'c';
        if(i==13) return 'd';
        if(i==14) return 'e';
        if(i==15) return 'f';
        System.out.println("This should not happen");
        return '*';
    }
}
