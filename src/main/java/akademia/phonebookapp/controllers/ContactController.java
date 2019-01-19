package akademia.phonebookapp.controllers;


import akademia.phonebookapp.model.Contact;
import akademia.phonebookapp.service.ContactService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ContactController {

    private ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contacts")
    public List<Contact> getAllContacts() {
        return contactService.getContacts();
    }

    @PostMapping("/contacts")
    public void addContact(@RequestBody Contact contact) {
        contactService.addContact(contact);
    }
}
