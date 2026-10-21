import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RentalContract {
    private String contractId;
    private Vehicle vehicle;
    private Customer customer;
    private LocalDate startDate;
    private int rentalDays;
    private double baseCost;
    private LocalDate actualReturnDate;
    private int delayDays;
    private boolean completed;
    private double finalCost;

    public RentalContract(String contractId, Vehicle vehicle, Customer customer,
                          LocalDate startDate, int rentalDays) {
        this.contractId = contractId;
        this.vehicle = vehicle;
        this.customer = customer;
        this.startDate = startDate;
        this.rentalDays = rentalDays;
        this.actualReturnDate = null;
        this.delayDays = 0;
        this.completed = false;
        this.baseCost = calculateBaseCost();
        this.finalCost = 0;
    }

    private double calculateBaseCost() {
        double dailyRate = vehicle.getDailyPrice();
        double extraCostRate = vehicle.calculateExtraCost();

        double dailyTotal = dailyRate * (1 + extraCostRate);
        return dailyTotal * rentalDays;
    }

    public double calculateDelayPenalty() {
        if (delayDays <= 0) {
            return 0;
        }
        double dailyRate = vehicle.getDailyPrice();
        return dailyRate * delayDays * 0.10;
    }

    public double calculateFinalCost() {
        double cost = baseCost;

        double discountRate = customer.getDiscountRate(this.rentalDays);
        cost = cost * (1 - discountRate);

        cost = cost + calculateDelayPenalty();
        return cost;
    }

    public void returnVehicle(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
        long daysBetween = ChronoUnit.DAYS.between(this.startDate, actualReturnDate);
        this.delayDays = (int) (daysBetween - this.rentalDays);
        if (this.delayDays < 0) this.delayDays = 0;

        // 1. حساب قيمة الخصم (نمرر الأيام لتفعيل ميزة الخصم الابتكاري للمؤسسات)
        double discountAmount = this.baseCost * this.customer.getDiscountRate(this.rentalDays);

        // 2. الفاتورة النهائية = (التكلفة الأساسية - قيمة الخصم) + غرامة التأخير
        this.finalCost = (this.baseCost - discountAmount) + calculateDelayPenalty();

        this.completed = true;
        this.vehicle.setAvailable(true);
    }

    public String getContractId() { return contractId; }
    public void setContractId(String contractId) { this.contractId = contractId; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public int getRentalDays() { return rentalDays; }
    public void setRentalDays(int rentalDays) { this.rentalDays = rentalDays; }

    public double getBaseCost() { return baseCost; }

    public LocalDate getActualReturnDate() { return actualReturnDate; }

    public int getDelayDays() { return delayDays; }

    public boolean isCompleted() { return completed; }

    public double getFinalCost() { return finalCost; }

    @Override
    public String toString() {
        return "Contract{" +
                "ID='" + contractId + '\'' +
                ", Vehicle=" + vehicle.getBrand() + " " + vehicle.getModel() +
                ", Customer=" + customer.getName() +
                ", Days=" + rentalDays +
                ", BaseCost=" + baseCost +
                ", DelayDays=" + delayDays +
                ", FinalCost=" + finalCost +
                '}';
    }
}