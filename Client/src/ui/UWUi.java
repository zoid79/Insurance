package ui;

import domain.Contract;
import domain.Customer;
import domain.customerInfo.CustomerInfo;
import domain.customerInfo.HomeCustomerInfo;
import domain.customerInfo.WorkplaceCustomerInfo;
import enumeration.contract.ContractStatus;
import enumeration.insurance.InsuranceType;
import exception.EmptyListException;
import exception.NoDataException;
import exception.TimeDelayException;
import service.ServiceContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UWUi {
    private final String employeeId;
    private final ServiceContainer serviceContainer;
    private final BufferedReader userInput;

    public UWUi(String employeeId, ServiceContainer serviceContainer, BufferedReader userInput) {
        this.employeeId = employeeId;
        this.serviceContainer = serviceContainer;
        this.userInput = userInput;
    }

    public void printMenu() throws IOException {
        while(true) {
            System.out.println("******************** UW 메인 메뉴 *********************");
            System.out.println("1. 인수심사");
            System.out.println("0. 로그아웃");
            System.out.println("x. 종료");
            switch(userInput.readLine().trim()) {
                case "1" : printUnderwriteMenu(); break;
                case "0" : return;
                case "x" : System.exit(0);
                default : System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void printUnderwriteMenu() throws IOException {
        while (true) {
            ArrayList<Contract> contractList;
            try {contractList = serviceContainer.getContractService().getContractList(ContractStatus.Apply);}
            catch (EmptyListException | TimeDelayException e) {System.out.println(e.getMessage()); return;}
            catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
            System.out.println("******************** 인수심사 메뉴 *********************");
            System.out.println("인수할 계약의 아이디를 입력하세요. 뒤로가려면 0을 입력하세요.");
            System.out.println("아이디\t고객아이디\t보험아이디\t보험유형");
            for(Contract contract : contractList) {
                InsuranceType insuranceType;
                try {insuranceType = serviceContainer.getInsuranceService().getInsurance(contract.getInsuranceId()).getType();}
                catch (NoDataException e) {System.out.println(e.getMessage()); return;}
                catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
                System.out.println(contract.getId()
                        + "\t" + contract.getCustomerId()
                        + "\t" + contract.getInsuranceId()
                        + "\t" + insuranceType.getName());
            }
            System.out.print("계약 아이디 : ");
            int id;
            try {id = Integer.parseInt(userInput.readLine().trim());}
            catch (NumberFormatException e) {System.out.println("잘못된 입력입니다."); continue;}
            if(id==0) return;
            Contract selectedContract = null;
            for(Contract contract : contractList) {if(contract.getId()==id) selectedContract = contract;}
            if(selectedContract==null){System.out.println("잘못된 입력입니다."); continue;}
            printContractDetail(selectedContract); return;
        }
    }

    private void printContractDetail(Contract contract) throws IOException {
        while (true) {
            System.out.println("******************** 계약 상세내용 *********************");
            System.out.println("계약 상세내역을 확인하고 버튼을 선택하세요.");

            System.out.println("[고객 상세내역]");
            Customer customer = null;
            CustomerInfo customerInfo = null;
            DecimalFormat decFormat = new DecimalFormat("###,###");
            try {
                customer = serviceContainer.getCustomerService().getCustomer(contract.getCustomerId());
                customerInfo = serviceContainer.getCustomerService().getInfo(contract.getCustomerInfoId());
            } catch (NoDataException e) {
                System.out.println(e.getMessage()); return;
            } catch (RemoteException | NullPointerException e) {
                System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;
            }

            System.out.println("아이디 : " + customer.getId()
                    + "\n이름 : " + customer.getName()
                    + "\n주소 : " + customer.getAddress()
                    + "\n전화번호 : " + customer.getPhoneNumber()
                    + "\n이메일 : " + customer.getEmail());

            System.out.println("\n[신청정보 상세내역]");
            if(customerInfo instanceof WorkplaceCustomerInfo){
                System.out.println("사업장 업종 : " + ((WorkplaceCustomerInfo) customerInfo).getUsage().getName()
                        +"\n층수 : " + ((WorkplaceCustomerInfo) customerInfo).getFloor());
            }else{
                System.out.println("거주 유형 : " + ((HomeCustomerInfo) customerInfo).getResidenceType().getName()
                        +"\n주택 유형 : " + ((HomeCustomerInfo) customerInfo).getHouseType().getName());
            }
            System.out.println("면적 : " + customerInfo.getSquareMeter()
                    +"\n기둥형태 : " + customerInfo.getPillarType().getName()
                    +"\n지붕형태 : " + customerInfo.getRoofType().getName()
                    +"\n외벽형태 : " + customerInfo.getOutwallType().getName()
                    +"\n보상금 : " + decFormat.format(contract.getCompensation()) +"원"
                    +"\n보험료 : " + decFormat.format(contract.getPaymentFee()) + "원"
                    +"\n보험기간 : " + contract.getTerm().getYear() + "년"
                    +"\n납입주기 : " + contract.getPayCycle().getMonth() + "개월"
            );

            System.out.println("\n[버튼]");
            System.out.println("1. 인수");
            System.out.println("2. 인수거절");
            System.out.println("0. 뒤로가기");
            String choice = userInput.readLine().trim();
            if (choice.equals("0")) {
                return;
            } else if(choice.equals("1")){
                boolean isSuccess = false;
                try{isSuccess = serviceContainer.getContractService().examineUnderwrite(contract.getId(), ContractStatus.Underwrite);}
                catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
                if(isSuccess) {System.out.println("인수되었습니다."); return;}
                else {System.out.println("인수가 실패되었습니다."); return;}
            } else if (choice.equals("2")){
                boolean isSuccess = false;
                try{isSuccess = serviceContainer.getContractService().examineUnderwrite(contract.getId(), ContractStatus.RefuseUnderwrite);}
                catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
                if(isSuccess) {System.out.println("인수거절되었습니다."); return;}
                else {System.out.println("인수거절이 실패되었습니다."); return;}
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }
}
