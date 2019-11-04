package models.application;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import models.event.Event;
import models.user.User;

public class VolunteerApplicationWithUser {
  private User user;
  private VolunteerApplication application;

  public VolunteerApplicationWithUser(User user, VolunteerApplication application) {
    this.user = user;
    this.application = application;
  }

  public User getUser() {
    return user;
  }

  public VolunteerApplication getApplication() {
    return application;
  }

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