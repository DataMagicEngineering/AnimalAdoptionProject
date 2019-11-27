package models.application;

import java.time.Instant;

/**
 * The VolunteerApplication class contains information obout a user's application.
 */
public class VolunteerApplication {
  private int id;
  private int applicantId;
  private boolean approved;
  private Instant dateRequested;
  private Instant dateApproved;

  /**
   * Constructor that is used when creating a VolunteerApplication object with known information.
   * @param id the int variable that determines application id.
   * @param applicantId the int variable that determines the applicant's id.
   * @param approved the boolean variable that determines is the application was approved.
   * @param dateRequested the Instant instance variable that stores the date requested.
   * @param dateApproved the Instant instance variable that stores the date approved.
   */
  public VolunteerApplication(int id, int applicantId, boolean approved,
      Instant dateRequested, Instant dateApproved) {
    this.id = id;
    this.applicantId = applicantId;
    this.approved = approved;
    this.dateRequested = dateRequested;
    this.dateApproved = dateApproved;
  }

  /**
   * Default Constructor for VolunteerApplication.
   */
  public VolunteerApplication() {
  }

  /**
   * Accessor for id.
   * @return id, a field variable of type int.
   */
  public int getId() {
    return id;
  }

  /**
   * Mutator for id
   * @param id the variable that the id field is assigned to.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Accessor for applicantId
   * @return applicantId, a field variable of type int.
   */
  public int getApplicantId() {
    return applicantId;
  }

  /**
   * Mutator for applicantId.
   * @param applicantId the variable that the applicantId field is assigned to.
   */
  public void setApplicantId(int applicantId) {
    this.applicantId = applicantId;
  }

  /**
   * Accessor for approved.
   * @return approved, a field variable of type boolean.
   */
  public boolean isApproved() {
    return approved;
  }

  /**
   * Mutator for approved.
   * @param approved the variable that the approved field is assigned to.
   */
  public void setApproved(boolean approved) {
    this.approved = approved;
  }

  /**
   * Accessor for dateRequested.
   * @return , a field variable of type Instant.
   */
  public Instant getDateRequested() {
    return dateRequested;
  }

  /**
   * Mutator for dateRequested.
   * @param dateRequested the variable that the dateRequested field is assigned to.
   */
  public void setDateRequested(Instant dateRequested) {
    this.dateRequested = dateRequested;
  }

  /**
   * Accessor for dateApproved
   * @return dateApproved, a field variable of type
   */
  public Instant getDateApproved() {
    return dateApproved;
  }

  /**
   * Mutator for dateApproved.
   * @param dateApproved the variable that the dateApproved field is assigned to.
   */
  public void setDateApproved(Instant dateApproved) {
    this.dateApproved = dateApproved;
  }

}
