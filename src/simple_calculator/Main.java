package simple_calculator;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



/*
 * Start (along with the the controller class) a nice Calculator application
 */
public class Main extends Application
{ 
	
	public void start(Stage stage) throws Exception
	{ 
		Parent root = (Parent) FXMLLoader.load(getClass().getResource("Calculator.fxml")); 
		Scene scene = new Scene(root); 
		stage.setTitle("Calculator"); 
		stage.setScene(scene); 
		stage.setResizable(false);
		stage.show();
	} 
	
	public static void main(String[] args) { 
		launch(args); 
		System.out.println();
	} 
	

}
