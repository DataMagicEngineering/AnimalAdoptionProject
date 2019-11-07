package customerlist;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CustomerListController {

  @FXML
  private AnchorPane ancPane;

  @FXML
  private Button btnGoBackToScreen;

  @FXML
  private Button btnSearchGo;

  @FXML
  private Button btnFilter;

  @FXML
  private Button btnGoToAnimal;

  @FXML
  private TextField searchField;

  @FXML
  private Label lblFilter;

  @FXML
  private ChoiceBox<?> chbFilter;

  @FXML
  private TableView<?> tbvAnimal;

  @FXML
  private TableColumn<?, ?> colName;

  @FXML
  private TableColumn<?, ?> colSpecies;

  @FXML
  private TableColumn<?, ?> colBreed;

  public void goBacktoCustomerDash(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../customerdashboard/CustomerDashboard.fxml"));
    primaryStage.setTitle("Dashboard");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }
}
