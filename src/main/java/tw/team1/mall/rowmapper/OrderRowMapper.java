package tw.team1.mall.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import tw.team1.mall.model.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        Order order = new Order();
        order.setOrderId(resultSet.getInt("order_id"));
        order.setUserId(resultSet.getInt("memberid"));
        order.setTotalAmount(resultSet.getInt("total_amount"));
        order.setCreatedDate(resultSet.getTimestamp("created_date"));
        order.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));
        order.setReceiverName(resultSet.getString("receiver_name"));
        order.setReceiverAddress(resultSet.getString("receiver_address"));
        order.setReceiverPhone(resultSet.getString("receiver_phone"));
        order.setReceiverEmail(resultSet.getString("receiver_email"));
        return order;
    }
}
