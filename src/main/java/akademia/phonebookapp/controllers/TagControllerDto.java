package akademia.phonebookapp.controllers;

import akademia.phonebookapp.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dto")
public class TagControllerDto {

    private ContactService contactService;

    public TagControllerDto(ContactService contactService) {

        this.contactService = contactService;
    }

    @PostMapping("/tags")
    public ResponseEntity<?> addTagsToContact(
            @RequestParam String phone,
            @RequestBody List<String> tags) {
        if (contactService.addTagsToContact(phone, tags)) {
            return new ResponseEntity<>("Tags added correctly", HttpStatus.OK);
        }
        return new ResponseEntity<>("ERROR: tags not added!", HttpStatus.NOT_FOUND);
    }
}

