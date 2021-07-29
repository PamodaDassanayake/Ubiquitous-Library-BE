package lk.ubiquitouslibrary.repository;

import lk.ubiquitouslibrary.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Book entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByTitleContainingOrAuthorContaining(String title, String author);
    List<Book> findAllByAuthorContaining(String author);
    List<Book> findAllByTitleContaining(String title);



}
