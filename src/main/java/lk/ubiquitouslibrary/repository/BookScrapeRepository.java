package lk.ubiquitouslibrary.repository;

import lk.ubiquitouslibrary.entity.BookScrape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Book entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookScrapeRepository extends JpaRepository<BookScrape, Long> {

}
