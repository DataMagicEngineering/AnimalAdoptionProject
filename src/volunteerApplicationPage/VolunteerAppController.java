package volunteerApplicationPage;

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

/**
 * Controller Class for the Volunteer Application page.
 */
public class VolunteerAppController {

  @FXML
  private TextArea txtFappSuccess;

  @FXML
  private Button btnReturnFromVolApp;

  /**
   * Method for a button that submits a volunteer application and sends the user to a confirmation page.
   * @param actionEvent gets the Source, Scene, and Window to the confirmation page.
   * @throws IOException since the code has a chance to contain an IOException.
   * @author Emily Schwarz and Luis Hernandez
   */
  public void submitVolApp(ActionEvent actionEvent) throws IOException {
    Database.get().applyForVolunteer(Database.getCurrentUser());

    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("/volunteerApplicationPage/volunteerSuccess.fxml"));
    primaryStage.setTitle("Dashboard");
    primaryStage.setScene(new Scene(root));
    root.getStylesheets().add("mainCSS.css");
    primaryStage.show();
  }

  /**
   * Method for a button that returns the user back to the Customer Dashboard once pressed.
   * @param actionEvent gets the Source, Scene, and Window for the Customer Dashboard.
   * @throws IOException since the code has a chance to contain an IOException.
   * @author Emily Schwarz and Luis Hernandez
   */
  public void goBackToCustomerScreen(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("/customerdashboard/CustomerDashboard.fxml"));
    primaryStage.setTitle("Main Screen");
    primaryStage.setScene(new Scene(root));
    root.getStylesheets().add("mainCSS.css");
    primaryStage.show();
  }
}
