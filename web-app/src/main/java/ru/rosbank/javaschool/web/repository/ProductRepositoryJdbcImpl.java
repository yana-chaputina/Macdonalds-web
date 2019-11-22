package ru.rosbank.javaschool.web.repository;


import ru.rosbank.javaschool.util.SQLTemplate;
import ru.rosbank.javaschool.util.RowMapper;
import ru.rosbank.javaschool.web.dto.ProductDetailsDto;
import ru.rosbank.javaschool.web.exception.DataAccessException;
import ru.rosbank.javaschool.web.model.ProductModel;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryJdbcImpl implements ProductRepository {
    private final DataSource ds;
    private final SQLTemplate template;
    private final RowMapper<ProductModel> mapper = rs -> new ProductModel(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getInt("price"),
            rs.getString("category"),
            rs.getString("description"),
            rs.getString("image_url")
    );

    public ProductRepositoryJdbcImpl(DataSource ds, SQLTemplate template) {
        this.ds = ds;
        this.template = template;

        try {
            template.update(ds, "CREATE TABLE IF NOT EXISTS products (\n" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "name TEXT NOT NULL, price INTEGER NOT NULL CHECK (price >= 0),\n" +
                    "category TEXT NOT NULL,\n" +
                    "description TEXT NOT NULL,image_url TEXT\n" +
                    ");");
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public List<ProductModel> getAll() {
        try {
            return template.queryForList(ds, "SELECT id, name, price,category, description, image_url FROM products;", mapper);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Optional<ProductModel> getById(int id) {
        try {
            return template.queryForObject(ds, "SELECT id, name, price,category, description, image_url FROM products WHERE id = ?;", stmt -> {
                stmt.setInt(1, id);
                return stmt;
            }, mapper);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public ProductModel save(ProductModel model) {
        try {

            if (model.getId() == 0) {
                int id = template.<Integer>updateForId(ds, "INSERT INTO products(name, price, category,description, image_url) VALUES (?, ?, ?, ?);", stmt -> {
                    int nextIndex = 1;
                    stmt.setString(nextIndex++, model.getName());
                    stmt.setInt(nextIndex++, model.getPrice());
                    stmt.setString(nextIndex++,model.getCategory());
                    stmt.setString(nextIndex++, model.getDescription());
                    stmt.setString(nextIndex++, model.getImageUrl());
                    return stmt;
                });
                model.setId(id);
                return model;
            }

            template.update(ds, "UPDATE products SET name = ?, price = ?,category=?, description = ?, image_url = ? WHERE id = ?;", stmt -> {
                int nextIndex = 1;
                stmt.setString(nextIndex++, model.getName());
                stmt.setInt(nextIndex++, model.getPrice());
                stmt.setString(nextIndex++, model.getCategory());
                stmt.setString(nextIndex++, model.getDescription());
                stmt.setString(nextIndex++, model.getImageUrl());
                stmt.setInt(nextIndex++, model.getId());
                return stmt;
            });
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return model;
    }

    @Override
    public boolean removeById(int id) {
        try {
            template.update(ds, "DELETE FROM products WHERE id = ?;", stmt -> {
                stmt.setInt(1, id);
                return stmt;
            });
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return true;
    }
}
