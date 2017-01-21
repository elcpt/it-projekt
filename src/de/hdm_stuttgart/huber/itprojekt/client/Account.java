package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

public class Account extends BasicVerticalView {

	private UserInfo loggedInUser = null;
	private Button deleteButton = new Button("Delete my account");
	private EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	UserCallback userCallback = new UserCallback();
	private static String logOutUrl;

	private TextBox name = new TextBox();
	private TextBox surname  = new TextBox();
	private TextBox	email = new TextBox();

	public Account() {

	}

	public Account(UserInfo userInfo) {
		this.loggedInUser = userInfo;
	}

	@Override
	public String getHeadlineText() {
		return "MY ACCOUNT";
		// TODO Auto-generated method stub

	}

	@Override
	public String getSubHeadlineText() {
		// TODO Auto-generated method stub
		return "Nickname: " + loggedInUser.getNickname();

	}

	@Override
	public void run() {

		name.setText(loggedInUser.getFirstName());
		surname.setText(loggedInUser.getSurName());
		email.setText(loggedInUser.getEmailAddress());

		deleteButton.addClickHandler(new DeleteClickHandler());
		editorVerwaltung.getCurrentUser(userCallback);
	
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.setWidth("100%");
		this.add(name);
		this.add(surname);
		this.add(email);
		this.add(deleteButton);

	}

	private class UserCallback implements AsyncCallback<UserInfo> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(UserInfo result) {

			loggedInUser = result;

		}

	}

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (Window.confirm("Möchten Sie Ihren Account " + loggedInUser.getNickname() + " wirklich löschen?")) {
				
				logOutUrl = loggedInUser.getLogoutUrl();
				editorVerwaltung.deleteUserInfo(loggedInUser, new DeleteCallback());
				
			}

		}
	}

	public static void setLogOutUrl(String logOutUrl) {
		Account.logOutUrl = logOutUrl;
	}

	private class DeleteCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			GWT.log(caught.toString());
		}

		@Override
		public void onSuccess(Void result) {

			// Und raus damit
			Window.Location.replace(logOutUrl);
			
		}

	}

}
