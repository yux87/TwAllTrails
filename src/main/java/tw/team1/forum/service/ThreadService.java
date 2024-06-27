package tw.team1.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.team1.forum.model.Thread;
import tw.team1.forum.repository.ThreadRepository;

import java.util.List;

@Service
@Transactional
public class ThreadService {

    @Autowired
    private ThreadRepository threadRepository;

    // 可以在這裡添加與主題相關的業務邏輯

    public List<Thread> getAllThreads() {
        return threadRepository.findAll();
    }

    public Thread getThreadById(int threadId) {
        return threadRepository.findById(threadId).orElse(null);
    }

    public List<Thread> findByCategoryid(int categoryid) {
        return threadRepository.findByCategoryid(categoryid);
    }
    
    public List<Thread> findThreadsByMemberId(int memberId) {
        return threadRepository.findByMemberid(memberId);
    }
    
    public List<Thread> searchThreadsByMemberIdAndKeyword(int memberId, String keyword) {
        return threadRepository.findByMemberidAndTitleContainingOrContentContaining(memberId, keyword, keyword);
    }
    
    public List<Thread> searchThreadsByTitleOrContent(String keyword) {
        return threadRepository.findByTitleContainingOrContentContaining(keyword, keyword);
    }

    public Thread saveThread(Thread thread) {
        return threadRepository.save(thread);
    }

    public void deleteThread(int threadId) {
        threadRepository.deleteById(threadId);
    }

//    public List<Thread> searchThreadsByKeyword(String keyword) {
//        // 在关键字两侧添加 '%' 以实现模糊搜索
//        String searchKeyword = "%" + keyword + "%";
//        // 调用 Repository 执行模糊搜索
//        return threadRepository.findByTitleContainingOrContentContaining(searchKeyword, searchKeyword);
//    }
}
