package asm04.model;

import asm01.Asm01;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID =1L;

    private String name;
    private String customerId;// CCCD

    public User(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        Asm01 asm01 =new Asm01();
        //call function check the validation of customerId(CCCD) from asm01
        if(asm01.isValidIdNum(customerId))
            this.customerId = customerId;
        else System.out.println("Customer Id: "+customerId+" khong hop le.");
    }
}
