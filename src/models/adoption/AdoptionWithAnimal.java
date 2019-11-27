package models.adoption;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import models.animal.Animal;
import models.user.User;

/**
 * The AdoptionWithAnimal Class processes the information for an Adoption Request, which includes both the Animal's and
 * the User's information. The information of the Animal and User are both obtained through their respective objects.
 *
 * @author The Data Magic Engineering Team.
 */
public class AdoptionWithAnimal {

    private AdoptionRequest request;
    private Animal animal;
    private User adopter;

    /**
     * The Constructor which is used when creating an object.
     *
     * @param request A request, stored as an AdoptionWithAnimal object.
     * @param animal  An Animal Object
     * @param adopter A User Object
     */
    public AdoptionWithAnimal(AdoptionRequest request, Animal animal, User adopter) {
        this.request = request;
        this.animal = animal;
        this.adopter = adopter;
    }

    /**
     * Accessor for request.
     *
     * @return request, a field of type AdoptionRequest.
     */
    public AdoptionRequest getRequest() {
        return request;
    }

    /**
     * Accessor for animal.
     *
     * @return animal, a field of type Animal.
     */
    public Animal getAnimal() {
        return animal;
    }

    /**
     * Accessor for adopter.
     *
     * @return adopter, a field of type User.
     */
    public User getAdopter() {
        return adopter;
    }

    /**
     * Method that processes an application and returns the information as a String.
     *
     * @return The user's information, animal's information, the requested date, and the processed application text,
     * formatted as a String.
     */
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
