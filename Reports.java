import java.util.ArrayList;
import java.util.List;

public class Reports {
    private List<RentalContract> contracts;

    public Reports(List<RentalContract> contracts) {
        this.contracts = contracts;
    }
    // ======================== 7. تقارير إحصائية ========================

    public double getTotalRevenue() {
        double total = 0;
        for (RentalContract contract : contracts) {
            if (contract.isCompleted()) {
                total += contract.getFinalCost();
            }
        }
        return total;
    }

    public void displayTotalRevenue() {
        System.out.println("\n========== TOTAL COMPANY REVENUE ==========");
        System.out.println("Total revenue from completed contracts: " + getTotalRevenue() + "$");
    }

    public void displayVIPCustomers(CustomerManager customerMgmt) {
        System.out.println("\n========== VIP CUSTOMERS (5+ completed contracts) ==========");
        boolean found = false;

        for (Customer customer : customerMgmt.getAllCustomers()) {
            int completedCount = 0;

            // حسب عدد العقود المنتهية لهذا العميل
            for (RentalContract contract : contracts) {
                if (contract.isCompleted() &&
                        contract.getCustomer().getId().equals(customer.getId())) {
                    completedCount++;
                }
            }

            if (completedCount >= 5) {
                System.out.println(customer.getName() + " (ID: " + customer.getId() +
                        ") - Completed contracts: " + completedCount);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No VIP customers yet.");
        }
    }
    public void displayMostRentedVehicles() {
        System.out.println("\n========== TOP 3 MOST RENTED VEHICLES ==========");

        List<Vehicle> vehicles = new ArrayList<>();
        List<Integer> rentCounts = new ArrayList<>();

        // عد مرات تأجير كل مركبة
        for (RentalContract contract : contracts) {
            Vehicle v = contract.getVehicle();
            int index = -1;
            for (int i = 0; i < vehicles.size(); i++) {
                if (vehicles.get(i).getPlateNumber().equals(v.getPlateNumber())) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                rentCounts.set(index, rentCounts.get(index) + 1);
            } else {
                vehicles.add(v);
                rentCounts.add(1);
            }
        }

        // ترتيب تنازلي (فقاعة بسيطة لأول 3)
        for (int i = 0; i < rentCounts.size() - 1; i++) {
            for (int j = i + 1; j < rentCounts.size(); j++) {
                if (rentCounts.get(i) < rentCounts.get(j)) {
                    int tempC = rentCounts.get(i);
                    rentCounts.set(i, rentCounts.get(j));
                    rentCounts.set(j, tempC);

                    Vehicle tempV = vehicles.get(i);
                    vehicles.set(i, vehicles.get(j));
                    vehicles.set(j, tempV);
                }
            }
        }

        // عرض أول 3
        for (int i = 0; i < Math.min(3, vehicles.size()); i++) {
            System.out.println((i+1) + ". " + vehicles.get(i).getBrand() + " " +
                    vehicles.get(i).getModel() + " - " + rentCounts.get(i) + " times");
        }
    }
}
