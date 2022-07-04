package com.meir.coupons.dal;

import com.meir.coupons.beans.Coupon;
import com.meir.coupons.enums.objectsParameters.CouponParameters;
import com.meir.coupons.utils.JdbcUtils;
import com.meir.coupons.utils.exceptions.ApplicationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.meir.coupons.enums.ErrorType.FAILED_TO_SET_NEW_VALUE;
import static com.meir.coupons.enums.ErrorType.GENERAL_ERROR;

public class CouponDal extends GeneralDal<Coupon> {
    private static final long now = System.currentTimeMillis();

    public CouponDal() {
    }

    @Override
    protected PreparedStatement getStatementForAdd(Coupon coupon, Connection connection) throws ApplicationException {
        PreparedStatement preparedStatement = null;
        try {
            if (coupon.getId() == null) {
                String sqlStatement = "INSERT INTO coupons (company_id, category_id, title, description, start_date, end_date, amount, price, image) VALUES(?,?,?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            } else {
                String sqlStatement = "UPDATE coupons SET company_id=?, category_id=?, title=?, description=?, start_date=?, end_date=?, amount=?, price=?, image=? WHERE id=?";
                preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setInt(10, coupon.getId());
            }
            if (coupon.getCompanyId() != null) {
                preparedStatement.setInt(1, coupon.getCompanyId());
            } else {
                preparedStatement.setNull(1, Types.NULL);
            }
            preparedStatement.setInt(2, coupon.getCategoryId());
            preparedStatement.setString(3, coupon.getTitle());
            preparedStatement.setString(4, coupon.getDescription());
            preparedStatement.setDate(5, coupon.getStartDate());
            preparedStatement.setDate(6, coupon.getEndDate());
            preparedStatement.setInt(7, coupon.getAmount());
            preparedStatement.setDouble(8, coupon.getPrice());
            preparedStatement.setString(9, coupon.getImage());
            return preparedStatement;
        } catch (Exception e) {
            throw new ApplicationException(e, GENERAL_ERROR, "failed to add coupon - " + coupon.toString());
        }
    }

    @Override
    protected Integer getObjectId(Coupon object) {
        return object.getId();
    }

    @Override
    protected String getTableName() {
        return "coupons";
    }

    @Override
    protected String getStatement() {
        return "SELECT * from coupons WHERE id = ?";
    }

    @Override
    protected Coupon extractObjectFromResultSet(ResultSet resultSet) throws ApplicationException, SQLException {
        try {
            int id = resultSet.getInt("id");
            int companyId = resultSet.getInt("company_id");
            int categoryId = resultSet.getInt("category_id");
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            Date startDate = resultSet.getDate("start_date");
            Date endDate = resultSet.getDate("end_date");
            int amount = resultSet.getInt("amount");
            double price = resultSet.getDouble("price");
            String image = resultSet.getString("image");
            Coupon coupon = new Coupon(id, companyId, categoryId, title, description, startDate, endDate, amount, price, image);
            return coupon;
        } catch (SQLException e) {
            throw new ApplicationException(e, GENERAL_ERROR, "failed to get COUPON by  id - " + resultSet.getInt("id"));
        }
    }

    public void updateById(int couponId, CouponParameters couponParameters, String value) throws Exception {
        Coupon couponForUpdate = getById(couponId);
        try {
            if (couponParameters == CouponParameters.companyId) {
                int valueByInt = Integer.parseInt(value);
                couponForUpdate.setCompanyId(valueByInt);
            }
            if (couponParameters == CouponParameters.categoryId) {
                int valueByInt = Integer.parseInt(value);
                couponForUpdate.setCategoryId(valueByInt);
            }
            if (couponParameters == CouponParameters.title) {
                couponForUpdate.setTitle(value);
            }
            if (couponParameters == CouponParameters.description) {
                couponForUpdate.setDescription(value);
            }
            if (couponParameters == CouponParameters.startDate) {
                Date valueByDate = Date.valueOf(value);
                couponForUpdate.setStartDate(valueByDate);
            }
            if (couponParameters == CouponParameters.endDate) {
                Date valueByDate = Date.valueOf(value);
                couponForUpdate.setEndDate(valueByDate);
            }
            if (couponParameters == CouponParameters.amount) {
                int valueByInt = Integer.parseInt(value);
                couponForUpdate.setAmount(valueByInt);
            }
            if (couponParameters == CouponParameters.price) {
                double valueByDouble = Double.valueOf(value);
                couponForUpdate.setPrice(valueByDouble);
            }
            if (couponParameters == CouponParameters.image) {
                couponForUpdate.setImage(value);
            }
            add(couponForUpdate);
        } catch (Exception e) {
            throw new ApplicationException(FAILED_TO_SET_NEW_VALUE, "failed to set new value for coupon id - " + couponId);
        }
    }

    public List<Integer> getCouponsIdByCompanyId(Integer companyId) throws ApplicationException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Integer> couponIds = new ArrayList<>();
        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT * from " + getTableName() + " WHERE company_id = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, companyId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer couponId = extractObjectFromResultSet(resultSet).getId();
                couponIds.add(couponId);
            }
            return couponIds;
        } catch (SQLException e) {
            throw new ApplicationException(e, GENERAL_ERROR, "failed to get coupon by company id - " + companyId);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public List<Integer> getCouponsIdByCategoryId(Integer categoryId) throws ApplicationException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Integer> couponIds = new ArrayList<>();
        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT * from " + getTableName() + " WHERE category_id = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer couponId = extractObjectFromResultSet(resultSet).getId();
                couponIds.add(couponId);
            }
            return couponIds;
        } catch (SQLException e) {
            throw new ApplicationException(e, GENERAL_ERROR, "failed to get coupons id's by category id - " + categoryId);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public void removeExpiredCoupons() throws ApplicationException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Date date = new Date(now);
            connection = JdbcUtils.getConnection();
            String sqlStatement = "DELETE from " + getTableName() + " WHERE end_date < " + date;
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e, GENERAL_ERROR, "failed to remove expired coupons");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }

    public List<Integer> getExpiredCouponsIds() throws ApplicationException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Integer> couponIds = new ArrayList<>();
        try {
            Date date = new Date(now);
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT * from " + getTableName() + " WHERE end_date < " + date;
            preparedStatement = connection.prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer couponId = extractObjectFromResultSet(resultSet).getId();
                couponIds.add(couponId);
            }
            return couponIds;
        } catch (SQLException e) {
            throw new ApplicationException(e, GENERAL_ERROR, "failed to get expired coupons id's");
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }
}

