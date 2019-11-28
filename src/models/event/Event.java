package models.event;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import models.user.AuthorizationLevel;

/**
 * The Event class contains the information about an event in the program, which includes the type of event, when it was
 * published, the target audience, and the description.
 */
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

    /**
     * The Constructor used for creating Event objects with known information.
     *
     * @param id             an int variable that is used to assign the id field to.
     * @param name           a String variable that is used to assign the name field to.
     * @param eventCreatorId an int variable that is used to assign the eventCreatorId field to.
     * @param date           an Instant instance variable that is used to assign the date field to.
     * @param isPublished    a boolean variable that is used to assign the isPublished field to.
     * @param targetAudience an AuthorizationLevel instance variable that is used to assign the targetAudience field to.
     * @param description    a String variable that is used to assign the description field to.
     */
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

    /**
     * Default constructor for Event.
     */
    public Event() {
    }

    /**
     * Accessor for eventCreatorId.
     *
     * @return eventCreatorId, a field variable of type int.
     */
    public int getEventCreatorId() {
        return eventCreatorId;
    }

    /**
     * Mutator for eventCreatorId.
     *
     * @param eventCreatorId
     */
    public void setEventCreatorId(int eventCreatorId) {
        this.eventCreatorId = eventCreatorId;
    }

    /**
     * Accessor for id.
     *
     * @return id, a field variable of type int.
     */
    public int getId() {
        return id;
    }

    /**
     * Mutator for id.
     *
     * @param id the variable that the id field is assigned to.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Accessor for name.
     *
     * @return name, a field variable of type String.
     */
    public String getName() {
        return name;
    }

    /**
     * Mutator for name.
     *
     * @param name the variable that the name field is assigned to.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Accessor for date.
     *
     * @return date, a field variable of type Instant.
     */
    public Instant getDate() {
        return date;
    }

    /**
     * Mutator for date.
     *
     * @param date the variable that the date field is assigned to.
     */
    public void setDate(Instant date) {
        this.date = date;
    }

    /**
     * Accessor for isPublished.
     *
     * @return isPublished, a field variable of type boolean.
     */
    public boolean isPublished() {
        return isPublished;
    }

    /**
     * Mutator for isPublished.
     *
     * @param published the variable that the isPublished field is assigned to.
     */
    public void setPublished(boolean published) {
        isPublished = published;
    }

    /**
     * Accessor for targetAudience.
     *
     * @return targetAudience, a field variable of type AuthorizationLevel.
     */
    public AuthorizationLevel getTargetAudience() {
        return targetAudience;
    }

    /**
     * Mutator for targetAudience.
     *
     * @param targetAudience the variable that the targetAudience field is assigned to.
     */
    public void setTargetAudience(AuthorizationLevel targetAudience) {
        this.targetAudience = targetAudience;
    }

    /**
     * Accessor for description.
     *
     * @return description, a field variable of type String.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Mutator for description.
     *
     * @param description the variable that the description field is assigned to.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Accessor for date.
     *
     * @return date, a field variable of type LocalDateTime, converted to local time.
     */
    public LocalDateTime getDateTime() {
        return date.atZone(EST).toLocalDateTime();
    }

    /**
     * A toString method that displays the Event information
     *
     * @return The Event's name, date, and description, formatted as a String.
     */
    @Override
    public String toString() {
        String format =
                "Event Name: %s\n"
                        + "Date: %s\n"
                        + "Description: %s";
        DateTimeFormatter formatDate;
        formatDate = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy @ hh:mm a").withZone(EST);
        return String.format(format, getName(), formatDate.format(getDate()), getDescription());
    }
}
