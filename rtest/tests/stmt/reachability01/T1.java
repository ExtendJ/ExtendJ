class E1 extends Exception {}
class E2 extends Exception {}

class T1 {
	{
		try {
			try {
				throw new E1();
			} catch (E1 e) {
				throw new E2();
			}
		} catch (E2 e) {
			// reachable
		}
	}
}
