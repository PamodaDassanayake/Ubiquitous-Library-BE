package lk.ubiquitouslibrary.controller;

import lk.ubiquitouslibrary.dto.MembershipDTO;
import lk.ubiquitouslibrary.entity.Membership;
import lk.ubiquitouslibrary.repository.MembershipRepository;
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
@RequestMapping("/api")
@Transactional
public class MembershipController {

    private final Logger log = LoggerFactory.getLogger(MembershipController.class);

    private static final String ENTITY_NAME = "membership";


    private final MembershipRepository membershipRepository;

//    private final MembershipService membershipService;

    public MembershipController(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
//        this.membershipService = membershipService;
    }

    /**
     * {@code POST  /memberships} : Create a new membership.
     *
     * @param membership the membership to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new membership, or with status {@code 400 (Bad Request)} if the membership has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/memberships")
    public ResponseEntity<Membership> createMembership(@RequestBody MembershipDTO membership) throws URISyntaxException {
        log.debug("REST request to save Membership : {}", membership);
        if (membership.getId() != null) {
            throw new RuntimeException("idexists");
        }
//        Membership result = membershipService.joinMembership(membership);
        return ResponseEntity.created(new URI("/api/memberships/")).build();
//            .created(new URI("/api/memberships/" + result.getId()))
//            .body(result);
    }

    /**
     * {@code GET  /memberships} : get all the memberships.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of memberships in body.
     */
    @GetMapping("/memberships")
    public List<Membership> getAllMemberships() {
        log.debug("REST request to get all Memberships");
        return membershipRepository.findAll();
    }

    /**
     * {@code GET  /memberships/:id} : get the "id" membership.
     *
     * @param id the id of the membership to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the membership, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/memberships/{id}")
    public ResponseEntity<Membership> getMembership(@PathVariable Long id) {
        log.debug("REST request to get Membership : {}", id);
        Optional<Membership> membership = membershipRepository.findById(id);
        return membership.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /memberships/:id} : delete the "id" membership.
     *
     * @param id the id of the membership to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/memberships/{id}")
    public ResponseEntity<Void> deleteMembership(@PathVariable Long id) {
        log.debug("REST request to delete Membership : {}", id);
        membershipRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
