package com.meir.coupons.logic;

import com.meir.coupons.beans.Coupon;
import com.meir.coupons.dal.CategoryDal;
import com.meir.coupons.dal.CompanyDal;
import com.meir.coupons.dal.CouponDal;
import com.meir.coupons.dal.PurchaseDal;
import com.meir.coupons.enums.objectsParameters.CouponParameters;

import java.util.ArrayList;
import java.util.List;
public class CouponLogic {
    private static PurchaseDal purchaseDal = new PurchaseDal();
    private static CompanyDal companyDal = new CompanyDal();
    private static CategoryDal categoryDal = new CategoryDal();
    private static CouponDal couponDal = new CouponDal();
    public CouponLogic() {
    }
    public void addCoupon(Coupon coupon) throws Exception {
        ValidationForAddCoupon(coupon);
        couponDal.add(coupon);
    }
    public List<Coupon> getCoupons() throws Exception {
        return couponDal.getObjects();
    }
    public Coupon getCouponById(int couponId) throws Exception {
        couponDal.validationForIdThatAlreadyExists(couponId);
        return couponDal.getById(couponId);
    }
    public void updateCouponById(int couponId, CouponParameters couponParameters, String value) throws Exception {
        couponDal.validationForIdThatAlreadyExists(couponId);
        couponDal.updateById(couponId, couponParameters, value);
    }
    public void removeCouponById(int couponId) throws Exception {
        couponDal.validationForIdThatAlreadyExists(couponId);
        purchaseDal.removeByCouponId(couponId);
        couponDal.removeById(couponId);
    }
    public void removeAllCoupons() throws Exception {
        couponDal.removeObjects();
    }

    private void removeExpiredCoupons() throws Exception {
        int index = 0;
        while (index < 10000){
            Thread.sleep(86400000);
            List<Integer> expiredCouponsIds = new ArrayList<>();
            expiredCouponsIds = couponDal.getExpiredCouponsIds();
            for (int i = 0; i < expiredCouponsIds.size(); i++) {
                purchaseDal.removeByCouponId(expiredCouponsIds.get(i));
            }
            couponDal.removeExpiredCoupons();
            index++;
        }
    }
    private void ValidationForAddCoupon(Coupon coupon) throws Exception {
        if (!categoryDal.isExistById(coupon.getCategoryId())){
            throw new Exception("this category id are not exist");
        }
        if (!companyDal.isExistById(coupon.getCompanyId())){
            throw new Exception("this company id are not exist");
        }
        if (coupon.getAmount() < 0){
            throw new Exception("the amount of coupons is less than - 0");
        }
    }
}
