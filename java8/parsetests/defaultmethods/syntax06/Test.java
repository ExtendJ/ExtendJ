
public interface Test {
	default int addNbr(int nbr) {
		switch(nbr) {
		default: return nbr + 5;
		}
	}
}