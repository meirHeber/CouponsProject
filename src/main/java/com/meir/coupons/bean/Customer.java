package com.meir.coupons.bean;

public class Customer {
    private User user;
    private String address;
    private int amountOfChildren;

    public Customer(){
    }

    public Customer(User user, String address, int amountOfChildren) {
        this.user = user;
        this.address = address;
        this.amountOfChildren = amountOfChildren;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAmountOfChildren() {
        return amountOfChildren;
    }

    public void setAmountOfChildren(int amountOfChildren) {
        this.amountOfChildren = amountOfChildren;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "user=" + user +
                ", address='" + address + '\'' +
                ", amountOfChildren=" + amountOfChildren +
                '}';
    }
}

