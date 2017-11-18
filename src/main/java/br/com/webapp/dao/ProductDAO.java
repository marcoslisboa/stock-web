package br.com.webapp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.webapp.entities.Product;
import lombok.extern.java.Log;

@Log
public class ProductDAO extends BaseDAO {

    public List<Product> findAll() {

        List<Product> products = new ArrayList<Product>();
        String sql = "SELECT * FROM t_produto";

        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                products.add(
                    Product
                        .builder()
                        .id(result.getLong("id"))
                        .name(result.getString("name"))
                        .description(result.getString("description"))
                        .price(result.getDouble("price"))
                        .build()
                );
            }
            closeConnection();

        } catch (SQLException e) {
            log.warning(e.getMessage());
        }

        return products;
    }

    public Product findById(Long id) {

        String sql = "SELECT * FROM t_produto WHERE id = ?";

        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return Product
                    .builder()
                    .id(result.getLong("id"))
                    .name(result.getString("name"))
                    .description(result.getString("description"))
                    .price(result.getDouble("price"))
                    .build();
            }
            closeConnection();

        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return null;
    }

    public Product insert(Product product) {

        String sql = "INSERT INTO t_produto(name, description, price) VALUES(?,?,?) RETURNING id";

        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                product.setId(resultSet.getLong(1));
            }

            closeConnection();

        } catch (SQLException e) {
            log.warning(e.getMessage());
        }

        return product;
    }

    public Product update(Product product) {

        String sql = "UPDATE t_produto SET name = ?, description = ?, price = ? WHERE id = ?";

        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setLong(4, product.getId());

            statement.executeUpdate();
            closeConnection();

        } catch (SQLException e) {
            log.warning(e.getMessage());
        }

        return product;
    }

    public Product delete(Product product) {

        String sql = "DELETE FROM t_produto WHERE id = ?";

        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setLong(1, product.getId());

            statement.executeUpdate();
            closeConnection();

        } catch (SQLException e) {
            log.warning(e.getMessage());
        }

        return product;
    }
}
