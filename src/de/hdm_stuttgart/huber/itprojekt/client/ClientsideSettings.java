package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.shared.GWT;

import de.hdm_stuttgart.huber.itprojekt.shared.Editor;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.PermissionService;
import de.hdm_stuttgart.huber.itprojekt.shared.PermissionServiceAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.SharedServices;
import de.hdm_stuttgart.huber.itprojekt.shared.SharedServicesAsync;

public class ClientsideSettings {
	/**
	 * Klasse mit Eigenschaften und Diensten, die für alle Clien Klassen relevant sind vgl. Bankprojekt 
	 * @author Nikita Nalivayko
	 */
	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitigen Dienst
	 */
	private static EditorAsync editorVerwaltung = null; 
	private static PermissionServiceAsync permissionVerwaltung=null;
/**
   *
   * Der Aufruf dieser Methode erfolgt im Client z.B. durch
   * <code>EditorAsync editorVerwaltung = ClientSideSettings.getEditorVerwaltung()</code>
   * .
   * </p>
   * 
   * @return eindeutige Instanz des Typs <code>EditorAsync</code>
   * Vgl. Bankprojekt 
   * 
   */
	
	
	public static EditorAsync getEditorVerwaltung(){
		
		if (editorVerwaltung==null){
			editorVerwaltung = GWT.create(Editor.class);
			
		}
		return editorVerwaltung;
	}
	public static PermissionServiceAsync getPermissionVerwaltung(){
		if (permissionVerwaltung==null)
			permissionVerwaltung = GWT.create(PermissionService.class);
		return permissionVerwaltung;
		
	}

	public static SharedServicesAsync getSharedService() {

	    return GWT.create(SharedServices.class);

    }
}
