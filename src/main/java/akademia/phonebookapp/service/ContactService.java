package akademia.phonebookapp.service;

import akademia.phonebookapp.model.Contact;
import akademia.phonebookapp.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private ContactRepository contactRepository;

    //@Autowired  - automatycznie dodane za nas
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }

    public void addContact(Contact contact) {
        contactRepository.save(contact);
    }

    public List<Contact> getContactsByName(String name) {
        return contactRepository.findAllByName(name);
    }

    public void deleteContact(String surname) {
        Contact contact = contactRepository.findBySurname(surname);

     //   contactRepository.deleteContactBySurname(surname);
        contactRepository.deleteById(contact.getId());

    }


}
