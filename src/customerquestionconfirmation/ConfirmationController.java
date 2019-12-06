package customerquestionconfirmation;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ConfirmationController {

  @FXML
  void onActionBackToMain(ActionEvent event) throws IOException {
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("/customerdashboard/CustomerDashboard.fxml"));
    primaryStage.setTitle("Main Screen");
    primaryStage.setScene(new Scene(root));
    root.getStylesheets().add("mainCSS.css");
    primaryStage.show();
  }

}

