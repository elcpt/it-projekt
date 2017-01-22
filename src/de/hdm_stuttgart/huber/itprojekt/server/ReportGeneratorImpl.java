package de.hdm_stuttgart.huber.itprojekt.server;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hdm_stuttgart.huber.itprojekt.server.db.NoteBookMapper;
import de.hdm_stuttgart.huber.itprojekt.server.db.NoteMapper;
import de.hdm_stuttgart.huber.itprojekt.server.db.PermissionMapper;
import de.hdm_stuttgart.huber.itprojekt.server.db.UserInfoMapper;
import de.hdm_stuttgart.huber.itprojekt.shared.Editor;
import de.hdm_stuttgart.huber.itprojekt.shared.PermissionService;
import de.hdm_stuttgart.huber.itprojekt.shared.ReportGenerator;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllNotebooksR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllNotesR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllPermissionsR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllUserNotebooksR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllUserNotesR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllUserPermissionsR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.Column;
import de.hdm_stuttgart.huber.itprojekt.shared.report.CustomReport;
import de.hdm_stuttgart.huber.itprojekt.shared.report.Row;
import de.hdm_stuttgart.huber.itprojekt.shared.report.SimpleParagraph;
import de.hdm_stuttgart.huber.itprojekt.shared.report.SimpleReport;

/**
 * Implementierung des <code>ReportGenerator</code>-Interface. Die technische
 * Realisierung bzgl. <code>RemoteServiceServlet</code> bzw. GWT RPC erfolgt
 * analog zu {@lBankAdministrationImplImpl}. Für Details zu GWT RPC siehe dort.
 * 
 * @see ReportGenerator
 * @author thies
 */
