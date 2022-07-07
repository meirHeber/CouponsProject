package com.meir.coupons.logic;

import com.meir.coupons.bean.Category;
import com.meir.coupons.enums.objectsParameters.CategoryParameters;

import java.util.List;

public class CategoryLogic {
    private static CategoryDal categoryDal = new CategoryDal();
    private static PurchaseDal purchaseDal = new PurchaseDal();
    private static CouponDal couponDal = new CouponDal();

    public CategoryLogic() {
    }

    public void addCategory(Category category) throws Exception {
        validationForAddCategory(category);
        categoryDal.add(category);
    }

    public Category getCategoryById(int categoryId) throws Exception {
        categoryDal.validationForIdThatAlreadyExists(categoryId);
        return categoryDal.getById(categoryId);

    }

    public List<Category> getCategories() throws Exception {
        return categoryDal.getObjects();
    }

    public void updateCategoryById(int categoryId, CategoryParameters categoryParameters, String value) throws Exception {
        categoryDal.validationForIdThatAlreadyExists(categoryId);
        Category category = new Category(categoryId, value);
        categoryDal.add(category);
    }

    public void removeCategoryById(int categoryId) throws Exception {
        categoryDal.validationForIdThatAlreadyExists(categoryId);
        List<Integer> couponsOfCategory = couponDal.getCouponsIdByCategoryId(categoryId);
        for (int index = 0; index < couponsOfCategory.size(); index++) {
            purchaseDal.removeByCouponId(couponsOfCategory.get(index));
        }
        couponDal.removeByCompanyId(categoryId);
        categoryDal.removeById(categoryId);
    }

    public void removeCategories() throws Exception {
        categoryDal.removeObjects();
    }

    private void validationForAddCategory(Category category) throws Exception {
    }
}
