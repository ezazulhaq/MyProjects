package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Notes {
	
    private final StringProperty noteid;
	private final StringProperty noteTitle;
    private final StringProperty noteDesc;
    
    /**
     * Default constructor.
     */
    public Notes() {
        this(null, null, null);
    }
    
    /**
     * Constructor with some initial data.
     * 
     * @param noteid
     * @param noteTitle
     * @param noteDesc
     */
    public Notes(String noteTitle, String noteDesc, String id) {
        this.noteTitle = new SimpleStringProperty(noteTitle);
        this.noteDesc = new SimpleStringProperty(noteDesc);
        this.noteid = new SimpleStringProperty(id);
   }
    
	public final StringProperty noteTitleProperty() {
		return this.noteTitle;
	}
	

	public final String getNoteTitle() {
		return this.noteTitleProperty().get();
	}
	

	public final void setNoteTitle(final String noteTitle) {
		this.noteTitleProperty().set(noteTitle);
	}
	

	public final StringProperty noteDescProperty() {
		return this.noteDesc;
	}
	

	public final String getNoteDesc() {
		return this.noteDescProperty().get();
	}
	

	public final void setNoteDesc(final String noteDesc) {
		this.noteDescProperty().set(noteDesc);
	}
	
    
	public final StringProperty noteidProperty() {
		return this.noteid;
	}
	

	public final String getNoteId() {
		return this.noteidProperty().get();
	}
	

	public final void setNoteId(final String noteId) {
		this.noteidProperty().set(noteId);
	}

}
