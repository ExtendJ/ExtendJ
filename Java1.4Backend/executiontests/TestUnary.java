public class TestUnary {
	public static void main(String[] args) {
		int i;
		i = -5;
		System.out.println(i);
		i = 5 - -1;
		System.out.println(i);
		i = 5 - +2;
		System.out.println(i);
		System.out.println(-i);
		i = 0x55555555;
		System.out.println(Integer.toHexString(~i));
		long l = 5L;
		System.out.println(Long.toHexString(~l));
		boolean z = false;
		if(!z)
			System.out.println("not z");
	}
}
