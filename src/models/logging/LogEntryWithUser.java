package models.logging;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import models.event.Event;
import models.user.User;

public class LogEntryWithUser {

  private LogEntry entry;

  /**
   * The user who caused this action
   */
  private User initiator;

  public LogEntryWithUser(LogEntry entry, User initiator) {
    this.entry = entry;
    this.initiator = initiator;
  }

  public LogEntry getEntry() {
    return entry;
  }

  public User getInitiator() {
    return initiator;
  }

  @Override
  public String toString() {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
    return String.format("[%s %s] - %s at %s",
        initiator.getFirstName(),
        initiator.getLastName(),
        entry.getMessage(),
        entry.getDateOccurred().atZone(Event.EST).toLocalDateTime().format(dateFormatter)
    );
  }
}
