package tsa_sim.person;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class PersonBuilder {

    private ArrayList<String> firstNames;
    private ArrayList<String> lastNames;

    public PersonBuilder() throws FileNotFoundException {
        this.seedLists();
    }

    private void seedLists() throws FileNotFoundException {
        //build name databases
        firstNames = new ArrayList<>();
        lastNames = new ArrayList<>();

        File firstNameFile = new File("resources/first_names.txt");
        File lastNameFile = new File("resources/last_names.txt");

        Scanner input = new Scanner(firstNameFile);
        while(input.hasNextLine()) {
            firstNames.add(input.nextLine());
        }
        input = new Scanner(lastNameFile);
        while(input.hasNextLine()) {
            lastNames.add(input.nextLine());
        }
    }

    public Person buildPerson(int id) {
        Random generator = new Random();
        return new Person(
                id,
                new Date(),
                firstNames.get(generator.nextInt(firstNames.size())),
                lastNames.get(generator.nextInt(lastNames.size())));
    }

}