@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {

	/**
	 * Ein ReportGenerator benötigt Zugriff auf die BankAdministration, da diese
	 * die essentiellen Methoden für die Koexistenz von Datenobjekten (vgl.
	 * bo-Package) bietet.
	 */
	private Editor editor = null;

	/**
	 * <p>
	 * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
	 * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu
	 * ist ein solcher No-Argument-Konstruktor anzulegen. Ein Aufruf eines
	 * anderen Konstruktors ist durch die Client-seitige Instantiierung durch
	 * <code>GWT.create(Klassenname.class)</code> nach derzeitigem Stand nicht
	 * möglich.
	 * </p>
	 * <p>
	 * Es bietet sich also an, eine separate Instanzenmethode zu erstellen, die
	 * Client-seitig direkt nach <code>GWT.create(Klassenname.class)</code>
	 * aufgerufen wird, um eine Initialisierung der Instanz vorzunehmen.
	 * </p>
	 */
	public ReportGeneratorImpl() throws IllegalArgumentException {
	}

	/**
	 * Initialsierungsmethode. Siehe dazu Anmerkungen zum
	 * No-Argument-Konstruktor.
	 *
	 * @see #ReportGeneratorImpl()
	 */
	@Override
	public void init() throws IllegalArgumentException {
		/*
		 * Ein ReportGeneratorImpl-Objekt instantiiert für seinen Eigenbedarf
		 * eine BankVerwaltungImpl-Instanz.
		 */
		EditorImpl a = new EditorImpl();
		a.init();
		this.editor = a;
	}

	/**
	 * Auslesen der zugehörigen BankAdministration (interner Gebrauch).
	 *
	 * @return das BankVerwaltungsobjekt
	 */
	protected Editor getNoteBookVerwaltung() {
		return this.editor;
	}

	/**
	 * Hinzufügen des Report-Impressums. Diese Methode ist aus den
	 * <code>create...</code>-Methoden ausgegliedert, da jede dieser Methoden
	 * diese Tätigkeiten redundant auszuführen hätte. Stattdessen rufen die
	 * <code>create...</code>-Methoden diese Methode auf.
	 *
	 * @param r
	 *            der um das Impressum zu erweiternde Report.
	 *
	 */

	@Override
	public AllUserNotebooksR createAllUserNotebooksR() throws IllegalArgumentException {

		UserInfo u = editor.getCurrentUser();

		Vector<NoteBook> allNoteBooksForUserId = NoteBookMapper.getNoteBookMapper().getAllNoteBooksForUserId(u.getId());

		AllUserNotebooksR report = new AllUserNotebooksR();
		report.setTitle("All notebooks from User");
		report.setCreated(new Date(System.currentTimeMillis()));

		StringBuilder sb = new StringBuilder();
		sb.append("Number of notebooks:");
		sb.append(allNoteBooksForUserId.size());
		report.setHeaderData(new SimpleParagraph(sb.toString()));

		Row headline = new Row();
		headline.addColumn((new Column("Title")));
		headline.addColumn((new Column("Subtitle")));
		headline.addColumn((new Column("Username")));
		headline.addColumn((new Column("Creation Date")));
		headline.addColumn((new Column("Modification Date")));
		report.addRow(headline);

		for (NoteBook element : allNoteBooksForUserId) {

			Row r = new Row();
			r.addColumn(new Column(element.getTitle()));
			r.addColumn(new Column(element.getSubtitle()));
			r.addColumn(new Column(element.getOwner().getNickname()));
			r.addColumn(new Column(element.getCreationDate().toString()));
			r.addColumn(new Column(element.getModificationDate().toString()));

			report.addRow(r);

		}

		report.setImprint(new SimpleParagraph("Lorem ipsum sit dolor amet"));

		return report;

	}

	@Override
	public AllNotebooksR createAllNotebooksR() throws IllegalArgumentException {

		Vector<NoteBook> allNoteBooks = NoteBookMapper.getNoteBookMapper().getAllNoteBooks();

		AllNotebooksR report2 = new AllNotebooksR();
		report2.setTitle("All notebooks of all User");
		report2.setCreated(new Date(System.currentTimeMillis()));

		StringBuilder sb = new StringBuilder();
		sb.append("Number of Notebooks:");
		sb.append(allNoteBooks.size());
		report2.setHeaderData(new SimpleParagraph(sb.toString()));

		Row headline = new Row();
		headline.addColumn((new Column("Title")));
		headline.addColumn((new Column("Subtitle")));
		headline.addColumn((new Column("Username")));
		headline.addColumn((new Column("Creation Date")));
		headline.addColumn((new Column("Modification Date")));
		report2.addRow(headline);

		for (NoteBook element : allNoteBooks) {

			Row r = new Row();
			r.addColumn(new Column(element.getTitle()));
			r.addColumn(new Column(element.getSubtitle()));
			r.addColumn(new Column(element.getOwner().getNickname()));
			r.addColumn(new Column(element.getCreationDate().toString()));
			r.addColumn(new Column(element.getModificationDate().toString()));

			report2.addRow(r);

		}

		report2.setImprint(new SimpleParagraph("Lorem ipsum sit dolor amet"));

		return report2;
	}

	
	@Override
	public AllUserNotesR createAllUserNotesR() throws IllegalArgumentException {
		
		UserInfo u = editor.getCurrentUser();
		return createAllUserNotesReportFor(u);
		
	}
	
	public AllUserNotesR createAllUserNotesReportFor(UserInfo u) throws IllegalArgumentException {

		

		Vector<Note> allNotesForUserId = new Vector<>();
		
		allNotesForUserId = NoteMapper.getNoteMapper().getAllNotesForUserId(u.getId());
	
		AllUserNotesR report2 = new AllUserNotesR();
		report2.setTitle("All Notes of current User");
		report2.setCreated(new Date(System.currentTimeMillis()));

		StringBuilder sb = new StringBuilder();
		sb.append("Number of notes:");
		sb.append(allNotesForUserId.size());
		report2.setHeaderData(new SimpleParagraph(sb.toString()));

		Row headline = new Row();
		headline.addColumn((new Column("Title")));
		headline.addColumn((new Column("Subtitle")));
		headline.addColumn((new Column("Username")));
		headline.addColumn((new Column("Creation Date")));
		headline.addColumn((new Column("Modification Date")));
		report2.addRow(headline);

		for (Note element : allNotesForUserId) {

			Row r = new Row();
			r.addColumn(new Column(element.getTitle()));
			r.addColumn(new Column(element.getSubtitle()));
			r.addColumn(new Column(element.getOwner().getNickname()));
			r.addColumn(new Column(element.getCreationDate().toString()));
			r.addColumn(new Column(element.getModificationDate().toString()));

			report2.addRow(r);

		}

		report2.setImprint(new SimpleParagraph("Lorem ipsum sit dolor amet"));

		return report2;

	}

	@Override
	public AllNotesR createAllNotesR() throws IllegalArgumentException {
		Vector<Note> allNotes = new Vector<>();
		try {
			allNotes = NoteMapper.getNoteMapper().getAllNotes();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		AllNotesR report = new AllNotesR();
		report.setTitle("All Notes of all User");
		report.setCreated(new Date(System.currentTimeMillis()));

		StringBuilder sb = new StringBuilder();
		sb.append("Anzahl der Notebooks:");
		sb.append(allNotes.size());
		report.setHeaderData(new SimpleParagraph(sb.toString()));

		Row headline = new Row();
		headline.addColumn((new Column("Title")));
		headline.addColumn((new Column("Subtitle")));
		headline.addColumn((new Column("Username")));
		headline.addColumn((new Column("Creation Date")));
		headline.addColumn((new Column("Modification Date")));
		report.addRow(headline);

		for (Note element : allNotes) {

			Row r = new Row();
			r.addColumn(new Column(element.getTitle()));
			r.addColumn(new Column(element.getSubtitle()));
			r.addColumn(new Column(element.getOwner().getNickname()));
			r.addColumn(new Column(element.getCreationDate().toString()));
			r.addColumn(new Column(element.getModificationDate().toString()));

			report.addRow(r);

		}

		report.setImprint(new SimpleParagraph("Lorem ipsum sit dolor amet"));

		return report;

	}

	@Override
	public AllUserPermissionsR createAllUserPermissionsR() throws IllegalArgumentException {

		UserInfo u = editor.getCurrentUser();

		Vector<Permission> allPermissionsCreatedBy = PermissionMapper.getPermissionMapper()
				.getAllPermissionsCreatedBy(u);

		AllUserPermissionsR report = new AllUserPermissionsR();
		report.setTitle("All Permission from User");
		report.setCreated(new Date(System.currentTimeMillis()));

		StringBuilder sb = new StringBuilder();
		sb.append("Number of permissions:");
		sb.append(allPermissionsCreatedBy.size());
		report.setHeaderData(new SimpleParagraph(sb.toString()));

		Row headline = new Row();
		headline.addColumn((new Column("Autor")));
		headline.addColumn((new Column("Shared Object")));
		headline.addColumn((new Column("Level")));
		headline.addColumn((new Column("Beneficiary")));
		report.addRow(headline);

		for (Permission element : allPermissionsCreatedBy) {

			Row r = new Row();
			r.addColumn(new Column(element.getAuthor().toString()));
			r.addColumn(new Column(element.getSharedObject().toString()));
			r.addColumn(new Column(Integer.toString(element.getLevelAsInt())));
			r.addColumn(new Column(element.getBeneficiary().toString()));

			report.addRow(r);

		}

		report.setImprint(new SimpleParagraph("Lorem ipsum sit dolor amet"));

		return report;

	}

	@Override
	public AllPermissionsR createAllPermissionsR() throws IllegalArgumentException {

		Vector<Permission> allPermissions = PermissionMapper.getPermissionMapper().getAllPermissions();

		AllPermissionsR report = new AllPermissionsR();
		report.setTitle("All Permission of all User");
		report.setCreated(new Date(System.currentTimeMillis()));

		StringBuilder sb = new StringBuilder();
		sb.append("Number of Permission:");
		sb.append(allPermissions.size());
		report.setHeaderData(new SimpleParagraph(sb.toString()));

		Row headline = new Row();
		headline.addColumn((new Column("Autor")));
		headline.addColumn((new Column("Shared Object")));
		headline.addColumn((new Column("Level")));
		headline.addColumn((new Column("Beneficiary")));
		report.addRow(headline);

		for (Permission element : allPermissions) {

			Row r = new Row();
			r.addColumn(new Column(element.getAuthor().toString()));
			r.addColumn(new Column(element.getSharedObject().toString()));
			r.addColumn(new Column(Integer.toString(element.getLevelAsInt())));
			r.addColumn(new Column(element.getBeneficiary().toString()));

			report.addRow(r);

		}

		report.setImprint(new SimpleParagraph("Lorem ipsum sit dolor amet"));

		return report;

	}

	@Override
	public CustomReport createCustomReport(String type, String userEmail, Map<String, java.sql.Date> timespan,
			boolean includePermissions) {
		
		Vector<UserInfo> applicableUsers = new Vector<UserInfo>();
		
		if (userEmail.equals("none")) {
			
			applicableUsers = new SharedServicesImpl().getAllUsers();
			System.out.println("Calling the Mapper for all Users");
			
		} else {
			
			applicableUsers.add(UserInfoMapper.getUserInfoMapper().findByEmailAdress(userEmail));
			System.out.println("Calling the Mapper for User with email: " + userEmail);
			
		}
		
		System.out.println("User Email: " + userEmail);
		System.out.println("Applicable user count: " + Integer.toString(applicableUsers.size()));
		System.out.println(Arrays.toString(applicableUsers.toArray()));
		System.out.println("Type: " + type);
		System.out.println("Include Perms: " + Boolean.toString(includePermissions));
		
		CustomReport report = new CustomReport();
		
		report.setTitle("Custom Report, created on: " + new Date().toString());
		
		for (UserInfo currentUser : applicableUsers) {
			
			if (type.equals("notes")) {
				
				if (includePermissions) {
					
					appendNotesWithPermissionsTo(report, currentUser);
					
				} else {
					
					appendNotesToReport(report, currentUser);
					
				}
				
			}
			
		}
		
		report.setHeaderData(new SimpleParagraph("Custom Report on " + type + ", includes Permissions: " + Boolean.toString(includePermissions)));
		report.setImprint(new SimpleParagraph("End of Custom Report"));
		
		return report;
	}

	private void appendNotesToReport(CustomReport report, UserInfo currentUser) {
		
		System.out.println("Appending Notes Report");
		SimpleReport toAdd = createAllUserNotesReportFor(currentUser);
		report.addSubReport(toAdd);
		
	}

	private void appendNotesWithPermissionsTo(CustomReport report, UserInfo currentUser) {
		
		
		Vector<Note> allNotes = editor.getAllNotesForUser(currentUser);
		PermissionService permService = new PermissionServiceImpl();
		
		System.out.println("Appending Notes w/Permissions Report");
		System.out.println("User has " + Integer.toString(allNotes.size()) + " Notes.");
		System.out.println(Arrays.toString(allNotes.toArray()));
		System.out.println("User is: " + currentUser.toString());
		
		for (Note element : allNotes) {
			
			SimpleReport reportOnNote = new SimpleReport();
			reportOnNote.setTitle("Report on Note" + element.getTitle());
			reportOnNote.setHeaderData(new SimpleParagraph(element.toString()));
			
			Vector<Permission> permissionsForNote = permService.getAllPermissionsFor(element);
			
			Row headerRow = new Row();
			headerRow.addColumn(new Column("id"));
			headerRow.addColumn(new Column("Author"));
			headerRow.addColumn(new Column("Beneficiary"));
			headerRow.addColumn(new Column("Level"));
			
			reportOnNote.addRow(headerRow);
			
			int permissionCounter = 0;
			
			for (Permission p : permissionsForNote) {
				
				Row row = new Row();
				row.addColumn(Integer.toString(p.getId()));
				row.addColumn(p.getAuthor().toString());
				row.addColumn(p.getBeneficiary().toString());
				row.addColumn(Integer.toString(p.getLevelAsInt()));
				
				reportOnNote.addRow(row);
				
				permissionCounter++;
			}
			
			reportOnNote.setImprint(new SimpleParagraph("Note has " + Integer.toString(permissionCounter) + " Permissions"));
			
			report.addSubReport(reportOnNote);
			
		}
		
	}

	
	

}
