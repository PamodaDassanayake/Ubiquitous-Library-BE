package lk.ubiquitouslibrary.controller;

import lk.ubiquitouslibrary.dto.MembershipDTO;
import lk.ubiquitouslibrary.entity.Membership;
import lk.ubiquitouslibrary.entity.MembershipType;
import lk.ubiquitouslibrary.repository.MembershipRepository;
import lk.ubiquitouslibrary.repository.MembershipTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link Membership}.
 */
@RestController
@RequestMapping("/api/membership-types")
@Transactional
public class MembershipTypeController {

    private final Logger log = LoggerFactory.getLogger(MembershipTypeController.class);

    private static final String ENTITY_NAME = "membership";


    private final MembershipTypeRepository membershipTypeRepository;

    public MembershipTypeController(MembershipTypeRepository membershipRepository) {
        this.membershipTypeRepository = membershipRepository;
    }

    /**
     * {@code GET  /membership-types} : get all the membershipTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of membershipTypes in body.
     */
    @GetMapping
    public List<MembershipType> getAllMembershipTypes() {
        log.debug("REST request to get all MembershipTypes");
        return membershipTypeRepository.findAll();
    }
}
