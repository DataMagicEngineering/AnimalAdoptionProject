package customerquestions;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import main.Database;
import models.questions.Question;

/**
 * The CustomerAskQsController is the class that contains the functionality withing the "Ask a
 * Question" screen from the customer's side. The scene includes a TextArea which a customer can
 * type a question, and it includes two buttons: One that their goes back to the customer dashboard,
 * or one that processes a question and sends the customer to a confirmation page.
 *
 * @author Emily Schwarz, Ramzy El-Taher
 */
public class CustomerAskQsController {

  @FXML
  private Button customerQsBackBtn;

  @FXML
  private TextArea customerQsTxtArea;

  @FXML
  private Button customerSubmitQsBtn;

  /**
   * Method that switches scenes to the customer dashboard.
   *
   * @param event gets the Source, Scene, and Window of the selected scene, and is casted to a
   *              Node.
   * @throws IOException since the method has a chance to contain an IOException.
   */
  @FXML
  void onActionGoBack(ActionEvent event) throws IOException {
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../customerdashboard/CustomerDashboard.fxml"));
    primaryStage.setTitle("Main Screen");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  /**
   * Method that processes an asked question, sends the information to the database, and informs the
   * user that the question was successfully submitted.
   *
   * @param event gets the Source, Scene, and Window of the selected scene, and is casted to a
   *              Node.
   * @throws IOException since the method has a chance to contain an IOException.
   */
  @FXML
  void onActionSubmitQuestion(ActionEvent event) throws IOException {
    Question customerQuestion = new Question();
    // Gets the ID of the current user
    customerQuestion.setUserId(Database.getCurrentUser().getId());
    // Sets the question to the text in the TextArea.
    customerQuestion.setQuestion(customerQsTxtArea.getText());
    // Sets the boolean variable to decide if the question was answered to false.
    customerQuestion.setAnswered(false);
    // Process the asked question and sends the information to the database.
    Database.get().askQuestion(customerQuestion);
    // Switches scenes to the confirmation page. (CURRENTLY SWITCHES BACK TO CUSTOMER DASHBOARD)
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass()
            .getResource("../customerquestionconfirmation/customerQuestionConfirmation.fxml.fxml"));
    primaryStage.setTitle("Confirmation");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }
}
