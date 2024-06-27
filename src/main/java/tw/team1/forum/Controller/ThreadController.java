package tw.team1.forum.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tw.team1.forum.model.Reply;
import tw.team1.forum.service.ReplyService;
import tw.team1.forum.model.Thread;
import tw.team1.forum.service.ThreadService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/threads")
public class ThreadController {

    @Autowired
    private ThreadService threadService;
    
    @Autowired
    private ReplyService replyService;

    @GetMapping("/findAll")
    public List<Thread> getAllThreads() {
        return threadService.getAllThreads();
    }

    @GetMapping("/{threadId}")
    public Thread getThreadById(@PathVariable int threadId) {
        return threadService.getThreadById(threadId);
    }
//    @GetMapping("/{categoryid}")
//    public List<Thread> getThreadByCategoryid(@PathVariable int categoryid) {
//        return threadService.getThreadByCategoryid(categoryid);
//    }
    @GetMapping("/category/{categoryid}")
    public List<Thread> getThreadsByCategoryId(@PathVariable int categoryid) {
        return threadService.findByCategoryid(categoryid);
    }
    
//    @GetMapping("/member/{memberId}")
//    public List<Thread> getThreadsByMemberId(@PathVariable int memberId) {
//        return threadService.findThreadsByMemberId(memberId);
//    }
    @GetMapping("/member/{memberId}")
    public List<Thread> getThreadsByMemberIdAndKeyword(@PathVariable int memberId, 
                                                       @RequestParam(required = false) String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return threadService.searchThreadsByMemberIdAndKeyword(memberId, keyword);
        } else {
            return threadService.findThreadsByMemberId(memberId);
        }
    }
    @GetMapping("/search")
    public List<Thread> searchThreads(@RequestParam String keyword) {
    	if (keyword != null && !keyword.trim().isEmpty()) {
        return threadService.searchThreadsByTitleOrContent(keyword);
    	} else {
            return threadService.getAllThreads();
        }
    }
    
//    @GetMapping("/search/{keyword}")
//    public List<Thread> searchThreadsByKeyword(@PathVariable String keyword) {
//        return threadService.searchThreadsByKeyword(keyword);
//    }

    @PostMapping
    public Thread saveThread(@RequestBody Thread thread) {
        thread.setContent(thread.getContent());

        // 設置創建日期
        thread.setCreatedate(new Date());
        thread.setIsupdated(false);

        // 呼叫服務層的方法保存主題
        return threadService.saveThread(thread);
    }

    @PutMapping("/update/{threadId}")
    public ResponseEntity<Thread> updateThread(@PathVariable int threadId, @RequestBody Thread threadDetails) {
        // 首先找到現有的貼文
        Thread thread = threadService.getThreadById(threadId);
        
        if (thread == null) {
            return ResponseEntity.notFound().build();
        }

        // 更新貼文資訊，根據需要更新的欄位來設定
        thread.setTitle(threadDetails.getTitle());

        thread.setContent(threadDetails.getContent());

        // 將 isUpdated 欄位更改為true
        thread.setIsupdated(true);

        // 儲存更新後的貼文
        Thread updatedThread = threadService.saveThread(thread);

        // 返回更新後的貼文
        return ResponseEntity.ok(updatedThread);
    }

    
    @DeleteMapping("/{threadId}")
    public void deleteThreadWithReplies(@PathVariable int threadId) {
        // 根据 threadId 获取所有与该主题相关的回复
        List<Reply> replies = replyService.getRepliesByThreadId(threadId);
        
        // 删除所有相关的回复
        replyService.deleteReplies(replies);
        
        // 最后删除主题
        threadService.deleteThread(threadId);
    }

}
