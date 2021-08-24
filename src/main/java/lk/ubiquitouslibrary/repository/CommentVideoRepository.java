package lk.ubiquitouslibrary.repository;

import lk.ubiquitouslibrary.entity.CommentVideo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentVideoRepository extends PagingAndSortingRepository<CommentVideo,Long> {

    List<CommentVideo> findAllByVideo_IdOrderByIdDesc(Long id);

}
