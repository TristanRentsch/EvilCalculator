package main;

class LevelFunctions {

	public static double numDuplicator(double x, double y) {
		String s = "";
		for(int i = 1; i < x; i++) {
			s += (int)y;
		}
		return Double.parseDouble(s);
	}
	
	public static double fib(double x) {
		double root = Math.sqrt(5);
		double gr = (1 + root) / 2;
		double igr = 1 - gr;
		double val = (Math.pow(gr, x) - Math.pow(igr, x)) / root;
		return Math.floor(val + 0.5);
	}
	
	
}
