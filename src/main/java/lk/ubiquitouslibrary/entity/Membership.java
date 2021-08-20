package lk.ubiquitouslibrary.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Membership.
 */
@Entity
@Table(name = "membership")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Membership implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "valid_till")
    private LocalDate validTill;

    @ManyToOne
    private User user;

    @ManyToOne
    private MembershipType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Membership)) {
            return false;
        }
        return id != null && id.equals(((Membership) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Membership{" +
            "id=" + getId() +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", validTill='" + getValidTill() + "'" +
            "}";
    }
}
