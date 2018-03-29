package com.ttp.shoppingapp.Model;

/**
 * Created by 0047TiTANplateform_ on 2018-01-24.
 */

public class User {
    private String name;
    private String password;
    private String phone;
    private String isStaff;
    private String secureCode;

    public User() {
    }

    public User(String name, String password, String secureCode) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.isStaff = isStaff;
        this.secureCode = secureCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(String isStaff) {
        this.isStaff = isStaff;
    }

    public String getSecureCode() {
        return secureCode;
    }

    public void setSecureCode(String secureCode) {
        this.secureCode = secureCode;
    }
}

