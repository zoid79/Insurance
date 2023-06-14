import dao.SaleDao;
import service.SaleService;

import java.rmi.Naming;

public class Main {
    public static void main(String[] args) {
        try {
            Naming.rebind("saleService", new SaleService());
            System.out.println("SaleServer is ready.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
