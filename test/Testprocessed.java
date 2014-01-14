import java.util.concurrent.Callable;

public class Test {
	public static final int HEJ = 4;

	public static int ret() {
		return 4;
	}	
	public static void main(String[] args) {
		
		Object f = new Integer(1)::toString;
		f = "abc"::length;
		f = "abcd"::<Integer, String>length;
		f = super::f;
		f = i.g.h.super::i;
		f = Callable::run;
		f = ArrayList:REF:<Integer, Double>::new;
		ret(Callable:REF:<Integer, ArrayList<Int, String>>::call);
		f = System::getProperty;
		f = String::length;
		f = List       
				
				
				:REF:<  String , Integer  , Double>    [  ][ ] 
						
						
						[   ] [ ]   ::    size;   // explicit class type args
		f = List::size;           // inferred class type args, or generic
		f = int[]::clone;
		f = T::tvarMember;

		f = "abc"::length;
		f = foo[x]::bar;
		f = (test ? list.map(String::length) : Collections.emptyList())::iterator;
		f = super::toString;

		f = String::valueOf;      // overload resolution needed     
		f = Arrays::sort;         // type args (if any) inferred from context
		f = Arrays::<String>sort; // explicit type args
		f = ArrayList:REF:<String>::new;    // constructor for parameterized type
		f = ArrayList::new;            // inferred class type args, or generic
		f = Foo::<Integer>new;         // explicit generic constructor type arguments
		f = Bar:REF:<String>::<Integer>new; // generic class, generic constructor
		f = Outer.Inner::new;          // inner class constructor
		f = int[]::new;                // array "constructor"
		f = int[][][]::new;
		
		f = (int[] a, int b, Callable<Integer, String> c) -> a + b - c.toInteger();
		f = (Runnable<Callable<Integer, String>>[][] r[], String a, Callable<Integer, String> c) -> { return 5; };
		
		f = (Object x) -> { return x.toString(); };
		f = () -> { int x = 4; int y = 7; return x + y; };
		f = () -> as - 5 + 3 / 4 * 5;
        f = (x, y, z, k) -> {return x + y + z + k;};
        f = g -> {return g;};
        f = g -> g + 4;
        f = () -> {f.toString();};
        f = () -> (a, b, c) -> (a + b) * c / ret((q,w)-> q + w - 456) && 345 >> 56;
        f = () -> as = 6;
        f = (String s, Callable<Int> c, int g, double q) -> s.toString() + c + g + q; 
		f = () -> {};                     // No parameters; result is void

		f = () -> 42;                     // No parameters, expression body
		f = () -> null;                   // No parameters, expression body
		f = () -> { return 42; };         // No parameters, block body with return
		f = () -> { System.gc(); };       // No parameters, void block body

		f = () -> {
		  if (true) return 12;
		  else {
		    int result = 15;
		    for (int i = 1; i < 10; i++)
		      result *= i;
		    return result;
		  }
		};                          // Complex block body with returns

		f = (int x) -> x+1;             // Single declared-type parameter
		f = (int x) -> { return x+1; }; // Single declared-type parameter
		f = (x) -> x+1;                 // Single inferred-type parameter
		f = x -> x+1;                   // Parens optional for single inferred-type case

		f = (String s) -> s.length();   // Single declared-type parameter
		f = (Thread t) -> { t.start(); }; // Single declared-type parameter
		f = s -> s.length();              // Single inferred-type parameter
		f = t -> { t.start(); };          // Single inferred-type parameter

		f = (int x, int y) -> x+y;      // Multiple declared-type parameters
		f = (x,y) -> x+y;               // Multiple inferred-type parameters
		f = (final int x) -> x+1;       // Modified declared-type parameter */
    }
}
