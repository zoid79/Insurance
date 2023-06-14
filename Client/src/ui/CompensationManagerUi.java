package ui;

import domain.Accident;
import enumeration.accident.AccidentStatus;
import exception.EmptyListException;
import exception.NoDataException;
import exception.TimeDelayException;
import service.ServiceContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CompensationManagerUi {
    private final String employeeId;
    private final ServiceContainer serviceContainer;
    private final BufferedReader userInput;
    public CompensationManagerUi(String id, ServiceContainer serviceContainer, BufferedReader userInput) {
        this.employeeId = id;
        this.serviceContainer = serviceContainer;
        this.userInput = userInput;
    }
    public void printMenu() throws IOException {
        while(true) {
            System.out.println("******************** 보상담당자 메인 메뉴 *********************");
            System.out.println("1. 사고접수조회 및 보상심사");
            System.out.println("0. 로그아웃");
            System.out.println("x. 종료");
            switch(userInput.readLine().trim()) {
                case "1" : printSearchAccidentReport(); break;
                case "0" : return;
                case "x" : System.exit(0);
                default : System.out.println("! 잘못된 입력입니다.");
            }
        }
    }
    private void printSearchAccidentReport() throws IOException {
        while (true) {
            ArrayList<Accident> accidentList;
            try {accidentList = serviceContainer.getAccidentService().getAccidentList(AccidentStatus.ReportAccident);}
            catch (EmptyListException | TimeDelayException e) {System.out.println(e.getMessage()); return;}
            catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
            System.out.println("******************** 사고접수목록 *********************");
            System.out.println("자세히 보고싶은 사고의 아이디를 입력하세요. 뒤로가기를 원하시면 0을 입력하세요.");
            System.out.println("아이디\t사고일시\t피해액(원)");
            DecimalFormat decFormat = new DecimalFormat("###,###");
            for(Accident accident : accidentList){
                System.out.println(accident.getId()
                        + "\t" + accident.getDate()
                        + "\t" + decFormat.format(accident.getDamage()) + "원"
                );
            }

            System.out.print("사고 아이디 : ");
            int accidentId;
            try {accidentId = Integer.parseInt(userInput.readLine().trim());}
            catch (NumberFormatException e) {System.out.println("! 잘못된 입력입니다."); continue;}
            if (accidentId == 0) return;
            Accident selectedAccident = null;
            for(Accident accident : accidentList) {if(accident.getId()==accidentId) selectedAccident = accident;}
            if(selectedAccident == null){System.out.println("! 잘못된 입력입니다."); continue;}
            printAccidentDetail(selectedAccident); return;
        }
    }

    private void printAccidentDetail(Accident accident) throws IOException {
        while(true){
            int contractCompensation;
            try {
                contractCompensation = serviceContainer.getContractService().getContract(accident.getContractId()).getCompensation();
                DecimalFormat decFormat = new DecimalFormat("###,###");
                System.out.println("******************** 사고 상세내용 *********************");
                System.out.println("사고아이디 : " + accident.getId()
                        + "\n계약아이디 : " + accident.getContractId()
                        + "\n사고일시 : " + accident.getDate()
                        + "\n원인 : " + accident.getCause()
                        + "\n내용 : " + accident.getContent()
                        + "\n피해액 : " + decFormat.format(accident.getDamage()) + "원"
                        + "\n가입보상금 : " + decFormat.format(contractCompensation) + "원"
                );
            } catch (NoDataException e) {
                System.out.println(e.getMessage()); return;
            } catch (RemoteException | NullPointerException e) {
                System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;
            }

            System.out.println("\n[버튼]");
            System.out.println("1. 보상");
            System.out.println("2. 보상거절");
            System.out.println("0. 뒤로가기");
            String choice = userInput.readLine().trim();
            boolean isSuccess = false;
            if (choice.equals("0")) {
                return;
            } else if(choice.equals("1")){
                try{isSuccess = serviceContainer.getCompensateService().examineCompensation(accident, contractCompensation, AccidentStatus.Compensate);}
                catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
                if(isSuccess) {System.out.println("보상이 완료되었습니다."); return;}
                else {System.out.println("보상이 실패하였습니다."); return;}
            } else if(choice.equals("2")){
                try{isSuccess = serviceContainer.getCompensateService().examineCompensation(accident, contractCompensation, AccidentStatus.RefuseCompensate);}
                catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
                if(isSuccess) {System.out.println("보상거절이 완료되었습니다."); return;}
                else {System.out.println("보상거절이 실패하였습니다."); return;}
            } else {
                System.out.println("! 잘못된 입력입니다."); continue;
            }
        }
    }
}
