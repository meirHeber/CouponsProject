package com.meir.coupons.dal;

import com.meir.coupons.beans.Company;
import com.meir.coupons.enums.objectsParameters.CompanyParameters;

import java.sql.*;

public class CompanyDal extends GeneralDal<Company> {
    public CompanyDal() {
    }

    @Override
    protected PreparedStatement getStatementForAdd(Company company, Connection connection) throws Exception {
        PreparedStatement preparedStatement = null;
        try {
            if (company.getId() == null) {
                String sqlStatement = "INSERT INTO companies (name, phone_number, address) VALUES(?,?,?)";
                preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            } else {
                String sqlStatement = "UPDATE companies SET name=?, phone_number=?, address=? WHERE id=?";
                preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setInt(4, company.getId());
            }
            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getPhoneNumber());
            preparedStatement.setString(3, company.getAddress());
            return preparedStatement;
        } catch (Exception e) {
            throw new Exception("Failed to get company " + company, e);
        }
    }

    @Override
    protected Integer getObjectId(Company company) {
        return company.getId();
    }

    @Override
    protected String getTableName() {
        return "companies";
    }

    @Override
    protected String getStatement() {
        return "SELECT * FROM companies WHERE id=?";
    }

    @Override
    protected Company extractObjectFromResultSet(ResultSet resultSet) throws Exception {
        try {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String phoneNumber = resultSet.getString("phone_number");
            String address = resultSet.getString("address");
            Company company = new Company(id, name, phoneNumber, address);
            return company;
        } catch (SQLException e) {
            throw new Exception("Failed to get company", e);
        }
    }

    public void updateById(int companyId, CompanyParameters companyParameters, String value) throws Exception {
        Company companyForUpdate = getById(companyId);
        if (companyParameters == CompanyParameters.name) {
            companyForUpdate.setName(value);
        }
        if (companyParameters == CompanyParameters.phoneNumber) {
            companyForUpdate.setPhoneNumber(value);
        }
        if (companyParameters == CompanyParameters.address) {
            companyForUpdate.setAddress(value);
        }
        add(companyForUpdate);
    }

}
