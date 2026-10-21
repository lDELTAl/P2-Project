import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class RentalManagement {
    private List<RentalContract> contracts;

    // Constructor
    public RentalManagement() {
        contracts = new ArrayList<>();
    }

    // ======================== 1. العمليات الأساسية ========================

    public void rentVehicle(RentalContract contract) {
        if (contract.getVehicle().isAvailable()) {
            contracts.add(contract);
            contract.getVehicle().setAvailable(false);
            System.out.println("Vehicle rented successfully. Contract ID: " + contract.getContractId());
        } else {
            System.out.println("Vehicle is not available");
        }
    }

    public void returnVehicle(String contractId, LocalDate actualReturnDate) {
        RentalContract contract = findContractById(contractId);
        if (contract != null && !contract.isCompleted()) {
            contract.returnVehicle(actualReturnDate);
            contract.getVehicle().setAvailable(true);
            displayInvoice(contract);
        } else if (contract == null) {
            System.out.println("Contract not found");
        } else {
            System.out.println("This contract is already completed");
        }
    }

    // ======================== 2. عرض العقود ========================

    public void displayActiveContracts() {
        System.out.println("\n========== ACTIVE CONTRACTS ==========");
        boolean found = false;
        for (RentalContract contract : contracts) {
            if (!contract.isCompleted()) {
                System.out.println(contract);
                found = true;
            }
        }
        if (!found) System.out.println("No active contracts");
    }

    public void displayCompletedContracts() {
        System.out.println("\n========== COMPLETED CONTRACTS ==========");
        boolean found = false;
        for (RentalContract contract : contracts) {
            if (contract.isCompleted()) {
                System.out.println(contract);
                System.out.println("Final cost: " + contract.getFinalCost() + "$");
                found = true;
            }
        }
        if (!found) System.out.println("No completed contracts");
    }

    public void displayAllContracts() {
        System.out.println("\n========== ALL CONTRACTS ==========");
        if (contracts.isEmpty()) {
            System.out.println("No contracts");
            return;
        }
        for (RentalContract contract : contracts) {
            System.out.println(contract);
            System.out.println("Status: " + (contract.isCompleted() ? "Completed" : "Active"));
            System.out.println("------------------------");
        }
    }

    public void displayContractsByCustomerName(String customerName) {
        System.out.println("\n========== CONTRACTS FOR CUSTOMER: " + customerName + " ==========");
        boolean found = false;
        for (RentalContract contract : contracts) {
            if (contract.getCustomer().getName().equalsIgnoreCase(customerName)) {
                System.out.println(contract);
                found = true;
            }
        }
        if (!found) System.out.println("No contracts found for this customer.");
    }

    public void displayContractsForInstitutions() {
        System.out.println("\n========== INSTITUTION CUSTOMER CONTRACTS ==========");
        boolean found = false;
        for (RentalContract contract : contracts) {
            if (contract.getCustomer() instanceof Institution) {
                System.out.println(contract);
                found = true;
            }
        }
        if (!found) System.out.println("No contracts found for institution customers.");
    }

    public void displayInvoice(RentalContract contract) {
        System.out.println("\n========== FINAL INVOICE ==========");
        System.out.println("Contract ID: " + contract.getContractId());
        System.out.println("Customer: " + contract.getCustomer().getName());
        System.out.println("Vehicle: " + contract.getVehicle().getBrand() + " " + contract.getVehicle().getModel());
        System.out.println("Rental days: " + contract.getRentalDays());
        System.out.println("Base cost: " + contract.getBaseCost() + " SYP");
        if (contract.getDelayDays() > 0) {
            System.out.println("Delay days: " + contract.getDelayDays());
            System.out.println("Penalty: " + contract.calculateDelayPenalty() + " SYP");
        }
        System.out.println("Discount: " + (contract.getCustomer().getDiscountRate(contract.getRentalDays()) * 100) + "%");
        System.out.println("-----------------------------------");
        System.out.println("FINAL COST: " + contract.getFinalCost() + " SYP");
        System.out.println("===================================\n");
    }

    // ======================== 3. عرض المركبات المؤجرة حالياً ========================

    public void displayCurrentlyRentedVehicles() {
        System.out.println("\n========== CURRENTLY RENTED VEHICLES ==========");
        boolean found = false;
        for (RentalContract contract : contracts) {
            if (!contract.isCompleted()) {
                System.out.println(contract.getVehicle());
                found = true;
            }
        }
        if (!found) System.out.println("No vehicles are currently rented.");
    }

    // ======================== 4. عرض المركبات التي عليها غرامات ========================

    public void displayVehiclesWithDelayPenalty() {
        System.out.println("\n========== VEHICLES WITH DELAY PENALTY ==========");
        boolean found = false;
        for (RentalContract contract : contracts) {
            if (contract.isCompleted() && contract.getDelayDays() > 0) {
                System.out.println(contract.getVehicle());
                System.out.println("  Delay days: " + contract.getDelayDays());
                found = true;
            }
        }
        if (!found) System.out.println("No vehicles with delay penalties.");
    }

    // ======================== 5. عرض المركبات ضمن فترة معينة ========================

    public void displayRentedVehiclesBetweenDates(LocalDate start, LocalDate end) {
        System.out.println("\n========== VEHICLES RENTED BETWEEN " + start + " AND " + end + " ==========");
        boolean found = false;
        for (RentalContract contract : contracts) {
            LocalDate contractStart = contract.getStartDate();
            if ((contractStart.isEqual(start) || contractStart.isAfter(start)) &&
                    (contractStart.isEqual(end) || contractStart.isBefore(end))) {
                System.out.println(contract.getVehicle());
                found = true;
            }
        }
        if (!found) System.out.println("No vehicles rented in this period.");
    }

    public void displayMotorcyclesRentedBetweenDates(LocalDate start, LocalDate end) {
        System.out.println("\n========== MOTORCYCLES RENTED BETWEEN " + start + " AND " + end + " ==========");
        boolean found = false;
        for (RentalContract contract : contracts) {
            LocalDate contractStart = contract.getStartDate();
            if ((contractStart.isEqual(start) || contractStart.isAfter(start)) &&
                    (contractStart.isEqual(end) || contractStart.isBefore(end)) &&
                    (contract.getVehicle() instanceof Motorcycle)) {
                System.out.println(contract.getVehicle());
                found = true;
            }
        }
        if (!found) System.out.println("No motorcycles rented in this period.");
    }

    // ======================== 6. العملاء الذين استأجروا مركبة محددة ========================

    public void displayCustomersWhoRentedVehicle(String licensePlate) {
        System.out.println("\n========== CUSTOMERS WHO RENTED VEHICLE: " + licensePlate + " ==========");
        boolean found = false;
        for (RentalContract contract : contracts) {
            if (contract.getVehicle().getPlateNumber().equalsIgnoreCase(licensePlate)) {
                System.out.println(contract.getCustomer());
                found = true;
            }
        }
        if (!found) System.out.println("No customers rented this vehicle.");
    }



    // ======================== 8. دوال مساعدة ========================

    public RentalContract findContractById(String contractId) {
        for (RentalContract contract : contracts) {
            if (contract.getContractId().equals(contractId)) {
                return contract;
            }
        }
        return null;
    }

    public boolean deleteContract(String contractId) {
        RentalContract contract = findContractById(contractId);
        if (contract != null) {
            if (!contract.isCompleted()) {
                contract.getVehicle().setAvailable(true);
            }
            contracts.remove(contract);
            System.out.println("Contract deleted: " + contractId);
            return true;
        }
        System.out.println("Contract not found");
        return false;
    }



    // ======================== 9. Getters ========================

    public List<RentalContract> getContracts() {
        return contracts;
    }

    public int getActiveContractsCount() {
        int count = 0;
        for (RentalContract contract : contracts) {
            if (!contract.isCompleted()) count++;
        }
        return count;
    }

    public int getCompletedContractsCount() {
        int count = 0;
        for (RentalContract contract : contracts) {
            if (contract.isCompleted()) count++;
        }
        return count;
    }
}