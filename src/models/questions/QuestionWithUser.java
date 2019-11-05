package models.questions;

import models.user.User;

public class QuestionWithUser {

  private Question question;
  private User asker;
  private User answerer;

  public QuestionWithUser(Question question, User asker, User answerer) {
    this.question = question;
    this.asker = asker;
    this.answerer = answerer;
  }

  public Question getQuestion() {
    return question;
  }

  public User getAsker() {
    return asker;
  }

  public User getAnswerer() {
    return answerer;
  }

  @Override
  public String toString() {
    String format = "%s %s\n"
        + "%s";

    return String.format(format, asker.getFirstName(), asker.getLastName(), question.getQuestion());
  }
}
