/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.file;

import entities.Member;
import entities.Vehicle;
import repository.vehicleManagement;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author USER
 */
public class VehicleRepositoryFile implements vehicleManagement {

    private Map<String, Vehicle> vehicles = new HashMap<>();
    private String PATH = "src\\main\\java\\";
    String filename = PATH + "Vehical.dat";

    public VehicleRepositoryFile(){
        File file = new File(filename);
        if (file.exists()){
            try (FileInputStream fi = new FileInputStream(filename);
                 BufferedInputStream bi = new BufferedInputStream(fi);
                 ObjectInputStream oi = new ObjectInputStream(bi)){
                 vehicles = (Map<String, Vehicle>) oi.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else {
            vehicles = new HashMap<>();
        }
    }
    public void writeToFile(){
        try (FileOutputStream fo = new FileOutputStream(filename);
             BufferedOutputStream bo = new BufferedOutputStream(fo);
             ObjectOutputStream oo = new ObjectOutputStream(bo)){
            oo.writeObject(vehicles);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Vehicle addVehicle(String vehicleId, String vehicleName, String vehicleType) {
        Vehicle v = new Vehicle(vehicleId,vehicleName,vehicleType);
        vehicles.put(vehicleId,new Vehicle(vehicleId, vehicleName, vehicleType));
        writeToFile();
        return v;
    }
    @Override
    public Vehicle deleteVehicle(Vehicle v) {
        try {
            vehicles.remove(v.getVehicleId(),v);
            writeToFile();
        }catch (Exception e){
            return null;
        }
        return v;
    }

    @Override
    public Vehicle findByVehicleID(String vehicleID) {
        return vehicles.get(vehicleID);
    }



    @Override
    public Vehicle updateVehicle(Vehicle v) {
        vehicles.replace(v.getVehicleId(), v);
        writeToFile();
        return v;
    }

    @Override
    public Stream<Vehicle> getAllVehicle() {
        return vehicles.values().stream();
    }
    
}
