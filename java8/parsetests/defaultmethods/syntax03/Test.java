
public interface Test {
	default public static String addExtension(String s) {
		s = s + ".extension";
		return s;
	}
}