package de.hdm_stuttgart.huber.itprojekt.shared.report;

import java.io.Serializable;
import java.util.Vector;

/**
 * Aus dem Bankprojekt übernommen
 * 
 * Zeile einer Tabelle eines <code>SimpleReport</code>-Objekts. <code>Row</code>
 * -Objekte implementieren das <code>Serializable</code>-Interface und können
 * daher als Kopie z.B. vom Server an den Client übertragen werden.
 *
 * @author Thies
 * @see SimpleReport
 * @see Column
 */
public class Row implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * Speicherplatz für die Spalten der Zeile.
     */
    private Vector<Column> columns = new Vector<Column>();

    /**
     * Hinzufügen einer Spalte.
     *
     * @param c das Spaltenobjekt
     */
    public void addColumn(Column c) {
        this.columns.addElement(c);
    }

    public void addColumn(String s) {
        this.columns.addElement(new Column(s));
    }

    /**
     * Entfernen einer benannten Spalte
     *
     * @param c das zu entfernende Spaltenobjekt
     */
    public void removeColumn(Column c) {
        this.columns.removeElement(c);
    }

    /**
     * Auslesen sämtlicher Spalten.
     *
     * @return <code>Vector</code>-Objekts mit sämtlichen Spalten
     */
    public Vector<Column> getColumns() {
        return this.columns;
    }

    /**
     * Auslesen der Anzahl sämtlicher Spalten.
     *
     * @return int Anzahl der Spalten
     */
    public int getNumColumns() {
        return this.columns.size();
    }

    /**
     * Auslesen eines einzelnen Spalten-Objekts.
     *
     * @param i der Index der auszulesenden Spalte (0 <= i < n), mit n =
     *          Anzahl der Spalten.
     * @return das gewünschte Spaltenobjekt.
     */
    public Column getColumnAt(int i) {
        return this.columns.elementAt(i);
    }

    @Override
    public String toString() {
        return "Row [columns=" + columns + "]";
    }

}
