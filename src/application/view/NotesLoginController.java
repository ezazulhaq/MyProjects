package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.MainApp;
import application.model.DBhandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NotesLoginController implements Initializable{
	
	@FXML
	private TextField userName;
	@FXML
	private TextField password;
	
	@FXML
	private Button loginBtn;
	
	@FXML
	private Hyperlink newUser;
	@FXML
	private Hyperlink forgotPass;
    @FXML
    private Label errorMsg;
    
    private DBhandler dbhandler = new DBhandler();
    public static String loginID;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
    /**
     * Called when the user clicks the Login button. Opens a dialog to Home scene
     * details of the logIN user notes.
     * @throws IOException 
     */
    @FXML
    private void handleLogin(ActionEvent event) throws IOException 
    {
        errorMsg.setText("");
        if (dbhandler.checkCredentials(userName.getText().toString(), password.getText().toString()))
        {
        	NotesLoginController.loginID = dbhandler.getLoginID(userName.getText());
        	
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/NotesOverview.fxml"));
            AnchorPane pane = loader.load();
            
            //NotesOverviewController controller = loader.getController();
            //controller.setLoginId(dbhandler.getLoginID(userName.getText()));
            
            Scene screen1 = new Scene(pane);
            Stage window = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
            
            window.setScene(screen1);
            window.show();

        } else {
            // Nothing selected.
        	errorMsg.setText("Please Enter Valid Credentials");
        }
        
    }
    
    /**
     * Called when the user clicks the Create New User Link. Opens a dialog to New User creation scene
     * details of the New user notes.
     * @throws IOException 
     */
    @FXML
    private void handleCreateNewUser(ActionEvent event) throws IOException 
    {
        errorMsg.setText("");      	
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/NotesNewUser.fxml"));
        AnchorPane pane = loader.load();
            
        Scene createUser = new Scene(pane);
        Stage window = (Stage) ((javafx.scene.control.Hyperlink) event.getSource()).getScene().getWindow();
            
        window.setScene(createUser);
        window.show();
    }
    
    /**
     * Called when the user clicks the Forgot Password Link. Opens a dialog to Change User password scene
     * details of the New user notes.
     * @throws IOException 
     */
    @FXML
    private void handleForgotPass(ActionEvent event) throws IOException 
    {
        errorMsg.setText("");      	
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/NotesForgotPassword.fxml"));
        AnchorPane pane = loader.load();
            
        Scene forgotPass = new Scene(pane);
        Stage window = (Stage) ((javafx.scene.control.Hyperlink) event.getSource()).getScene().getWindow();
            
        window.setScene(forgotPass);
        window.show();
    }

}
