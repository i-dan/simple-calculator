package simple_calculator;



/*
 * Calculator:
 * manage a simple calculator processor. (+, * , /  are supported. no backspace and no parenthesis)
 * contains functions to add  the following characters:
 * a digit, supported mathematical signs, decimal point (refers to as dot), and equals.
 * each functions returns the mathematical expression currently being processed.
 * 
 * we could have manage the class in other ways, probably simpler, (such as keep only the current outcome
 * and process partial expressions one by one), but we wanted to expand the class to support 
 * much more functions and utilities, unfortunately "what the heart wants the time takes" :\    
 */
public class Calculator 
{
    private String output;
    private boolean precedingZero; // we are after the first digit in a number, and it's zero
    private boolean firstDigit; // true: yet not digit has been typed for the current operation 
    private boolean isDivisor; // current operation is division
    private boolean dot; // decimal point has been used for this number
    private boolean beginning; // cursor on the beginning of the input 
    
       
    /*
     * Constructor
     */
    public Calculator()
    {
    	beginning = true;
    	precedingZero = true;
        firstDigit = false;
        isDivisor = false;
        dot = false;
        output = "0";
    }
    
    
    /*
     * return a String with the initial text to display to the user
     */
    public String getInitialText() 
	{
		return output;
	}
    
    
    /*
     * process the next digit
     * return a String with the text to display (mathematical expression being processed <so far>)  
     */
    public String addDigit(char c)
    {
    	if(precedingZero)
    	{
    		if(c != '0')
    		{
    			output = output.substring(0, output.length()-1) + c; // it's safe since we always keep output's length >= 1
    			beginning = false;
    			precedingZero = false;
    		}
    		// if c = 0 all stay the same, don't change anything
    	}
       	else	// we have another preceding digit or  
    	{
    		if(firstDigit)
    		{
	    		if(c == '0')
	    			precedingZero = true;
	    		firstDigit = false;
    		}
    		output += c;
    	}
    	
    	return output;
    }
    
    
    
    /*
     * add a decimal point to the current expression
     * return a String with the text to display (mathematical expression being processed <so far>)
     */
    public String addDot()
    {
    	if(firstDigit) // we are toward the first digit of a number, add "0." instead of merely dot
    	{
    		output += "0.";
    		firstDigit = false;
    		dot = true;
    	}
    	else if(dot)
    	{
    		System.out.println("You can't use more than one decimal point");
    	}
    	else // we are after a digit (in the current operand)
    	{
    		precedingZero = false;
    		beginning = false;
    		dot = true;
    		output += '.';
    	}
    	
    	return output;
    }
    
    
    /*
     * clear the expression set it to the initial text
     */
    public String clear()
    {
    	beginning = true;
    	precedingZero = true;
        firstDigit = false;
        isDivisor = false;
        dot = false;
        output = "0";
        
        return output;
    }
    
    /*
     * process minus operation
     * return a String with the text to display (mathematical expression being processed <so far>)  
     */
    public String minus()    
    {    	
    	if(!firstDigit)
    	{
    		if(isDivisor) 
				if(isCurrentOperandZero())
					throw new ArithmeticException("Divsion by zero");
    		beginning = false;
    	    isDivisor = false;
        	precedingZero = false;
            firstDigit = true;
            dot = false;
        	output += '-';
    	}
    	else
    		output = output.substring(0, output.length()-1) + '-';

    	return output;
    }
    
    
    
