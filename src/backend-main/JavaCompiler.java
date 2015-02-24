/**
 * @deprecated Use org.jastadd.jastaddj.JavaCompiler instead.
 */
@Deprecated
class JavaCompiler extends org.jastadd.jastaddj.JavaCompiler {
	public static void main(String args[]) {
		if (!compile(args)) {
			System.exit(1);
		}
	}
}
