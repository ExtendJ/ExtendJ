
public interface Test {
	public static default String addExtension(String s) {
		s = s + ".extension";
		return s;
	}
}