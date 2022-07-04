package com.meir.coupons.dal;

import com.meir.coupons.beans.User;
import com.meir.coupons.enums.UserTypes;
import com.meir.coupons.enums.objectsParameters.UserParameters;
import com.meir.coupons.utils.JdbcUtils;
import com.meir.coupons.utils.exceptions.ApplicationException;

import java.sql.*;

import static com.meir.coupons.enums.ErrorType.FAILED_TO_SET_NEW_VALUE;
import static com.meir.coupons.enums.ErrorType.GENERAL_ERROR;

public class UserDal extends GeneralDal<User> {
    public UserDal() {
    }
    @Override
    protected PreparedStatement getStatementForAdd(User user, Connection connection) throws ApplicationException {
        PreparedStatement preparedStatement = null;
        try {
            if (user.getId() == null) {
                String sqlStatement = "INSERT INTO users (first_name, last_name, username, password, type, company_id) VALUES(?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            } else {
                String sqlStatement = "UPDATE users SET first_name=?, last_name=?, username=?, password=?, type=?, company_id=? WHERE id=?";
                preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setInt(7, user.getId());
            }
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, String.valueOf(user.getUserType()));
            if (user.getCompanyId() != null) {
                preparedStatement.setInt(6, user.getCompanyId());
            } else {
                preparedStatement.setNull(6, Types.NULL);
            }
            return preparedStatement;
        } catch (Exception e) {
            throw new ApplicationException(e, GENERAL_ERROR, "failed to add user where username is - " + user.getUsername());
        }
    }
    @Override
    protected Integer getObjectId(User user) {
        return user.getId();
    }
    @Override
    protected String getTableName() {
        return "users";
    }
    @Override
    protected String getStatement() {
        return "SELECT * FROM users WHERE id=?";
    }
    @Override
    protected User extractObjectFromResultSet(ResultSet resultSet) throws ApplicationException, SQLException {
        try {
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            UserTypes userType = UserTypes.valueOf(resultSet.getString("type"));
            Integer companyId = null;
            if (resultSet.getObject("company_id") != null) {
                companyId = resultSet.getInt("company_id");
            }
            User user = new User(id, firstName, lastName, username, password, userType, companyId);
            return user;
        } catch (SQLException e) {
            throw new ApplicationException(e, GENERAL_ERROR, "failed to get user by  id - " + resultSet.getInt("id"));
        }
    }
    public User setParametersToUserObject(int userId, UserParameters userColumnName, String value) throws Exception {
        User userForUpdate = getById(userId);
        try {
            if (userColumnName == UserParameters.username) {
                userForUpdate.setUsername(value);
            }
            if (userColumnName == UserParameters.firstName) {
                userForUpdate.setFirstName(value);
            }
            if (userColumnName == UserParameters.lastName) {
                userForUpdate.setLastName(value);
            }
            if (userColumnName == UserParameters.password) {
                userForUpdate.setPassword(value);
            }
            if (userColumnName == UserParameters.userType) {
                userForUpdate.setUserType(UserTypes.valueOf(value));
            }
            if (userColumnName == UserParameters.companyId) {
                Integer valueForCompanyId = Integer.parseInt(value);
                userForUpdate.setCompanyId(valueForCompanyId);
            }
            return userForUpdate;
        }
        catch (Exception e){
            throw new ApplicationException(FAILED_TO_SET_NEW_VALUE, "failed to set new value by user id - " + userId);
        }
    }
    public void update(int userId, UserParameters userParameters, String value) throws Exception {
        User user = setParametersToUserObject(userId, userParameters, value);
        add(user);
    }
    public String getUsernameFromDb(String username) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            String sqlStatement = "SELECT username from " + getTableName() + " WHERE username = ?";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }
            String usernameForReturn = resultSet.getString("username");
            return usernameForReturn;
        } catch (SQLException e) {
            throw new ApplicationException(e, GENERAL_ERROR, "failed to get user by username - " + username);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }
    public boolean isUsernameExist(String username) throws Exception {
        return getUsernameFromDb(username) != null;
    }
}
