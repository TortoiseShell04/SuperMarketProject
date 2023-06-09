package com.supermarket.project.supermarketproject;

import java.util.ArrayList;
import java.util.Date;
public class GoldenClients extends RegularClient{
    private Date birthDay;
    private Product favoriteProduct;

    public GoldenClients(String id, String name, int age, String telephoneNum, String street, String town, String homeNum, Date birthDay, Product favoriteProduct, String passwordHash){
        super(id, name, age, telephoneNum, street, town, homeNum,passwordHash);
        this.birthDay = birthDay;
        this.favoriteProduct = favoriteProduct;
        GenreateQR.generateQRCode(toString(),getName(),getId());
        DataStore.store(this);
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public Product getFavoriteProduct() {
        return favoriteProduct;
    }

    @Override
    public void buy(ArrayList<Product> p, Staff s) {
        StringBuilder gc=new StringBuilder();
        Date z= new Date();
        gc.append(z + "\n");
        this.check();
        s.sell(p);
        double currentReceipt = 0;
        boolean favourtieBought = false;
        for (Product y : p) {
               gc.append(y.getName()+"     ");
               gc.append(y.getBuyPrice()+"      ");
               gc.append(y.getQuantity()+"      ");
               gc.append(y.getQuantity()*y.getBuyPrice()+"       ");

            y.setNumInStock(y.getNumInStock() - y.getQuantity());
            setMoneySpent(getMoneySpent()+(int) y.getBuyPrice()*y.getQuantity());
            if (y.getName() == favoriteProduct.getName())
            {
                favourtieBought = true;
            }
            currentReceipt += y.getBuyPrice()*y.getQuantity();
            y.setProductsSold(y.getProductsSold() + y.getQuantity());
        }
        gc.append("staff:"+s.getName()+"      ");
        gc.append( s.getId()+"   ");
        gc.append(s.getPrivatecard().getPOS().getId() + "\n");

        String x="totalprice";
        System.out.println("totalprice"+currentReceipt);
        gc.append( ("totalprice"+currentReceipt));
        if (getMoneySpent() >= 4000) {
            currentReceipt -= currentReceipt * 0.05;
            String f="priceaafterdiscount";
            System.out.println("priceafterdiscount"+currentReceipt);
            gc.append(("priceafterdiscount"+currentReceipt));
            
        }
        if (isBday())
        {
            currentReceipt -= currentReceipt * 0.1;
            String h="priceafterdiscount";
            System.out.println("priceafterdiscount"+currentReceipt);
              gc.append(("priceafterdiscount"+currentReceipt));
            
        }
        if (favourtieBought)
        {
            currentReceipt -= currentReceipt * 0.2;
             String t="priceafterdiscount";
            System.out.println("priceafterdiscount"+currentReceipt);
              gc.append(("priceafterdiscount"+currentReceipt));
            
            
        }
        currentReceipt -= currentReceipt * 0.02;
        String t="priceafterdiscount";
            System.out.println("priceafterdiscount"+currentReceipt);
              gc.append(("priceafterdiscount"+currentReceipt));
              gc.append("thank you for shopping with us");
              invoice.x(gc.toString(), name, id);
            
    }
    private boolean isBday()
    {
        Date now = new Date();
        return now.getMonth() == birthDay.getMonth() && now.getDay() == birthDay.getDay();
    }

    @Override
    public String toString() {
        return super.toString() + "GoldenClients{" +
                "birthDay=" + birthDay +
                ", favoriteProduct=" + favoriteProduct +
                '}';
    }
}
