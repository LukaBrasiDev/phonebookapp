package akademia.phonebookapp.repositories;

import akademia.phonebookapp.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// @Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    // String BY_NAME = "select * from contact where name = ?1";

    // @Query(value = BY_NAME, nativeQuery = true) // alternatywnie przez zmienną BY_NAME
    @Query(value = "select * from contacts where name = ?1", nativeQuery = true)
    //to zapytanie zrobi za nas automatycznie JPA, gdy zakomentujemy ta linię.
    List<Contact> findAllByName(String name);

    Contact findBySurname(String surname);


    @Query(value = "delete from contacts where surname = ?1", nativeQuery = true)
    void deleteContactBySurname(String surname); //todo nalezy poprawic query

}

