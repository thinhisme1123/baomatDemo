package com.example.finalmobileproject.Domain;


import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    private String userId;
    private double totalAmount;
    private ArrayList<Foods> food;
    private long orderId;
    private String dateOrder;
    private String userName;
    private String address;
    private String status;
    private String phoneNumber;
    private String timeOrder;
    private String noteOrder;

    public Order(String userId, double totalAmount, ArrayList<Foods> food, long orderId, String dateOrder, String userName, String address, String status,String phoneNumber, String timeOrder, String noteOrder) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.food = food;
        this.orderId = orderId;
        this.dateOrder = dateOrder;
        this.userName = userName;
        this.address = address;
        this.status = status;
        this.phoneNumber = phoneNumber;
        this.timeOrder = timeOrder;
        this.noteOrder = noteOrder;
    }

    public Order() {

    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ArrayList<Foods> getFood() {
        return food;
    }

    public void setFood(ArrayList<Foods> food) {
        this.food = food;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }

    public String getNoteOrder() {
        return noteOrder;
    }

    public void setNoteOrder(String noteOrder) {
        this.noteOrder = noteOrder;
    }
}
