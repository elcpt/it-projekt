package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hdm_stuttgart.huber.itprojekt.shared.*;

import java.util.logging.Logger;

public class ClientsideSettings {
    /**
     * Name des Client-seitigen Loggers.
     */
    private static final String LOGGER_NAME = "BankProjekt Web Client";
    /**
     * Instanz des Client-seitigen Loggers.
     */
    private static final Logger log = Logger.getLogger(LOGGER_NAME);
    /**
     * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitigen
     * Dienst
     */
    private static EditorAsync editorVerwaltung = null;
    private static ReportGeneratorAsync reportGenerator = null;
    private static PermissionServiceAsync permissionVerwaltung = null;

    public static Logger getLogger() {
        return log;
    }

    /**
     * Der Aufruf dieser Methode erfolgt im Client z.B. durch
     * <code>EditorAsync editorVerwaltung = ClientSideSettings.getEditorVerwaltung()</code>
     * .
     * </p>
     *
     * @return eindeutige Instanz des Typs <code>EditorAsync</code> Vgl.
     * Bankprojekt
     */

    public static EditorAsync getEditorVerwaltung() {

        if (editorVerwaltung == null) {
            editorVerwaltung = GWT.create(Editor.class);
        }

        return editorVerwaltung;
    }

    public static PermissionServiceAsync getPermissionVerwaltung() {
        if (permissionVerwaltung == null)
            permissionVerwaltung = GWT.create(PermissionService.class);
        return permissionVerwaltung;

    }

    public static SharedServicesAsync getSharedService() {

        return GWT.create(SharedServices.class);

    }

    public static ReportGeneratorAsync getReportGenerator() {
        // Gab es bislang noch keine ReportGenerator-Instanz, dann...
        if (reportGenerator == null) {
            // Zunächst instantiieren wir ReportGenerator
            reportGenerator = GWT.create(ReportGenerator.class);

            final AsyncCallback<Void> initReportGeneratorCallback = new AsyncCallback<Void>() {
                @Override
                public void onFailure(Throwable caught) {
                    ClientsideSettings.getLogger().severe("Der ReportGenerator konnte nicht initialisiert werden!");
                    GWT.log(caught.toString());
                    GWT.log(caught.getMessage());
                }

                @Override
                public void onSuccess(Void result) {
                    ClientsideSettings.getLogger().info("Der ReportGenerator wurde initialisiert.");
                    GWT.log("RG initialisiert.");
                }
            };

            reportGenerator.init(initReportGeneratorCallback);
        }

        // So, nun brauchen wir den ReportGenerator nur noch zurückzugeben.
        return reportGenerator;
    }
}
