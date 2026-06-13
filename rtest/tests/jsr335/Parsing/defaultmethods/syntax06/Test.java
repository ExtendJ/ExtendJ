// Tests default methods with switch cases (since there is a potential conflict with the default keyword)
// .result=COMPILE_OUTPUT
// .options=XstructuredPrint

public interface Test {
	default int addNbr(int nbr) {
		switch(nbr) {
		default: return nbr + 5;
		}
	}
}
