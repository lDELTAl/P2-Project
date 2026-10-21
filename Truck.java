public class Truck extends Vehicle {
    private double cargoCapacity;
    private boolean isRefrigerated;

    public Truck(double cargoCapacity, boolean isRefrigerated) {
        this.cargoCapacity = cargoCapacity;
        this.isRefrigerated = isRefrigerated;
    }

    public double getCargoCapacity() {
        return cargoCapacity;
    }

    public void setCargoCapacity(double cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    public boolean isRefrigerated() {
        return isRefrigerated;
    }

    public void setRefrigerated(boolean refrigerated) {
        isRefrigerated = refrigerated;
    }

    public Truck(double dailyPrice, String plateNumber, String brand, String model, boolean isAvailable, double cargoCapacity, boolean isRefrigerated) {
        super(dailyPrice, plateNumber, brand, model, isAvailable);
        this.cargoCapacity = cargoCapacity;
        this.isRefrigerated = isRefrigerated;

    }
    @Override
    public double calculateExtraCost() {
        double extra = 0.0;



        if (cargoCapacity>=1000 ){
            extra += 0.015;
        }


        if (isRefrigerated) {
            extra += 0.05;
        }

        return extra;
    }
}