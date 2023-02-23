package main;

class LevelFunctions {

	public static double numDuplicator(double x, double y) {
		String s = "";
		for(int i = 1; i < x; i++) {
			s += y;
		}
		return Double.parseDouble(s);
	}
	
	public static double fib(double x) {
		if(x == 0 || x == 1) {
			return 1;
		}
		else {
			return fib(x-1) + fib(x-2);
		}
	}
}
