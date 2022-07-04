package com.meir.coupons.logic;

import com.meir.coupons.beans.User;
import com.meir.coupons.dal.CompanyDal;
import com.meir.coupons.dal.CustomerDal;
import com.meir.coupons.dal.UserDal;
import com.meir.coupons.enums.UserTypes;
import com.meir.coupons.enums.objectsParameters.UserParameters;

import java.util.List;

public class UserLogic {
    private static UserDal userDal = new UserDal();

    private static CompanyDal companyDal = new CompanyDal();
    private static CustomerLogic customerLogic = new CustomerLogic();
    private static CustomerDal customerDal = new CustomerDal();

    public UserLogic() {
    }

    public int addUser(User user) throws Exception {
        validationForAddUser(user);
        return userDal.add(user);
    }

    public User getUserById(int userId) throws Exception {
        userDal.validationForIdThatAlreadyExists(userId);
        return userDal.getById(userId);
    }

    public void updateUserById(int userId, UserParameters userParameters, String value) throws Exception {
        userDal.validationForIdThatAlreadyExists(userId);
        userDal.update(userId, userParameters, value);
    }

    public List<User> getUsers() throws Exception {
        return userDal.getObjects();
    }

    public void removeUserById(Integer id) throws Exception {
        userDal.validationForIdThatAlreadyExists(id);
        User user = userDal.getById(id);
        if (user.getUserType() == UserTypes.customer) {
            customerDal.validationForIdThatAlreadyExists(id);
            /// הפונקציה הבאה מוחקת גם את הרכישות
            customerLogic.removeCustomerById(id);
        }
        userDal.removeById(id);
    }

    public void removeUsers() throws Exception {
        userDal.removeObjects();
    }

    private void validationForAddUser(User user) throws Exception {
        if (!isLegalUserType(user.getUserType())) {
            throw new Exception("it is not a legal user type");
        }
        if (user.getUserType() == UserTypes.customer && user.getCompanyId() != null) {
            throw new Exception("it is not a company or admin user");
        }
        if (user.getUserType() == UserTypes.company) {
            if (user.getCompanyId() == null) {
                throw new Exception("company id are missing");
            }
            companyDal.validationForIdThatAlreadyExists(user.getCompanyId());
        }
        if (userDal.isUsernameExist(user.getUsername())) {
            throw new Exception("this username are already exist");
        }
    }

    private boolean isLegalUserType(UserTypes userType) {
        for (int i = 0; i < UserTypes.values().length; i++) {
            if (userType == UserTypes.values()[i]) {
                return true;
            }
        }
        return false;
    }
}



