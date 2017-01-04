package de.hdm_stuttgart.huber.itprojekt.client.gui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author Nikita Nalivayko, Dominik Erdmann
 *
 */
public class ListItemWidget extends SimplePanel {

	public ListItemWidget() {
		super((Element) Document.get().createLIElement().cast());
	}

	public ListItemWidget(String s) {
		this();
		getElement().setInnerText(s);
	}

	public ListItemWidget(Widget w) {
		this();
		this.add(w);
	}
}
