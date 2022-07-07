package com.meir.coupons.logic;

import com.meir.coupons.bean.Company;
import com.meir.coupons.enums.objectsParameters.CompanyParameters;

import java.util.List;

public class CompanyLogic {

    private static PurchaseDal purchaseDal = new PurchaseDal();
    private static CouponDal couponDal = new CouponDal();
    private static CompanyDal companyDal = new CompanyDal();

    public CompanyLogic() {
    }

    public void addCompany(Company companyMock) throws Exception {
        validationForAddCompany(companyMock);
        companyDal.add(companyMock);
    }

    public Company getCompanyById(Integer companyId) throws Exception {
        companyDal.validationForIdThatAlreadyExists(companyId);
        return companyDal.getById(companyId);
    }

    public List<Company> getCompanies() throws Exception {
        return companyDal.getObjects();
    }

    public void removeCompanyById(Integer companyId) throws Exception {
        companyDal.validationForIdThatAlreadyExists(companyId);
        List<Integer> couponsOfCompany = couponDal.getCouponsIdByCompanyId(companyId);
        for (int index = 0; index < couponsOfCompany.size(); index++) {
            purchaseDal.removeByCouponId(couponsOfCompany.get(index));
        }
        couponDal.removeByCategoryId(companyId);
        companyDal.removeById(companyId);
    }
    public void removeCompanies() throws Exception {
        companyDal.removeObjects();
    }
    public void updateCompanyById(int companyId, CompanyParameters companyParameters, String value) throws Exception {
        companyDal.validationForIdThatAlreadyExists(companyId);
        companyDal.updateById(companyId, companyParameters, value);
    }
    private void validationForAddCompany(Company company) throws Exception {

    }
}
