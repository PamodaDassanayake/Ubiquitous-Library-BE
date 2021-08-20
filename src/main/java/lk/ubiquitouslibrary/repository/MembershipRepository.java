package lk.ubiquitouslibrary.repository;

import lk.ubiquitouslibrary.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * Spring Data SQL repository for the Membership entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

    Membership findByUser_IdAndValidTillGreaterThanEqual(Long userId, LocalDate date);

}
