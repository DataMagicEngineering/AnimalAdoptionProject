package models.adoption;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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

  @Override
  public String toString() {
    String processedText = "";

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
    if (request.isProcessed()) {
      processedText += "\n";
      processedText += request.isApproved() ? "Approved" : "Rejected";
      processedText += " on " + dateFormatter.format(request.getDateApproved().atZone(ZoneId.systemDefault()));
    }

    String format = "%s %s wants to adopt %s\n"
        + "Requested on %s%s";

    String requestedDate = dateFormatter.format(request.getDateRequested().atZone(ZoneId.systemDefault()));

    return String.format(format,
        adopter.getFirstName(), adopter.getLastName(), animal.getName(),
        requestedDate, processedText);
  }
}
