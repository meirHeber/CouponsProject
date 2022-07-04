package com.meir.coupons.dal;

import com.meir.coupons.utils.JdbcUtils;
import com.meir.coupons.utils.ValidationUtils;
import com.meir.coupons.utils.exceptions.ApplicationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.meir.coupons.enums.ErrorType.*;

public abstract class GeneralDal<T> {
    public GeneralDal() {
    }

    public T getById(int id) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = getStatement();
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }
            T object = extractObjectFromResultSet(resultSet);
            return object;
        } catch (SQLException e) {
            throw new ApplicationException(e, GENERAL_ERROR, "failed to get object " + id);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public List<T> getByUserId(int userId) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<T> objects = new ArrayList<>();
        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = getStatementForGetByUserId();
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T object = extractObjectFromResultSet(resultSet);
                objects.add(object);
            }
            return objects;
        } catch (SQLException e) {
            throw new ApplicationException(e, GENERAL_ERROR, "failed to get object by user id - " + userId);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public List<T> getByCompanyId(int companyId) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<T> objects = new ArrayList<>();
        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = getStatementForGetByCompanyId();
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, companyId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T object = extractObjectFromResultSet(resultSet);
                objects.add(object);
            }
            return objects;
        } catch (SQLException e) {
            throw new ApplicationException(e, GENERAL_ERROR, "failed to get object by company id - " + companyId);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public List<T> getByCouponId(int couponId) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<T> objects = new ArrayList<>();
        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = getStatementForGetByCouponId();
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, couponId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T object = extractObjectFromResultSet(resultSet);
                objects.add(object);
            }
            return objects;
        } catch (SQLException e) {
            throw new ApplicationException(e, GENERAL_ERROR, "failed to get object by company id - " + couponId);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    private String getStatementForGetByCouponId() {
        return null;
    }

    protected String getStatementForGetByCompanyId() {
        return null;
    }


    public List<T> getObjects() throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<T> objects = new ArrayList<>();
        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT * FROM " + getTableName();
            preparedStatement = connection.prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T object = extractObjectFromResultSet(resultSet);
                objects.add(object);
            }
            return objects;
        } catch (SQLException e) {
            throw new ApplicationException(e, GENERAL_ERROR, "failed to get objects");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public void removeById(int id) throws ApplicationException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "DELETE from " + getTableName() + " WHERE id = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e, GENERAL_ERROR, "failed to remove object by id - " + id);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public void removeObjects() throws ApplicationException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "DELETE from " + getTableName();
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e, GENERAL_ERROR, "failed to remove objects");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public int add(T object) throws ApplicationException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtils.getConnection();
            preparedStatement = getStatementForAdd(object, connection);
            preparedStatement.executeUpdate();
            if (getObjectId(object) == null) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if (!resultSet.next()) {
                }
                return resultSet.getInt(1);
            }
            return getObjectId(object);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e, GENERAL_ERROR, "failed to add object - " + object.toString());
        } finally {
            //Closing the resources
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public void removeByCompanyId(Integer companyId) throws ApplicationException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "DELETE from " + getTableName() + " WHERE company_id = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, companyId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e, GENERAL_ERROR, "failed to remove object by company id - " + companyId);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public void removeByCategoryId(Integer categoryId) throws ApplicationException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "DELETE from " + getTableName() + " WHERE category_id = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, categoryId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e, GENERAL_ERROR, "failed to remove object by category id - " + categoryId);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public void removeByUserId(int userId) throws ApplicationException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "DELETE from " + getTableName() + " WHERE user_id = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e, GENERAL_ERROR, "failed to remove object by user id - " + userId +", from table " + getTableName());
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public boolean isExistById(int id) throws Exception {
        return getById(id) != null;
    }

    public void validationForIdThatAlreadyExists(int id) throws Exception {
        if (!ValidationUtils.isDigitsOnly(id)) {
            throw new ApplicationException(ID_ARE_NOT_DIGITS_ONLY, "id are not digits only");
        }
        if (!isExistById(id)) {
            throw new ApplicationException(ID_DOES_NOT_EXIST, "this id are not exist");
        }
    }

    protected abstract PreparedStatement getStatementForAdd(T object, Connection connection) throws Exception;

    protected abstract Integer getObjectId(T object);

    protected String getStatementForGetByUserId(){
        return null;
    }

    protected abstract String getTableName();

    protected abstract String getStatement();

    protected abstract T extractObjectFromResultSet(ResultSet resultSet) throws Exception;
}
