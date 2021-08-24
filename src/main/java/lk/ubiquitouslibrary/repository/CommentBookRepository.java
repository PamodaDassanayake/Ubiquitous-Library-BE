package lk.ubiquitouslibrary.repository;

import lk.ubiquitouslibrary.entity.CommentBook;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentBookRepository extends PagingAndSortingRepository<CommentBook,Long> {

    List<CommentBook> findAllByBook_IdOrderByIdDesc(Long id);

}
