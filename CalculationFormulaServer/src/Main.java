import dao.CalculationFormulaDao;
import service.CalculationFormulaService;

import java.rmi.Naming;

public class Main {
    public static void main(String[] args) {
        try {
            Naming.rebind("calculationFormulaService", new CalculationFormulaService());
            System.out.println("CalculationFormulaServer is ready.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
