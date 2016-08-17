package com.dto;

import java.io.Serializable;

/**
 * Created by vaibhav on 14/8/16.
 */
public class Login implements Serializable {
    String uniqueId;
    String passWord;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "Login{" +
                "uniqueId='" + uniqueId + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
