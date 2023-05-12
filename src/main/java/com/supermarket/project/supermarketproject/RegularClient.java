/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supermarket.project.supermarketproject;

import java.util.Arrays;
import java.util.Date;


public class RegularClient extends Person{
    
    private int age;
    private String[] address;
    private Date initialDate;
    private Date nextCheck;
    private int moneySpent;
    private boolean isGolden;
    private int goldenClientIndex;
    private boolean stored;

    public boolean isStored() {
        return stored;
    }

    public void setStored(boolean stored) {
        this.stored = stored;
    }

    public RegularClient(String id, String name, int age, String phoneNum, String street , String town, String homeNum){
        this.address = new String[3];
        
        this.id = id;
        this.name = name;
        this.age = age;
        this.phoneNum = phoneNum;
        this.address[0] = street;
        this.address[1] = town;
        this.address[2] = homeNum;
        this.initialDate = new Date();
        this.nextCheck = new Date();
        this.moneySpent = 0;
        this.nextCheck.setMonth(this.initialDate.getMonth() + 1);
        this.isGolden = false;
        if (!isGolden) {
            GenreateQR.generateQRCode(toString(), name, id);
            DataStore.store(this);
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String getPhoneNum() {
        return phoneNum;
    }

    @Override
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }
    public Date getInitialDate() {
        return initialDate;
    }
    @Override
    public void check(){
       Date now = new Date();
       if(now.getMonth() >= this.nextCheck.getMonth()&& now.getDay() >= this.nextCheck.getDay()){
          this.moneySpent = 0;
          this.nextCheck= now;
          this.nextCheck.setMonth(now.getMonth()+1);
          }
       
        }

    public int getMoneySpent() {
        return moneySpent;
    }

    public void setMoneySpent(int moneySpent) {
        this.moneySpent = moneySpent;
    }

    @Override
    public String toString() {
        return "RegularClient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", telephoneNum='" + phoneNum + '\'' +
                ", address=" + Arrays.toString(address) +
                '}';
    }

    public void buy(Product[] p, Staff s){
        if (!isGolden) {
            isGoldenClients(this);
            check();
            s.sell(p);
            double currentReceipt = 0;
            for (Product y : p) {
                y.setNumInStock(y.getNumInStock() - 1);
                moneySpent += (int) y.getBuyPrice();
                currentReceipt += y.getBuyPrice();
                y.setProductsSold(y.getProductsSold() + 1);
            }
            if (moneySpent >= 4000) {
                currentReceipt -= currentReceipt * 0.05;
            }
        }
        else{
            ApplicationMain.goldenClients.get(goldenClientIndex).buy(p,s);
        }
        }
    public void isGoldenClients(RegularClient c) {
        Date currentDate = new Date();
        if (currentDate.getYear() - getInitialDate().getYear() >= 5){
            c = new GoldenClients(this.id,this.name, this.age,this.phoneNum, this.address[0], this.address[1],this.address[2],null,null);
}
       
    }
   
}
