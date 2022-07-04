package com.meir.coupons.logic;

import com.meir.coupons.beans.Coupon;
import com.meir.coupons.beans.Purchase;
import com.meir.coupons.dal.CouponDal;
import com.meir.coupons.dal.PurchaseDal;
import com.meir.coupons.enums.objectsParameters.CouponParameters;

import java.util.List;

public class PurchaseLogic {

    private static CouponDal couponDal = new CouponDal();
    private static PurchaseDal purchaseDal = new PurchaseDal();
    public PurchaseLogic() {
    }
    public void addPurchase(Purchase purchase) throws Exception {
        Coupon coupon = couponDal.getById(purchase.getCouponId());
        validationForAddPurchase(coupon, purchase);
        purchase.setTotalPrice(couponDal.getById(coupon.getId()).getPrice() * purchase.getAmount());
        purchaseDal.add(purchase);
        couponDal.updateById(purchase.getCouponId(), CouponParameters.amount, String.valueOf(coupon.getAmount() - purchase.getAmount()));
    }
    public Purchase getPurchaseById(int purchaseId) throws Exception {
        return purchaseDal.getById(purchaseId);
    }
    public List<Purchase> getPurchaseByUserId(int userId) throws Exception {
        return purchaseDal.getByUserId(userId);
    }
    public List<Purchase> getPurchaseByCompanyId(int companyId) throws Exception {
        return purchaseDal.getByCompanyId(companyId);
    }

    public List<Purchase> getPurchaseByCouponId(int couponId) throws Exception {
        return purchaseDal.getByCouponId(couponId);
    }
    public List<Purchase> getPurchases() throws Exception {
        return purchaseDal.getObjects();
    }
    private void validationForAddPurchase(Coupon coupon,Purchase purchase) throws Exception {
        if (coupon.getId() == null){
            throw new Exception("this coupon id are not exist");
        }
        if (coupon.getAmount() < purchase.getAmount()){
            throw new Exception("There is not enough of the requested coupon for your purchase. Only left " + coupon.getAmount());
        }
    }
}
