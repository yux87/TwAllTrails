package tw.team1.forum.Api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApiController {

    @GetMapping("/Category/{categoryName}")
    public String getCategory(@PathVariable("categoryName") String categoryName) {
        // 直接回傳頁面路徑
        return "forum/" + categoryName;
    }

    @GetMapping("/forum")
    public String getforum() {
        // 直接回傳頁面路徑
        return "forum/forum";
    }
    @GetMapping("/forum/New-Post")
    public String getPost() {
        // 直接回傳頁面路徑
        return "forum/NewPost";
    }
    @GetMapping("/forum/posts")
    public String getPostmanagementcenter() {
        // 直接回傳頁面路徑
        return "forum/posts";
    }

//    @GetMapping("/Category/{categoryName}")
//    public ModelAndView getCategoryView(@PathVariable("categoryName") String categoryName) {
//        // 內部轉發到靜態資源
//        return new ModelAndView("forward:/forum/" + categoryName + ".html");
//    }
//
//    @GetMapping("/forum")
//    public ModelAndView getforumView() {
//        // 內部轉發到靜態資源
//        return new ModelAndView("forward:/forum/forum.html");
//    }
//    @GetMapping("/forum/New-Post")
//    public ModelAndView getPostView() {
//    	// 內部轉發到靜態資源
//    	return new ModelAndView("forward:/forum/NewPost.html");
//    }
//    @GetMapping("/forum/posts")
//    public ModelAndView getPostmanagementcenterView() {
//    	// 內部轉發到靜態資源
//    	return new ModelAndView("forward:/forum/posts.html");
//    }
//
//    @GetMapping("/thread/{threadid}")
//    public ModelAndView getThreadView(@PathVariable("threadid") String threadId) {
//        // 內部轉發到靜態資源
//        return new ModelAndView("forward:/" + threadId + ".html");
//    }
}


