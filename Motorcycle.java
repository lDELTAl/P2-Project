public class Motorcycle extends Vehicle {
    private int engineCapacity;
    private boolean hasSidecar;

    public Motorcycle(int engineCapacity, boolean hasSidecar) {
        this.engineCapacity = engineCapacity;
        this.hasSidecar = hasSidecar;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public boolean isHasSidecar() {
        return hasSidecar;
    }

    public void setHasSidecar(boolean hasSidecar) {
        this.hasSidecar = hasSidecar;
    }

    public Motorcycle(double dailyPrice, String plateNumber, String brand, String model, boolean isAvailable, int engineCapacity, boolean hasSidecar) {
        super(dailyPrice, plateNumber, brand, model, isAvailable);
        this.engineCapacity = engineCapacity;
        this.hasSidecar = hasSidecar;

    }
    @Override
    public double calculateExtraCost() {
        double extra = 0.0;



        if (engineCapacity>=30 ){
            extra += 0.02;
        }


        if (hasSidecar) {
            extra += 0.03;
        }

        return extra;
    }
}
