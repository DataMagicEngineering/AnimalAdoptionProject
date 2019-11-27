package models.application;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import models.event.Event;
import models.user.User;

/**
 * The class that checks whether a User's volunteer application has been accepted or rejected.
 */
public class VolunteerApplicationWithUser {
  private User user;
  private VolunteerApplication application;

  /**
   * Constructor which is used for creating a new object for the class.
   * @param user A user instance.
   * @param application A VolunteerApplication instance.
   */
  public VolunteerApplicationWithUser(User user, VolunteerApplication application) {
    this.user = user;
    this.application = application;
  }

  /**
   * Accessor for user.
   * @return user, a field of type User.
   */
  public User getUser() {
    return user;
  }

  /**
   * Accessor for application.
   * @return application, a field of type VolunteerApplication.
   */
  public VolunteerApplication getApplication() {
    return application;
  }

  /**
   * A method that returns an application's information as a String.
   * @return the user's first name, last name, request date, and approved line, formatted as a String.
   */
  @Override
  public String toString() {
    String format =
        "%s %s\n"
      + "Requested on %s"
      + "%s";

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

    String requestedDate = dateFormatter.format(application.getDateRequested().atZone(Event.EST));

    String approvedLine = "";
    if (application.getDateApproved() != null) {
      approvedLine += "\n";
      approvedLine += application.isApproved() ? "Approved " : "Rejected ";
      approvedLine += "on ";
      approvedLine += dateFormatter.format(application.getDateApproved().atZone(Event.EST));
    }

    return String.format(format, user.getFirstName(), user.getLastName(), requestedDate, approvedLine);
  }
}