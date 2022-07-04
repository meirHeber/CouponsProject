package com.meir.coupons.enums;

public enum ErrorType {

    GENERAL_ERROR(601, "General error", true),
    LOGIN_FAILED(603, "Login failed. Please try again.", false),
    NAME_ALREADY_EXISTS(604, "The name you chose already exists. Please enter another name", false),
    MUST_ENTER_NAME(605, "Can not insert an null/empty name", false),
    ID_DOES_NOT_EXIST(607, "This ID does'nt exist", false),
    INVALID_PASSWORD(608, "Password must contain at least 8 charaters, only UpperCase lettersand and at least one digit", false),
    NOT_ENOUGH_COUPONS_LEFT(609, "Not enough coupons left to purchase the amount requested", false),
    COUPON_EXPIERED(610, "The coupon is expiered", false),
    COUPON_TITLE_EXIST(611, "The title of this coupon is already exists, please change the title", false),
    INVALID_PRICE(612, "Coupon price must be more than 0", false),
    INVALID_EMAIL(613, "Email address is InValid, Please enter a valid email address", false),
    INVALID_AMOUNT(614, "Coupon's amount must be more than 0", false),
    INVALID_DATES(615, "The dates you've entered are wrong or null", false),
    MUST_INSERT_A_VALUE(616, "Must insert a value", false),
    COULD_NOT_GENERATE_ID(617,"could not generate Id", false ),
    NAME_TOO_SHORT(618, "The name must contain at least two letters" , false),
    INVALID_COUPON_TYPE (619, "Can not insert an null/emptycoupon type", false),
    INVALID_ID(620, "Id can not be empty or null and must contain at least one digit", false),
    INVALID_ADDRESS(621, "Address is InValid, Please enter a valid address", false),
    INVALID_PHONE_NUMBER(622, "Phone number is InValid, Please enter a valid phone number", false),
    NO_PERMISSION_GRANTED(623, "No promission gramted", true),
    ID_ARE_NOT_DIGITS_ONLY(624, "id are not digits only", true),
    FAILED_TO_SET_NEW_VALUE(625, "failed to set the new value",false);

    private int errorNumber;
    private String errorMessage;
    private boolean isShowStackTrace;

    private ErrorType(int errorNumber, String internalMessage, boolean isShowStackTrace) {
        this.errorNumber = errorNumber;
        this.errorMessage = internalMessage;
        this.isShowStackTrace = isShowStackTrace;
    }

    private ErrorType(int errorNumber, String internalMessage) {
        this.errorNumber = errorNumber;
        this.errorMessage = internalMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isShowStackTrace() {
        return isShowStackTrace;
    }

    public int getErrorNumber() {
        return errorNumber;
    }
}
