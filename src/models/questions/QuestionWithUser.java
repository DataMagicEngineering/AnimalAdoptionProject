package models.questions;

import models.user.User;

/**
 * The QuestionWithUser class contains the information for a question, the user who asked the question, and the user
 * who answers the question.
 * @author The Data Magic Engineering Team.
 */
public class QuestionWithUser {

  private Question question;
  private User asker;
  private User answerer;

  /**
   * Constructor to create Question objects with.
   * @param question an instance of Question that the "question" field is assigned to.
   * @param asker an instance of User that the "asker" field is assigned to.
   * @param answerer an instance of User that the "answerer" field is assigned to.
   */
  public QuestionWithUser(Question question, User asker, User answerer) {
    this.question = question;
    this.asker = asker;
    this.answerer = answerer;
  }

  /**
   * Accessor for question.
   * @return question, a field of type Question.
   */
  public Question getQuestion() {
    return question;
  }

  /**
   * Accessor for asker.
   * @return asker, a field of type User.
   */
  public User getAsker() {
    return asker;
  }

  /**
   * Accessor for answerer.
   * @return answerer, a field of type User.
   */
  public User getAnswerer() {
    return answerer;
  }

  /**
   * A toString method that displays the user's name and question
   * @return the user's first name, last name, and question.
   */
  @Override
  public String toString() {
    String format = "%s %s\n"
        + "%s";

    return String.format(format, asker.getFirstName(), asker.getLastName(), question.getQuestion());
  }
}
