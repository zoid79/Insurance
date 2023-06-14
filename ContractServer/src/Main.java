import dao.ContractDao;
import service.ContractService;

import java.rmi.Naming;

public class Main {
    public static void main(String[] args) {
        try {
            Naming.rebind("contractService", new ContractService());
            System.out.println("ContractServer is ready.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
