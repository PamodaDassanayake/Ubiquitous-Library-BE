package lk.ubiquitouslibrary.controller;

import lk.ubiquitouslibrary.dto.BookingDTO;
import lk.ubiquitouslibrary.dto.CheckBookingDTO;
import lk.ubiquitouslibrary.entity.BookingBook;
import lk.ubiquitouslibrary.entity.BookingVideo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

//    @Autowired
//    LibraryService libraryService;

    @PostMapping("/reserve")
    public ResponseEntity<BookingDTO> reserve(@RequestBody BookingDTO bookingDTO){
//        bookingDTO = libraryService.reserve(bookingDTO);
        return ResponseEntity.ok(bookingDTO);
    }

    @PostMapping("/check")
    public ResponseEntity<CheckBookingDTO> check(@RequestBody CheckBookingDTO bookingDTO){
//        bookingDTO = libraryService.checkBookBookings(bookingDTO);
        return ResponseEntity.ok(bookingDTO);
    }

    @PostMapping("/reserved/books")
    public ResponseEntity<BookingBook> getReservedBooks(){
//        bookingDTO = libraryService.checkBookBookings();
//        return ResponseEntity.ok(bookingDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reserved/videos")
    public ResponseEntity<BookingVideo> getReservedVideos(){
//        bookingDTO = libraryService.checkBookBookings();
//        return ResponseEntity.ok(bookingDTO);
        return ResponseEntity.ok().build();
    }
}
