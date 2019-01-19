package akademia.phonebookapp.dtos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    private String name;
    private String surname;
    private String number;
    private String category;
    private String address;
    private int ranking;
    private List<String> tags;

}
