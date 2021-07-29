package lk.ubiquitouslibrary.repository;

import lk.ubiquitouslibrary.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Video entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findAllByTitleContaining(String title);

}
