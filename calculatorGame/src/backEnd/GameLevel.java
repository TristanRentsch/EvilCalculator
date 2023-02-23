package backEnd;

import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;


public class GameLevel {
	
	// Functions are: 1:@, 2:#, 3:&
	private DoubleBinaryOperator[] binFuncs = new DoubleBinaryOperator[3];
	private DoubleUnaryOperator[] unFuncs = new DoubleUnaryOperator[3];

	private boolean[] numdisabled = new boolean[10];
	private boolean isDecDisabled = false;
	
	private String lvlTxt; //Shown before start of level
	
	public GameLevel(String lvlTxt, int[] disabledNums){
		this.lvlTxt = lvlTxt;
		
		for(int i: disabledNums) {
			numdisabled[i] = true;
		}
	}

	public GameLevel(String lvlTxt) {
		this.lvlTxt = lvlTxt;
	}
	
	public String getLvlTxt() {
		return lvlTxt;
	}
	
	// === Utility Methods ===
	
	private boolean isFuncUnary(int funcNum) {
		return unFuncs[funcNum-1] != null;
	}
	
	public boolean isNumDisabled(int num) {
		return numdisabled[num];
	}
	
	public boolean isFuncDisabled(int funcNum) {
		return unFuncs[funcNum-1] == null && binFuncs[funcNum-1] == null;
	}
	public void setIsDecDisabled(boolean b) {
		isDecDisabled = b;
	}
	
	public boolean getIsDecDisabled() {
		return isDecDisabled;
	}
	
	// === Set Methods ===
	
	public void setFunc1(DoubleBinaryOperator func1) {
		unFuncs[0] = null;
		binFuncs[0] = func1;
	}
	public void setFunc1(DoubleUnaryOperator func1) {
		binFuncs[0] = null;
		unFuncs[0] = func1;
	}
	
	public void setFunc2(DoubleBinaryOperator func2) {
		unFuncs[1] = null;
		binFuncs[1] = func2;
	}
	public void setFunc2(DoubleUnaryOperator func2) {
		binFuncs[1] = null;
		unFuncs[1] = func2;
	}
	
	public void setFunc3(DoubleBinaryOperator func3) {
		unFuncs[2] = null;
		binFuncs[2] = func3;
	}
	public void setFunc3(DoubleUnaryOperator func3) {
		binFuncs[2] = null;
		unFuncs[2] = func3;
	}
	
	// === Run Calculation ===
	// Operators are: 1:@, 2:#, 3:&
	
	//Call function if enabled, and given proper number of arguments
	//Otherwise throw exception
	private double runCalculationHelper(int funcNum, double arg1, String[] dataStr) {
		String arg2 = dataStr.length >= 2 ? dataStr[1] : "";
		if(isFuncDisabled(funcNum)) {
			throw new ArithmeticException("Operator is disabled");
		}
		else if(!isFuncUnary(funcNum) && !(arg2.equals("@") || arg2.equals("#") || arg2.equals("&") || arg2.equals(""))) {
			return runCalculation(binFuncs[funcNum-1].applyAsDouble(arg1, Double.parseDouble(arg2)),
					Arrays.copyOfRange(dataStr, 2, dataStr.length));
		}
		else if(isFuncUnary(funcNum) && (arg2.equals("@") || arg2.equals("#") || arg2.equals("&") || arg2.equals(""))) {
			return runCalculation(unFuncs[funcNum-1].applyAsDouble(arg1),
					Arrays.copyOfRange(dataStr, 1, dataStr.length));
		}
		else {
			throw new ArithmeticException("Operator has incorrect number of arguments");
		}
	}
	
	//Define base case, call appropriate functions, and initialize total with first argument
	//If no first argument is given, assume zero
	private double runCalculation(double total, String[] dataStr) {
		if(dataStr.length == 0) {
			return total;
		}
		switch(dataStr[0]) {
			case "@":
				return runCalculationHelper(1, total, dataStr);
			case "#":
				return runCalculationHelper(2, total, dataStr);
			case "&":
				return runCalculationHelper(3, total, dataStr);
			default: 
				return runCalculation(Double.parseDouble(dataStr[0]), 
						Arrays.copyOfRange(dataStr, 1, dataStr.length));
		}
	}
	
	//Convert input string into arr and pass on to main runCalculation function
	public double runCalculation(String str) {
		String[] strArr = str.split("((?<=@)|(?=@)|(?<=#)|(?=#)|(?<=&)|(?=&))");
		return runCalculation(0,strArr);
	}
}
