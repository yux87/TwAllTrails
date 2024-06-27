package tw.team1.trail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class VueController {
    @GetMapping("/demoVue.controller")
    public String getList(Model model) {
        List<String> items = Arrays.asList("Item 1", "Item 2", "Item 3");
        model.addAttribute("items", items);
        return "test";
    }
}
