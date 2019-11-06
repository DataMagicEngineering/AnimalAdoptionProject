package models.adoption;

import models.animal.Animal;
import models.user.User;

public class AdoptionWithAnimal {

  private AdoptionRequest request;
  private Animal animal;
  private User adopter;

  public AdoptionWithAnimal(AdoptionRequest request, Animal animal, User adopter) {
    this.request = request;
    this.animal = animal;
    this.adopter = adopter;
  }

  public AdoptionRequest getRequest() {
    return request;
  }

  public Animal getAnimal() {
    return animal;
  }

  public User getAdopter() {
    return adopter;
  }
}
