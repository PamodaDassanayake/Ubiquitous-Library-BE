package lk.ubiquitouslibrary.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A BookingVideo.
 */
@Entity
@Table(name = "booking_video")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingVideo implements Serializable {

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
    private Video video;

    @Column(name = "fee")
    private Double fee;

    @Column(name = "paid")
    private Double paid;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookingVideo)) {
            return false;
        }
        return id != null && id.equals(((BookingVideo) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookingVideo{" +
            "id=" + getId() +
            "}";
    }
}
