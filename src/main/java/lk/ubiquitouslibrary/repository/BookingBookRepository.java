package lk.ubiquitouslibrary.repository;

import lk.ubiquitouslibrary.entity.BookingBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BookingBook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingBookRepository extends JpaRepository<BookingBook, Long> {}
