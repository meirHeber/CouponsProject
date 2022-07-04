package com.meir.coupons.logic;

import com.meir.coupons.beans.Customer;
import com.meir.coupons.dal.CouponDal;
import com.meir.coupons.dal.CustomerDal;
import com.meir.coupons.dal.PurchaseDal;
import com.meir.coupons.enums.UserTypes;
import com.meir.coupons.enums.objectsParameters.CustomerParameters;

import java.util.List;

public class CustomerLogic {

    private static CustomerDal customerDal = new CustomerDal();
    private static CouponDal couponDal = new CouponDal();
    private static PurchaseDal purchaseDal = new PurchaseDal();
    private static UserLogic userLogic = new UserLogic();

    public CustomerLogic() {
    }

    public void addCustomer(Customer customer) throws Exception {
        validationForAddCustomer(customer);
        customer.getUser().setId(userLogic.addUser(customer.getUser()));
        customerDal.add(customer);
    }

    public List<Customer> getCustomers() throws Exception {
        return customerDal.getObjects();
    }

    public Customer getCustomerById(int customerId) throws Exception {
        customerDal.validationForIdThatAlreadyExists(customerId);
        return customerDal.getById(customerId);
    }

    public void updateCustomerById(int customerId, CustomerParameters customerParameters, String value) throws Exception {
        customerDal.validationForIdThatAlreadyExists(customerId);
        customerDal.updateById(customerId, customerParameters, value);
    }

    public void removeCustomerById(int customerId) throws Exception {
        customerDal.validationForIdThatAlreadyExists(customerId);
        purchaseDal.removeByUserId(customerId);
        customerDal.removeById(customerId);
    }

    public void removeCustomers() throws Exception {
        customerDal.removeObjects();
    }

    private void validationForAddCustomer(Customer customer) throws Exception {
        if (customer.getAmountOfChildren() < 0) {
            throw new Exception("the amount of children is less than - 0");
        }
        if (customer.getUser().getUserType() != UserTypes.customer){
            throw new Exception("user type are not customer");
        }
    }
}
