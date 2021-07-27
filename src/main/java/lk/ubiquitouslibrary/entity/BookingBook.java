package lk.ubiquitouslibrary.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A BookingBook.
 */
@Entity
@Table(name = "booking_book")
@Data
public class BookingBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_start")
    private LocalDate bookingStart;

    @Column(name = "booking_end")
    private LocalDate bookingEnd;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private Booking booking;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookingBook)) {
            return false;
        }
        return id != null && id.equals(((BookingBook) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookingBook{" +
            "id=" + getId() +
            "}";
    }
}
