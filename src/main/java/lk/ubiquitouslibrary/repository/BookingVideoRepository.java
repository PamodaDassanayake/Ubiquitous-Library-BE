package lk.ubiquitouslibrary.repository;

import lk.ubiquitouslibrary.entity.BookingVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data SQL repository for the BookingVideo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingVideoRepository extends JpaRepository<BookingVideo, Long> {

    List<BookingVideo> findAllByUser_Id(Long id);

    Long countAllByUser_IdAndBookingStartBetweenOrBookingEndBetween(Long userId, LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2);

}
