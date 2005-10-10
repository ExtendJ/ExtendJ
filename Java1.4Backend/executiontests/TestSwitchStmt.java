public class TestSwitchStmt {
/*	static int sw1(int i) {
		int res = 0;
		switch (i) {
		default: res = 4;
		case 1: res = res + 11; break;
		case 2: res = 20;
		case 3: res = res + 3; break;
		}
		return res;
	}
*/
  /*	int sw2(int i) {
		int res = 0;
		switch (i) {
		case 100: res = 11; break;
		case -200: res = 20;
		case 3: res = res + 3; break;
		default: res = 4;
		}
		return res;
	}
  */
	public static void main(String[] args) {
    switch (2) {
      case 8:
        System.out.println(8);
        break;
      case 7:
        System.out.println(7);
        break;
      case 6:
        System.out.println(6);
        break;
      case 16:
        System.out.println(12);
        break;
      case 4:
        System.out.println(4);
        break;
      case -1:
        System.out.println(-1);
        break;
      case 3:
        System.out.println(3);
        break;
      case 2:
        System.out.println(2);
        break;
      case 1:
        System.out.println(1);
        break;
      case 0:
        System.out.println(0);
        break;
      default: System.out.println("Default");
    }
    /*
		System.out.println(sw1(1));
		System.out.println(sw1(2));
		System.out.println(sw1(3));
		System.out.println(sw1(-1234));
    */
		/*
    System.out.println(sw2(100));
		System.out.println(sw2(-200));
		System.out.println(sw2(3));
		System.out.println(sw2(-1234));
    */
	}
}
