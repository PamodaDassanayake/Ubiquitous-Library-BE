package lk.ubiquitouslibrary.service;

import lk.ubiquitouslibrary.dto.MembershipDTO;
import lk.ubiquitouslibrary.entity.Membership;
import lk.ubiquitouslibrary.entity.MembershipType;
import lk.ubiquitouslibrary.entity.User;
import lk.ubiquitouslibrary.repository.MembershipRepository;
import lk.ubiquitouslibrary.repository.MembershipTypeRepository;
import lk.ubiquitouslibrary.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Arrays;

@Service
public class MembershipService {

    MembershipRepository membershipRepository;
    UserRepository userRepository;
    MembershipTypeRepository membershipTypeRepository;

    public MembershipService(MembershipRepository membershipRepository, UserRepository userRepository, MembershipTypeRepository membershipTypeRepository) {
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
        this.membershipTypeRepository = membershipTypeRepository;
    }

    public Membership joinMembership(MembershipDTO dto){
        Membership membership = new Membership();
        membership.setPaymentDate(dto.getPaymentDate());
        membership.setValidTill(dto.getPaymentDate().plusYears(1));
        User user = userRepository.findById(dto.getUserId()).get();
        membership.setUser(user);
        MembershipType membershipType = membershipTypeRepository.findByType(dto.getMembershipType()).get();
        membership.setType(membershipType);

        return membershipRepository.save(membership);
    }

    public Membership getUserMembership(Long userId){
        Membership membership = membershipRepository.findByUser_IdAndValidTillGreaterThanEqual(userId, LocalDate.now());
        if (membership!=null)
            return membership;

        throw new RuntimeException("No valid Membership found for User");
    }

    @PostConstruct
    private void initMembership(){
        Membership membership = Membership.builder()
                .type(new MembershipType().id(2L))
                .user(User.builder().id(2L).build())
                .paymentDate(LocalDate.now())
                .validTill(LocalDate.now().plusYears(1))
                .build();

        if (membershipTypeRepository.existsById(1L) && !membershipRepository.existsById(1L)) {
            membershipRepository.save(membership);
        }
    }

}
