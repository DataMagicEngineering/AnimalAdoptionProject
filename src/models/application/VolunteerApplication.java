package models.application;

import java.time.Instant;

public class VolunteerApplication {
  private int id;
  private int applicantId;
  private boolean approved;
  private Instant dateRequested;
  private Instant dateApproved;

  public VolunteerApplication(int id, int applicantId, boolean approved,
      Instant dateRequested, Instant dateApproved) {
    this.id = id;
    this.applicantId = applicantId;
    this.approved = approved;
    this.dateRequested = dateRequested;
    this.dateApproved = dateApproved;
  }

  public VolunteerApplication() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getApplicantId() {
    return applicantId;
  }

  public void setApplicantId(int applicantId) {
    this.applicantId = applicantId;
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
