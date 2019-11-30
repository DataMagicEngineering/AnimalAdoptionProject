package models.logging;

import java.time.Instant;

/**
 * A LogEntry represents a brief description and record of an action that happened and the entities
 * that may have been involved in the action.
 */
public class LogEntry {

  /**
   * The id of this log entry.
   */
  private int entryId;

  /**
   * The type of entity that was changed.
   */
  private LogEntity entityAffected;

  /**
   * The user's id who initiated this event.
   */
  private int userId;

  /**
   * A brief description of the action that has occurred.
   */
  private String message;

  /**
   * The id if the entity that was affected as a result of this action. This will be -1 if no entity
   * was affected.
   */
  private int affectedId;

  private Instant dateOccurred;

  public LogEntry(LogEntity entityAffected, int userId, String message, int affectedId,
      Instant dateOccurred) {
    this.entityAffected = entityAffected;
    this.userId = userId;
    this.message = message;
    this.affectedId = affectedId;
    this.dateOccurred = dateOccurred;
  }

  public LogEntry(int entryId, LogEntity entityAffected, int userId, String message, int affectedId,
      Instant dateOccurred) {
    this.entryId = entryId;
    this.entityAffected = entityAffected;
    this.userId = userId;
    this.message = message;
    this.affectedId = affectedId;
    this.dateOccurred = dateOccurred;
  }

  public int getEntryId() {
    return entryId;
  }

  public void setEntryId(int entryId) {
    this.entryId = entryId;
  }

  public LogEntity getEntityAffected() {
    return entityAffected;
  }

  public void setEntityAffected(LogEntity entityAffected) {
    this.entityAffected = entityAffected;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getAffectedId() {
    return affectedId;
  }

  public void setAffectedId(int affectedId) {
    this.affectedId = affectedId;
  }

  public Instant getDateOccurred() {
    return dateOccurred;
  }

  public void setDateOccurred(Instant dateOccurred) {
    this.dateOccurred = dateOccurred;
  }

  /**
   * Types of entities that may be affected or changed by others.
   *
   * Example: If a user were to request to adopt an animal, the affected entity is of type Animal
   */
  public enum LogEntity {
    User,
    Animal,
    Question,
    Event
  }
}
