import dao.EmployeeDao;
import service.EmployeeService;

import java.rmi.Naming;

public class Main {
    public static void main(String[] args) {
        try {
            Naming.rebind("employeeService", new EmployeeService());
            System.out.println("EmployeeServer is ready.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
