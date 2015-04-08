package edu.brown.cs.lmcooke.twilio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


/** MainPageController: controls interaction
 * 						with stage, sends sms.
 */
public class MainPageController {

	@SuppressWarnings("restriction")
	@FXML private TextArea numberText = null;
	@SuppressWarnings("restriction")
	@FXML private Text resultnumText = null;

	@SuppressWarnings("restriction")
	@FXML private TextArea bodyText = null;
	@SuppressWarnings("restriction")
	@FXML private Text resultbodText = null;

	//private Stage stage = null;

	// fill in the following three lines with your twilio account information:
	// make sure twilio number is in correct +1... format
	public static final String ACCOUNT_SID = "AC.....";
	public static final String AUTH_TOKEN = "1234";
	public static final String TWILIO_NUM = "+1.....";


	/** formatToNumber: cleans out the "To" number of 
	 * 					anything thats not 0-9, and 
	 * 					adds "+1" to the front. If 
	 * 					the number is invalid, the
	 * 					user will be prompted again
	 * 					in getToNumber()
	 * 
	 * @param toNumber - String to be formatted
	 * @return - String, formatted number
	 */
	public static String formatToNumber(String toNumber) {
		String noPunct = toNumber.replaceAll("[^\\d]","");
		char[] ca = noPunct.toCharArray();
		if (ca.length == 10) {
			String toReturn = "+1" + (new String(ca));
			return toReturn;
		} 
		if ((ca.length == 11) && (ca[0] == '1')) {
			String toReturn = "+" + (new String(ca));
			return toReturn;
		} else {		
			return "not a valid number";
		}
	}	

	/** handleSubmitButtonAction: 
	 * 		handles submit -- formats number, 
	 * 		sends message, clears TextAreas.
	 * 		Will print in console if number is
	 * 		invalid (TwilioRestException).
	 * @param - ActionEvent: event - button click 
	 * @return -void
	 **/
	@SuppressWarnings("restriction")
	@FXML protected void handleSubmitButtonAction(ActionEvent event) { 
		String submittedNumber = numberText.getText();
		String submittedBody = bodyText.getText();
		String evaluatedNumber = formatToNumber(submittedNumber);

		try {
			sendMessage(evaluatedNumber, submittedBody);
		} catch (TwilioRestException e){
			System.out.println("(TwilioRestException) Invalid number: try again.");
		}
		numberText.clear();
		bodyText.clear();
	}

	/** handleClearButtonAction: 
	 * 	handles clear -- clears text in both areas.
	 * 
	 * @param ActionEvent: event - button click
	 * @return - void
	 */
	@SuppressWarnings("restriction")
	@FXML protected void handleClearButtonAction(ActionEvent event) {
		numberText.clear();
		bodyText.clear();
	}

	/** sendMessage: sends the body to the given toNumber using
	 * 				 Twilio REST API 
	 * 
	 * @param toNumber - String, in correct "+1..." format
	 * @param body - text to send
	 * @throws TwilioRestException - if invalid number.
	 */
	public void sendMessage(String toNumber, String body) throws TwilioRestException {
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

		Account account = client.getAccount();

		MessageFactory messageFactory = account.getMessageFactory();
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("To", toNumber)); 
		params.add(new BasicNameValuePair("From", TWILIO_NUM)); 
		params.add(new BasicNameValuePair("Body", body));

		Message sms = messageFactory.create(params);
	}

}
