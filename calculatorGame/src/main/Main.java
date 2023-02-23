package main;

import java.awt.EventQueue;

import backEnd.GameLevel;
import frontEnd.GameUi;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameLevel[] levels = initializeLevels();
		initializeUI(levels);
		
	}
	
	private static void initializeUI(GameLevel[] levels) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameUi window = new GameUi(levels);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static GameLevel[] initializeLevels() {
		GameLevel[] levels = new GameLevel[5];
		
		levels[0] = new GameLevel("Welcome to Evil Calculator!\n"
				+ "Try to create the number 666 using the mystery operators.\n");
		levels[0].setFunc1((x, y) -> x + y);
		
		levels[1] = new GameLevel("Operators can be unary or binary.");
		levels[1].setFunc1((x) -> (x*2) + 5);
		
		levels[2] = new GameLevel("Sometimes buttons will be disabled.", new int[]{3,6,9});
		levels[2].setFunc1((x,y) -> x * y);
		
		levels[3] = new GameLevel("Operators aren't always mathmatical in function");
		levels[3].setIsDecDisabled(true);
		levels[3].setFunc1(LevelFunctions::numDuplicator);
		
		levels[4] = new GameLevel("You must use all operators provided to reach your answer.");
		levels[4].setIsDecDisabled(true);
		levels[4].setFunc1(LevelFunctions::fib);
		levels[4].setFunc2((x) -> x + 56);
		
		return levels;
		
	}
}