    /*
     * process division operation
     * return a String with the text to display (mathematical expression being processed <so far>)  
     */
    public String division()
    {
    	if(!firstDigit)
    	{
			beginning = false;
	        isDivisor = true;
	    	precedingZero = false;
	        firstDigit = true;
	        dot = false;
	        output += '/';
    	}
    	else if(!beginning)		// firstDigit = true -> replace the former operation with division
    		output = output.substring(0, output.length()-1) + '/';
    	// else it's beginning -> do nothing
    	
    	return output;
    }
    
    
    /*
     * process invert-sign "+/-" operation.
     * return a String with the text to display (mathematical expression being processed <so far>)  
     */
    public String invertSign()
    {
    	// the calculator doe's not support expressions of the form "z*-y" so we have to invert the sign at
    	// the first possible place (where it's a minus/plus sign)
    	int i = lastIndexOfPlusOrMinus(output); 
    		
    	if(i < 0) // add minus sign at the beginning
    	{
			output = '-' + output;
			return output;
    	}
		StringBuilder sb = new StringBuilder(output);
		
		if(output.charAt(i) == '+')
			sb.setCharAt(i, '-');
		else if(output.charAt(i) == '-') // take care if i = 0*****************************
			if( i > 0)
				sb.setCharAt(i, '+');
			else
				sb.deleteCharAt(0); // remove the preceding minus
		output = sb.toString();
		
		return output;
    }
    
    
    /*
     * process multiplication operation
     * return a String with the text to display (mathematical expression being processed <so far>)  
     */
    public String mult() 
    {
    	if(beginning)
    		return output;
    	
    	if(firstDigit)
    	{
    		isDivisor = false;
    		output = output.substring(0, output.length()-1) + 'ª';
    	}
    	else
    	{
    		if(isDivisor) 
				if(isCurrentOperandZero())
					throw new ArithmeticException("Divsion by zero");
    		precedingZero = false;
            firstDigit = true;
            isDivisor = false;
            dot = false;
    		output += 'ª';
    	}
    	return output;
    }

    
    /*
     * process plus operation
     * return a String with the text to display (mathematical expression being processed <so far>)  
     */
    public String plus() 
    {
      	if(beginning)
    		return output;
    	
    	if(firstDigit)
    	{
    		isDivisor = false;
    		output = output.substring(0, output.length()-1) + '+';
    	}
    	else
    	{
    		if(isDivisor) 
				if(isCurrentOperandZero())
					throw new ArithmeticException("Divsion by zero");
    		precedingZero = false;
            firstDigit = true;
            isDivisor = false;
            dot = false;
    		output += '+';
    	}
    	return output;
    }

    
    /*
     * process equals operation. 
     * return a String with the outcome to display (mathematical expression being processed <so far>)  
     */
    public String equals() 
    {
    	if(beginning)
    		return output;
    	if(firstDigit)		// last char is operation: policy is to treat it as an "equals" sign
    						// that's to remove the operation sign and perform equals
    		output = output.substring(0, output.length()-1);
    	
    	else if(isDivisor) 
			if(isCurrentOperandZero())
				throw new ArithmeticException("Divsion by zero");
    	
    	double outcome = evaluateExpression(output);
    	
    	if((int)outcome == outcome) // outcome is an integer
    		output = "" + (int)outcome;
    	else
    		output = "" + outcome;
    	// update flags
    	isDivisor = false;
    	if(outcome == 0)
    	{
    		precedingZero = true;
    		beginning = true;
    	}
    	dot = (int)outcome == outcome? false : true;
    	
    	return output;
    }
	
	
	/*
	 * evaluate mathematical expression formalized by the rules of this class.
	 * you should use it only with output (since it always manage its string according to the rules)
	 * otherwise you may enter a string with the wrong form - which result in unpredictable answer 
	 */
    private double evaluateExpression(String s) 
    {
    	double d = 0;
    	String[] termsStrings = s.split("(?=[+-])");
    
    	double temp;
    	for(String t : termsStrings)
    	{
        	String[] factorsStrings = t.split("[ª/]");
        	
        	char[] signs = new char[factorsStrings.length-1];
        	int j = 0;
        	for(char c : t.toCharArray())
        		if(c == 'ª' || c == '/')
        			signs[j++] = c;
        	
        	temp = Double.parseDouble(factorsStrings[0]);
        	for(int i = 1; i < factorsStrings.length; i++)
        		temp = (signs[i-1] == '/')? temp/Double.parseDouble(factorsStrings[i]) : temp*Double.parseDouble(factorsStrings[i]);
        	
        	d += temp;
    	}
   
    	return d;
	}
    
    /* 
     * return if the current operand is zero
     * assumption is firstDigit == false (otherwise it's pointless to check..)
     */
    private boolean isCurrentOperandZero()
    {
    	int i = lastIndexOfOperation(output); 
		return (Double.parseDouble(output.substring(i+1)) == 0);
    }

	
    /*
     * Intended for mathematical expression formalized by the rules of this class.
     * return the last index of operation. return -1 if with absence
     */
	private int lastIndexOfOperation(String s)
	{
		int i; 
    	for(i = s.length()-1; i > -1; i--)
    	{
    		char c = s.charAt(i);
    		if(c == '/' || c == '+'|| c == '-'|| c == 'ª')
    			return i;
    	}
    	return i; // i = -1
	}
	
	
    /*
     * Intended for mathematical expression formalized by the rules of this class.
     * return the last index of plus or minus sign. return -1 if with absence
     */
	private int lastIndexOfPlusOrMinus(String s)
	{
		int i; 
    	for(i = s.length()-1; i > -1; i--)
    	{
    		char c = s.charAt(i);
    		if(c == '+'|| c == '-')
    			return i;
    	}
    	return i; // i = -1
	}
}
