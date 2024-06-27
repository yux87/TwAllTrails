package tw.team1.mall.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mall")

public class MallPageController {
    @GetMapping("/index")
    public String toindex() {
        return "mall/index";
    }


    @GetMapping("/product")
    public String toproduct() {
        return "mall/product";
    }

    @GetMapping("/order")
    public String toorder() {
        return "mall/orders";
    }


    @GetMapping("/cart")
    public String tocart() {
        return "mall/cart";
    }


    @GetMapping("/product.background")
    public String tobackground() {
        return "mall/product-background";
    }




}


