package tw.team1.member.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import tw.team1.member.model.Member;
import tw.team1.member.model.MembersRepository;
import tw.team1.member.model.VerificationToken;
import tw.team1.member.model.VerificationTokenRepository;

@Service
public class MembersService {

    @Autowired
    MembersRepository membersRepository;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	//所有結果失敗或為空值，都返回null


	// 獲取所有會員
	public List<Member> getAllMembers() {
	    List<Member> members = membersRepository.findByDeletedFalse();
	    return members.isEmpty() ? null : members;
	}

	// 根據用戶id獲取特定會員
	public Member getMemberById(Integer id) {
	    return membersRepository.findByMemberidAndDeletedFalse(id);
	}

	// 根據用戶名稱模糊搜尋會員
	public List<Member> searchMemberByUsername(String username) {
	    List<Member> members = membersRepository.findByUsernameContainingAndDeletedFalse(username);
	    return members.isEmpty() ? null : members;
	}
	// 精準搜尋 username測試登入用
	public Member checkMemberByUsername(String username) {
	    Member member = membersRepository.findByUsername(username);
		if (member.getSignature() == null) {
		member.setSignature("<p></p>");
		}
		return member;
	}

 	// 新增會員
 	public Member createMember(@RequestBody Member newMember) {
// 		對密碼進行BCrypt雜湊
 		String encodedPassword = passwordEncoder.encode(newMember.getPassword());
//		 填入密碼及其他資料
		newMember.setPassword(encodedPassword);
		newMember.setAdmin(false);
		newMember.setDeleted(true);
 		return membersRepository.save(newMember);
 	}

 	// 更新會員
 	public Member updateMember(@PathVariable Integer id, @RequestBody Member updatedMember) {
 			//檢查 id是否存在
 			Member existingMember = membersRepository.findByMemberidAndDeletedFalse(id);
 			if (existingMember == null) {
 				return null;
 			}
 	        // 更新password
 	        if (updatedMember.getPassword() != null && !updatedMember.getPassword().isEmpty()) {
 	            // 對密碼進行BCrypt雜湊
 	            String encodedPassword = passwordEncoder.encode(updatedMember.getPassword());
 	            existingMember.setPassword(encodedPassword);
 	        }
 	        //更新username及nickname
 	        if (updatedMember.getUsername() !=null) {
 	        	existingMember.setUsername(updatedMember.getUsername());
 	        }
 	        if (updatedMember.getNickname() !=null) {
 	        	existingMember.setNickname(updatedMember.getNickname());
 	        }
			 //更新birthday
 	        if (updatedMember.getBirthday() !=null) {
 	        	existingMember.setBirthday(updatedMember.getBirthday());
 	        }
//			 更新avatar
 	        if (updatedMember.getAvatar() !=null) {
 	        	existingMember.setAvatar(updatedMember.getAvatar());
 	        }
//			 更新phone
 	        if (updatedMember.getPhone() !=null) {
 	        	existingMember.setPhone(updatedMember.getPhone());
 	        }
//			 更新signature
 	        if (updatedMember.getSignature() !=null) {
 	        	existingMember.setSignature(updatedMember.getSignature());
 	        }
 	        //保存更新後的資料進資料庫
 	        return membersRepository.save(existingMember);
 	}

 	public Member deleteMember(@PathVariable Integer id) {
 		//檢查 id是否存在
 		Member existingMember = membersRepository.findByMemberidAndDeletedFalse(id);
 		if (existingMember == null) {
 			return null;
 		}
 		//將deleted設為true
 		existingMember.setDeleted(true);
 		//保存更新後的資料進資料庫
 		return membersRepository.save(existingMember);
 	}

	 @Autowired
	 VerificationTokenRepository tokenRepository;
	 @Autowired
	 VerificationTokenService tokenService;
	public boolean verifyMember(String token, Member member) {
		VerificationToken verificationToken = tokenService.getVerificationToken(token);

		if (verificationToken == null || verificationToken.getExpirydate().isBefore(LocalDateTime.now())) {
			// Token 無效或已過期
			return false;
		}

		// 驗證成功,儲存會員
		member.setDeleted(false);
		membersRepository.save(member);
		// 刪除驗證 Token
//		tokenService.deleteVerificationToken(verificationToken);
		//將token改為失效
		verificationToken.setExpirydate(LocalDateTime.now().minusHours(1));
		tokenRepository.save(verificationToken);
		return true;
	}

}
