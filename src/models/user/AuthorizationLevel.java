package models.user;

/**
 * The level of authority a user has on the management system.
 *
 * Basic authority contains skills such as submitting adoption requests, viewing animals, and
 * asking questions.
 *
 * Volunteer authority allows users to log hours worked and all features of Basic authority.
 *
 * Administrator authority has total control over the management system including creating and
 * removing animals, approving adoption requests, and the features of Volunteer and Administrator.
 */
public enum AuthorizationLevel {
  BASIC,
  VOLUNTEER,
  ADMINISTRATION
}
