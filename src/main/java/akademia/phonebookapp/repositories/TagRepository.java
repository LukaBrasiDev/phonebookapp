package akademia.phonebookapp.repositories;

import akademia.phonebookapp.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query(value = "select * from tags where title = ?1", nativeQuery = true)//true=SQL,  false=JPQL select o from Tag o whwere o.title = ?1
    Optional<Tag> findByTitle(String title);


}
