package br.senai.sc.enums;

public enum Roles {
    ADMIN("admin"),
    USER("user"),
    FUNC("func");

    public String role;

    Roles(String role){
        this.role = role;
    }

    String getRole(){
        return this.role;
    }



}
