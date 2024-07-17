package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Database.DatabaseConnection;

public class OrderDAO {

    public void placeOrder(Order order) {
        String orderSql = "INSERT INTO `Order` (customer_id, order_date, total_amount, status) VALUES (?, ?, ?, ?)";
        String orderItemSql = "INSERT INTO OrderItem (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, order.getCustomerId());
                orderStmt.setTimestamp(2, Timestamp.valueOf(order.getOrderDate()));
                orderStmt.setDouble(3, order.getTotalAmount());
                orderStmt.setString(4, order.getStatus());
                orderStmt.executeUpdate();

                ResultSet rs = orderStmt.getGeneratedKeys();
                if (rs.next()) {
                    int orderId = rs.getInt(1);
                    for (OrderItem item : order.getOrderItems()) {
                        try (PreparedStatement itemStmt = conn.prepareStatement(orderItemSql)) {
                            itemStmt.setInt(1, orderId);
                            itemStmt.setInt(2, item.getProductId());
                            itemStmt.setInt(3, item.getQuantity());
                            itemStmt.setDouble(4, item.getPrice());
                            itemStmt.executeUpdate();

                            // Update product stock quantity
                            updateProductStock(item.getProductId(), -item.getQuantity(), conn);
                        }
                    }
                    conn.commit();
                }
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateProductStock(int productId, int quantityChange, Connection conn) throws SQLException {
        String sql = "UPDATE Product SET stock_quantity = stock_quantity + ? WHERE product_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantityChange);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        }
    }

    public Order getOrder(int orderId) {
        String orderSql = "SELECT * FROM `Order` WHERE order_id = ?";
        String orderItemSql = "SELECT * FROM OrderItem WHERE order_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement orderStmt = conn.prepareStatement(orderSql)) {
            orderStmt.setInt(1, orderId);
            ResultSet orderRs = orderStmt.executeQuery();
            if (orderRs.next()) {
                Order order = new Order();
                order.setOrderId(orderRs.getInt("order_id"));
                order.setCustomerId(orderRs.getInt("customer_id"));
                order.setOrderDate(orderRs.getTimestamp("order_date").toLocalDateTime());
                order.setTotalAmount(orderRs.getDouble("total_amount"));
                order.setStatus(orderRs.getString("status"));

                try (PreparedStatement itemStmt = conn.prepareStatement(orderItemSql)) {
                    itemStmt.setInt(1, orderId);
                    ResultSet itemRs = itemStmt.executeQuery();
                    List<OrderItem> orderItems = new ArrayList<>();
                    while (itemRs.next()) {
                        OrderItem item = new OrderItem();
                        item.setOrderItemId(itemRs.getInt("order_item_id"));
                        item.setOrderId(itemRs.getInt("order_id"));
                        item.setProductId(itemRs.getInt("product_id"));
                        item.setQuantity(itemRs.getInt("quantity"));
                        item.setPrice(itemRs.getDouble("price"));
                        orderItems.add(item);
                    }
                    order.setOrderItems(orderItems);
                }
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateOrder(Order order) {
        String sql = "UPDATE `Order` SET customer_id = ?, order_date = ?, total_amount = ?, status = ? WHERE order_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getCustomerId());
            stmt.setTimestamp(2, Timestamp.valueOf(order.getOrderDate()));
            stmt.setDouble(3, order.getTotalAmount());
            stmt.setString(4, order.getStatus());
            stmt.setInt(5, order.getOrderId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelOrder(int orderId) {
        String sql = "UPDATE `Order` SET status = 'cancelled' WHERE order_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.executeUpdate();


            restoreProductStock(orderId, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void restoreProductStock(int orderId, Connection conn) throws SQLException {
        String sql = "SELECT product_id, quantity FROM OrderItem WHERE order_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                updateProductStock(rs.getInt("product_id"), rs.getInt("quantity"), conn);
            }
        }
    }
}

