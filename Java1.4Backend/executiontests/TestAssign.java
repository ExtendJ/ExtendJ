public class TestAssign {
	static int stat;
	int inst;
	public static void main(String[] args) {
		int local;
		local = 1;
		local += 1;
		System.out.println(local);
		stat = 1;
		stat += 2;
		System.out.println(stat);
		TestAssignSimple.stat = 1;
		TestAssignSimple.stat += 3;
		System.out.println(TestAssignSimple.stat);
		TestAssignSimple s = new TestAssignSimple();
		TestAssignSimple t = new TestAssignSimple();
		s.inst = 1;
		t.inst = 1;
		s.inst += 4;
		t.inst += 5;
		System.out.println(s.inst);
		System.out.println(t.inst);
	}
}		
