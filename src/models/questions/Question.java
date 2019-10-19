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

  public Question() {
  }

  public Question(int id, int userId, boolean answered, int employeeId, String question, String answer) {
    this.id = id;
    this.userId = userId;
    this.answered = answered;
    this.employeeId = employeeId;
    this.question = question;
    this.answer = answer;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public boolean isAnswered() {
    return answered;
  }

  public void setAnswered(boolean answered) {
    this.answered = answered;
  }

  public int getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(int employeeId) {
    this.employeeId = employeeId;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }
}
