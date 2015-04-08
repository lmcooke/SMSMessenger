package edu.brown.cs.lmcooke.twilio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** OfficerTwil (short for officer twilio of course): 
 * 				Main class, launches application. 
 * 				Allows user to send SMS message 
 * 				using Twilio's REST API. 
 * 
 * 				Before running, enter the Account_Sid,
 * 				Auth_Token, and Twilio number in
 * 				MainPageController.java.
 * 
 * 				If using a trial Twilio account, you
 * 				must verify the number to which you
 * 				want to send an SMS, this is done
 * 				online through your Twilio account.
 *  
 * 				Application will remain open and can
 * 				send multiple messages until it is 
 * 				closed.
 * 
 * 				"ToNumber" can be in whatever format
 * 				user prefers; there's a method to
 * 				format it with "+1..." without 
 * 				spaces/punctuation/etc.
 */
@SuppressWarnings("restriction")
public class OfficerTwil extends Application {

	/** start: overriden method, 
	 * 		   sets up stage and fxml_mainentry.fxml
	 */
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader fload = new FXMLLoader(getClass().getResource("fxml_mainentry.fxml"));
		Parent parentRoot = fload.load();

		stage.setTitle("Twilio SMS Messenger");
		stage.setScene(new Scene(parentRoot, 400, 350));
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(OfficerTwil.class, args);
	}
}
