import dao.CompensationDao;
import service.CompensateService;

import java.rmi.Naming;

public class Main {
    public static void main(String[] args) {
        try {
            Naming.rebind("compensateService", new CompensateService());
            System.out.println("CompensateServer is ready.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
