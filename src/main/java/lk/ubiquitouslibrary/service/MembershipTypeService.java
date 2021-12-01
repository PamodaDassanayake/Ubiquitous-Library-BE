package lk.ubiquitouslibrary.service;

import lk.ubiquitouslibrary.dto.MembershipDTO;
import lk.ubiquitouslibrary.entity.Membership;
import lk.ubiquitouslibrary.entity.MembershipType;
import lk.ubiquitouslibrary.entity.User;
import lk.ubiquitouslibrary.repository.MembershipRepository;
import lk.ubiquitouslibrary.repository.MembershipTypeRepository;
import lk.ubiquitouslibrary.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
@Slf4j
public class MembershipTypeService {

    MembershipTypeRepository membershipTypeRepository;

    public MembershipTypeService(MembershipTypeRepository membershipTypeRepository) {
        this.membershipTypeRepository = membershipTypeRepository;
    }

    @PostConstruct
    private void createTypes(){
        MembershipType membershipType = MembershipType.builder()
                .type("Platinum")
                .booksPerUser(10)
                .videosPerUser(10)
                .bookLendingDurationDays(35)
                .videoLendingDurationDays(14)
                .annualFee(5000D)
                .overdueChargesPerDay(5D)
                .bookPrice(20D)
                .videoPrice(40D)
                .build();

        MembershipType membershipType2 = MembershipType.builder()
                .type("Gold")
                .booksPerUser(7)
                .videosPerUser(9)
                .bookLendingDurationDays(28)
                .videoLendingDurationDays(10)
                .annualFee(3000D)
                .overdueChargesPerDay(10D)
                .bookPrice(30D)
                .videoPrice(60D)
                .build();

        MembershipType membershipType3 = MembershipType.builder()
                .type("Silver")
                .booksPerUser(5)
                .videosPerUser(7)
                .bookLendingDurationDays(28)
                .videoLendingDurationDays(7)
                .annualFee(2000D)
                .overdueChargesPerDay(15D)
                .bookPrice(40D)
                .videoPrice(80D)
                .build();

        MembershipType membershipType4 = MembershipType.builder()
                .type("Bronze")
                .booksPerUser(3)
                .videosPerUser(5)
                .bookLendingDurationDays(21)
                .videoLendingDurationDays(5)
                .annualFee(1000D)
                .overdueChargesPerDay(20D)
                .bookPrice(50D)
                .videoPrice(100D)
                .build();

        try {
            if (! membershipTypeRepository.existsById(1L))
                membershipTypeRepository.saveAll(Arrays.asList(membershipType,membershipType2,membershipType3,membershipType4));
        }catch (Exception e){
            log.warn("Membership Types exists. Skipping creation.");
        }

    }

}
