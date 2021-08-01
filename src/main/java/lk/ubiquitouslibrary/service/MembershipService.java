package lk.ubiquitouslibrary.service;

import lk.ubiquitouslibrary.dto.MembershipDTO;
import lk.ubiquitouslibrary.entity.Membership;
import lk.ubiquitouslibrary.entity.MembershipType;
import lk.ubiquitouslibrary.entity.User;
import lk.ubiquitouslibrary.repository.MembershipRepository;
import lk.ubiquitouslibrary.repository.MembershipTypeRepository;
import lk.ubiquitouslibrary.repository.UserRepository;
import org.springframework.stereotype.Service;

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

}
