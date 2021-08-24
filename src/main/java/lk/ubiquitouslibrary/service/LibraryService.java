package lk.ubiquitouslibrary.service;

import lk.ubiquitouslibrary.dto.BookingDTO;
import lk.ubiquitouslibrary.dto.CheckBookingDTO;
import lk.ubiquitouslibrary.entity.*;
import lk.ubiquitouslibrary.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class LibraryService {

    UserRepository userRepository;
    BookRepository bookRepository;
    VideoRepository videoRepository;
    BookingBookRepository bookingBookRepository;
    BookingVideoRepository bookingVideoRepository;
    UserService userService;
    MembershipService membershipService;
    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<BookingBook> getAllBooksAdmin() {
        return bookingBookRepository.findAll();
    }

    public List<BookingVideo> getAllVideosAdmin() {
        return bookingVideoRepository.findAll();
    }

    public BookingDTO reserve(BookingDTO bookingDTO) {
        User user = userRepository.getById(bookingDTO.getUserId());

        MembershipType membershipType = membershipService.getUserMembership(bookingDTO.getUserId()).getType();

        if (membershipType == null){
            throw new RuntimeException("Please join a membership");
        }

        Double fee = 0D;
        long numberOfDays = ChronoUnit.DAYS.between(bookingDTO.getBookingStart(), bookingDTO.getBookingEnd());

        if (bookingDTO.getBook() != null) {
            // Validate books lending limit
            if (membershipType.getBooksPerUser() < countBookBookingsForUserOnDates(user.getId(), bookingDTO.getBookingStart(), bookingDTO.getBookingEnd())) {
                throw new RuntimeException("Exceeding Book Lending limit for membership");
            }

            fee = membershipType.getOverdueChargesPerDay() * (
                    numberOfDays > membershipType.getVideoLendingDurationDays() ? (numberOfDays - membershipType.getVideoLendingDurationDays()) : 0
            );

            Book book = bookRepository.getById(bookingDTO.getBook());
            BookingBook bookingBook = BookingBook.builder()
                    .book(book)
                    .bookingStart(bookingDTO.getBookingStart())
                    .bookingEnd(bookingDTO.getBookingEnd())
                    .user(user)
                    .fee(fee)
                    .build();

            bookingBookRepository.save(bookingBook);
        }

        if (bookingDTO.getVideo() != null) {
            // Validate books lending limit
            if (membershipType.getVideosPerUser() < countVideoBookingsForUserOnDates(user.getId(), bookingDTO.getBookingStart(), bookingDTO.getBookingEnd())) {
                throw new RuntimeException("Exceeding Video Lending limit for membership");
            }

            fee = membershipType.getOverdueChargesPerDay() * (
                    numberOfDays > membershipType.getVideoLendingDurationDays() ? (numberOfDays - membershipType.getVideoLendingDurationDays()) : 0
            );

            Video video = videoRepository.getById(bookingDTO.getVideo());
            BookingVideo bookingVideo = BookingVideo.builder()
                    .video(video)
                    .bookingStart(bookingDTO.getBookingStart())
                    .bookingEnd(bookingDTO.getBookingEnd())
                    .user(user)
                    .fee(fee)
                    .build();

            bookingVideoRepository.save(bookingVideo);
        }

        bookingDTO.setFee(fee);

        return bookingDTO;
    }

    public BookingBook payForBookLend(Long booking, Double paid) {
        BookingBook booking1 = bookingBookRepository.getById(booking);
        booking1.setPaid(paid);
        bookingBookRepository.save(booking1);
        return booking1;
    }

    public BookingVideo payForVideoLend(Long booking, Double paid) {
        BookingVideo booking1 = bookingVideoRepository.getById(booking);
        booking1.setPaid(paid);
        bookingVideoRepository.save(booking1);
        return booking1;
    }

    public CheckBookingDTO checkBookBookings(CheckBookingDTO checkBookingDTO) {
        String sql = "SELECT COUNT(BB.id) AS bb_count, B.no_of_copies FROM booking_book BB, book B " +
                "WHERE BB.book_id = :book_id " +
                "AND B.id = :book_id " +
                "AND (" +
                    "(BB.booking_start <= :booking_start AND BB.booking_end >= :booking_end) " +
                    "OR (BB.booking_start >= :booking_start AND BB.booking_start <= :booking_end) " +
                    "OR (BB.booking_end >= :booking_start AND BB.booking_end <= :booking_end)" +
                ")";

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
    }

    public List<BookingBook> getBookBookingsForUser() {
        User user = userService.getUserWithAuthorities().get();
        return bookingBookRepository.findAllByUser_Id(user.getId());
    }

    public List<BookingVideo> getVideoBookingsForUser() {
        User user = userService.getUserWithAuthorities().get();
        return bookingVideoRepository.findAllByUser_Id(user.getId());
    }

    private long countBookBookingsForUserOnDates(Long userId, LocalDate start, LocalDate end) {
        return bookingBookRepository.countAllByUser_IdAndBookingStartBetweenOrBookingEndBetween(
                userId,
                start, end, start, end
        );
    }

    private long countVideoBookingsForUserOnDates(Long userId, LocalDate start, LocalDate end) {
        return bookingVideoRepository.countAllByUser_IdAndBookingStartBetweenOrBookingEndBetween(
                userId,
                start, end, start, end
        );
    }

}
