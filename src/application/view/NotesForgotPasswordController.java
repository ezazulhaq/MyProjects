package application.view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.MainApp;
import application.model.DBhandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NotesForgotPasswordController implements Initializable {
	
	@FXML
	private TextField userName;
	@FXML
	private TextField newpassword;
	
	@FXML
	private Button changePass;
	@FXML
	private Button loginscene;
	
    @FXML
    private Label errorMsg;

    private DBhandler dbhandler = new DBhandler();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
    /**
     * Called when the user clicks the Login button. Opens a dialog to Home scene
     * details of the logIN user notes.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML
    private void handleForgotPass(ActionEvent event) throws IOException, SQLException 
    {
        errorMsg.setText("");
        if (isInputValid())
        {        	
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/NotesLogin.fxml"));
            AnchorPane pane = loader.load();
            
            Scene login = new Scene(pane);
            Stage window = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
            
            window.setScene(login);
            window.show();

        } else {
            // Nothing selected.
        	//errorMsg.setText("Please Enter Valid Credentials");
        } 
    }
    
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     * @throws SQLException 
     */
    private boolean isInputValid() throws SQLException {
        String errorMessage = "";

        if (userName.getText() == null || userName.getText().length() == 0) {
            errorMessage += "Please enter User Name!!!"; 
        }
        else if (newpassword.getText() == null || newpassword.getText().length() == 0) {
            errorMessage += "Please enter New Password!!!"; 
        }
        else if (userName.getText().length() > 0 && !dbhandler.checkValidUser(userName.getText())){
    		errorMessage += "User Name Doesn't Exits";
        }
        
        if (errorMessage.length() == 0) {
        	errorMsg.setText("");
            return true;
        } else {
            // Show the error message.
            errorMsg.setText(errorMessage);
            return false;
        }
    }

    /**
     * Called when the user clicks on the Cancel button for going to Login Scene.
     * @throws SQLException 
     */
    @FXML
    private void handleCancel(ActionEvent event) throws IOException 
    {

    	errorMsg.setText("");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/NotesLogin.fxml"));
        AnchorPane loginpane = loader.load();
        
        NotesLoginController.loginID = "";
        
        Scene login = new Scene(loginpane);
        Stage window = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
        
        window.setScene(login);
        window.show();
        
    }
}
