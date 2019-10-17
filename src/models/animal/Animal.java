package models.animal;

import java.time.Instant;
import java.util.List;

public class Animal {
  private int id;
  private String name;
  private String species;
  private String description;
  private char gender;
  private List<Color> colors;
  private boolean adopted;
  private Instant dateArrived;
  private Instant dateAdopted;
  private Instant dateOfBirth;
  private boolean serviceTrained;
  private float weight; //in kg
  private float height;
  private List<String> breeds;
  private List<Trick> tricks;
  private Proficiency bathroomTraining;
  private List<Vaccine> vaccines;
  private Color aggression;

  /**
   * Default constructor for filling in an animal's properties at another time.
   */
  public Animal() {
  }

  /**
   * Constructor for creating new animals. This does not take an `id` argument since after being
   * inserted in the database, an id will automatically be generated.
   */
  public Animal(String name, String species, String description, char gender,
      List<Color> colors, boolean adopted, Instant dateArrived, Instant dateAdopted,
      Instant dateOfBirth, boolean serviceTrained, float weight, float height,
      List<String> breeds, List<Trick> tricks, Proficiency bathroomTraining,
      List<Vaccine> vaccines, Color aggression) {
    this.name = name;
    this.species = species;
    this.description = description;
    this.gender = gender;
    this.colors = colors;
    this.adopted = adopted;
    this.dateArrived = dateArrived;
    this.dateAdopted = dateAdopted;
    this.dateOfBirth = dateOfBirth;
    this.serviceTrained = serviceTrained;
    this.weight = weight;
    this.height = height;
    this.breeds = breeds;
    this.tricks = tricks;
    this.bathroomTraining = bathroomTraining;
    this.vaccines = vaccines;
    this.aggression = aggression;
  }

  /**
   * Constructor for creating animals that already have an id.
   */
  public Animal(int id, String name, String species, String description, char gender,
      List<Color> colors, boolean adopted, Instant dateArrived, Instant dateAdopted,
      Instant dateOfBirth, boolean serviceTrained, float weight, float height,
      List<String> breeds, List<Trick> tricks, Proficiency bathroomTraining,
      List<Vaccine> vaccines, Color aggression) {
    this.id = id;
    this.name = name;
    this.species = species;
    this.description = description;
    this.gender = gender;
    this.colors = colors;
    this.adopted = adopted;
    this.dateArrived = dateArrived;
    this.dateAdopted = dateAdopted;
    this.dateOfBirth = dateOfBirth;
    this.serviceTrained = serviceTrained;
    this.weight = weight;
    this.height = height;
    this.breeds = breeds;
    this.tricks = tricks;
    this.bathroomTraining = bathroomTraining;
    this.vaccines = vaccines;
    this.aggression = aggression;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSpecies() {
    return species;
  }

  public void setSpecies(String species) {
    this.species = species;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public char getGender() {
    return gender;
  }

  public void setGender(char gender) {
    this.gender = gender;
  }

  public List<Color> getColors() {
    return colors;
  }

  public void setColors(List<Color> colors) {
    this.colors = colors;
  }

  public boolean isAdopted() {
    return adopted;
  }

  public void setAdopted(boolean adopted) {
    this.adopted = adopted;
  }

  public Instant getDateArrived() {
    return dateArrived;
  }

  public void setDateArrived(Instant dateArrived) {
    this.dateArrived = dateArrived;
  }

  public Instant getDateAdopted() {
    return dateAdopted;
  }

  public void setDateAdopted(Instant dateAdopted) {
    this.dateAdopted = dateAdopted;
  }

  public boolean isServiceTrained() {
    return serviceTrained;
  }

  public void setServiceTrained(boolean serviceTrained) {
    this.serviceTrained = serviceTrained;
  }

  public float getWeight() {
    return weight;
  }

  public void setWeight(float weight) {
    this.weight = weight;
  }

  public float getHeight() {
    return height;
  }

  public void setHeight(float height) {
    this.height = height;
  }

  public Instant getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Instant dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public List<String> getBreeds() {
    return breeds;
  }

  public void setBreeds(List<String> breeds) {
    this.breeds = breeds;
  }

  public List<Trick> getTricks() {
    return tricks;
  }

  public void setTricks(List<Trick> tricks) {
    this.tricks = tricks;
  }

  public Proficiency getBathroomTraining() {
    return bathroomTraining;
  }

  public void setBathroomTraining(Proficiency bathroomTraining) {
    this.bathroomTraining = bathroomTraining;
  }

  public List<Vaccine> getVaccines() {
    return vaccines;
  }

  public void setVaccines(List<Vaccine> vaccines) {
    this.vaccines = vaccines;
  }

  public Color getAggression() {
    return aggression;
  }

  public void setAggression(Color aggression) {
    this.aggression = aggression;
  }

}
