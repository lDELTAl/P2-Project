public abstract class Vehicle {
    private String plateNumber;
    private String brand;
    private String model;
    private double dailyPrice;
    private boolean isAvailable;

    public Vehicle() {}

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Vehicle(double dailyPrice, String plateNumber, String brand, String model, boolean isAvailable) {
        this.dailyPrice = dailyPrice;
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.model = model;
        this.isAvailable = isAvailable;
    }
    public abstract double calculateExtraCost();

}
