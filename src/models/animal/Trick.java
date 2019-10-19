package models.animal;

/**
 * Represents a 'trick' an animal can learn and the level of proficiency in said skill.
 * @author The Data Magic Engineering Team
 */
public class Trick {
  private String name;
  private Proficiency proficiency;

  /**
   * Constructor for creating tricks and how proficient an animal is at the trick.
   * @param name The String field that is initialized for a Trick object.
   * @param proficiency The Proficiency enum field that is initialized for a Trick object.
   */
  public Trick(String name, Proficiency proficiency) {
    this.name = name;
    this.proficiency = proficiency;
  }

  /**
   * Accessor for name
   * @return name, which is the String field in the Trick class.
   */
  public String getName() {
    return name;
  }

  /**
   * Mutator for name
   * @param name a String variable that the name field is assigned to.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Accessor for proficiency
   * @return proficiency, which is the Enum field in the Trick class.
   */
  public Proficiency getProficiency() {
    return proficiency;
  }

  /**
   * Mutator for proficiency
   * @param proficiency a Proficiency enum variable that the proficiency field is assigned to.
   */
  public void setProficiency(Proficiency proficiency) {
    this.proficiency = proficiency;
  }
}
