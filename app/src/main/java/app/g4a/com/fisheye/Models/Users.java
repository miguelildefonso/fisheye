package app.g4a.com.fisheye.Models;

public class Users {
    public String name, email, phone, status;

    public Users(){

    }

    public Users(String name, String email, String phone, String status) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }
}
