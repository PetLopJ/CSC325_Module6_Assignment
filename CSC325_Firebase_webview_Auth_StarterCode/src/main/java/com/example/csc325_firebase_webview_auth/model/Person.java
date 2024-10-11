/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.csc325_firebase_webview_auth.model;

/**
 *
 * @author MoaathAlrajab
 *
 *
 *
 *
 * Model:
 *
 *
 */
public class Person {
    private String fname;
    private String lname;
    private String dep;
    private String major;
    private String email;
    private String image;

    public Person(String fname, String lname, String dep, String major, String email, String image) {
        this.fname = fname;
        this.lname = lname;
        this.dep = dep;
        this.major = major;
        this.email = email;
        this.image = image;
    }

    public String getFname() {return fname;}

    public void setFname(String fname) {this.fname = fname;}

    public String getLname() {return lname;}

    public void setLname(String lname) {this.lname = lname;}

    public String getDep() {return dep;}

    public void setDep(String dep) {this.dep = dep;}

    public String getMajor() {return major;}

    public void setMajor(String major) {this.major = major;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getImage() {return image;}

    public void setImage(String image) {this.image = image;}
}
