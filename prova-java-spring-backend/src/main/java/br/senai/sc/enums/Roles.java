package br.senai.sc.enums;

public enum Roles {
    ADMIN("admin"),
    USER("user");

    public String role;

    Roles(String role){
        this.role = role;
    }

    String getRole(){
        return this.role;
    }



}
