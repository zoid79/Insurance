package service;

import service.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ServiceContainer {
    private AccidentServiceIF accidentService;
    private CalculationFormulaServiceIF calculationFormulaService;
    private CompensateServiceIF compensateService;
    private ContractServiceIF contractService;
    private CustomerServiceIF customerService;
    private EmployeeServiceIF employeeService;
    private InsuranceServiceIF insuranceService;
    private PayServiceIF payService;
    private SaleServiceIF saleService;

    public ServiceContainer()  {
        connect();
    }

    public void connect(){
        try {this.accidentService = (AccidentServiceIF) Naming.lookup("accidentService");}
        catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("! 사고 서버가 연결되지 않았습니다. 서비스 이용이 일부 제한될 수 있습니다.");
        }
        try {this.compensateService = (CompensateServiceIF) Naming.lookup("compensateService");}
        catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("! 보상 서버가 연결되지 않았습니다. 서비스 이용이 일부 제한될 수 있습니다.");
        }
        try {this.customerService = (CustomerServiceIF) Naming.lookup("customerService");}
        catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("! 고객 서버가 연결되지 않았습니다. 서비스 이용이 일부 제한될 수 있습니다.");
        }
        try {this.contractService = (ContractServiceIF) Naming.lookup("contractService");}
        catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("! 계약 서버가 연결되지 않았습니다. 서비스 이용이 일부 제한될 수 있습니다.");
        }
        try {this.payService = (PayServiceIF) Naming.lookup("payService");}
        catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("! 납부 서버가 연결되지 않았습니다. 서비스 이용이 일부 제한될 수 있습니다.");
        }
        try {this.calculationFormulaService = (CalculationFormulaServiceIF) Naming.lookup("calculationFormulaService");}
        catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("! 계산식 서버가 연결되지 않았습니다. 서비스 이용이 일부 제한될 수 있습니다.");
        }
        try {this.insuranceService = (InsuranceServiceIF) Naming.lookup("insuranceService");}
        catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("! 보험 서버가 연결되지 않았습니다. 서비스 이용이 일부 제한될 수 있습니다.");
        }
        try {this.employeeService = (EmployeeServiceIF) Naming.lookup("employeeService");}
        catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("! 직원 서버가 연결되지 않았습니다. 서비스 이용이 일부 제한될 수 있습니다.");
        }
        try {this.saleService = (SaleServiceIF) Naming.lookup("saleService");}
        catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("! 영업 서버가 연결되지 않았습니다. 서비스 이용이 일부 제한될 수 있습니다.");
        }

        try {this.compensateService.setAccidentService(this.accidentService);}
        catch (RemoteException | NullPointerException e) {}
        try {this.contractService.setCustomerService(this.customerService);}
        catch (RemoteException | NullPointerException e) {}
        try {this.payService.setContractService(this.contractService);}
        catch (RemoteException | NullPointerException e) {}
    }

    public AccidentServiceIF getAccidentService() {return accidentService;}

    public CalculationFormulaServiceIF getCalculationFormulaService() {return calculationFormulaService;}

    public CompensateServiceIF getCompensateService() {return compensateService;}

    public ContractServiceIF getContractService() {
        return contractService;
    }

    public CustomerServiceIF getCustomerService() {
        return customerService;
    }

    public EmployeeServiceIF getEmployeeService() {
        return employeeService;
    }

    public InsuranceServiceIF getInsuranceService() {
        return insuranceService;
    }

    public PayServiceIF getPayService() {
        return payService;
    }
    
    public SaleServiceIF getSaleService() {
        return saleService;
    }

}
