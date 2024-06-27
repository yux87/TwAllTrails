package tw.team1.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.team1.forum.model.Reply;
import tw.team1.forum.repository.ReplyRepository;

import java.util.List;

@Service
@Transactional
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    // 可以在這裡添加與回覆相關的業務邏輯

    public List<Reply> getAllReplies() {
        return replyRepository.findAll();
    }

    public Reply getReplyById(int replyId) {
        return replyRepository.findById(replyId).orElse(null);
    }

    public Reply saveReply(Reply reply) {
        return replyRepository.save(reply);
    }

    public void deleteReply(int replyId) {
        replyRepository.deleteById(replyId);
    }

	public List<Reply> getRepliesByThreadId(int threadId) {
		// TODO Auto-generated method stub
		return replyRepository.findByThreadid(threadId);
	}

	public void deleteReplies(List<Reply> replies) {
		for (Reply reply : replies) {
	        replyRepository.deleteById(reply.getReplyid());
	    }
		
	}
}

