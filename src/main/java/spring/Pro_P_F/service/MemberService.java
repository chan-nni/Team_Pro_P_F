package spring.Pro_P_F.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입
    @Transactional
    public String join(Member member) {
        memberRepository.save(member);

        return member.getMid();
    }

    public Member findOne(String memberId) {
        return memberRepository.findByMid(memberId);
    }

    public List<Member> findByMember(String memberId) {
        return memberRepository.findAllByMid(memberId);
    }

    public boolean login(String memberid, String password) {
        Member member = memberRepository.findByMid(memberid);
        if (member != null && member.getM_pwd().equals(password)) {
            return true;
        }
        return false;
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    // id 중복확인
    public boolean existsByMember(String memberId) {
        return memberRepository.existsByMid(memberId);
    }

    // 번호 중복확인
    public boolean isPhoneNumberDuplicate(String phoneNumber) {
        // 데이터베이스에서 전화번호 중복 확인
        return memberRepository.existsByMphone(phoneNumber);
    }


    // 탈퇴하기
    @Transactional
    public void deleteMember(String memberId) {
        Member member = memberRepository.findByMid(memberId);
        if (member != null) {
            memberRepository.delete(member);
        }
    }
}
