package com.meir.coupons.dal;

import com.meir.coupons.beans.Purchase;
import com.meir.coupons.utils.JdbcUtils;
import com.meir.coupons.utils.exceptions.ApplicationException;

import java.sql.*;

import static com.meir.coupons.enums.ErrorType.GENERAL_ERROR;

public class PurchaseDal extends GeneralDal<Purchase> {
    private static final long now = System.currentTimeMillis();
    private static CouponDal couponDal = new CouponDal();
    public PurchaseDal() {
    }
    @Override
    protected String getStatementForGetByUserId() {
        return "select p.id, p.coupon_id, p.user_id, us.first_name, us.last_name, cou.company_id, com.name as company_name, cat.name as category_name, cou.start_date, cou.end_date, p.time_stamp, p.amount, p.total_price from purchases p join users us on p.user_id = us.id join coupons cou on p.coupon_id = cou.id join companies com on cou.company_id = com.id join categories cat on cou.category_id = cat.id WHERE us.id = ?";
    }
    @Override
    protected PreparedStatement getStatementForAdd(Purchase purchase, Connection connection) throws Exception {
        PreparedStatement preparedStatement = null;
        try {
            if (purchase.getId() == null) {
                String sqlStatement = "INSERT INTO purchases (coupon_id, user_id, time_stamp, amount, total_price) VALUES(?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            } else {
                String sqlStatement = "UPDATE purchases SET coupon_id=?, coupon_id=?, time_stamp=?, amount=?, TOTAL_PRICE=? WHERE id=?";
                preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setInt(6, purchase.getId());
            }
            preparedStatement.setInt(1, purchase.getCouponId());
            preparedStatement.setInt(2, purchase.getUserId());
            preparedStatement.setDate(3, new Date(now));
            preparedStatement.setInt(4, purchase.getAmount());
            preparedStatement.setDouble(5, purchase.getTotalPrice());
            return preparedStatement;
        } catch (Exception e) {
            throw new Exception("Failed to add purchase " + purchase, e);
        }
    }
    @Override
    protected Integer getObjectId(Purchase object) {
        return object.getId();
    }
    @Override
    protected String getTableName() {
        return "purchases";
    }
    @Override
    protected String getStatement() {
        return "select p.id, p.coupon_id, p.user_id, us.first_name, us.last_name, cou.company_id, com.name as company_name, cat.name as category_name, cou.start_date, cou.end_date, p.time_stamp, p.amount, p.total_price from purchases p join users us on p.user_id = us.id join coupons cou on p.coupon_id = cou.id join companies com on cou.company_id = com.id join categories cat on cou.category_id = cat.id WHERE p.id = ?";
    }
    @Override
    protected Purchase extractObjectFromResultSet(ResultSet resultSet) throws ApplicationException, SQLException {
        try {
            int id = resultSet.getInt("id");
            int couponId = resultSet.getInt("coupon_id");
            int userId = resultSet.getInt("user_id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            int companyId = resultSet.getInt("company_id");
            String companyName = resultSet.getString("company_name");
            String categoryName = resultSet.getString("category_name");
            Date startDate = resultSet.getDate("start_date");
            Date endDate = resultSet.getDate("end_date");
            Date timeStamp = resultSet.getDate("time_stamp");
            int amount = resultSet.getInt("amount");
            double totalPrice = resultSet.getDouble("total_price");
            Purchase purchase = new Purchase(id, couponId, userId, firstName, lastName, companyId, companyName, categoryName, startDate, endDate, timeStamp, amount, totalPrice);
            return purchase;
        } catch (SQLException e) {
            throw new ApplicationException(e, GENERAL_ERROR, "failed to get object by  id - " + resultSet.getInt("id"));
        }
    }
    @Override
    protected String getStatementForGetByCompanyId() {
        return "select p.id, p.coupon_id, p.user_id, us.first_name, us.last_name, cou.company_id, com.name as company_name, cat.name as category_name, cou.start_date, cou.end_date, p.time_stamp, p.amount, p.total_price from purchases p join users us on p.user_id = us.id join coupons cou on p.coupon_id = cou.id join companies com on cou.company_id = com.id join categories cat on cou.category_id = cat.id WHERE com.id = ?";
    }
    public void removeByCouponId(Integer couponId) throws ApplicationException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "DELETE from " + getTableName() + " WHERE coupon_id = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, couponId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e, GENERAL_ERROR, "failed to remove purchase by coupon id - " + couponId);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }
    protected String getStatementForGetByCouponId() {
        return "select p.id, p.coupon_id, p.user_id, us.first_name, us.last_name, cou.company_id, com.name as company_name, cat.name as category_name, cou.start_date, cou.end_date, p.time_stamp, p.amount, p.total_price from purchases p join users us on p.user_id = us.id join coupons cou on p.coupon_id = cou.id join companies com on cou.company_id = com.id join categories cat on cou.category_id = cat.id WHERE cou.id = ?";
    }

}
