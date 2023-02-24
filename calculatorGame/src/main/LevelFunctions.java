package main;

class LevelFunctions {

	public static double numDuplicator(double x, double y) {
		String s = "";
		for(int i = 0; i < x; i++) {
			s += (int)y;
		}
		if(s.isBlank()) return 0;
		
		return Double.parseDouble(s);
	}

	public static double fib(double x) {
		double root = Math.sqrt(5);
		double gr = (1 + root) / 2;
		double igr = 1 - gr;
		double val = (Math.pow(gr, x) - Math.pow(igr, x)) / root;
		return Math.floor(val + 0.5);
	}

	public static double findGCD(double x, double y) {
		if(x == 0 || y == 0) {
			throw new ArithmeticException("Arguments can't be zero");
		}
		double r = 0, a, b;
		a = (x > y) ? x : y; // a is greater number
		b = (x < y) ? x : y; // b is smaller number
		r = b;
		while (a % b != 0) {
			r = a % b;
			a = b;
			b = r;
		}
		return r;
	}
}
