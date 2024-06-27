package tw.team1.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tw.team1.member.model.Member;
import tw.team1.member.service.MembersService;
@RestController
public class UserController {
    @Autowired
    MembersService membersService;

    /**
     * 獲取當前登入會員資料
     * @return 單個會員物件,如果找不到或未登入,返回 NULL
     */
    @ResponseBody
    @GetMapping("/getUserProfiles")
    Member getUserProfiles(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String account = authentication.getName();
            System.out.println("請求會員資訊 = "+ account);
            return membersService.checkMemberByUsername(account);
        }
        return null;
    }
}
