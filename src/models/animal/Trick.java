package models.animal;

/**
 * Represents a 'trick' an animal can learn and the level of proficiency in said skill.
 */
public class Trick {
  private String name;
  private Proficiency proficiency;

  public Trick(String name, Proficiency proficiency) {
    this.name = name;
    this.proficiency = proficiency;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Proficiency getProficiency() {
    return proficiency;
  }

  public void setProficiency(Proficiency proficiency) {
    this.proficiency = proficiency;
  }
}
