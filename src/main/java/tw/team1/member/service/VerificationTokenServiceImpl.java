package tw.team1.member.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.team1.member.model.VerificationTokenRepository;
import tw.team1.member.model.VerificationToken;
import tw.team1.member.model.Member;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Override
    public VerificationToken createVerificationToken(Member member) {
        // 生成 token
        String token = UUID.randomUUID().toString();

        // 設置過期時間
        LocalDateTime expirydate = LocalDateTime.now().plusHours(1); // 假設過期時間為 1 小時

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setExpirydate(expirydate);
        verificationToken.setMember(member);

        return tokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public void deleteVerificationToken(VerificationToken token) {
        tokenRepository.delete(token);
    }
}