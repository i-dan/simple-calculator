package simple_calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


/*
 * Calculator GUI controller class.
 * manages a calculator GUI interface 
 * 
 * p.s. we chose to use textField although a label might be a better option,
 * we have done this since we wanted to allow typing support later
 */
public class CalculatorController 
{
	private Calculator calculator;
	
    @FXML
    private TextField display;
    
    
    /*
     * initialization operations
     */
    public void initialize()
    {
    	calculator = new Calculator();
    	display.setText(calculator.getInitialText());    	
    }

    
    
    /*
     * 
     * FUNCTIONS TO MANAGE THE CURRENT USER PRESS
     */
    @FXML
    void numberPressed(ActionEvent event) 
    {
    	display.setText(calculator.addDigit((((Button)event.getSource())).getText().charAt(0)));
    }
    
    
    @FXML
    void clearPressed(ActionEvent event) 
    {
    	display.setText(calculator.clear());
    }

    @FXML
    void dividePressed(ActionEvent event) 
    {
    	display.setText(calculator.division());
    }

    @FXML
    void dotPressed(ActionEvent event) 
    {
    	display.setText(calculator.addDot());
    }

    
    @FXML
    void equalsPressed(ActionEvent event) 
    {
    	display.setText(calculator.equals());
    }

    @FXML
    void invertSignPressed(ActionEvent event) 
    {
    	display.setText(calculator.invertSign());
    }

    @FXML
    void minusPressed(ActionEvent event) 
    {
    	display.setText(calculator.minus());
    }

    @FXML
    void multPressed(ActionEvent event) 
    {
    	display.setText(calculator.mult());
    }

    @FXML
    void plusPressed(ActionEvent event) 
    {
    	display.setText(calculator.plus());
    }
}

