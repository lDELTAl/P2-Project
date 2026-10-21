import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialize managers
        VehicleManagement vehicleMgmt = new VehicleManagement();
        CustomerManager customerMgmt = new CustomerManager();
        RentalManagement rentalMgmt = new RentalManagement();
        // Pass the contract list to reports
        Reports reports = new Reports(rentalMgmt.getContracts());

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        // --- Hardcoded Data for Easy Testing ---
        // Adding Vehicles
        vehicleMgmt.addVehicle(new Car(60, "CAR-101", "Tesla", "Model 3", true, "Electric", true, 5));
        vehicleMgmt.addVehicle(new Car(40, "CAR-102", "Toyota", "Camry", true, "Gasoline", true, 5));
        vehicleMgmt.addVehicle(new Motorcycle(25, "MOTO-201", "Yamaha", "R6", true, 35, true));
        vehicleMgmt.addVehicle(new Truck(100, "TRUCK-301", "Volvo", "FH16", true, 1500, true));

        // Adding Customers
        customerMgmt.addCustomer(new Individual("C001", "John Doe", "Damascus", "0911111111", "LIC-777", LocalDate.of(1995, 5, 20)));
        customerMgmt.addCustomer(new Institution("C002", "Al-Baraka Bank", "Aleppo", "021222222", "REG-5555", 0.15)); // 15% standard discount
        // ----------------------------------------

        System.out.println("==================================================");
        System.out.println("  Welcome to the Vehicle Rental Management System ");
        System.out.println("==================================================");

        while (!exit) {
            System.out.println("\n---------------- MAIN MENU ----------------");
            System.out.println("1. Vehicle Management");
            System.out.println("2. Customer Management");
            System.out.println("3. Rental Operations (Rent / Return)");
            System.out.println("4. Search & Filter Displays");
            System.out.println("5. Statistical Reports");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");

            int mainChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (mainChoice) {
                case 1: // Vehicle Management
                    System.out.println("\n--- Vehicle Management ---");
                    System.out.println("1. Display All Vehicles");
                    System.out.println("2. Display Available Vehicles");
                    System.out.println("3. Search Vehicle by Plate");
                    System.out.println("4. Delete Vehicle");
                    System.out.print("Choice: ");
                    int vChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (vChoice == 1) vehicleMgmt.displayAllVehicles();
                    else if (vChoice == 2) vehicleMgmt.displayAvailableVehicles();
                    else if (vChoice == 3) {
                        System.out.print("Enter plate number: ");
                        String plate = scanner.nextLine();
                        Vehicle v = vehicleMgmt.searchVehicle(plate);
                        System.out.println(v != null ? v : "Vehicle not found.");
                    } else if (vChoice == 4) {
                        System.out.print("Enter plate to delete: ");
                        String plate = scanner.nextLine();
                        vehicleMgmt.deleteVehicle(plate);
                    }
                    break;

                case 2: // Customer Management
                    System.out.println("\n--- Customer Management ---");
                    System.out.println("1. Display All Customers");
                    System.out.println("2. Search Customer by ID");
                    System.out.print("Choice: ");
                    int cChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (cChoice == 1) customerMgmt.displayCustomers();
                    else if (cChoice == 2) {
                        System.out.print("Enter Customer ID: ");
                        String id = scanner.nextLine();
                        Customer c = customerMgmt.searchCustomer(id);
                        System.out.println(c != null ? "Name: " + c.getName() + " | Phone: " + c.getPhoneNumber() : "Customer not found.");
                    }
                    break;

                case 3: // Rental Operations
                    System.out.println("\n--- Rental Operations ---");
                    System.out.println("1. Rent a Vehicle (Create Contract)");
                    System.out.println("2. Return a Vehicle (Close Contract & Invoice)");
                    System.out.print("Choice: ");
                    int rChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (rChoice == 1) {
                        // 1. عرض المركبات المتاحة تلقائياً أولاً ليختار منها العميل
                        System.out.println("\n--- Available Vehicles for Rent ---");
                        vehicleMgmt.displayAvailableVehicles();
                        System.out.println("------------------------------------");

                        System.out.print("Enter New Contract ID: ");
                        String contractId = scanner.nextLine();
                        System.out.print("Enter Vehicle Plate from the list above: ");
                        String plate = scanner.nextLine();

                        // التحقق من وجود المركبة وصلاحيتها للتأجير
                        Vehicle v = vehicleMgmt.searchVehicle(plate);
                        if (v == null || !v.isAvailable()) {
                            System.out.println("Error: Vehicle not found or already rented!");
                            break; // الخروج والعودة للقائمة الرئيسية
                        }

                        System.out.print("Enter Customer ID: ");
                        String custId = scanner.nextLine();
                        Customer c = customerMgmt.searchCustomer(custId);

                        // 2. إذا كان الـ ID جديد وغير مسجل، نقوم بإنشائه تلقائياً هنا
                        if (c == null) {
                            System.out.println("\n[New Customer Detected!] This ID is not registered.");
                            System.out.println("Select Customer Type to Register:");
                            System.out.println("1. Individual (Theperson)");
                            System.out.println("2. Institution");
                            System.out.print("Choice (1-2): ");
                            int typeChoice = scanner.nextInt();
                            scanner.nextLine(); // consume newline

                            System.out.print("Enter Customer Name: ");
                            String name = scanner.nextLine();
                            System.out.print("Enter Address: ");
                            String address = scanner.nextLine();
                            System.out.print("Enter Phone Number: ");
                            String phone = scanner.nextLine();

                            if (typeChoice == 1) {
                                System.out.print("Enter Driving License: ");
                                String license = scanner.nextLine();
                                // يمكنك وضع تاريخ ميلاد افتراضي أو طلبه من المستخدم
                                c = new Individual(custId, name, address, phone, license, LocalDate.of(2000, 1, 1));
                            } else {
                                System.out.print("Enter Registration Number: ");
                                String regNum = scanner.nextLine();
                                System.out.print("Enter Standard Discount Rate (e.g., 0.10 for 10%): ");
                                double discount = scanner.nextDouble();
                                scanner.nextLine(); // consume newline
                                c = new Institution(custId, name, address, phone, regNum, discount);
                            }

                            // إضافة العميل الجديد إلى مدير العملاء لكي يُحفظ في النظام دائماً
                            customerMgmt.addCustomer(c);
                            System.out.println("🎉 Customer " + name + " registered successfully!");
                        }

                        // 3. إكمال عملية التأجير الآن بعد أن أصبح العميل والمركبة جاهزين بالتأكيد
                        System.out.print("Enter Rental Duration (Days): ");
                        int days = scanner.nextInt();
                        scanner.nextLine();

                        RentalContract contract = new RentalContract(contractId, v, c, LocalDate.now(), days);
                        rentalMgmt.rentVehicle(contract);
                    } else if (rChoice == 2) {
                        System.out.println("\n--- Active Contracts Available for Return ---");

                        // 1. فحص أولاً إذا كان هناك عقود نشطة في النظام قبل أي إجراء
                        // نقوم بإنشاء متغير للتأكد إن كان هناك أي عقد غير منتهٍ
                        boolean hasActiveContracts = false;
                        for (RentalContract contract : rentalMgmt.getContracts()) {
                            if (!contract.isCompleted()) {
                                hasActiveContracts = true;
                                break; // وجدنا عقداً واحداً نشطاً على الأقل، يكفي لتغيير الحالة
                            }
                        }

                        // 2. إذا لم يجد أي عقد نشط، يطبع رسالة تنبيه ويخرج فوراً للقائمة الرئيسية
                        if (!hasActiveContracts) {
                            System.out.println("⚠️ Warning: No active contracts found. You cannot perform a return operation!");
                            System.out.println("----------------------------------------------");
                            break; // يكسر الشرط ويعود بالقارئ للمنيو الرئيسي فوراً
                        }

                        // 3. إذا وجد عقوداً نشطة، يكمل الكود طبيعياً ويعرضها ويطلب الـ ID
                        rentalMgmt.displayActiveContracts();
                        System.out.println("----------------------------------------------");

                        System.out.print("Enter Contract ID to Return from the list above: ");
                        String contractId = scanner.nextLine();

                        RentalContract contract = rentalMgmt.findContractById(contractId);
                        if (contract != null) {
                            if (contract.isCompleted()) {
                                System.out.println("Error: This contract is already closed and completed.");
                                break;
                            }

                            System.out.println("\nSimulating Return Date Selection:");
                            System.out.println("1. Return on time (Today)");
                            System.out.println("2. Return with 3 days delay");
                            System.out.print("Choice: ");
                            int delayChoice = scanner.nextInt();
                            scanner.nextLine(); // consume newline

                            LocalDate returnDate = contract.getStartDate().plusDays(contract.getRentalDays());
                            if (delayChoice == 2) {
                                returnDate = returnDate.plusDays(3);
                            }

                            rentalMgmt.returnVehicle(contractId, returnDate);
                        } else {
                            System.out.println("Error: Contract ID not found.");
                        }
                    }

                case 4: // Search & Filter Displays
                    System.out.println("\n--- Search & Filter Displays ---");
                    System.out.println("1. Display Active Contracts");
                    System.out.println("2. Display Completed Contracts");
                    System.out.println("3. Display Contracts by Customer Name");
                    System.out.println("4. Display All Institution Contracts");
                    System.out.println("5. Display Currently Rented Vehicles");
                    System.out.println("6. Display Vehicles with Delay Penalties");
                    System.out.println("7. Display All Vehicles Rented in a Period");
                    System.out.println("8. Display Motorcycles Rented in a Period");
                    System.out.print("Choice: ");
                    int fChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (fChoice == 1) rentalMgmt.displayActiveContracts();
                    else if (fChoice == 2) rentalMgmt.displayCompletedContracts();
                    else if (fChoice == 3) {
                        System.out.print("Enter Customer Name: ");
                        String name = scanner.nextLine();
                        rentalMgmt.displayContractsByCustomerName(name);
                    }
                    else if (fChoice == 4) rentalMgmt.displayContractsForInstitutions();
                    else if (fChoice == 5) rentalMgmt.displayCurrentlyRentedVehicles();
                    else if (fChoice == 6) rentalMgmt.displayVehiclesWithDelayPenalty(); //  صحيح ومطابق لدالتك
                    else if (fChoice == 7 || fChoice == 8) {
                        // Testing period: from 5 days ago until 10 days from now
                        LocalDate startPeriod = LocalDate.now().minusDays(5);
                        LocalDate endPeriod = LocalDate.now().plusDays(10);
                        if (fChoice == 7) {
                            rentalMgmt.displayRentedVehiclesBetweenDates(startPeriod, endPeriod);
                        } else {
                            rentalMgmt.displayMotorcyclesRentedBetweenDates(startPeriod, endPeriod);
                        }
                    }
                    break;

                case 5: // Statistical Reports
                    System.out.println("\n--- Statistical Reports ---");
                    System.out.println("1. Display Total Company Revenue");
                    System.out.println("2. Display Top 3 Most Rented Vehicles");
                    System.out.println("3. Display VIP Customers");
                    System.out.print("Choice: ");
                    int repChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (repChoice == 1) reports.displayTotalRevenue();
                    else if (repChoice == 2) reports.displayMostRentedVehicles();
                    else if (repChoice == 3) reports.displayVIPCustomers(customerMgmt);
                    break;

                case 6: // Exit
                    exit = true;
                    System.out.println("\nThank you for using our system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please select from 1 to 6.");
            }
        }
        scanner.close();
    }
}