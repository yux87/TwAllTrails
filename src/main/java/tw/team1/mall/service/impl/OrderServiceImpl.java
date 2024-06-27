package tw.team1.mall.service.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import tw.team1.mall.dao.OrderDao;
import tw.team1.mall.dao.ProductDao;
import tw.team1.mall.dao.UserDao;
import tw.team1.mall.dto.BuyItem;
import tw.team1.mall.dto.CreateOrderRequest;
import tw.team1.mall.dto.OrderQueryParams;
import tw.team1.mall.model.Order;
import tw.team1.mall.model.OrderItem;
import tw.team1.mall.model.Product;
import tw.team1.mall.model.User;
import tw.team1.mall.service.OrderService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    private APIContext apiContext;

    @Autowired
    public OrderServiceImpl(@Value("${paypal.client.id}") String clientId,
                            @Value("${paypal.client.secret}") String clientSecret,
                            @Value("${paypal.mode}") String mode) {
        try {
            // 初始化 PayPal SDK
            Map<String, String> configMap = new HashMap<>();
            configMap.put("mode", mode);

            OAuthTokenCredential oAuthTokenCredential = new OAuthTokenCredential(clientId, clientSecret, configMap);
            apiContext = new APIContext(oAuthTokenCredential.getAccessToken());
        } catch (PayPalRESTException e) {
            log.error("初始化 PayPal SDK 時發生異常", e);
            throw new RuntimeException("初始化 PayPal SDK 時發生異常", e);
        }
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        return orderDao.countOrder(orderQueryParams);
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        List<Order> orderList = orderDao.getOrders(orderQueryParams);

        for (Order order : orderList) {
            List<OrderItem> orderItemList = orderDao.getOrderItemsByOderId(order.getOrderId());
            order.setOrderItemList(orderItemList);
        }
        return orderList;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOderId(orderId);

        order.setOrderItemList(orderItemList);
        return order;
    }

    @Transactional
    @Override
    public Integer creaOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        User user = userDao.getUserById(userId);

        if (user == null) {
            log.warn("該 userId {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());

            if (product == null) {
                log.warn("商品 {} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()) {
                log.warn("商品 {} 庫存數量不足,無法購買。剩餘庫存 {} 購買數量 {}",
                        buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        CreateOrderRequest.DeliveryInfo deliveryInfo = createOrderRequest.getDeliveryInfo();
        Integer orderId = orderDao.createOrder(userId, Integer.valueOf(totalAmount), deliveryInfo);
        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }

    @Override
    public Payment createPayment(Integer orderId) throws PayPalRESTException {
        Order order = orderDao.getOrderById(orderId);
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItemList()) {
            Item item = new Item();
            item.setName(orderItem.getProductName());
            item.setCurrency("TWD");
            item.setPrice(orderItem.getAmount().toString());
            item.setQuantity(orderItem.getQuantity().toString());
            items.add(item);
        }
        itemList.setItems(items);

        Amount amount = new Amount();
        amount.setCurrency("TWD");
        amount.setTotal(order.getTotalAmount().toString());

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setItemList(itemList);
        transaction.setDescription("購買商品");
        transaction.setInvoiceNumber(String.valueOf(orderId));

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/cancel");
        redirectUrls.setReturnUrl("http://localhost:8080/success");

        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    @Override
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }
}