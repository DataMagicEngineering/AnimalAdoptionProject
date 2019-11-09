package models.event;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import models.user.AuthorizationLevel;

public class Event {

  public static ZoneId EST = ZoneOffset.of("EST", ZoneId.SHORT_IDS);
  private int id;
  private String name;

  // Id of the user who originally created this event
  private int eventCreatorId;
  private Instant date;
  private boolean isPublished;
  private AuthorizationLevel targetAudience;
  private String description;

  public Event(int id, String name, int eventCreatorId, Instant date, boolean isPublished,
      AuthorizationLevel targetAudience, String description) {
    this.id = id;
    this.name = name;
    this.eventCreatorId = eventCreatorId;
    this.date = date;
    this.isPublished = isPublished;
    this.targetAudience = targetAudience;
    this.description = description;
  }

  public Event() {
  }

  public int getEventCreatorId() {
    return eventCreatorId;
  }

  public void setEventCreatorId(int eventCreatorId) {
    this.eventCreatorId = eventCreatorId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Instant getDate() {
    return date;
  }

  public void setDate(Instant date) {
    this.date = date;
  }

  public boolean isPublished() {
    return isPublished;
  }

  public void setPublished(boolean published) {
    isPublished = published;
  }

  public AuthorizationLevel getTargetAudience() {
    return targetAudience;
  }

  public void setTargetAudience(AuthorizationLevel targetAudience) {
    this.targetAudience = targetAudience;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getDateTime() {
    return date.atZone(EST).toLocalDateTime();
  }

  @Override
  public String toString() {
    String format =
        "Event Name: %S\n"
        + "Date: %s\n"
        + "Description: %s";
    DateTimeFormatter formatDate;
    formatDate = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy").withZone(EST);
    return String.format(format, getName(), formatDate.format(getDate()), getDescription());
  }
}
