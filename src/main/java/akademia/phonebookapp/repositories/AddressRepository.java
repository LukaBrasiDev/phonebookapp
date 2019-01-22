package akademia.phonebookapp.repositories;

import akademia.phonebookapp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long> {



    Optional<Address> findByCity(String city);
}
