package com.example.tripzoadmin;

public class RideData {
    private String pickupLocation;
    private String dropLocation;
    private String pickupTime;
    private String dropTime;
    private String customerName;
    private String customerMobile;
    private int OTPGenerated;
    private int totalFare;
    private String driverName;
    private String driverMobile;
    private String driverVehicleNumber;
    private String rideRatings;

    public RideData() {
    }

    public RideData(String pickupLocation, String dropLocation, String pickupTime, String dropTime, String customerName, String customerMobile, int OTPGenerated, int totalFare, String driverName, String driverMobile, String driverVehicleNumber, String rideRatings) {
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.pickupTime = pickupTime;
        this.dropTime = dropTime;
        this.customerName = customerName;
        this.customerMobile = customerMobile;
        this.OTPGenerated = OTPGenerated;
        this.totalFare = totalFare;
        this.driverName = driverName;
        this.driverMobile = driverMobile;
        this.driverVehicleNumber = driverVehicleNumber;
        this.rideRatings = rideRatings;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getDropTime() {
        return dropTime;
    }

    public void setDropTime(String dropTime) {
        this.dropTime = dropTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public int getOTPGenerated() {
        return OTPGenerated;
    }

    public void setOTPGenerated(int OTPGenerated) {
        this.OTPGenerated = OTPGenerated;
    }

    public int getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(int totalFare) {
        this.totalFare = totalFare;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverMobile() {
        return driverMobile;
    }

    public void setDriverMobile(String driverMobile) {
        this.driverMobile = driverMobile;
    }

    public String getDriverVehicleNumber() {
        return driverVehicleNumber;
    }

    public void setDriverVehicleNumber(String driverVehicleNumber) {
        this.driverVehicleNumber = driverVehicleNumber;
    }

    public String getRideRatings() {
        return rideRatings;
    }

    public void setRideRatings(String rideRatings) {
        this.rideRatings = rideRatings;
    }
}

