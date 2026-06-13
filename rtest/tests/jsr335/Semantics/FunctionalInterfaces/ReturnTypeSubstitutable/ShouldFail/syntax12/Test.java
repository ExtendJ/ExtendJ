// .result=COMPILE_FAIL
import java.util.*;

class Test {
	interface X<A> { <B> A execute(int a); }
	interface Y<B> { <A> A execute(int a); }
	
	// This test previously failed because there was a bug for generic interfaces.
	// For Y<B> { <A> A execute(int a); }, since no type arguments are specified
	// for Y in the extends-clause, the method should be erased leaving a non-generic
	// method. The methods: 
  //
	//   <B> Integer execute(int a);
  //
  // and
  //
	//   Object execute(int a); 
  //
	// should be compared, and since Object is not subtype of Integer these are not
	// return-type substitutable. In JastAddJ though, it seems like these methods
	// are being compared:
  //
	// <B> Integer execute(int a); and
	// <A> Object  execute(int a);
  //
	// which means it is enough for Integer to be a subtype of Object for the compilation
	// to pass.  
	@FunctionalInterface
	interface Exec extends Y, X<Integer> { }
}
