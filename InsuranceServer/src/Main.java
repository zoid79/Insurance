import dao.InsuranceDao;
import service.InsuranceService;

import java.rmi.Naming;

public class Main {
    public static void main(String[] args) {
        try {
            Naming.rebind("insuranceService", new InsuranceService());
            System.out.println("InsuranceServer is ready.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
