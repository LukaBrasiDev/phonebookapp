package akademia.phonebookapp.repositories;

import akademia.phonebookapp.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// @Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    // String BY_NAME = "select * from contact where name = ?1";

    // @Query(value = BY_NAME, nativeQuery = true) // alternatywnie przez zmienną BY_NAME
    @Query(value = "select * from contacts where name = ?1", nativeQuery = true)
    //to zapytanie zrobi za nas automatycznie JPA, gdy zakomentujemy ta linię.
    List<Contact> findAllByName(String name);

    Contact findBySurname(String surname);


    @Query(value = "select * from contacts where number = ?1", nativeQuery = true)
    Optional<Contact> findByPhoneNumber(String phone);

    @Query(value = "select contacts.id,\n" +
            "contacts.name,\n" +
            "contacts.number,\n" +
            "contacts.surname,\n" +
            "contacts.fk_address,\n" +
            "contacts.fk_category,\n" +
            "contacts.fk_ranking\n" +
            "from contacts\n" +
            "join contact_tag on contact_id = contact_tag.contact_id\n" +
            "join tags on contact_tag.tag_id = tags.id\n" +
            "where tags.id=(select id from tags where title=?1)", nativeQuery = true)
    List<Contact> findContactsByTag(String title);


}

