package customerquestions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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

}
