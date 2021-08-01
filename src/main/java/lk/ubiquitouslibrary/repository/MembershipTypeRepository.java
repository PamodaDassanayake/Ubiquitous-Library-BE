package lk.ubiquitouslibrary.repository;

import lk.ubiquitouslibrary.entity.MembershipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the MembershipType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MembershipTypeRepository extends JpaRepository<MembershipType, Long> {

    Optional<MembershipType> findByType(String type);

}
