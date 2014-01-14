
public class Test {
	public static void main(String[] args) {
		ArrayList<Integer> l = (AbstractCollection<Integer> & List<Integer> & Queue<Integer>) new ArrayList<Integer>();
    }
}
