package tw.team1.forum.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tw.team1.forum.model.Reply;
import tw.team1.forum.service.ReplyService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/replies")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @GetMapping
    public List<Reply> getAllReplies() {
        return replyService.getAllReplies();
    }

    @GetMapping("/{replyId}")
    public Reply getReplyById(@PathVariable int replyId) {
        return replyService.getReplyById(replyId);
    }
    @GetMapping("/thread/{threadid}")
    public List<Reply> getRepliesByThreadId(@PathVariable int threadid) {
        return replyService.getRepliesByThreadId(threadid);
    }

    @PostMapping
    public Reply saveReply(@RequestBody Reply reply) {
    	reply.setCreatedate(new Date());
        return replyService.saveReply(reply);
    }

    @DeleteMapping("/{replyId}")
    public void deleteReply(@PathVariable int replyId) {
        replyService.deleteReply(replyId);
    }
}

