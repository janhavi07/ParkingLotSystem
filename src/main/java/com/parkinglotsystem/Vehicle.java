package com.parkinglotsystem;

public class Vehicle {

    private String attendantName;
    private String numberPlate;
    private String vehicleName;
    private String color;

    public Vehicle(String color) {
        this.color = color;
    }

    public Vehicle(String color, String vehicleName, String numberPlate, String attendantName) {
        this.color = color;
        this.vehicleName = vehicleName;
        this.numberPlate = numberPlate;
        this.attendantName = attendantName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "attendantName='" + attendantName + '\'' +
                ", numberPlate='" + numberPlate + '\'' +
                ", vehicleName='" + vehicleName + '\'' +
                ", color='" + color;
    }
}
