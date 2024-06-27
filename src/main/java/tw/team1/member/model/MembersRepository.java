package tw.team1.member.model;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MembersRepository extends JpaRepository<Member, Integer> {

	//獲取所有用戶資料
	List<Member> findByDeletedFalse();

	//模糊搜尋username
	List<Member> findByUsernameContainingAndDeletedFalse(String userName);

	//根據id查找用戶資料
	Member findByMemberidAndDeletedFalse(int memberId);

	//根據username查找用戶資料
	Member findByUsernameAndDeletedFalse(String username);


    Member findByUsername(String username);
}
