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

public class volunteerAppController {

  @FXML
  private TextArea txtFappSuccess;

  @FXML
  private Button btnReturnFromVolApp;

  public void submitVolApp(javafx.event.ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../volunteerApplicationPage/volunteerSuccess.fxml"));
    primaryStage.setTitle("Dashboard");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  public void goBackToCustomerScreen(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../mainScreen/AnimalAdoptMainScreen.fxml"));
    primaryStage.setTitle("Main Screen");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }
}
