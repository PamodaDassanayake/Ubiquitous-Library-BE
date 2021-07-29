package lk.ubiquitouslibrary.repository;

import lk.ubiquitouslibrary.entity.BookingVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BookingVideo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingVideoRepository extends JpaRepository<BookingVideo, Long> {}
