package akademia.phonebookapp.commons.mapper;

public interface Mapper<F,T> {

    //T - obiekt z mapowany, czyli DTO
    //F - obiekt DAO, czysta encja(klasa w tym przypadku Contact)
    T map(F from);

}
