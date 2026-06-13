// Method descriptor parameter length is at most 255.
// double and long count twice toward the parameter list length in this check
// This class is just below the limit for number of long parameters in the constructor.
// This class has 127 long parameters and 1 implicit this = 127*2+1 = 255
// .result=COMPILE_PASS
class Test {
	Test(long i001, long i002, long i003, long i004, long i005, long i006, long i007, long i008, long i009, long i010,
long i011, long i012, long i013, long i014, long i015, long i016, long i017, long i018, long i019, long i020,
long i021, long i022, long i023, long i024, long i025, long i026, long i027, long i028, long i029, long i030,
long i031, long i032, long i033, long i034, long i035, long i036, long i037, long i038, long i039, long i040,
long i041, long i042, long i043, long i044, long i045, long i046, long i047, long i048, long i049, long i050,
long i051, long i052, long i053, long i054, long i055, long i056, long i057, long i058, long i059, long i060,
long i061, long i062, long i063, long i064, long i065, long i066, long i067, long i068, long i069, long i070,
long i071, long i072, long i073, long i074, long i075, long i076, long i077, long i078, long i079, long i080,
long i081, long i082, long i083, long i084, long i085, long i086, long i087, long i088, long i089, long i090,
long i091, long i092, long i093, long i094, long i095, long i096, long i097, long i098, long i099, long i100,
long i101, long i102, long i103, long i104, long i105, long i106, long i107, long i108, long i109, long i110,
long i111, long i112, long i113, long i114, long i115, long i116, long i117, long i118, long i119, long i120,
long i121, long i122, long i123, long i124, long i125, long i126, long i127) {
	}
}
