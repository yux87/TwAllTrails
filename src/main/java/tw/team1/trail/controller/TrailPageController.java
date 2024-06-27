package tw.team1.trail.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TrailPageController {



    @GetMapping("/toTestSecret")
    public String toTestSecret() {
        return "trail/customer/testSecret";
    }
    @GetMapping("/toWriteTrailPhoto")
    public String toWriteTrailPhoto() {
        return "trail/firebase/writeTrailPhotos";
    }
    @GetMapping("/toTestVue")
    public String toTestVue() {return "trail/testVue";}
    @GetMapping("/testCookies")
    public String testCookies() {
        return "trail/testCookies";
    }
    @GetMapping("/toAdminPage")
    public String toAdminPage() {
        return "trail/admin/testVue_v2_async";
    }
    @GetMapping("/toCustomerPage")
    public String toCustomerPage() {
        return "trail/customer/mapSearch_liangTheme";
    }
    @GetMapping("/toDetailPage")
    public String toDetailPage() {
        return "trail/customer/TrailDetail";
    }
    @GetMapping("/toTrailFilter")
    public String toTrailFilter() {
        return "trail/customer/trailFilter_liangTheme";
    }

    @GetMapping("/toIndexTest")
    public String toIndexTest() {
        return "index_trailCustomer";
    }
    @GetMapping("/toIndexTest2")
    public String toIndexTest2() {
        return "index_trailFilter";
    }
}
