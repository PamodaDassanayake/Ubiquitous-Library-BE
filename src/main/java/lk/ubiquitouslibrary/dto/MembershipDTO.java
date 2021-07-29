package lk.ubiquitouslibrary.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Membership.
 */
@Data
public class MembershipDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalDate paymentDate;

    private LocalDate validTill;

    private long userId;

    private String membershipType;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MembershipDTO)) {
            return false;
        }
        return id != null && id.equals(((MembershipDTO) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
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
