package models.animal;

import java.time.Instant;

/**
 * Represents a vaccine an animal has received and when the vaccine was given.
 * @author The Data Magic Engineering Team
 */
public class Vaccine {
  private String name;
  private Instant dateReceived;

  /**
   * Mutator for name
   * @return name, which is the String field in the Vaccine class.
   */
  public String getName() {
    return name;
  }

  /**
   * Accessor for name
   * @param name the String variable that the name field is assigned to.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Mutator for dateReceived
   * @return dateReceived, which is an Instant field in the Vaccine class.
   */
  public Instant getDateReceived() {
    return dateReceived;
  }

  /**
   * Accessor for dateReceived
   * @param dateReceived the Instant variable that the dateReceived field is assigned to.
   */
  public void setDateReceived(Instant dateReceived) {
    this.dateReceived = dateReceived;
  }

  /**
   * toString method that gives information about the Vaccine object.
   * @return name of the Vaccine and the date that the animal received it.
   */
  @Override
  public String toString() {
    return "Type: " + name + "\nReceived: "+ dateReceived.toString();
  }
}
