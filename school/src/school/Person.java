//Name: Huang Jiaming
//NSID: jih211
//StuID: 11207964


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package school;

import java.io.Serializable;

/**
 *
 * @author jiajia
 */
public class Person implements Serializable{
//    String name_first;
//    String name_middle;
//    String name_last;
    private String[] name;
    private String[] phoneNumHome;
    private String[] phoneNumWork;
    private String address;
    private String email;    
    
    Person(String[] name, String[] phoneNumHome, String[] phoneNumWork, String address, String email) {
        this.name = name;
        this.phoneNumHome = phoneNumHome;
        this.phoneNumWork = phoneNumWork;
        this.address = address;
        this.email = email;
    }
    @Override
    public String toString(){
        if (name[1].equals("")){
            return name[0]+" "+name[2]+"---[ Person ]";
        }
        return name[0]+" "+name[1]+" "+name[2]+"---[ Person ]";
    }
    
    public String[] getName() {
        return name;
    }

    public String[] getPhoneNumHome() {
        return phoneNumHome;
    }

    public String[] getPhoneNumWork() {
        return phoneNumWork;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public void setPhoneNumHome(String[] phoneNumHome) {
        this.phoneNumHome = phoneNumHome;
    }

    public void setPhoneNumWork(String[] phoneNumWork) {
        this.phoneNumWork = phoneNumWork;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
