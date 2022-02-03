package repository;

import eceptions.NotFoundException;
import eceptions.SaveException;
import entity.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderRepository implements BaseRepository<Order> {
    @Override
    public int save(Order order) {
        String sql = "insert into orders (product_id, customer_id, shopping_card_id) VALUES (?,?,?);";
        int id =0 ;
        try {
            PreparedStatement preparedStatement = MyConnection.getConnection().prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,order.getProduct().getId());
            preparedStatement.setInt(2,order.getCustomer().getId());
            preparedStatement.setInt(3,order.getShoppingCard().getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            id = resultSet.getInt(1);
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new SaveException(" order can not save");
        }
        return id;
    }

    @Override
    public void update(Order order) {
        String sql = "update orders set product_id = ? , customer_id = ? , shopping_card_id = ? where id = ? " ;
        try {
            PreparedStatement preparedStatement = MyConnection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1,order.getProduct().getId());
            preparedStatement.setInt(2,order.getCustomer().getId());
            preparedStatement.setInt(3,order.getShoppingCard().getId());
            preparedStatement.setInt(4,order.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new NotFoundException("order not found");
        }
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public void delete(int id) {
        String sql = "delete from orders where id = ? ";
        try {
            PreparedStatement preparedStatement = MyConnection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order findById(int id) {
        return null;
    }
}
