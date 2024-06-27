package tw.team1.mall.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.team1.mall.dto.CreateOrderRequest;
import tw.team1.mall.dto.OrderQueryParams;
import tw.team1.mall.model.Order;
import tw.team1.mall.service.OrderService;
import tw.team1.mall.util.Page;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);


    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ) {
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);

        //取得 order list
        List<Order> orderList = orderService.getOrders(orderQueryParams);

        //取得 order 總數
        Integer count = orderService.countOrder(orderQueryParams);

        //分頁
        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResults(orderList);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }


    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest) {
        Integer orderId = orderService.creaOrder(userId, createOrderRequest);
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }


    @PostMapping("/users/{userId}/orders/{orderId}/pay")
    public ResponseEntity<String> payOrder(@PathVariable Integer userId, @PathVariable Integer orderId) {
        try {
            Payment payment = orderService.createPayment(orderId);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return ResponseEntity.ok(link.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.badRequest().body("訂單支付失敗");
    }


    @GetMapping("/success")
    public ResponseEntity<String> successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = orderService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return ResponseEntity.ok("支付成功");
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.badRequest().body("支付失敗");
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancelPay() {
        return ResponseEntity.ok("支付已取消");
    }


}
