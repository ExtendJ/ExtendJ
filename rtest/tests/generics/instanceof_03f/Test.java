// It is an error if the second part of instanceof references a non-reifiable type
// See JLS SE7 $15.20.2
// https://bitbucket.org/jastadd/jastaddj/issue/109/comparing-to-non-reifiable-type-in
// .result=COMPILE_FAIL
public class Test<U> {
	boolean test(U u) {
		return (u instanceof Test<? extends Integer>[]);
	}
}
