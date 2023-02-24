package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backEnd.GameLevel;

class GameLevelTesting {

	GameLevel testLvl;
	
	@BeforeEach
	public void setUp() throws Exception {
		testLvl = new GameLevel("Testing", new int[]{1,4,0});
	}

	@Test
	public void testGetLvlTxt() {
		assertEquals(testLvl.getLvlTxt(), "Testing");
	}
	
	@Test
	public void testIsNumDisabled_returnsNumDisabledStatus() {
		assertTrue(testLvl.isNumDisabled(1));
		assertTrue(testLvl.isNumDisabled(4));
		assertTrue(testLvl.isNumDisabled(0));
		
		assertFalse(testLvl.isNumDisabled(2));
		assertFalse(testLvl.isNumDisabled(5));
		assertFalse(testLvl.isNumDisabled(9));
	}
	
	@Test
	public void testIsFuncDisabled_returnsFuncDisabledStatus() {
		DoubleUnaryOperator op1 = (double x) -> x + 1;
		DoubleBinaryOperator op2 = (double x, double y) -> x + y;
		
		testLvl.setFunc2(op1);
		testLvl.setFunc3(op2);
		
		assertTrue(testLvl.isFuncDisabled(0));
		assertFalse(testLvl.isFuncDisabled(1));
		assertFalse(testLvl.isFuncDisabled(2));
	}
	
	// === runCalculationTests ===
	// Operators are: 1:@, 2:#, 3:&
	
	@Test
	public void testRunCalculation_withValidCalculationString_andUnaryOperator() {
		DoubleUnaryOperator op1 = (double x) -> x + 1;
		testLvl.setFunc1(op1);
		
		String calcStr = "1@";
		double expectedValue = 2;
		double actualValue = testLvl.runCalculation(calcStr);
		
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	public void testRunCalculation_withValidCalculationString_andBinaryOperator() {
		DoubleBinaryOperator op1 = (double x, double y) -> x + y;
		testLvl.setFunc1(op1);
		
		String calcStr = "1@2";
		double expectedValue = 3;
		double actualValue = testLvl.runCalculation(calcStr);
		
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	public void testRunCalculation_withValidCalculationString_andUnaryandBinaryOperators() {
		DoubleUnaryOperator op1 = (double x) -> x + 1;
		DoubleBinaryOperator op2 = (double x, double y) -> x + y;
		DoubleUnaryOperator op3 = (double x) -> x * x;
		
		//With unary op first
		testLvl.setFunc1(op1);
		testLvl.setFunc2(op2);
		
		String calcStr = "1@#2";
		double expectedValue = 4;
		double actualValue = testLvl.runCalculation(calcStr);
		
		assertEquals(expectedValue, actualValue);
		
		//With binary op first
		testLvl.setFunc1(op2);
		testLvl.setFunc2(op1);
		
		calcStr = "1@2#";
		actualValue = testLvl.runCalculation(calcStr);
		
		assertEquals(expectedValue, actualValue);
		
		//With 3 ops
		testLvl.setFunc3(op3);
		
		calcStr = "1@2#&";
		expectedValue = 16;
		actualValue = testLvl.runCalculation(calcStr);
		
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	public void testRunCalculation_withoutFirstArgumentInCalculationString_thenUsesZeroAsFirstArgument() {
		DoubleBinaryOperator op1 = (double x, double y) -> x + y;
		DoubleUnaryOperator op2 = (double x) -> x + 1;
		
		testLvl.setFunc1(op1);
		testLvl.setFunc2(op2);
		
		String calcStr = "@1";
		double expectedValue = 1;
		double actualValue = testLvl.runCalculation(calcStr); 
		
		assertEquals(expectedValue, actualValue);
		
		calcStr = "#";
		actualValue = testLvl.runCalculation(calcStr);
		
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	public void testRunCalculation_whenGivenOperatorsWithIncorrectNumberOfArguments_throwsException() {
		DoubleBinaryOperator op1 = (double x, double y) -> x + y;
		DoubleUnaryOperator op2 = (double x) -> x + 1;
		
		testLvl.setFunc1(op1);
		testLvl.setFunc2(op2);
		
		String calcStr = "1@";
		
		assertThrows(ArithmeticException.class, () -> testLvl.runCalculation(calcStr), "Operator has incorrect number of arguments");
	
		String calcStr2 = "1#2";
		
		assertThrows(ArithmeticException.class, () -> testLvl.runCalculation(calcStr2), "Operator has incorrect number of arguments");
	}
	
	@Test
	public void testRunCalculation_withDisabledOperators_throwsException() {
		
		String calcStr = "1@";
		
		assertThrows(ArithmeticException.class, () -> testLvl.runCalculation(calcStr), "Operator is disabled");
	
		String calcStr2 = "1#2";
		
		assertThrows(ArithmeticException.class, () -> testLvl.runCalculation(calcStr2), "Operator is disabled");
	}
}
