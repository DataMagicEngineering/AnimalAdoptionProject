package newevent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Database;
import models.event.Event;
import models.user.AuthorizationLevel;
import models.user.User;

/**
 * The Base Controller Class to create new events.
 */
public class NewEventController {

  private Database database = Database.get();

  @FXML
  private TextField eventTitleInput;

  @FXML
  private RadioButton radioCustomers;

  @FXML
  private ToggleGroup eventVisibility;

  @FXML
  private RadioButton radioVolunteers;

  @FXML
  private RadioButton radioEmployees;

  @FXML
  private DatePicker eventDatePicker;

  @FXML
  private ToggleButton publishEventToggle;

  @FXML
  private TextArea eventDescriptionInput;

  @FXML
  private Button backBtn;

  @FXML
  private Button createEventBtn;

  @FXML
  private TextField timeInput;

  @FXML
  private ToggleButton timeOfDayToggle;

  @FXML
  private Text errorText;

  /**
   * The initialize method in the NewEventController Class sets the currently created events every time the program
   * starts.
   */
  public void initialize() {
    eventDatePicker.setValue(LocalDate.now());

    timeOfDayToggle.setOnAction(event -> {
      if (timeOfDayToggle.isSelected()) {
        timeOfDayToggle.setText("PM");
      } else {
        timeOfDayToggle.setText("AM");
      }
    });

    if (Database.getCurrentEvent() != null) {
      Event event = Database.getCurrentEvent();

      eventTitleInput.setText(event.getName());
      eventDatePicker.setChronology(Chronology.from(event.getDate()));
      eventDescriptionInput.setText(event.getDescription());
      timeInput.setText(event.getDateTime().getHour() + ":" + event.getDateTime().getMinute());
      if (event.getDateTime().getHour() > 11) {
        timeOfDayToggle.setText("PM");
        timeOfDayToggle.setSelected(true);
      }
      switch (event.getTargetAudience()) {
        case ADMINISTRATION:
          radioEmployees.setSelected(true);
          break;
        case VOLUNTEER:
          radioVolunteers.setSelected(true);
          break;
        case BASIC:
          radioCustomers.setSelected(true);
          break;
      }
    }
  }

  /**
   * The method that allows a user to create an event. If an invalid input is entered, the user will be prompted to
   * re-enter the input.
   */
  public void createEvent() {
    if (eventTitleInput.getText().length() == 0) {
      showError("Please enter an event title");
      return;
    }

    if (!validTimeIsEntered()) {
      return;
    }

    RadioButton selectedToggle = (RadioButton) eventVisibility.getSelectedToggle();
    AuthorizationLevel targetAudience;

    switch (selectedToggle.getId()) {
      case "radioVolunteers":
        targetAudience = AuthorizationLevel.VOLUNTEER;
        break;
      case "radioEmployees":
        targetAudience = AuthorizationLevel.ADMINISTRATION;
        break;
      default:
        targetAudience = AuthorizationLevel.BASIC;
        break;
    }

    LocalDate eventDate = eventDatePicker.getValue();
    String[] inputTime = timeInput.getText().split(":");
    int hour = Integer.parseInt(inputTime[0]);
    int minutes = Integer.parseInt(inputTime[1]);
    boolean inAfternoon = timeOfDayToggle.isSelected();

    // If the user wants the event to be in the afternoon, add 12 hours to the given time.
    if (hour == 12 && !inAfternoon) {
      hour = 0;
    } else if (hour < 12 && inAfternoon) {
      hour += 12;
    }

    LocalDateTime eventDateAndTime = LocalDateTime.of(eventDate, LocalTime.of(hour, minutes));

    Event newEvent = new Event();

    if (Database.getCurrentEvent() != null) {
      newEvent = Database.getCurrentEvent();
    }

    ZoneId EST = Event.EST;

    newEvent.setName(eventTitleInput.getText());
    newEvent.setDate(eventDateAndTime.toInstant(EST.getRules().getOffset(eventDateAndTime)));
    newEvent.setTargetAudience(targetAudience);
    newEvent.setDescription(eventDescriptionInput.getText());
    newEvent.setPublished(publishEventToggle.isSelected());

    if (Database.getCurrentEvent() != null) {
      if (database.updateEvent(newEvent)) {
        System.out.println("Event successfully saved!");
      } else {
        System.out.println("Error saving event.");
      }
    } else {
      if (database.addEvent(newEvent)) {
        System.out.println("Event successfully saved!");
      } else {
        System.out.println("Error saving event.");
      }
    }
  }

  /**
   * The method that shows text whenever an invalid input is entered.
   * @param message The text of the error.
   */
  private void showError(String message) {
    if (message.length() == 0) {
      hideError();
      return;
    }

    errorText.setText(message);
    errorText.setVisible(true);
  }

  private void hideError() {
    errorText.setText("");
    errorText.setVisible(false);
  }

  private boolean validTimeIsEntered() {
    String currentInput = timeInput.getText().trim();
    if (!currentInput.contains(":")) {
      showError("Please enter a valid time with hours and minutes!");
      return false;
    }

    String[] tokens = currentInput.split(":"); // Separate the hour and minute
    if (tokens.length > 2) {
      return false;
    }

    String hourStr = tokens[0];
    String minutesStr = tokens[1];

    // If hour is between 01-12
    if (!hourStr.matches("0?[1-9]|1[0-2]")) {
      showError("Please enter an hour from 1-12.");
      return false;
    }

    // If minutes is between 00-59
    if (!minutesStr.matches("[0-5][0-9]")) {
      showError("Please enter minutes from 00-59.");
      return false;
    }

    hideError();
    return true;
  }

  /**
   * Method that switches the user's scenes to the Employee Dashboard.
   * @param actionEvent gets the Source, Scene, and Window.
   * @throws IOException since the code has a chance to contain an Exception.
   */
  public void goBackToScreen(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../employeedashboard/EmployeeDashboard.fxml"));
    primaryStage.setTitle("Dashboard");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();

    Database.setCurrentEvent(null);
  }
}
