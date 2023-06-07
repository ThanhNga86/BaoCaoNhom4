/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Lenovo
 */
public class Bill {

    private int id;
    private String idOrder;
    private String idMoTo;
    private String title;
    private long price;
    private int quantity;

    public Bill() {
    }

    public Bill(String idOrder, String idMoTo, String title, long price, int quantity) {
        this.idOrder = idOrder;
        this.idMoTo = idMoTo;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdMoTo() {
        return idMoTo;
    }

    public void setIdMoTo(String idMoTo) {
        this.idMoTo = idMoTo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
