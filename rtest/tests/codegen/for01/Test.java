public class Test {
	public static void main(String[] argv) {
		java.util.List<Long> longList = new java.util.LinkedList<Long>();
		longList.add(3l);
		longList.add(17l);
		longList.add(-2l);
		for (Long l: longList)
			System.out.println("" + l);
	}
}
