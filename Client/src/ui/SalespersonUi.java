package ui;

import domain.Contract;
import domain.Customer;
import domain.Insurance;
import enumeration.contract.ContractStatus;
import enumeration.insurance.InsuranceStatus;
import enumeration.insurance.InsuranceType;
import exception.EmptyListException;
import exception.NoDataException;
import exception.TimeDelayException;
import service.ServiceContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class SalespersonUi {
    private final String employeeId;
    private final ServiceContainer serviceContainer;
    private final BufferedReader userInput;

    public SalespersonUi(String employeeId, ServiceContainer serviceContainer, BufferedReader userInput) {
        this.employeeId = employeeId;
        this.serviceContainer = serviceContainer;
        this.userInput = userInput;
    }

    public void printMenu() throws IOException {
        while(true) {
            System.out.println("******************** 영업사원 메인 메뉴 *********************");
            System.out.println("1. 계약체결");
            System.out.println("2. 보험제안");
            System.out.println("0. 로그아웃");
            System.out.println("x. 종료");
            switch(userInput.readLine().trim()) {
                case "1" : printConcludeMenu(); break;
                case "2" : printOfferInsuranceMenu(); break;
                case "0" : return;
                case "x" : System.exit(0);
                default : System.out.println("! 잘못된 입력입니다.");
            }
        }
    }
    public void printConcludeMenu() throws IOException {
        while (true) {
            ArrayList<Contract> contractList = null;
            try {contractList = serviceContainer.getContractService().getContractList(ContractStatus.Underwrite);}
            catch (EmptyListException | TimeDelayException e) {System.out.println(e.getMessage()); return;}
            catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
            System.out.println("******************** 계약체결 메뉴 *********************");
            System.out.println("체결할 계약의 아이디를 입력하세요. 뒤로가려면 0을 입력하세요.");
            System.out.println("아이디\t고객아이디\t보험아이디");
            for(Contract contract : contractList) {
                System.out.println(contract.getId()
                        + "\t" + contract.getCustomerId()
                        + "\t" + contract.getInsuranceId());
            }
            System.out.print("계약 아이디 : ");
            int id;
            try {id = Integer.parseInt(userInput.readLine().trim());}
            catch (NumberFormatException e) {System.out.println("! 잘못된 입력입니다."); continue;}
            if(id==0) return;
            Contract selectedContract = null;
            for(Contract contract : contractList) {if(contract.getId()==id) selectedContract = contract;}
            if(selectedContract==null){System.out.println("! 잘못된 입력입니다."); continue;}
            printContractDetail(selectedContract); return;
        }
    }
    private void printContractDetail(Contract contract) throws IOException {
        while (true) {
            System.out.println("******************** 계약 상세내용 *********************");
            System.out.println("계약 상세내역을 확인하고 버튼을 선택하세요.");
            Customer customer = null;
            Insurance insurance = null;
            try {
                customer = serviceContainer.getCustomerService().getCustomer(contract.getCustomerId());
                insurance = serviceContainer.getInsuranceService().getInsurance(contract.getInsuranceId());
            } catch (NoDataException e) {
                System.out.println(e.getMessage()); return;
            } catch (RemoteException | NullPointerException e) {
                System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;
            }

            System.out.println("고객 아이디 : " + customer.getId()
                    + "\n고객 이름 : " + customer.getName()
                    + "\n보험 아이디 : " + insurance.getId()
                    + "\n보험 이름 : " + insurance.getName()
                    + "\n보험 유형 : " + insurance.getType().getName());

            System.out.println("\n[버튼]");
            System.out.println("1. 계약체결");
            System.out.println("0. 뒤로가기");
            String choice = userInput.readLine().trim();
            if (choice.equals("0")) {
                return;
            } else if(choice.equals("1")){
                boolean isSuccess = false;
                try {isSuccess = serviceContainer.getContractService().conclude(contract.getId());}
                catch (NoDataException e) {System.out.println(e.getMessage()); return;}
                catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
                if(isSuccess) {System.out.println("체결되었습니다."); return;}
                else {System.out.println("체결이 실패되었습니다."); return;}
            } else {
                System.out.println("! 잘못된 입력입니다.");
            }
        }
    }
    private void printOfferInsuranceMenu() throws IOException {
        while (true) {
            ArrayList<Customer> customerList = null;
            try {customerList = serviceContainer.getCustomerService().getCustomerList();}
            catch (EmptyListException | TimeDelayException e) {System.out.println(e.getMessage()); return;}
            catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
            System.out.println("******************** 보험제안 메뉴 *********************");
            System.out.println("보험제안할 고객의 아이디를 입력하세요. 뒤로가려면 0을 입력하세요.");
            System.out.println("고객아이디\t고객이름");
            for(Customer customer : customerList) {
                System.out.println(customer.getId()
                        + "\t" + customer.getName()
                );
            }
            System.out.print("고객 아이디 : ");
            String id = userInput.readLine().trim();
            if(id.contains(" ")||id.isEmpty()) {System.out.println("! 잘못된 입력입니다."); continue;}
            if(id.equals("0")) return;
            Customer selectedCustomer = null;
            for(Customer customer : customerList) {if(customer.getId().equals(id)) selectedCustomer = customer;}
            if(selectedCustomer==null){System.out.println("! 잘못된 입력입니다."); continue;}
            printCustomerDetailAndInsuranceList(selectedCustomer); return;
        }
    }

    private void printCustomerDetailAndInsuranceList(Customer customer) throws IOException {
        while (true) {
            System.out.println("******************** 제안할 보험 선택 *********************");
            System.out.println("고객의 상세 정보를 조회하고 제안할 보험의 아이디를 입력하세요.");
            System.out.println("뒤로가기를 원하시면 0을 입력하세요.");

            System.out.println("\n[고객 상세정보]");
            System.out.println("아이디 : " + customer.getId()
                    + "\n이름 : " + customer.getName()
                    + "\n주소 : " + customer.getAddress()
                    + "\n전화번호 : " + customer.getPhoneNumber()
                    + "\n이메일 : " + customer.getEmail()
                    + "\n사업장 유무 : " + customer.isHasWorkplace()
                    + "\n주택 유무 : " + customer.isHasHome()
            );

            ArrayList<Insurance> insuranceList = null;
            try {
                if(customer.isHasHome() && customer.isHasWorkplace()){insuranceList = serviceContainer.getInsuranceService().getInsuranceList(InsuranceStatus.Authorize);}
                else if(customer.isHasHome()){insuranceList = serviceContainer.getInsuranceService().getInsuranceList(InsuranceType.HomeFire, InsuranceStatus.Authorize);}
                else if(customer.isHasWorkplace()){insuranceList = serviceContainer.getInsuranceService().getInsuranceList(InsuranceType.WorkplaceFire, InsuranceStatus.Authorize);}
            } catch (EmptyListException | TimeDelayException e) {
                System.out.println(e.getMessage());
                return;
            } catch (RemoteException | NullPointerException e) {
                System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;
            }

            System.out.println("\n[해당고객 맞춤 보험 목록]");
            if(!customer.isHasWorkplace() && !customer.isHasHome()){ System.out.println("해당고객의 맞춤 보험이 존재하지 않습니다."); return;}
            System.out.println("아이디\t이름\t유형\t가입대상");
            for(Insurance insurance : insuranceList){
                System.out.println(insurance.getId()
                        + "\t" + insurance.getName()
                        + "\t" + insurance.getType().getName()
                        + "\t" + insurance.getTarget()
                );
            }
            System.out.print("보험 아이디 : ");
            int insuranceId;
            try {insuranceId = Integer.parseInt(userInput.readLine().trim());}
            catch (NumberFormatException e) {System.out.println("! 잘못된 입력입니다."); continue;}
            if (insuranceId == 0) return;
            Insurance selectedInsurance = null;
            for(Insurance insurance : insuranceList) {if(insurance.getId()==insuranceId) selectedInsurance = insurance;}
            if(selectedInsurance == null){System.out.println("! 잘못된 입력입니다."); continue;}
            printInsuranceDetail(customer.getId(), selectedInsurance); return;
        }
    }

    private void printInsuranceDetail(String customerId, Insurance insurance) throws IOException {
        while (true) {
            System.out.println("******************** 보험 상세정보 *********************");
            System.out.println("아이디 : " + insurance.getId()
                    + "\n이름 : " + insurance.getName()
                    + "\n유형 : " + insurance.getType().getName()
                    + "\n가입대상자 : " + insurance.getTarget()
                    + "\n보상조건 : " + insurance.getCompensateCondition()
                    + "\n비보상조건 : " + insurance.getNotCompensateCondition());
            System.out.println("\n[버튼]");
            System.out.println("1. 보험제안");
            System.out.println("0. 뒤로가기");
            String choice = userInput.readLine().trim();
            if (choice.equals("0")) {
                return;
            } else if(choice.equals("1")){
                System.out.println("보험 제안과 함께 전달할 메시지를 입력하세요.");
                System.out.println("뒤로가기를 원하시면 0을 입력하세요.");
                String message = userInput.readLine().trim();
                if(message.isEmpty()){System.out.println("! 잘못된 입력입니다."); continue;}
                if(message.equals("0")) return;
                int saleId = 0;
                try{saleId = serviceContainer.getSaleService().offerInsurance(employeeId, customerId, insurance.getId(), message);}
                catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
                if(saleId!=0) {System.out.println("보험 제안이 완료되었습니다."); return;}
                else {System.out.println("보험 제안이 실패하였습니다."); return;}
            } else {
                System.out.println("! 잘못된 입력입니다.");
            }
        }
    }
}
