package application.view;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.MainApp;
import application.model.DBhandler;
import application.model.Notes;
import application.util.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NotesOverviewController implements Initializable{
    @FXML
    private TableView<Notes> notesTable;
    @FXML
    private TableColumn<Notes, String> idColumn;
    @FXML
    private TableColumn<Notes, String> titleColumn;
    @FXML
    private TableColumn<Notes, String> descColumn;
    
    @FXML
    private TextField searchField;
    @FXML
    private Button searchBtn;
    @FXML
    private Button searchBtnAll;
    @FXML
    private Button newBtn;
    @FXML
    private Button logOUT;
    @FXML
    private Label errorMsg;
 
    @FXML
    private Label footerLabel;
        
    // Reference to the main application.
    private ObservableList<Notes> data;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        // Initialize the Notes table with the two columns.
        titleColumn.setCellValueFactory(
                cellData -> cellData.getValue().noteTitleProperty());
        descColumn.setCellValueFactory(
                cellData -> cellData.getValue().noteDescProperty());
        idColumn.setCellValueFactory(
        		cellData -> cellData.getValue().noteidProperty());
        
        /**
         * Handling double click event on Each Row
         */
        notesTable.setRowFactory(tv -> {
            TableRow<Notes> row = new TableRow<>();
            row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                	if (event.getClickCount() == 2) {
                		//Notes rowData = row.getItem();
                        System.out.println("############### Double Click Successfull ###############");
                        
                        errorMsg.setText("");

                        try {
							Notes selectedNote = notesTable.getSelectionModel().getSelectedItem();
							if (selectedNote != null) {

							    FXMLLoader loader = new FXMLLoader();
							    loader.setLocation(MainApp.class.getResource("view/NotesEditDialog.fxml"));
							    AnchorPane pane = loader.load();
							    
							    NotesEditDialogController controller = loader.getController();
							    controller.setNote(selectedNote);
							    controller.setNewNotes("EDIT");
							    controller.setTempTitle(selectedNote.getNoteTitle().toString());
							    
							    Scene screen2 = new Scene(pane);
							    @SuppressWarnings("unchecked")
								Stage window = (Stage) ((javafx.scene.control.TableRow<Notes>) event.getSource()).getScene().getWindow();
							    
							    window.setScene(screen2);
							    window.show();
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
                }
            });
            return row;
        });
        errorMsg.setText("");
        searchField.setText("");
        
        // Load note details.
        loadNotesData();

    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void loadNotesData()
    {
    	errorMsg.setText("");
    	
      try
      {
        Connection conn = dbConnection.getConnection();
        if(conn != null)
        {
        	System.out.println("###############DB Connected Successfully###############");
        	this.data = FXCollections.observableArrayList();
        	
            String sql = "SELECT * FROM note_table where login_id='" + NotesLoginController.loginID + "' order by title";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
              this.data.add(new Notes(rs.getString(2), rs.getString(3), rs.getString(1)));
            }
        }
        conn.close();
      }
      catch (SQLException e)
      {
        System.err.println("Error " + e);
      }
      this.idColumn.setCellValueFactory(new PropertyValueFactory("noteid"));
      this.titleColumn.setCellValueFactory(new PropertyValueFactory("noteTitle"));
      this.descColumn.setCellValueFactory(new PropertyValueFactory("noteDesc"));
      
      this.notesTable.setItems(null);
      this.notesTable.setItems(this.data);
    }  

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void loadNotesDataSearch(String title)
    {
        errorMsg.setText("");

      try
      {
        Connection conn = dbConnection.getConnection();
        if(conn != null)
        {
        	System.out.println("###############DB Connected Successfully###############");
        	this.data = FXCollections.observableArrayList();
            
        	String sql = "SELECT * FROM note_table where login_id = '" + NotesLoginController.loginID + "' and title like '%"+title+"%'";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
              this.data.add(new Notes(rs.getString(2), rs.getString(3), rs.getString(1)));
            }
        }
        conn.close();
      }
      catch (SQLException e)
      {
        System.err.println("Error " + e);
      }
      this.idColumn.setCellValueFactory(new PropertyValueFactory("noteid"));
      this.titleColumn.setCellValueFactory(new PropertyValueFactory("noteTitle"));
      this.descColumn.setCellValueFactory(new PropertyValueFactory("noteDesc"));
      
      this.notesTable.setItems(null);
      this.notesTable.setItems(this.data);
    } 
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new notes.
     * @throws IOException 
     */
    @FXML
    private void handleNew(ActionEvent event) throws IOException {

    	errorMsg.setText("");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/NotesEditDialog.fxml"));
        AnchorPane editpane = loader.load();
        
        NotesEditDialogController controller = loader.getController();
        controller.setNewNotes("NEW");
        //controller.setLoginId(loginId);
        
        Scene edit = new Scene(editpane);
        Stage window = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
        
        window.setScene(edit);
        window.show();
        
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected notes.
     * @throws IOException 
     */
    @FXML
    private void handleEdit(ActionEvent event) throws IOException 
    {
    	
        errorMsg.setText("");

        Notes selectedNote = notesTable.getSelectionModel().getSelectedItem();
        if (selectedNote != null) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/NotesEditDialog.fxml"));
            AnchorPane pane = loader.load();
            
            NotesEditDialogController controller = loader.getController();
            controller.setNote(selectedNote);
            controller.setNewNotes("EDIT");
            controller.setTempTitle(selectedNote.getNoteTitle().toString());
            
            Scene screen2 = new Scene(pane);
            Stage window = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
            
            window.setScene(screen2);
            window.show();

        } else {
            // Nothing selected.
        	errorMsg.setText("Please select a Note to Edit");
        }
        
    }    
    
    /**
     * Called when the user clicks on the delete button.
     * @throws SQLException 
     */
    @FXML
    private void handleDeleteNotes() throws SQLException 
    {
    	
        errorMsg.setText("");

    	DBhandler dBhandler = new DBhandler();
        Notes selectedNote = notesTable.getSelectionModel().getSelectedItem();
        if (selectedNote != null) {
        	dBhandler.deleteNote(selectedNote.getNoteId().toString());
            loadNotesData();
        } else {
            // Nothing selected.
        	errorMsg.setText("Please select a Note to Delete");
        }
        
    }
    
    
    /**
     * Called when the user clicks on the search button.
     * @throws SQLException 
     */
    @FXML
    private void handleSearchBtn() throws SQLException 
    {
    	
        errorMsg.setText("");
        // Load note details from search field.
    	loadNotesDataSearch(searchField.getText());
    
    }
    
    /**
     * Called when the user clicks on the search All button.
     * @throws SQLException 
     */
    @FXML
    private void handleSearchAll() throws SQLException 
    {
    
    	errorMsg.setText("");
        // Load note details.
        loadNotesData();
        searchField.setText("");
   
    }

    /**
     * Called when the user clicks on the LogOUT button.
     * @throws SQLException 
     */
    @FXML
    private void handleLogOUT(ActionEvent event) throws IOException 
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
