package repository;

import eceptions.NotFoundException;
import eceptions.SaveException;
import entity.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository implements BaseRepository<Category> {

    @Override
    public int save(Category category) {
        String sql="insert into category (title, description, parent_id) VALUES (?,?,?)";
        int id = 0;
        try {
            PreparedStatement preparedStatement = MyConnection.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,category.getTitle());
            preparedStatement.setString(2,category.getDescription());
            preparedStatement.setInt(3,category.getCategory().getId());
            preparedStatement.executeUpdate();
            ResultSet  keys = preparedStatement.getGeneratedKeys();
            keys.next();
            id = keys.getInt(1);
            preparedStatement.close();
        } catch (SQLException e) {
            throw new SaveException(" category can not save");
        }
        return id;
    }

    @Override
    public void update(Category category) {
        String sql = "update category set title = ? , description = ? , parent_id = ? where id = ? ";
        try {
            PreparedStatement preparedStatement = MyConnection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1,category.getTitle());
            preparedStatement.setString(2,category.getDescription());
            preparedStatement.setInt(3,category.getCategory().getId());
            preparedStatement.setInt(4,category.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Category> findAll() {
        String sql = "select * from category";
        List<Category> categoryList= new ArrayList<>();
        try {
            PreparedStatement preparedStatement =MyConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                categoryList.add(new Category(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        new Category(resultSet.getInt("parent_id"),null,null,null)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    @Override
    public void delete(int id) {
        String sql = "delete from category where id  = ?";
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
    public Category findById(int id) {
        String sql = "select * from category where id = ?";
        try {
            PreparedStatement preparedStatement = MyConnection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new Category(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        new Category(resultSet.getInt("id"),null,null,null));
            }
        } catch (SQLException e) {
            throw new NotFoundException("category not found");
        }
        return null;
    }
}
