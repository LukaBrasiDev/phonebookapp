package akademia.phonebookapp.controllers;


import akademia.phonebookapp.dtos.mappers.ContactMapper;
import akademia.phonebookapp.dtos.model.ContactDto;
import akademia.phonebookapp.model.Contact;
import akademia.phonebookapp.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/dto/")
public class ContactControllerDto {

    private ContactService contactService;


    public ContactControllerDto(ContactService contactService) {
        this.contactService = contactService;

    }

    @GetMapping("/contacts")
    public List<ContactDto> getAllContacts() {

        return contactService.getContactsDto();
    }


    @GetMapping("/contacts/{name}")
    public List<ContactDto> getAllContactsByName(@PathVariable String name) {

        return contactService.getContactsDtoByName(name);
    }

    @DeleteMapping("/contacts")
    public ResponseEntity<?> deleteContact(@RequestParam(name = "phone") String phone) {
        if (contactService.deleteContactByPhone(phone)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/contacts")
    public void addNewContact(@RequestBody ContactDto contactDto) {
        contactService.addNewContactDto(contactDto);
    }

    @PutMapping("/contacts")
    public ResponseEntity<String> updateContactAddress(
            @RequestParam("phone") String phone,
            @RequestParam("city") String city) {
        if (contactService.updateAddressByPhone(phone, city)) {//true /fasle
            return new ResponseEntity<>("Contact updated!", HttpStatus.OK);
        }
        return new ResponseEntity<>("ERROR: contact not found!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/contacts/tag")
    public List<ContactDto> getContactsByTag(@RequestParam String title){
        return contactService.getContactDtoByTag(title);
    }




}
