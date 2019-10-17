package models.animal;

import java.time.Instant;

/**
 * Represents a vaccine an animal has received and when the vaccine was given.
 */
public class Vaccine {
  private String name;
  private Instant dateReceived;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Instant getDateReceived() {
    return dateReceived;
  }

  public void setDateReceived(Instant dateReceived) {
    this.dateReceived = dateReceived;
  }

  @Override
  public String toString() {
    return "Type: " + name + "\nReceived: "+ dateReceived.toString();
  }
}
