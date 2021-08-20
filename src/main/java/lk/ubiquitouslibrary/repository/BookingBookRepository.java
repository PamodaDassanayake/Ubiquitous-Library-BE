package lk.ubiquitouslibrary.repository;

import lk.ubiquitouslibrary.entity.BookingBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data SQL repository for the BookingBook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingBookRepository extends JpaRepository<BookingBook, Long> {

    List<BookingBook> findAllByUser_Id(Long id);

    Long countAllByUser_IdAndBookingStartBetweenOrBookingEndBetween(Long userId, LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2);
}
