package tw.team1.mall.dao;

import tw.team1.mall.dto.CreateOrderRequest;
import tw.team1.mall.dto.OrderQueryParams;
import tw.team1.mall.model.Order;
import tw.team1.mall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer countOrder(OrderQueryParams orderQueryParams);
    List<Order> getOrders(OrderQueryParams orderQueryParams);
    Order getOrderById(Integer orderId);
    List<OrderItem> getOrderItemsByOderId(Integer orderId);

    Integer createOrder(Integer userId, Integer totalAmount, CreateOrderRequest.DeliveryInfo deliveryInfo);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
