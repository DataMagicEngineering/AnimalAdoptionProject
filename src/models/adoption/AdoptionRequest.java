package models.adoption;

import java.time.Instant;

public class AdoptionRequest {
  private int id;
  private int customerId;
  private int animalId;
  private boolean approved;
  private Instant dateRequested;
  private Instant dateApproved;

  public AdoptionRequest() {
  }

  public AdoptionRequest(int id,int customerId, int animalId, boolean approved,
      Instant dateRequested, Instant dateApproved) {
    this.id = id;
    this.customerId = customerId;
    this.animalId = animalId;
    this.approved = approved;
    this.dateRequested = dateRequested;
    this.dateApproved = dateApproved;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public int getAnimalId() {
    return animalId;
  }

  public void setAnimalId(int animalId) {
    this.animalId = animalId;
  }

  public boolean isApproved() {
    return approved;
  }

  public void setApproved(boolean approved) {
    this.approved = approved;
  }

  public Instant getDateRequested() {
    return dateRequested;
  }

  public void setDateRequested(Instant dateRequested) {
    this.dateRequested = dateRequested;
  }

  public Instant getDateApproved() {
    return dateApproved;
  }

  public void setDateApproved(Instant dateApproved) {
    this.dateApproved = dateApproved;
  }
}
