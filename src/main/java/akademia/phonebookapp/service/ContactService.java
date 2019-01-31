package akademia.phonebookapp.service;

import akademia.phonebookapp.dtos.mappers.ContactMapper;
import akademia.phonebookapp.dtos.model.ContactDto;
import akademia.phonebookapp.model.*;
import akademia.phonebookapp.repositories.*;
import com.fasterxml.jackson.datatype.jdk8.Jdk8OptionalBeanPropertyWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContactService {

    private ContactRepository contactRepository;
    private AddressRepository addressRepository;
    private CategoryRepository categoryRepository;
    private RankingRepository rankingRepository;
    private TagRepository tagRepository;
    private ContactMapper contactMapper;

    //@Autowired  - automatycznie dodane za nas
    public ContactService(ContactRepository contactRepository,
                          AddressRepository addressRepository,
                          CategoryRepository categoryRepository,
                          RankingRepository rankingRepository,
                          TagRepository tagRepository,
                          ContactMapper mapper) {
        this.contactRepository = contactRepository;
        this.addressRepository = addressRepository;
        this.categoryRepository = categoryRepository;
        this.rankingRepository = rankingRepository;
        this.tagRepository = tagRepository;
        this.contactMapper = mapper;
    }

    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }

    //DTO
    public List<ContactDto> getContactsDto() {
        List<ContactDto> contactDtos = new ArrayList<>();

        for (Contact c : contactRepository.findAll()) {
            contactDtos.add(contactMapper.map(c));
        }
        return contactDtos;
    }

    public void addContact(Contact contact) {
        contactRepository.save(contact);
    }

    public List<Contact> getContactsByName(String name) {
        return contactRepository.findAllByName(name);
    }

    //DTO
    public List<ContactDto> getContactsDtoByName(String name) {
        List<ContactDto> contactDtos = new ArrayList<>();
        for (Contact c : contactRepository.findAllByName(name)) {
            contactDtos.add(contactMapper.map(c));
        }
        return contactDtos;
    }

    //DTO
    public boolean deleteContactByPhone(String phone) {
        Optional<Contact> contact = contactRepository.findByPhoneNumber(phone);

        if (contact.isPresent()) {
            contactRepository.deleteById(contact.get().getId());
            return true;
        }
        return false;
    }

    public void addNewContactDto(ContactDto contactDto) {


        Contact contact = new Contact();

        //ADDRESS
        Optional<Address> address = addressRepository.findByCity(contactDto.getAddress());
        if (address.isPresent()) {
            contact.setAddress(address.get());
        } else {
            Address addr = new Address();
            addr.setCity(contactDto.getAddress());
            contact.setAddress(addressRepository.save(addr));
        }


        //RANKING
        Optional<Ranking> rankning = rankingRepository.findRankningByNumber(contactDto.getRanking());

        if (rankning.isPresent()) {
            contact.setRanking(rankning.get());
        } else {
            Ranking rank = new Ranking();
            rank.setNumber(contactDto.getRanking());
            contact.setRanking(rankingRepository.save(rank));
        }


        //CATEGORY
        Optional<Category> category = categoryRepository.findCategoryByTitle(contactDto.getCategory());

        if (category.isPresent()) {
            contact.setCategory(category.get());
        } else {
            Category cat = new Category();
            cat.setTitle(contactDto.getCategory());
            contact.setCategory(categoryRepository.save(cat));
        }
        //TAG
        Set<Tag> emptyTags = new HashSet<>();
        contact.setTags(emptyTags);

        // FIELDS
        contact.setName(contactDto.getName());
        contact.setSurname(contactDto.getSurname());
        contact.setNumber(contactDto.getNumber());

        //SAVE
        contactRepository.save(contact);

    }

    //DTO
    public boolean updateAddressByPhone(String phone, String city) {
        //krok 1 znajduje kontakt po nr telefonu
        Optional<Contact> contactOptional = contactRepository.findByPhoneNumber(phone);


        //jezeli kontakt istnieje sprawdzam czy juz istnieje podany adres do aktualizacji w bazie
        if (contactOptional.isPresent()) {
            Optional<Address> addr = addressRepository.findByCity(city);

            //jezeli taki adres istnieje aktualizuje pobrany wczesniej kontakt
            if (addr.isPresent()) {
                contactOptional.get().setAddress(addr.get());
                contactRepository.save(contactOptional.get());
            } else {//jezeli podany adres nie istnieje to tworze obiekt adresu i zapisuje
                Address address = new Address();
                address.setCity(city);
                contactOptional.get().setAddress(addressRepository.save(address));
                //zapisuje zaktualizowany obiekt z powrotem do bazy danych
                contactRepository.save(contactOptional.get());
            }
            return true;

        }
        return false;
    }

   //DTO
    public boolean addTagsToContact(String phone, List<String> tagList) {
        //szukam kontaktu w bazie danych na podstawie nr telefonu
        Optional<Contact> contactOptional = contactRepository.findByPhoneNumber(phone);
        //jesli taki kontak istnieje
        if (contactOptional.isPresent()) {

            //iteruje po liscie tagów
            for (String t : tagList) {

                //przy kazdej iteracji pętli sprawdzam czytaki tag istnieje w bazie danych
                Optional<Tag> tagOptional = tagRepository.findByTitle(t);

                //jezeli tag nie istnieje w bazie to zapisuje go do bazy jako nowy tag oraz dodaje do go do kontaktu
                if (!tagOptional.isPresent()) {
                    contactOptional.get().getTags().add(tagRepository.save(new Tag(t)));
                    //jezeli tag istnieje w bazie to tylko dodaje go do kontaktu
                } else {
                    contactOptional.get().getTags().add(tagOptional.get());
                }
            }
            //zapisuje zaktualizowany kontakt z powrotem do bazy danych
            contactRepository.save(contactOptional.get());
            return true;
        }
        return false;
    }
//DTO
    public List<ContactDto> getContactDtoByTag (String title){
        List<ContactDto>contactDtos = new ArrayList<>();

        for (Contact c: contactRepository.findContactsByTag(title)){
contactDtos.add(contactMapper.map(c));
        }
        return contactDtos;
    }


}