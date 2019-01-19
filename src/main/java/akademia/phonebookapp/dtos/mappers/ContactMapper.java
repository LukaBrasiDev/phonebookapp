package akademia.phonebookapp.dtos.mappers;


import akademia.phonebookapp.commons.mapper.Mapper;
import akademia.phonebookapp.dtos.model.ContactDto;
import akademia.phonebookapp.model.Contact;
import akademia.phonebookapp.model.Tag;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component //kontener springa stworzy nam Beana z tej klasy - czyli obiekt.
public class ContactMapper implements Mapper<Contact, ContactDto> {

    @Override
    public ContactDto map(Contact from) {

        List<String> tags = from.getTags()
                .stream()
                .map(TagsToStringsList.INSTANCE)
                .collect(Collectors.toList());

        return new ContactDto(
                from.getName(),
                from.getSurname(),
                from.getNumber(),
                from.getAddress().getCity(),
                from.getCategory().getTitle(),
                from.getRanking().getNumber(),
                tags );
    }

    //pomocniczy enum do wywolywania metody na obiekcie Tag z listy
    private enum TagsToStringsList implements Function<Tag, String> {
        INSTANCE;

        @Override
        public String apply(Tag tag) {
            return tag.getTitle();
        }
    }
}
