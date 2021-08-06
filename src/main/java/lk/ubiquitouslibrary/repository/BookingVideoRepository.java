package lk.ubiquitouslibrary.repository;

import lk.ubiquitouslibrary.entity.BookingVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the BookingVideo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingVideoRepository extends JpaRepository<BookingVideo, Long> {

    List<BookingVideo> findAllByUser_Id(Long id);

}
