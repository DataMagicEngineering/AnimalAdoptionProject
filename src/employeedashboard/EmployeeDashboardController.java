package employeedashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.Database;

public class EmployeeDashboardController {

  @FXML
  private Label userStatusLbl;

  @FXML
  private Label dashLoginLbl;

  public void initialize() {
    dashLoginLbl.setText(
        "Welcome " + Database.getCurrentUser().getLastName() + ", " + Database.getCurrentUser()
            .getFirstName()+"!");
  }

}
