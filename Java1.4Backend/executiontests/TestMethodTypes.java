class TestMethodTypes {
    void f(int x, int y) {
	x = y;
    }
    void ff(int x, int y) {
	x = y;
	return;
    }
    int virt(int a1, int a2) {
        return a1 = a2;
    }
    static int stat(int a1, int a2) {
        return a1 = a2;
    }
    TestMethodTypes(int a1, int a2) {
        a1 = a2;
	System.out.print("Constructor: ");
	System.out.print(a1);
	System.out.print(" ");
	System.out.println(a2);
    }
    public static void main(String[] args) {
        TestMethodTypes t = new TestMethodTypes(1,2);
        int i = 3;
        System.out.println(t.virt(i, i*i));
        System.out.println(stat(1,2));
        System.out.println(stat(3*i,t.virt(3,4)));
    }
}
