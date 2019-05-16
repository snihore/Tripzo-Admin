package com.example.tripzoadmin;

public class DriverData {
    private String firstName;
    private String lastName;
    private String dob;
    private String gender;
    private String email;
    private String mobile;
    private String emergencyMobile;
    private String permanentAddress;
    private String currentAddress;
    private String vehicleManufacturer;
    private String vehicleType;
    private String vehicleCategory;
    private String vehicleNumber;
    private String activeStatus;

    public DriverData() {
    }

    public DriverData(String firstName, String lastName, String dob, String gender, String email, String mobile, String emergencyMobile, String permanentAddress, String currentAddress, String vehicleManufacturer, String vehicleType, String vehicleCategory, String vehicleNumber, String activeStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
        this.mobile = mobile;
        this.emergencyMobile = emergencyMobile;
        this.permanentAddress = permanentAddress;
        this.currentAddress = currentAddress;
        this.vehicleManufacturer = vehicleManufacturer;
        this.vehicleType = vehicleType;
        this.vehicleCategory = vehicleCategory;
        this.vehicleNumber = vehicleNumber;
        this.activeStatus = activeStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmergencyMobile() {
        return emergencyMobile;
    }

    public void setEmergencyMobile(String emergencyMobile) {
        this.emergencyMobile = emergencyMobile;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getVehicleManufacturer() {
        return vehicleManufacturer;
    }

    public void setVehicleManufacturer(String vehicleManufacturer) {
        this.vehicleManufacturer = vehicleManufacturer;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleCategory() {
        return vehicleCategory;
    }

    public void setVehicleCategory(String vehicleCategory) {
        this.vehicleCategory = vehicleCategory;
    }
    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }
}
