package customerquestions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 *
 * @author Emily Schwarz
 */
public class CustomerAskQsController {

  @FXML
  private Button customerQsBackBtn;

  @FXML
  private TextArea customerQsTxtArea;

  @FXML
  private Button customerSubmitQsBtn;

  @FXML
  void onActionGoBack(ActionEvent event) throws IOException {
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../customerdashboard/CustomerDashboard.fxml"));
    primaryStage.setTitle("Main Screen");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  @FXML
  void onActionSubmitQuestion(ActionEvent event) {

  }
}
