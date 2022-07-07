package com.meir.coupons.bean;

import java.sql.Date;

public class Purchase {
    private Integer id;
    private int couponId;
    private int userId;
    private String userFirstName;
    private String userLastName;
    private int companyId;
    private String companyName;
    private String categoryName;
    private Date couponStartDate;
    private Date couponEndDate;
    private Date timeStamp;
    private int amount;
    private double totalPrice;

    public Purchase() {
    }

    public Purchase(Integer id, int couponId, int userId, String userFirstName, String userLastName, int companyId, String companyName, String categoryName, Date couponStartDate, Date couponEndDate, Date timeStamp, int amount, double totalPrice) {
        this.id = id;
        this.couponId = couponId;
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.companyId = companyId;
        this.companyName = companyName;
        this.categoryName = categoryName;
        this.couponStartDate = couponStartDate;
        this.couponEndDate = couponEndDate;
        this.timeStamp = timeStamp;
        this.amount = amount;
        this.totalPrice = totalPrice;
    }

    public Purchase(int couponId, int userId, int amount) {
        this.couponId = couponId;
        this.userId = userId;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getCouponStartDate() {
        return couponStartDate;
    }

    public void setCouponStartDate(Date couponStartDate) {
        this.couponStartDate = couponStartDate;
    }

    public Date getCouponEndDate() {
        return couponEndDate;
    }

    public void setCouponEndDate(Date couponEndDate) {
        this.couponEndDate = couponEndDate;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", couponId=" + couponId +
                ", userId=" + userId +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", couponStartDate=" + couponStartDate +
                ", couponEndDate=" + couponEndDate +
                ", timeStamp=" + timeStamp +
                ", amount=" + amount +
                ", totalPrice=" + totalPrice +
                '}';
    }
}