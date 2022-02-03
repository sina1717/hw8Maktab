package repository;

import eceptions.NotFoundException;
import eceptions.SaveException;
import entity.ShoppingCard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCardRepository implements BaseRepository<ShoppingCard> {

    @Override
    public int save(ShoppingCard shoppingCard) {
        String sql = "insert into shopping_card (date, is_payed) VALUES (?,?)";
        int id =0;
        try {
            PreparedStatement preparedStatement = MyConnection.getConnection().prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1,shoppingCard.getDate());
            preparedStatement.setBoolean(2,shoppingCard.isPayed());
            preparedStatement.executeUpdate();
            ResultSet resultSet= preparedStatement.getGeneratedKeys();
            resultSet.next();
            id = resultSet.getInt(1);
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new SaveException("shopping card can not save");
        }
        return id;
    }

    @Override
    public void update(ShoppingCard shoppingCard) {
        String sql = "update shopping_card set date = ? , is_payed = ? where id = ? ";
        try {
            PreparedStatement preparedStatement = MyConnection.getConnection().prepareStatement(sql);
            preparedStatement.setDate(1,shoppingCard.getDate());
            preparedStatement.setBoolean(2,shoppingCard.isPayed());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ShoppingCard> findAll() {
        String sql = "select * from shopping_card";
        try {
            PreparedStatement preparedStatement = MyConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ShoppingCard> shoppingCardList= new ArrayList<>();
            while (resultSet.next()){
                shoppingCardList.add(new ShoppingCard(
                        resultSet.getInt("id"),
                        resultSet.getDate("date"),
                        resultSet.getBoolean("is_payed")
                ));
            }
            return shoppingCardList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id) {
        String sql ="delete from shopping_card where id = ? " ;
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
    public ShoppingCard findById(int id) {
        String sql = "select * from shopping_card where id = ? ";
        try {
            PreparedStatement preparedStatement = MyConnection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ShoppingCard shoppingCard= null;
            if (resultSet.next()){
                shoppingCard = new ShoppingCard(
                        resultSet.getInt("id"),
                        resultSet.getDate("date"),
                        resultSet.getBoolean("is_payed")
                );
            }
            return shoppingCard;
        } catch (SQLException e) {
            throw new NotFoundException("shoppingCard not found");
        }

    }

}
