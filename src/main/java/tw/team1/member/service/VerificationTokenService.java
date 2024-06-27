package tw.team1.member.service;

import tw.team1.member.model.Member;
import tw.team1.member.model.VerificationToken;

public interface VerificationTokenService {
    VerificationToken createVerificationToken(Member member);
    VerificationToken getVerificationToken(String token);
    void deleteVerificationToken(VerificationToken token);
}
