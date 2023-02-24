package frontEnd;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;

import backEnd.GameLevel;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Toolkit;

public class GameUi {

	private JFrame frmCalculatorGame;
	private JTextField txtDisplay;
	private JScrollPane scrollPane;
	private JTextArea history;
	private JButton equalsButton;
	private JButton deciButton;
	private JButton ClrButton;
	private JButton[] numButtons = new JButton[10];
	private JButton[] funcButtons = new JButton[3];
	private boolean newCalculation = false;
	
	private GameLevel[] levels;
	private int curLevel = 0;
	
	private final double TARGET_VALUE = 666;

	/**
	 * Create the application.
	 */
	public GameUi(GameLevel[] levels) {
		this.levels = levels;
		initialize();
	}

	public void setVisible(boolean b) {
		frmCalculatorGame.setVisible(b);
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCalculatorGame = new JFrame();
		frmCalculatorGame.setIconImage(Toolkit.getDefaultToolkit().getImage(GameUi.class.getResource("/resources/EvilCalculatorIcon.png")));
		frmCalculatorGame.setFont(new Font("Tahoma", Font.PLAIN, 13));
		frmCalculatorGame.setTitle("Evil Calculator");
		frmCalculatorGame.setResizable(false);
		frmCalculatorGame.getContentPane().setBackground(new Color(128, 128, 128));
		frmCalculatorGame.setBounds(100, 100, 433, 305);
		frmCalculatorGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCalculatorGame.getContentPane().setLayout(null);
		
		txtDisplay = new JTextField();
		txtDisplay.setBounds(10, 10, 215, 28);
		txtDisplay.setEditable(false);
		txtDisplay.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
		frmCalculatorGame.getContentPane().add(txtDisplay);
		txtDisplay.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(234, 11, 177, 249);
		frmCalculatorGame.getContentPane().add(scrollPane);
		
		history = new JTextArea();
		history.setEditable(false);
		history.setWrapStyleWord(true);
		history.setLineWrap(true);
		history.setText("Level " + (curLevel + 1) + "\n" + levels[curLevel].getLvlTxt());
		scrollPane.setViewportView(history);
		
		//=== Number Buttons ===
		numButtons[0] = new JButton("0");
		numButtons[0].setBounds(10, 215, 45, 45);	
		
		numButtons[1] = new JButton("1");
		numButtons[1].setBounds(10, 50, 45, 45);
		
		numButtons[2] = new JButton("2");
		numButtons[2].setBounds(65, 50, 45, 45);
		
		numButtons[3] = new JButton("3");
		numButtons[3].setBounds(120, 50, 45, 45);
		
		numButtons[4] = new JButton("4");
		numButtons[4].setBounds(10, 105, 45, 45);
		
		numButtons[5] = new JButton("5");
		numButtons[5].setBounds(65, 105, 45, 45);
		
		numButtons[6] = new JButton("6");
		numButtons[6].setBounds(120, 105, 45, 45);
		
		numButtons[7] = new JButton("7");
		numButtons[7].setBounds(10, 160, 45, 45);
		
		numButtons[8] = new JButton("8");
		numButtons[8].setBounds(65, 160, 45, 45);
		
		numButtons[9] = new JButton("9");
		numButtons[9].setBounds(120, 160, 45, 45);
		
		int i = 0;
		
		for(JButton butt: numButtons) {
			butt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(newCalculation) {
						txtDisplay.setText("");
						newCalculation = false;
					}
					txtDisplay.setText(txtDisplay.getText() + butt.getText());
				}
			});
			
			butt.setEnabled(!levels[curLevel].isNumDisabled(i));
			frmCalculatorGame.getContentPane().add(butt);
			i++;
		}
		
		// === Function Buttons ===
		
		funcButtons[0] = new JButton("@");
		funcButtons[0].setBounds(175, 50, 50, 45);
		
		funcButtons[1] = new JButton("#");
		funcButtons[1].setBounds(175, 105, 50, 45);
		
		funcButtons[2] = new JButton("&");
		funcButtons[2].setBounds(175, 160, 50, 45);
		
		i = 0;
		for(JButton funButt: funcButtons) {
			funButt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(newCalculation) {
						txtDisplay.setText("");
						newCalculation = false;
					}
					txtDisplay.setText(txtDisplay.getText() + funButt.getText());
				}
			});
			funButt.setEnabled(!levels[curLevel].isFuncDisabled(i));
			frmCalculatorGame.getContentPane().add(funButt);
			i++;
		}
		
		//=== Other Buttons ===
		
		deciButton = new JButton(".");
		deciButton.setBounds(65, 215, 45, 45);
		deciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(newCalculation) {
					txtDisplay.setText("");
					newCalculation = false;
				}
				txtDisplay.setText(txtDisplay.getText() + deciButton.getText());
			}
		});
		deciButton.setEnabled(!levels[curLevel].getIsDecDisabled());
		frmCalculatorGame.getContentPane().add(deciButton);
		
		ClrButton = new JButton("Clr");
		ClrButton.setBounds(175, 215, 50, 45);
		ClrButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtDisplay.setText("");
				newCalculation = false;
			}
		});
		frmCalculatorGame.getContentPane().add(ClrButton);
		
		//=== Equals Button ===
		
		equalsButton = new JButton("=");
		equalsButton.setBounds(120, 215, 45, 45);
		equalsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double result;
				if(newCalculation) {
					txtDisplay.setText("");
					newCalculation = false;
				}
				String calcTxt = txtDisplay.getText();
				if(calcTxt.isBlank()) return;
				newCalculation = true;
				try {
					result = levels[curLevel].runCalculation(calcTxt);
					txtDisplay.setText(String.valueOf(result));
					
					//Check Result
					if(result == TARGET_VALUE && 
							(calcTxt.contains("@") || levels[curLevel].isFuncDisabled(0)) &&
							(calcTxt.contains("#") || levels[curLevel].isFuncDisabled(1)) &&
							(calcTxt.contains("&") || levels[curLevel].isFuncDisabled(2))
							) {
						if(curLevel+1 == levels.length) {
							txtDisplay.setText("You Win!");
							equalsButton.setEnabled(false);
							return;
						}
						else {
							txtDisplay.setText("Good Job!");
							history.setText("Level " + (++curLevel + 1) + "\n" + levels[curLevel].getLvlTxt());
							refreshButtons();
						}
					} 
					else {
						history.setText(calcTxt + "=" + result + "\n" + history.getText());
					}
					
				} 
				catch(ArithmeticException ex) {
					txtDisplay.setText("ERROR");
					history.setText(calcTxt + "=ERR" + "\n" + history.getText());
				}
				catch(Exception ex) {
					txtDisplay.setText("ERROR");
					ex.printStackTrace();
				}
				finally {
					history.setCaretPosition(0);
				}
			}
		});
		frmCalculatorGame.getContentPane().add(equalsButton);
	}
	
	private void refreshButtons() {
		for(int i = 0; i < numButtons.length; i++) {
			JButton butt = numButtons[i];
			butt.setEnabled(!levels[curLevel].isNumDisabled(i));
		}
		for(int i = 0; i < funcButtons.length; i++) {
			JButton butt = funcButtons[i];
			butt.setEnabled(!levels[curLevel].isFuncDisabled(i));
		}
		deciButton.setEnabled(!levels[curLevel].getIsDecDisabled());
	}
}
