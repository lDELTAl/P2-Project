import java.util.ArrayList;

public class CustomerManager {
    private ArrayList<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer customer) {
        customers.add(customer);
        System.out.println("Customer added successfully.");
    }

    public Customer searchCustomer(String id) {
        for (Customer c : customers) {
            if (c.getId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    public void displayCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers registered.");
            return;
        }
        for (Customer c : customers) {
            System.out.println("ID: " + c.getId() + " | Name: " + c.getName() + " | Phone: " + c.getPhoneNumber());
        }
    }

    public boolean updateCustomerInfo(String id, String newName, String newAddress, String newPhone) {
        Customer c = searchCustomer(id);
        if (c != null) {
            c.setName(newName);
            c.setAddress(newAddress);
            c.setPhoneNumber(newPhone);
            System.out.println("Customer information updated successfully.");
            return true;
        }
        System.out.println("Customer not found.");
        return false;
    }

    public ArrayList<Customer> getAllCustomers() {
        return customers;
    }

}