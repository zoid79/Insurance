import dao.PayDao;
import service.PayService;

import java.rmi.Naming;

public class Main {
    public static void main(String[] args) {
        try {
            Naming.rebind("payService", new PayService());
            System.out.println("PayServer is ready.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
