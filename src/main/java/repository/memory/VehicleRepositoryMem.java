/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.memory;

import entities.Vehicle;
import repository.vehicleManagement;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author USER
 */
public class VehicleRepositoryMem implements vehicleManagement {
    private Map<String,Vehicle> vehicles = new HashMap<>();

    @Override
    public Vehicle addVehicle(String vehicleId, String vehicleName, String vehicleType) {
        return vehicles.put(vehicleId,new Vehicle(vehicleId, vehicleName, vehicleType));
    }

    @Override
    public Vehicle findByVehicleID(String vehicleID) {
        return vehicles.get(vehicleID);
    }

    @Override
    public Vehicle deleteVehicle(Vehicle v) {
        try {
            vehicles.remove(v.getVehicleId(),v);
        }catch (Exception e){
            return null;
        }
        return v;
    }

    @Override
    public Vehicle updateVehicle(Vehicle v) {
        return vehicles.replace(v.getVehicleId(), v);
    }

    @Override
    public Stream<Vehicle> getAllVehicle() {
        return vehicles.values().stream();
    }
}
