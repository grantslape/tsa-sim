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
    private final int idLimit;

    public PersonBuilder(int idLimit) throws FileNotFoundException {
        this.seedLists();
        this.idLimit = idLimit;
    }

    private void seedLists() throws FileNotFoundException {
        //build name databases
        firstNames = new ArrayList<>();
        lastNames = new ArrayList<>();

        File firstNameFile = new File("../../first_names.txt");
        File lastNameFile = new File("../../last_names.txt");

        Scanner input = new Scanner(firstNameFile);
        while(input.hasNextLine()) {
            firstNames.add(input.nextLine());
        }
        input = new Scanner(lastNameFile);
        while(input.hasNextLine()) {
            lastNames.add(input.nextLine());
        }
    }

    public Person buildPerson() {
        Random generator = new Random();
        return new Person(
                generator.nextInt(idLimit),
                new Date(),
                firstNames.get(generator.nextInt(firstNames.size()))
                lastNames.get(generator.nextInt(lastNames.size())));
    }

}
