package com.meir.coupons.dal;

import com.meir.coupons.beans.Category;
import com.meir.coupons.utils.exceptions.ApplicationException;

import java.sql.*;

import static com.meir.coupons.enums.ErrorType.GENERAL_ERROR;

public class CategoryDal extends GeneralDal<Category> {
    public CategoryDal() {
    }
    @Override
    protected PreparedStatement getStatementForAdd(Category category, Connection connection) throws ApplicationException {
        PreparedStatement preparedStatement = null;
        try {
            if (category.getId() == null) {
                String sqlStatement = "INSERT INTO categories (name) VALUES(?)";
                preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            } else {
                String sqlStatement = "UPDATE categories SET name=? WHERE id=?";
                preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setInt(2, category.getId());
            }
            preparedStatement.setString(1, category.getName());
            return preparedStatement;
        } catch (Exception e) {
            throw new ApplicationException(e, GENERAL_ERROR, "failed to add category - " + category.toString());
        }
    }
    @Override
    protected Integer getObjectId(Category category) {
        return category.getId();
    }
    @Override
    protected String getTableName() {
        return "categories";
    }
    @Override
    protected String getStatement() {
        return "SELECT * FROM categories WHERE id=?";
    }
    @Override
    protected Category extractObjectFromResultSet(ResultSet resultSet) throws ApplicationException, SQLException {
        try {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Category category = new Category(id, name);
            return category;
        } catch (SQLException e) {
            throw new ApplicationException(e, GENERAL_ERROR, "failed to get category by id - " + resultSet.getInt("id"));
        }
    }
}
