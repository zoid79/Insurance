import dao.CustomerDao;
import service.CustomerService;

import java.rmi.Naming;

public class Main {
    public static void main(String[] args) {
        try {
            Naming.rebind("customerService", new CustomerService());
            System.out.println("CustomerServer is ready.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
