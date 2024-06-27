package tw.team1.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.team1.forum.model.Reply;



public interface ReplyRepository extends JpaRepository<Reply, Integer> {

	List<Reply> findByThreadid(int threadid);
    // 可以新增一些自訂的查詢方法
}

