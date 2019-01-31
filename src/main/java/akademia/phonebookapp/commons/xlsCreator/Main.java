package akademia.phonebookapp.commons.xlsCreator;

import org.apache.poi.ss.usermodel.Row;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        CreatorXLS<Person> creatorXLS = new CreatorXLS<>(Person.class);
        List<Person> persons = Arrays.asList(
                new Person("Adam", "Anaczkowski", 37),
                new Person("Łukasz", "Anaczkowski", 7),
                new Person("Kamila", "Anaczkowska", 30),
                new Person("Ada", "Anaczkowska", 2),
                new Person("Ala", "Anaczkowska", 2)
        );
        try {
            creatorXLS.createFile(persons, "src/main/resources/", "persons");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


    }
}
