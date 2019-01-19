package akademia.phonebookapp.controllers;


import akademia.phonebookapp.dtos.mappers.ContactMapper;
import akademia.phonebookapp.dtos.model.ContactDto;
import akademia.phonebookapp.model.Contact;
import akademia.phonebookapp.service.ContactService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/dto/")
public class ContactControllerDto {

    private ContactService contactService;
    private ContactMapper mapper;

    public ContactControllerDto(ContactService contactService, ContactMapper mapper) {
        this.contactService = contactService;
        this.mapper = mapper;
    }

    @GetMapping("/contacts")
    public List<ContactDto> getAllContacts() {

        List<Contact> contacts = contactService.getContacts(); //zwraca listę warstwy DAO
        List<ContactDto> contactDtos = new ArrayList<>();

        for (Contact c : contacts) {
            contactDtos.add(mapper.map(c));
        }
        return contactDtos;
    }


    @GetMapping("/contacts/{name}")
    public List<ContactDto> getAllContactsByName(@PathVariable String name) {
        List<Contact> contacts = contactService.getContactsByName(name);
        List<ContactDto> contactDtos = new ArrayList<>();

        for (Contact c : contacts) {
            contactDtos.add(mapper.map(c));
        }
        return contactDtos;
    }

    @DeleteMapping("/contacts/{surname}")
    public void deleteContact(@PathVariable String surname) {
        contactService.deleteContact(surname);
    }

}
