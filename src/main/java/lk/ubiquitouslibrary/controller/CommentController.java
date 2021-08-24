package lk.ubiquitouslibrary.controller;

import lk.ubiquitouslibrary.entity.CommentBook;
import lk.ubiquitouslibrary.entity.CommentVideo;
import lk.ubiquitouslibrary.repository.CommentBookRepository;
import lk.ubiquitouslibrary.repository.CommentVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    CommentBookRepository commentBookRepository;

    @Autowired
    CommentVideoRepository commentVideoRepository;

    @GetMapping("/books/{id}")
    public List<CommentBook> getCommentBooks(@PathVariable Long id) {
        return commentBookRepository.findAllByBook_IdOrderByIdDesc(id);
    }

    @GetMapping("/videos/{id}")
    public List<CommentVideo> getCommentVideos(@PathVariable Long id) {
        return commentVideoRepository.findAllByVideo_IdOrderByIdDesc(id);
    }

    @PostMapping("/books")
    public CommentBook setCommentBooks(@RequestBody CommentBook comment) {
        comment.setDateTime(LocalDateTime.now());
        return commentBookRepository.save(comment);
    }

    @PostMapping("/videos")
    public CommentVideo setCommentVideos(@RequestBody CommentVideo comment) {
        comment.setDateTime(LocalDateTime.now());
        return commentVideoRepository.save(comment);
    }

}
