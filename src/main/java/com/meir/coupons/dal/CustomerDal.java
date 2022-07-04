package com.meir.coupons.dal;

import com.meir.coupons.beans.Customer;
import com.meir.coupons.beans.User;
import com.meir.coupons.enums.objectsParameters.CustomerParameters;
import com.meir.coupons.utils.exceptions.ApplicationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.meir.coupons.enums.ErrorType.FAILED_TO_SET_NEW_VALUE;
import static com.meir.coupons.enums.ErrorType.GENERAL_ERROR;

public class CustomerDal extends GeneralDal<Customer> {
    private static UserDal userDal = new UserDal();
    public CustomerDal() {
    }
    @Override
    protected PreparedStatement getStatementForAdd(Customer customer, Connection connection) throws ApplicationException {
        PreparedStatement preparedStatement = null;
        try {
            String sqlStatement = null;
            if (!isExistById(customer.getUser().getId())) {
                sqlStatement = "INSERT INTO customers (id, address, amount_of_children) VALUES(?,?,?)";
                preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setInt(1, customer.getUser().getId());
                preparedStatement.setString(2, customer.getAddress());
                preparedStatement.setInt(3, customer.getAmountOfChildren());
            } else {
                sqlStatement = "UPDATE customers SET address=?, amount_of_children=? WHERE id=?";
                preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setInt(3, customer.getUser().getId());
                preparedStatement.setString(1, customer.getAddress());
                preparedStatement.setInt(2, customer.getAmountOfChildren());
            }
            return preparedStatement;
        } catch (Exception e) {
            throw new ApplicationException(e, GENERAL_ERROR, "failed to add customer - " + customer.toString());
        }
    }
    @Override
    protected Integer getObjectId(Customer object) {
        return object.getUser().getId();
    }
    @Override
    protected String getTableName() {
        return "customers";
    }
    @Override
    protected String getStatement() {
        return "SELECT * from customers WHERE id = ?";
    }
    @Override
    protected Customer extractObjectFromResultSet(ResultSet resultSet) throws Exception, SQLException {
        try {
            int id = resultSet.getInt("id");
            String address = resultSet.getString("address");
            int amountOfChildren = resultSet.getInt("amount_of_children");
            User user = userDal.getById(id);
            Customer customer = new Customer(user, address, amountOfChildren);
            return customer;
        } catch (SQLException e) {
            throw new ApplicationException(e, GENERAL_ERROR, "failed to get customer by  id - " + resultSet.getInt("id"));
        }
    }
    public void updateById(int customerId, CustomerParameters customerParameters, String value) throws Exception {
        Customer customer = getById(customerId);
        try {

            if (customerParameters == CustomerParameters.address) {
                customer.setAddress(value);
            }
            if (customerParameters == CustomerParameters.amountOfChildren) {
                Integer valueForCompanyId = Integer.parseInt(value);
                customer.setAmountOfChildren(valueForCompanyId);
            }
            add(customer);
        }
        catch (Exception e){
            throw new ApplicationException(FAILED_TO_SET_NEW_VALUE, "failed to set new value to customer by id - " + customerId);
        }
    }
}
