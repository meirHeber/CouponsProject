package com.meir.coupons.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ValidationUtils {



    public static boolean isDigitsOnly(int id) {
        String strNumber = id + "";
        for (int index = 0; index < strNumber.length(); index++) {
            char c = strNumber.charAt(index);
            if (c < '0') {
                return false;
            }
            if (c > '9') {
                return false;
            }
        }
        return true;

    }

//    private static boolean isObjectIdExist(Integer objectId, NamesOfObjectsInDb namesOfObjectsInDb) throws Exception {
//        String nameOfObjectInDb = String.valueOf(namesOfObjectsInDb);
//        try {
//            getObjectIdFromDb(objectId, nameOfObjectInDb);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

    private static void getObjectIdFromDb(int objectId, String nameOfObjectInDb) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection from the connection manager
            connection = JdbcUtils.getConnection();

            // Creating the SQL query and executing
            String sqlStatement = "SELECT id FROM ? WHERE id=?";
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, nameOfObjectInDb);
            preparedStatement.setInt(2, objectId);


            ResultSet resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new Exception("this object id  " + objectId, e);
        } finally {
            JdbcUtils.closeResources(connection, preparedStatement);
        }
    }
}

