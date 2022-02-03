package repository;

import eceptions.NotFoundException;
import eceptions.SaveException;
import entity.Category;
import entity.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements ProductInterface {


    @Override
    public int save(Product product) {

        String sql="insert into product (name, description, category_id, qty, price) VALUES (?,?,?,?,?)";
        int id = 0;
        try {
            PreparedStatement preparedStatement = MyConnection.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,product.getName());
            preparedStatement.setString(2,product.getDescription());
            preparedStatement.setInt(3,product.getCategory().getId());
            preparedStatement.setInt(4,product.getQty());
            preparedStatement.setInt(5,product.getPrice());
            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            keys.next();
            id = keys.getInt(1);
            preparedStatement.close();
        } catch (SQLException e) {
            throw new SaveException(" product can not save");
        }
        return id;
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE product set name = ? , description = ? ,category_id = ? , qty = ? , price = ? where id = ? ";
        try {
            PreparedStatement preparedStatement = MyConnection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1,product.getName());
            preparedStatement.setString(2,product.getDescription());
            preparedStatement.setInt(3,product.getCategory().getId());
            preparedStatement.setInt(4,product.getQty());
            preparedStatement.setInt(5,product.getPrice());
            preparedStatement.setInt(6,product.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> findAll() {
        String sql = "select * from product p inner join category c on c.id = p.category_id ";
        List<Product> productList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = MyConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                productList.add(new Product(
                        resultSet.getInt("p.id"),
                        resultSet.getString("name"),
                        resultSet.getString("p.description"),
                        new Category(resultSet.getInt("c.id"),
                                resultSet.getString("title"),
                                resultSet.getString("c.descriptoin"),
                                new Category(resultSet.getInt("parent_id"),null,null,null)),
                        resultSet.getInt("qty"),
                        resultSet.getInt("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public void delete(int id) {
        String sql ="delete from product where id = ? ";
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
    public Product findById(int id) {
        String sql = "select * from product p inner join category c on c.id = p.category_id where id ?";
        Product product = null;
        try {
            PreparedStatement preparedStatement = MyConnection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                product = new Product(
                        resultSet.getInt("p.id"),
                        resultSet.getString("name"),
                        resultSet.getString("p.description"),
                        new Category(resultSet.getInt("c.id"),
                                resultSet.getString("title"),
                                resultSet.getString("c.descriptoin"),
                                new Category(resultSet.getInt("parent_id"),null,null,null)),
                        resultSet.getInt("qty"),
                        resultSet.getInt("price")
                );
            }
        } catch (SQLException e) {
            throw new NotFoundException("product not found");
        }
        return product;
    }

    @Override
    public List<Product> findByCategory(int categoryId) {
        String sql = "select * from product p inner join category c on c.id = p.category_id where p.category_id = ? ";
        List<Product> productList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = MyConnection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1,categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                productList.add(new Product(
                        resultSet.getInt("p.id"),
                        resultSet.getString("name"),
                        resultSet.getString("p.description"),
                        new Category(resultSet.getInt("c.id"),
                                resultSet.getString("title"),
                                resultSet.getString("c.descriptoin"),
                                new Category(resultSet.getInt("parent_id"),null,null,null)),
                        resultSet.getInt("qty"),
                        resultSet.getInt("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

}
