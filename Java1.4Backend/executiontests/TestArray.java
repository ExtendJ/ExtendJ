public class TestArray {
	public static void main(String[] args) {
    String[] strings = new String[3];
		strings[0] = "first";
		strings[1] = "second";
		strings[2] = "third";
		System.out.println(strings[0]);
		System.out.println(strings[1]);
		System.out.println(strings[2]);
    String[][][] multiStrings = new String[2][3][4];
		multiStrings[0][1] = strings;
    System.out.println(multiStrings[0][1][1]);
    String[][] multiStrings2 = { { "a" }, { "a", "b"} };
		System.out.println(multiStrings2[1][1]);
    int[][][] ints = new int[1][2][3];
    ints[0][1][2] = 10;
    System.out.println(ints[0][1][2]);
    boolean[][] bs = { {true}, {false}};
    System.out.println(bs[0][0]);
    bs[0][0] = false;
    System.out.println(bs[0][0]);
	}
}
