package tw.team1.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tw.team1.member.model.Member;
import tw.team1.member.model.MembersRepository;
import tw.team1.member.model.UsernameNotFoundException;

@Service
@Transactional
public class UserProfilesService implements UserDetailsService {

	@Autowired
	private MembersRepository userProfilesRepository;



	public UserProfilesService(MembersRepository userProfilesRepository) {
		this.userProfilesRepository = userProfilesRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		Member userProfiles = userProfilesRepository.findByUsernameAndDeletedFalse(username);
		 if (userProfiles == null) {
			throw new UsernameNotFoundException("登入失敗：帳號或密碼錯誤");
		}
        return User.builder()
                .username(userProfiles.getUsername())
                .password(userProfiles.getPassword())
                .roles(userProfiles.isAdmin() ? "ADMIN" : "USER")
                .build();
	}

	public Member findNameByAccount(String username) {
		return userProfilesRepository.findByUsernameAndDeletedFalse(username);

	}


}
