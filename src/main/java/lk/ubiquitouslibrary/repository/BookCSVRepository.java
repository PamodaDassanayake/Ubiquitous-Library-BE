package lk.ubiquitouslibrary.repository;

import lk.ubiquitouslibrary.entity.BookCSV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Book entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookCSVRepository extends JpaRepository<BookCSV, Long> {

}
