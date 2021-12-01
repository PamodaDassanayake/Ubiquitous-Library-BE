package lk.ubiquitouslibrary.controller;

import lk.ubiquitouslibrary.dto.BookingDTO;
import lk.ubiquitouslibrary.dto.CheckBookingDTO;
import lk.ubiquitouslibrary.entity.BookingBook;
import lk.ubiquitouslibrary.entity.BookingVideo;
import lk.ubiquitouslibrary.service.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
@Slf4j
public class LibraryController {

    @Autowired
    LibraryService libraryService;

    @PostMapping("/reserve")
    public ResponseEntity<BookingDTO> reserve(@RequestBody BookingDTO bookingDTO){
        bookingDTO = libraryService.reserve(bookingDTO);
        return ResponseEntity.ok(bookingDTO);
    }

    @PostMapping("/check")
    public ResponseEntity<CheckBookingDTO> check(@RequestBody CheckBookingDTO bookingDTO){
        log.info("Request to check availability");
        bookingDTO = libraryService.checkBookBookings(bookingDTO);
        return ResponseEntity.ok(bookingDTO);
    }

    @GetMapping("admin/reserved/books")
    public List<BookingBook> getAllReservedBooks(){
        return libraryService.getAllBooksAdmin();
    }

    @GetMapping("admin/reserved/videos")
    public List<BookingVideo> getAllReservedVideos(){
        return libraryService.getAllVideosAdmin();
    }

    @PostMapping("/reserved/books")
    public ResponseEntity<List<BookingBook>> getReservedBooks(){
        return ResponseEntity.ok(libraryService.getBookBookingsForUser());
    }

    @PostMapping("/reserved/videos")
    public ResponseEntity<List<BookingVideo>> getReservedVideos(){
        return ResponseEntity.ok(libraryService.getVideoBookingsForUser());
    }

    @PostMapping("/pay/book/{id}/{amount}")
    public BookingBook payForBookLend(@PathVariable Long id, @PathVariable Double amount){
        return libraryService.payForBookLend(id,amount);
    }

    @PostMapping("/pay/video/{id}/{amount}")
    public BookingVideo payForVideoLend(@PathVariable Long id, @PathVariable Double amount){
        return libraryService.payForVideoLend(id,amount);
    }
}
