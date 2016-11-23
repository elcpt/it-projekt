/**
 * 
 */
package de.hdm_stuttgart.huber.itprojekt.shared;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;

/**
 * @author elcpt
 *
 */
public interface Editor extends RemoteService {
	
	public void init() throws IllegalArgumentException;
	
	public String getHelloWorld();
	
	// CRUD-Methoden für NoteBook
	public NoteBook createNoteBook(NoteBook noteBook);
	public NoteBook saveNoteBook(NoteBook noteBook);
	public NoteBook getNoteBookById(NoteBook noteBook);
	public void deleteNoteBook(NoteBook noteBook);
	
	// CRUD-Methoden für Note
	public Note createNote(Note note);
	public Note saveNote(Note note);
	public Note getNoteById(Note note) throws Exception;
	public void deleteNote(Note note);
	
	// Zusätzliche Methoden zu NoteBook
	public Vector<NoteBook> getAllNoteBooks();
	public Vector<Note> getAllNotes() throws BullshitException;
	
}
