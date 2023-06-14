import dao.AccidentDao;
import service.AccidentService;

import java.rmi.Naming;

public class Main {
    public static void main(String[] args) {
        try {
            Naming.rebind("accidentService", new AccidentService());
            System.out.println("AccidentServer is ready.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
