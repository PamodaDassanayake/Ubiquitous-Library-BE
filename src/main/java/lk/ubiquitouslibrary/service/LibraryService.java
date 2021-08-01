package lk.ubiquitouslibrary.service;

import lk.ubiquitouslibrary.dto.BookingDTO;
import lk.ubiquitouslibrary.dto.CheckBookingDTO;
import lk.ubiquitouslibrary.entity.*;
import lk.ubiquitouslibrary.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    MembershipRepository membershipRepository;
    UserRepository userRepository;
    MembershipTypeRepository membershipTypeRepository;
    BookRepository bookRepository;
    VideoRepository videoRepository;
    BookingBookRepository bookingBookRepository;
    BookingVideoRepository bookingVideoRepository;
    UserService userService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public LibraryService(MembershipRepository membershipRepository, UserRepository userRepository, MembershipTypeRepository membershipTypeRepository, BookRepository bookRepository, VideoRepository videoRepository, BookingBookRepository bookingBookRepository, BookingVideoRepository bookingVideoRepository) {
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
        this.membershipTypeRepository = membershipTypeRepository;
        this.bookRepository = bookRepository;
        this.videoRepository = videoRepository;
        this.bookingBookRepository = bookingBookRepository;
        this.bookingVideoRepository = bookingVideoRepository;
    }

//    public BookingDTO reserve(BookingDTO bookingDTO) {
//        if (bookingDTO.getBooking().getId() == null) {
//            User user = userRepository.getById(bookingDTO.getBooking().getUser().getId());
//
//            booking = new Booking()
//                    .bookingStart(bookingDTO.getBooking().getBookingStart())
//                    .bookingEnd(bookingDTO.getBooking().getBookingEnd())
//                    .user(user);
//
//            booking = bookingRepository.save(booking);
//        } else {
//            booking = bookingRepository.getById(bookingDTO.getBooking().getId());
//        }
//
//        bookingDTO.setBooking(booking);
//
//        if (bookingDTO.getBook() != null) {
//            Book book = bookRepository.getById(bookingDTO.getBook());
//            BookingBook bookingBook = new BookingBook()
//                    .book(book)
//                    .booking(booking);
//
//            bookingBookRepository.save(bookingBook);
//        }
//
//        if (bookingDTO.getVideo() != null) {
//            Video video = videoRepository.getById(bookingDTO.getVideo());
//            BookingVideo bookingVideo = new BookingVideo()
//                    .video(video)
//                    .booking(booking);
//
//            bookingVideoRepository.save(bookingVideo);
//        }
//
//        return bookingDTO;
//    }
//
//    public CheckBookingDTO checkBookBookings(CheckBookingDTO checkBookingDTO) {
//        String sqlInner_GetBookingId = "SELECT B.id FROM booking B " +
//                "WHERE (B.booking_start <= :booking_start AND B.booking_end >= :booking_end) " +
//                "OR (B.booking_start >= :booking_start AND B.booking_start <= :booking_end) " +
//                "OR (B.booking_end >= :booking_start AND B.booking_end <= :booking_end) ";
//
//        String sql = "SELECT COUNT(BB.id) AS bb_count, B.no_of_copies FROM booking_book BB, book B " +
//                "WHERE BB.book_id = :book_id " +
//                "AND B.id = :book_id " +
//                "AND BB.booking_id IN (" + sqlInner_GetBookingId + ") ";
//
//        String sql2 = sqlInner_GetBookingId;
//
//        MapSqlParameterSource parameters = new MapSqlParameterSource();
//        parameters.addValue("book_id", checkBookingDTO.getBook());
//        parameters.addValue("booking_start", checkBookingDTO.getBookingStart());
//        parameters.addValue("booking_end", checkBookingDTO.getBookingEnd());
//
//        return namedParameterJdbcTemplate.query(sql, parameters,
//                rs -> {
//                    rs.next();
//                    checkBookingDTO.setAvailableQty(rs.getInt("no_of_copies") - rs.getInt("bb_count"));
//                    return checkBookingDTO;
//                }
//        );
//    }
//
//    public List<BookingBook> getBookBookingsForUser(){
//        User user = userService.getUserWithAuthorities().get();
//
//
//
//        return null;
//    }

    /*public CheckBookingDTO checkVideoBookings(CheckBookingDTO checkBookingDTO) {
        String sqlInner_GetBookingId = "SELECT B.id FROM booking B " +
                "WHERE (B.booking_start <= :booking_start AND B.booking_end >= :booking_end) " +
                "OR (B.booking_start >= :booking_start AND B.booking_start <= :booking_end) " +
                "OR (B.booking_end >= :booking_start AND B.booking_end <= :booking_end) ";

        String sql = "SELECT COUNT(BB.id) AS bb_count, B.no_of_copies FROM booking_book BB, book B " +
                "WHERE BB.book_id = :book_id " +
                "AND B.id = :book_id" +
                "AND BB.booking_id IN (" + sqlInner_GetBookingId + ") ";

        String sql2 = sqlInner_GetBookingId;

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("book_id", checkBookingDTO.getBook());
        parameters.addValue("booking_start", checkBookingDTO.getBookingStart());
        parameters.addValue("booking_end", checkBookingDTO.getBookingEnd());

        return namedParameterJdbcTemplate.query(sql, parameters,
                rs -> {
                    rs.next();
                    checkBookingDTO.setAvailableQty(rs.getInt("no_of_copies") - rs.getInt("bb_count"));
                    return checkBookingDTO;
                }
        );
    }*/


}
