package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class EmbedCode extends BasicView {

	public EmbedCode() {
		
	}

	@Override
	public String getHeadlineText() {
		
		return "Embed this application";
	}

	@Override
	public String getSubHeadlineText() {

		return "Just copy & paste this HTML code in your personal homepage:";
	}

	@Override
	public void run() {
	

		String s = "<form action=\'https://a-1-dot-it-projekt-149914.appspot.com/it_projekt/listener\' method=\'POST\'>" +
			"<input type=\'hidden\' name=\'test\' value=\'test vom huber\'>" +
			"<input type=\'image\' src=\'https://a-1-dot-it-projekt-149914.appspot.com/sharebutton.png\' alt=\'Share\' style=\'border:2px black solid\'>" +
			"</form>";
	
		Label l = new Label(s);
		
		RootPanel.get("main").clear();
		RootPanel.get("table").clear();
		RootPanel.get("main").add(l);
		
	
		
	}

}
