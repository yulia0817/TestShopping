package com.ttp.getapp.Model;

/**
 * Created by 0047TiTANplateform_ on 2018-01-31.
 */

public class User {

    private  String name, password, phone, isStaff;

    public User(String name, String password) {
        name = name;
        password = password;
    }

    public User() {
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
}
