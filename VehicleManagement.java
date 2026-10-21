import java.util.ArrayList;
public class VehicleManagement {
    private ArrayList<Vehicle> vehicles = new ArrayList<>();

        public void addVehicle(Vehicle vehicle) {
            vehicles.add(vehicle);
            System.out.println("Vehicle added successfully.");
        }

        public boolean deleteVehicle(String plateNumber) {
            for (int i = 0; i < vehicles.size(); i++) {
                if (vehicles.get(i).getPlateNumber().equalsIgnoreCase(plateNumber)) {
                    vehicles.remove(i);
                    System.out.println("Vehicle deleted successfully.");
                    return true;
                }
            }
            System.out.println("Vehicle not found.");
            return false;
        }

        public Vehicle searchVehicle(String plateNumber) {
            for (Vehicle v : vehicles) {
                if (v.getPlateNumber().equalsIgnoreCase(plateNumber)) {
                    return v;
                }
            }
            return null;
        }

        public void displayAllVehicles() {
            if (vehicles.isEmpty()) {
                System.out.println("No vehicles available.");
                return;
            }
            for (Vehicle v : vehicles) {
                System.out.println("Plate: " + v.getPlateNumber() + " | Brand: " + v.getBrand() + " | Model: " + v.getModel() + " | Daily Price: " + v.getDailyPrice());
            }
        }

        public void displayAvailableVehicles() {
            boolean found = false;
            for (Vehicle v : vehicles) {
                if (v.isAvailable()) {
                    System.out.println("Plate: " + v.getPlateNumber() + " | Brand: " + v.getBrand() + " | Model: " + v.getModel() + " | Daily Price: " + v.getDailyPrice());
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No available vehicles for rent.");
            }
        }
    public ArrayList<Vehicle> getAllVehicles() {
        return vehicles;
    }
    }



