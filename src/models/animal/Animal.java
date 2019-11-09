package models.animal;

import com.sun.istack.internal.Nullable;
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

  //May be null if this animal hasn't been adopted yet.
  @Nullable
  private Instant dateAdopted;
  private Instant dateOfBirth;
  private boolean serviceTrained;
  private float weight; //in pounds
  private float height; //in inches
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
   * Overloaded constructor for creating new animals. This does not take an `id` argument since after being
   * inserted in the database, an id will automatically be generated.
   * @param name The string field that is initialized for an animal object.
   * @param species The string field that is initialized for an animal object.
   * @param description The string field that is initialized for an animal object.
   * @param gender The char field that is initialized for an animal object.
   * @param colors The List of type Color, which is an enum, that is initialized for an animal object.
   * @param adopted The boolean field that is initialized for an animal object.
   * @param dateArrived An Instant that is initialized for an animal object.
   * @param dateAdopted An Instant that is initialized for an animal object.
   * @param dateOfBirth An Instant that is initialized for an animal object.
   * @param serviceTrained The boolean field that is initialized for an animal object.
   * @param weight The float field that is initialized for an animal object.
   * @param height The float field that is initialized for an animal object.
   * @param breeds A list of type String that is initialized for an animal object.
   * @param tricks A list of type Trick, which is an enum, that is initialized for an animal object.
   * @param bathroomTraining A proficiency enum field that is initialized for an animal object.
   * @param vaccines A list of type Vaccine, which is a class, that is initialized for an animal object.
   * @param aggression An enum field that is initialized for an animal object.
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
   * @param id The int field that is initialized for an animal object.
   * @param name The string field that is initialized for an animal object.
   * @param species The string field that is initialized for an animal object.
   * @param description The string field that is initialized for an animal object.
   * @param gender The char field that is initialized for an animal object.
   * @param colors The List of type Color, which is an enum, that is initialized for an animal object.
   * @param adopted The boolean field that is initialized for an animal object.
   * @param dateArrived An Instant that is initialized for an animal object.
   * @param dateAdopted An Instant that is initialized for an animal object.
   * @param dateOfBirth An Instant that is initialized for an animal object.
   * @param serviceTrained The boolean field that is initialized for an animal object.
   * @param weight The float field that is initialized for an animal object.
   * @param height The float field that is initialized for an animal object.
   * @param breeds A list of type String that is initialized for an animal object.
   * @param tricks A list of type Trick, which is an enum, that is initialized for an animal object.
   * @param bathroomTraining A proficiency enum field that is initialized for an animal object.
   * @param vaccines A list of type Vaccine, which is a class, that is initialized for an animal object.
   * @param aggression An enum field that is initialized for an animal object.
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

  /**
   * Accessor for id
   * @return id, an int field in the Animal class.
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
   * Accessor for name
   * @return name, the String field in the Animal class.
   */
  public String getName() {
    return name;
  }

  /**
   * Mutator for name
   * @param name the String variable that the name field is assigned to.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Accessir for species
   * @return species, the String field in the Animal class.
   */
  public String getSpecies() {
    return species;
  }

  /**
   * Mutator for species
   * @param species the String variable that the species field is assigned to.
   */
  public void setSpecies(String species) {
    this.species = species;
  }

  /**
   * Accessor for description
   * @return description, the String field in the Animal class.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Mutator for
   * @param description The String variable that the description field is assigned to.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Accessor for gender
   * @return gender, the char field in the Animal class.
   */
  public char getGender() {
    return gender;
  }

  /**
   * Mutator for gender
   * @param gender The char variable that the gender field is assigned to.
   */
  public void setGender(char gender) {
    this.gender = gender;
  }

  /**
   * Accessor for colors
   * @return colors, the List of type Color field in the Animal class.
   */
  public List<Color> getColors() {
    return colors;
  }

  /**
   * Mutator for colors
   * @param colors The List of type Color variable that the colors field is assigned to.
   */
  public void setColors(List<Color> colors) {
    this.colors = colors;
  }

  /**
   * Accessor for adopted
   * @return adopted, the boolean field in the Animal class.
   */
  public boolean isAdopted() {
    return adopted;
  }

  /**
   * Mutator for adopted
   * @param adopted The boolean variable that the adopted field is assigned to.
   */
  public void setAdopted(boolean adopted) {
    this.adopted = adopted;
  }

  /**
   * Accessor for dateArrived
   * @return dateArrived, the Instant field in the Animal class.
   */
  public Instant getDateArrived() {
    return dateArrived;
  }

  /**
   * Mutator for dateArrived
   * @param dateArrived The Instant variable that the dateArrived field is assigned to.
   */
  public void setDateArrived(Instant dateArrived) {
    this.dateArrived = dateArrived;
  }

  /**
   * Accessor for dateAdopted
   * @return dateAdopted, the Instant field in the Animal class.
   */
  public Instant getDateAdopted() {
    return dateAdopted;
  }

  /**
   * Mutator for dateAdopted
   * @param dateAdopted The Instant variable that the dateAdopted field is assigned to.
   */
  public void setDateAdopted(Instant dateAdopted) {
    this.dateAdopted = dateAdopted;
  }

  /**
   * Accessor for serviceTrained
   * @return serviceTrained, the boolean field in the Animal class.
   */
  public boolean isServiceTrained() {
    return serviceTrained;
  }

  /**
   * Mutator for serviceTrained
   * @param serviceTrained The boolean variable that the serviceTrained field is assigned to.
   */
  public void setServiceTrained(boolean serviceTrained) {
    this.serviceTrained = serviceTrained;
  }

  /**
   * Accessor for weight
   * @return weight, the float field in the Animal class.
   */
  public float getWeight() {
    return weight;
  }

  /**
   * Mutator for weight
   * @param weight The float variable that the weight field is assigned to.
   */
  public void setWeight(float weight) {
    this.weight = weight;
  }

  /**
   * Accessor for height
   * @return height, the float field in the Animal class.
   */
  public float getHeight() {
    return height;
  }

  /**
   * Mutator for height
   * @param height The float variable that the height field is assigned to.
   */
  public void setHeight(float height) {
    this.height = height;
  }

  /**
   * Accessor for dateOfBirth
   * @return dateOfBirth, the Instant field in the Animal class.
   */
  public Instant getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * Mutator for dateOfBirth
   * @param dateOfBirth The Instant variable that the dateOfBirth field is assigned to.
   */
  public void setDateOfBirth(Instant dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  /**
   * Accessor for breeds
   * @return breeds, the List of type String field in the Animal class.
   */
  public List<String> getBreeds() {
    return breeds;
  }

  /**
   * Mutator for breeds
   * @param breeds The List of type String variable that the breeds field is assigned to.
   */
  public void setBreeds(List<String> breeds) {
    this.breeds = breeds;
  }

  /**
   * Accessor for tricks
   * @return tricks, the List of class type Trick field in the Animal class.
   */
  public List<Trick> getTricks() {
    return tricks;
  }

  /**
   * Mutator for tricks
   * @param tricks The List of class type Trick variable that the tricks field is assigned to.
   */
  public void setTricks(List<Trick> tricks) {
    this.tricks = tricks;
  }

  /**
   * Accessor for bathroomTraining
   * @return bathroomTraining, the Proficiency field in the Animal class.
   */
  public Proficiency getBathroomTraining() {
    return bathroomTraining;
  }

  /**
   * Mutator for bathroomTraining
   * @param bathroomTraining The enum Proficiency variable that the bathroomTraining field is assigned to.
   */
  public void setBathroomTraining(Proficiency bathroomTraining) {
    this.bathroomTraining = bathroomTraining;
  }

  /**
   * Accessor for vaccines
   * @return vaccines, the List of class type Vaccine field in the Animal class.
   */
  public List<Vaccine> getVaccines() {
    return vaccines;
  }

  /**
   * Mutator for vaccines
   * @param vaccines The List of class type Vaccine variable that the vaccines field is assigned to.
   */
  public void setVaccines(List<Vaccine> vaccines) {
    this.vaccines = vaccines;
  }

  /**
   * Accessor for aggression
   * @return aggression, the Color enum field in the Animal class.
   */
  public Color getAggression() {
    return aggression;
  }

  /**
   * Mutator for aggression
   * @param aggression The enum type Color variable that the aggression field is assigned to.
   */
  public void setAggression(Color aggression) {
    this.aggression = aggression;
  }

  /**
   * Accessor to obtain the Color enums in the form of a String
   * @author Luis Hernandez
   * @return theColors, which is the String variable that the color enums is assigned to.
   */
  public String getColorString(){
    String theColors = "";
    //loop through colors, add their name, a separator and add it into string
    for(Color color : colors){
      theColors += color.toString();
      theColors += "|";
    }
    return theColors;
  }

  /**
   * Accessor to obtain the Breeds List in the form of a String
   * @author Luis Hernandez
   * @return theBreeds, which is the String variable that the list is assigned to.
   */
  public String getBreedString(){
    String theBreeds = "";
    //loop through colors, add their name, a separator and add it into string
    for(String breed : breeds){
      theBreeds += breed;
      theBreeds += "|";
    }
    return theBreeds;
  }
}
