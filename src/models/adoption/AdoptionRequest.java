package models.adoption;

import java.time.Instant;

public class AdoptionRequest {
  private int id;
  private int customerId;
  private int animalId;
  private boolean approved;
  private Instant dateRequested;
  private Instant dateApproved;

  /**
   * Default Constructor for an AdoptionRequest object.
   */
  public AdoptionRequest() {
  }

  /**
   * Overloaded Constructor for an AdoptionRequest object. Information about an Adoption Request will be stored within
   * this type of Adoption Request.
   * @param id The int field that is initialized for an AdoptionRequest object.
   * @param customerId The int field that is initialized for an AdoptionRequest object.
   * @param animalId The int field that is initialized for an AdoptionRequest object.
   * @param approved The boolean field that is initialized for an AdoptionRequest object.
   * @param dateRequested The Instant field that is initialized for an AdoptionRequest object.
   * @param dateApproved The Instant field that is initialized for an AdoptionRequest object.
   */
  public AdoptionRequest(int id,int customerId, int animalId, boolean approved,
      Instant dateRequested, Instant dateApproved) {
    this.id = id;
    this.customerId = customerId;
    this.animalId = animalId;
    this.approved = approved;
    this.dateRequested = dateRequested;
    this.dateApproved = dateApproved;
  }

  /**
   * Accessor for id
   * @return id, an int field in the AdoptionRequest class.
   */
  public int getId() {
    return id;
  }

  /**
   * Mutator for id
   * @param id the int variable that the id field is assigned to.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Accessor for customerId
   * @return customerId, an int field in the AdoptionRequest class.
   */
  public int getCustomerId() {
    return customerId;
  }

  /**
   * Mutator for customerId
   * @param customerId the int variable that the customerId field is assigned to.
   */
  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  /**
   * Accessor for animalId
   * @return animalId, an int field in the AdoptionRequest class.
   */
  public int getAnimalId() {
    return animalId;
  }

  /**
   * Mutator for animalId
   * @param animalId the int variable that the animalId field is assigned to.
   */
  public void setAnimalId(int animalId) {
    this.animalId = animalId;
  }

  /**
   * Accessor for approved
   * @return approved, a boolean field in the AdoptionRequest class.
   */
  public boolean isApproved() {
    return approved;
  }

  /**
   * Mutator for approved
   * @param approved the boolean variable that the approved field is assigned to.
   */
  public void setApproved(boolean approved) {
    this.approved = approved;
  }

  /**
   * Accessor for dateRequested
   * @return dateRequested, an Instant field in the AdoptionRequest class.
   */
  public Instant getDateRequested() {
    return dateRequested;
  }

  /**
   * Mutator for dateRequested
   * @param dateRequested the Instant variable that the dateRequested field is assigned to.
   */
  public void setDateRequested(Instant dateRequested) {
    this.dateRequested = dateRequested;
  }

  /**
   * Accessor for dateApproved
   * @return dateApproved, an Instant field in the AdoptionRequest class.
   */
  public Instant getDateApproved() {
    return dateApproved;
  }

  /**
   * Mutator for dateApproved
   * @param dateApproved the Instant variable that the dateApproved field is assigned to.
   */
  public void setDateApproved(Instant dateApproved) {
    this.dateApproved = dateApproved;
  }

  /**
   * Returns true if this request has been processed, meaning that the dateApproved is not
   * null.
   * @return true if this request has been processed, else false.
   */
  public boolean isProcessed() {
    return dateApproved != null;
  }
}
