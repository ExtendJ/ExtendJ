
public interface Test {
	public default static int addNbr(int nbr) {
		switch(nbr) {
		case 1: return nbr + 1;
		case 2: return nbr + 2;
		case 3: return nbr + 3;
		default: return nbr + 5;
		}
	}
}