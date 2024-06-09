/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class Vehicle implements Serializable {
    private final String vehicleId;
    private String vehicleName;
    private String vehicleType;


    public Vehicle(String vehicleId, String vehicleName, String vehicleType) {
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.vehicleType = vehicleType;
    }



    public String getVehicleId() {
        return vehicleId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }



    @Override
    public String toString() {
        return "Vehicle{" + "vehicleId=" + vehicleId + ", vehicleName=" + vehicleName + ", vehicleType=" + vehicleType + '}';
    }
    
    
    
}
