package application.view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.MainApp;
import application.model.DBhandler;
import application.model.Notes;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class NotesEditDialogController implements Initializable{

    @FXML
    private TextField titleField;
    @FXML
    private TextArea descArea;
    @FXML
    private Label errorMsg;

    private String newNotes;
    private String tempTitle;
    private String loginId;

	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		errorMsg.setText("");
		descArea.setWrapText(true);
		titleField.addEventFilter(KeyEvent.KEY_TYPED , restrictMaxLength(30));
    }
    
	/**
	 * Restricting Keypress to fix maximum length when Scene Loads
	 */
    public EventHandler<KeyEvent> restrictMaxLength(final Integer max_Lengh) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                TextField txt_TextField = (TextField) e.getSource();                
                if (txt_TextField.getText().length() >= max_Lengh) {                    
                    e.consume();
                }
            }
        };
    } 

	/**
     * Sets the Notes to be edited in the dialog.
     * 
     * @param notes
     */
    public void setNote(Notes note) {
        titleField.setText(note.getNoteTitle());
        descArea.setText(note.getNoteDesc());
        
    }

    /**
     * Called when the user clicks Save.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML
    private void handleOk() throws IOException, SQLException {
    	
    	String newtitle = titleField.getText().toString();
    	if(!newtitle.equals(tempTitle)){
    		newNotes = "NEW";
    	}
    	
        if (isInputValid() && saveNotes()) {
        	//Check conditions
        }
    }

    /**
     * Called when the user clicks cancel.
     * @throws IOException 
     */
    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
    	//mainApp.showHomeScreen(event);
    	/**
    	 * Code commented because Home is now only to go to Home page but not to save the Data
    	 */
    	/*
    	String newtitle = titleField.getText().toString();
    	if(!newtitle.equals(tempTitle)){
    		newNotes = "NEW";
    	}
    	
        if (isInputValid() && saveNotes()) {
        	//Check conditions
        }
    	*/
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/NotesOverview.fxml"));
        AnchorPane homepane = loader.load();
        
        //NotesOverviewController controller = loader.getController();
        //controller.setLoginId(loginId);
        
        Scene home = new Scene(homepane);
        Stage window = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
        
        window.setScene(home);
        window.show();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (titleField.getText() == null || titleField.getText().length() == 0) {
            errorMessage += "Please enter Title!!!"; 
        }
        else if (descArea.getText() == null || descArea.getText().length() == 0) {
            errorMessage += "Please enter Description!!!"; 
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
     * Save or Update database
     * @return boolean
     * @throws SQLException 
     */
    private boolean saveNotes() throws SQLException
    {
    	DBhandler dBhandler = new DBhandler();
    	boolean insertCheck = false;
    	boolean editCheck = false;
    	if (newNotes.equalsIgnoreCase("NEW"))
    	{
    		insertCheck = dBhandler.saveNotesData(titleField.getText().trim(), descArea.getText().trim(), NotesLoginController.loginID);
    		if(insertCheck == false) {
    			errorMsg.setText("Failed to Save or Update");
    			return false;
    		}
    		else
    			return insertCheck;
    	}
    	else if (newNotes.equalsIgnoreCase("EDIT"))
    	{
    		editCheck = dBhandler.editNotesData(titleField.getText().trim(), descArea.getText().trim(), NotesLoginController.loginID);
    		if(editCheck == false) {
    			errorMsg.setText("Failed to Save or Update");
    			return false;
    		}
    		else
    			return editCheck;
		}
    	else
    	{
    		errorMsg.setText("Failed to Save or Update");
    		return false;
    	}
    }
	
    public String getNewNotes() {
		return newNotes;
	}

	public void setNewNotes(String newNotes) {
		this.newNotes = newNotes;
	}
	
	public String getTempTitle() {
		return tempTitle;
	}

	public void setTempTitle(String tempTitle) {
		this.tempTitle = tempTitle;
	}

	/**
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
}
