package repository;

import entities.Vehicle;

import java.util.stream.Stream;

public interface vehicleManagement {
    Vehicle addVehicle(String vehicleId, String vehicleName, String vehicleType);
    Vehicle findByVehicleID(String vehicleID);
    Vehicle deleteVehicle(Vehicle v);
    Vehicle updateVehicle(Vehicle v);
    Stream<Vehicle> getAllVehicle();
}
