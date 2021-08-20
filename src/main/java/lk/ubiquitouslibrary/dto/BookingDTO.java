package lk.ubiquitouslibrary.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A BookingBook.
 */
@Data
public class BookingDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long book;

    private Long video;

    private LocalDate bookingStart;

    private LocalDate bookingEnd;

    private Long userId;

    private Double fee;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookingDTO)) {
            return false;
        }
        return id != null && id.equals(((BookingDTO) o).id);
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
