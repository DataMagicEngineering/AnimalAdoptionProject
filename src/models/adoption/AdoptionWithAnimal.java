package models.adoption;

import models.animal.Animal;

public class AdoptionWithAnimal {

  private AdoptionRequest request;
  private Animal animal;

  public AdoptionWithAnimal(AdoptionRequest request, Animal animal) {
    this.request = request;
    this.animal = animal;
  }

  public AdoptionRequest getRequest() {
    return request;
  }

  public Animal getAnimal() {
    return animal;
  }
}
