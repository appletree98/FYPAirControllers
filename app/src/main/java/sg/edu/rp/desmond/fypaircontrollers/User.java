package sg.edu.rp.desmond.fypaircontrollers;

import java.io.Serializable;

/**
 * Created by 15017452 on 15/6/2017.
 */

public class User{

    public String role;
    public String name;
    public String uid;

    public User(){

    }

    public User(String role, String name, String uid) {
        this.role = role;
        this.name = name;
        this.uid = uid;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
