public class Car extends Vehicle {


    private String fuelType;
    private boolean hasAirConditioner;
    private int seatsCount;

    public Car(String fuelType, boolean hasAirConditioner, int seatsCount) {
        this.fuelType = fuelType;
        this.hasAirConditioner = hasAirConditioner;
        this.seatsCount = seatsCount;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public boolean isHasAirConditioner() {
        return hasAirConditioner;
    }

    public void setHasAirConditioner(boolean hasAirConditioner) {
        this.hasAirConditioner = hasAirConditioner;
    }

    public int getSeatsCount() {
        return seatsCount;
    }

    public void setSeatsCount(int seatsCount) {
        this.seatsCount = seatsCount;
    }

    public Car(double dailyPrice, String plateNumber, String brand, String model, boolean isAvailable, String fuelType, boolean hasAirConditioner, int seatsCount) {
        super(dailyPrice, plateNumber, brand, model, isAvailable);
        this.fuelType = fuelType;
        this.hasAirConditioner = hasAirConditioner;
        this.seatsCount = seatsCount;

    }


    @Override
    public double calculateExtraCost() {
        double extra = 0.0;



        if (fuelType.equalsIgnoreCase("Electric") ){
            extra += 0.05;
        }


        if (hasAirConditioner) {
            extra += 0.02;
        }

        return extra;
    }
//    @Override
//    public double calculateExtraCost() {
//        if(fuelType.equalsIgnoreCase("Electric"){
//         0.07;}
//        if (hasAirConditioner){
//            return 0.02;}
//        return 0.0;
//        }
   }

