package lk.ubiquitouslibrary.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A MembershipType.
 */
@Entity
@Table(name = "membership_type")
@Data
@NoArgsConstructor
public class MembershipType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Builder
    public MembershipType(Long id, String type, Integer booksPerUser, Integer videosPerUser, Integer bookLendingDurationDays, Integer videoLendingDurationDays, Double annualFee, Double overdueChargesPerDay) {
        this.id = id;
        this.type = type;
        this.booksPerUser = booksPerUser;
        this.videosPerUser = videosPerUser;
        this.bookLendingDurationDays = bookLendingDurationDays;
        this.videoLendingDurationDays = videoLendingDurationDays;
        this.annualFee = annualFee;
        this.overdueChargesPerDay = overdueChargesPerDay;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "books_per_user")
    private Integer booksPerUser;

    @Column(name = "videos_per_user")
    private Integer videosPerUser;

    @Column(name = "book_lending_duration_days")
    private Integer bookLendingDurationDays;

    @Column(name = "video_lending_duration_days")
    private Integer videoLendingDurationDays;

    @Column(name = "annual_fee")
    private Double annualFee;

    @Column(name = "overdue_charges_per_day")
    private Double overdueChargesPerDay;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MembershipType)) {
            return false;
        }
        return id != null && id.equals(((MembershipType) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MembershipType{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", booksPerUser=" + getBooksPerUser() +
            ", videosPerUser=" + getVideosPerUser() +
            ", bookLendingDurationDays=" + getBookLendingDurationDays() +
            ", videoLendingDurationDays=" + getVideoLendingDurationDays() +
            ", annualFee=" + getAnnualFee() +
            ", overdueChargesPerDay=" + getOverdueChargesPerDay() +
            "}";
    }
}
