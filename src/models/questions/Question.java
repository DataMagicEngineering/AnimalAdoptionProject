package models.questions;

public class Question {
  private int id;

  // The id of the customer who asked this question.
  private int userId;
  private boolean answered;

  // The id of the employee who answered this question.
  private int employeeId;
  private String question;
  private String answer;

  /**
   * Default constructor for the Question object.
   */
  public Question() {
  }

  /**
   * Overloaded constructor for a Question object.
   * @param id The int field that is initialized for a Question object.
   * @param userId The int field that is initialized for a Question object.
   * @param answered The boolean field that is initialized for a Question object.
   * @param employeeId The int field that is initialized for a Question object.
   * @param question The string field that is initialized for a Question object.
   * @param answer The string field that is initialized for a Question object.
   */
  public Question(int id, int userId, boolean answered, int employeeId, String question, String answer) {
    this.id = id;
    this.userId = userId;
    this.answered = answered;
    this.employeeId = employeeId;
    this.question = question;
    this.answer = answer;
  }

  /**
   * Accessor for id
   * @return id, the int variable in the Question class.
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
   * Accessor for userId
   * @return userId, the int variable in the Question class.
   */
  public int getUserId() {
    return userId;
  }

  /**
   * Mutator for userId
   * @param userId the int variable that the userId field is assigned to.
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }

  /**
   * Accessor for answered
   * @return answered, the boolean variable in the Question class.
   */
  public boolean isAnswered() {
    return answered;
  }

  /**
   * Mutator for answered
   * @param answered the boolean variable that the id field is assigned to.
   */
  public void setAnswered(boolean answered) {
    this.answered = answered;
  }

  /**
   * Accessor for employeeId
   * @return employeeId, the int variable in the Question class.
   */
  public int getEmployeeId() {
    return employeeId;
  }

  /**
   * Mutator for employeeId
   * @param employeeId the int variable that the id field is assigned to.
   */
  public void setEmployeeId(int employeeId) {
    this.employeeId = employeeId;
  }

  /**
   * Accessor for question
   * @return question, the String variable in the Question class.
   */
  public String getQuestion() {
    return question;
  }

  /**
   * Mutator for question
   * @param question the String variable that the id field is assigned to.
   */
  public void setQuestion(String question) {
    this.question = question;
  }

  /**
   * Accessor for answer
   * @return answer, the String variable in the Question class.
   */
  public String getAnswer() {
    return answer;
  }

  /**
   * Mutator for answer
   * @param answer the String variable that the id field is assigned to.
   */
  public void setAnswer(String answer) {
    this.answer = answer;
  }
}
