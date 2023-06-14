package ui;

import domain.*;
import domain.customerInfo.CustomerInfo;
import domain.customerInfo.HomeCustomerInfo;
import domain.customerInfo.WorkplaceCustomerInfo;
import enumeration.accident.AccidentStatus;
import enumeration.calculationFormula.OutwallType;
import enumeration.calculationFormula.PillarType;
import enumeration.calculationFormula.RoofType;
import enumeration.calculationFormula.homeFormula.HomeSquareMeter;
import enumeration.calculationFormula.homeFormula.HouseType;
import enumeration.calculationFormula.homeFormula.ResidenceType;
import enumeration.calculationFormula.workplaceFormula.Floor;
import enumeration.calculationFormula.workplaceFormula.WorkplaceSquareMeter;
import enumeration.calculationFormula.workplaceFormula.WorkplaceUsage;
import enumeration.contract.ContractStatus;
import enumeration.contract.ContractTerm;
import enumeration.contract.PaymentCycle;
import enumeration.employee.Department;
import enumeration.insurance.InsuranceStatus;
import enumeration.insurance.InsuranceType;
import exception.EmptyListException;
import exception.NoDataException;
import exception.TimeDelayException;
import service.ServiceContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomerUi {
	
	private final String customerId;
	private final ServiceContainer serviceContainer;
	private final BufferedReader userInput;
	
	public CustomerUi(String customerId, ServiceContainer serviceContainer, BufferedReader userInput) {
		this.customerId = customerId;
		this.serviceContainer = serviceContainer;
		this.userInput = userInput;
	}

	public void printMenu() throws IOException {
		while(true) {
			System.out.println("******************** 고객 메인 메뉴 *********************");
			System.out.println("1. 보험가입");
			System.out.println("2. 계약조회");
			System.out.println("3. 사고접수");
			System.out.println("4. 심사조회");
			System.out.println("5. 요금납부");
			System.out.println("6. 보험제안조회");
			System.out.println("0. 로그아웃");
			System.out.println("x. 종료");
			switch(userInput.readLine().trim()) {
				case "1" : printApplyInsuranceMenu(); break;
				case "2" : printSearchContractMenu(); break;
				case "3" : printReportAccidentMenu(); break;
				case "4" : printSearchExamineResultMenu(); break;
				case "5" : printPayMenu(); break;
				case "6" : printSearchOfferMenu(); break;
				case "0" : return;
				case "x" : System.exit(0);
				default : System.out.println("! 잘못된 입력입니다.");
			}
		}
	}

	private void printApplyInsuranceMenu() throws IOException {
		while(true) {
			System.out.println("********************  보험가입 메뉴 *********************");
			System.out.println("자세히 보고싶은 보험의 아이디를 입력하세요.");
			System.out.println("뒤로가기를 원하시면 0을 입력하세요.");
			ArrayList<Insurance> insuranceList;
			try {insuranceList = serviceContainer.getInsuranceService().getInsuranceList(InsuranceStatus.Authorize);}
			catch (EmptyListException | TimeDelayException e) {System.out.println(e.getMessage()); return;}
			catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
			System.out.println("아이디\t이름\t유형");
			for(Insurance insurance : insuranceList){
				System.out.println(insurance.getId()
						+ "\t" + insurance.getName()
						+ "\t" + insurance.getType().getName());
			}
			System.out.println();
			System.out.print("보험 아이디 : ");
			int insuranceId;
			try {insuranceId = Integer.parseInt(userInput.readLine().trim());}
			catch (NumberFormatException e) {System.out.println("! 잘못된 입력입니다."); continue;}
			if (insuranceId == 0) return;
			Insurance selectedInsurance = null;
			for(Insurance insurance : insuranceList) {if(insurance.getId()==insuranceId) selectedInsurance = insurance;}
			if(selectedInsurance == null){System.out.println("! 잘못된 입력입니다."); continue;}
			printInsuranceDetail(selectedInsurance); return;
		}
	}

	private void printInsuranceDetail(Insurance insurance) throws IOException {
		while (true) {
			System.out.println("******************** 보험 상세정보 *********************");
			System.out.println("아이디 : " + insurance.getId()
					+ "\n이름 : " + insurance.getName()
					+ "\n유형 : " + insurance.getType().getName()
					+ "\n가입대상자 : " + insurance.getTarget()
					+ "\n보상조건 : " + insurance.getCompensateCondition()
					+ "\n비보상조건 : " + insurance.getNotCompensateCondition()
			);
			System.out.println("\n[버튼]");
			System.out.println("1. 신청");
			System.out.println("0. 뒤로가기");
			String choice = userInput.readLine().trim();
			if (choice.equals("0")) {
				return;
			} else if(choice.equals("1")){
				if(insurance.getType()==InsuranceType.WorkplaceFire) {printWorkplaceFireApplyForm(insurance); return;}
				else {printHomeFireApplyForm(insurance); return;}
			}  else {
				System.out.println("! 잘못된 입력입니다.");
			}
		}
	}
	private void printWorkplaceFireApplyForm(Insurance insurance) throws IOException {
		while (true){
			System.out.println("******************** 사업장화재보험 가입양식 *********************");
			System.out.println("뒤로가기를 원하시면 0을 입력하세요.");

			System.out.println("사업장 용도를 선택하세요.");
			for(int i = 0; i < WorkplaceUsage.values().length; i++) {
				System.out.println((i + 1) + ". " + WorkplaceUsage.values()[i].getName());
			}
			WorkplaceUsage workplaceUsage;
			switch(userInput.readLine().trim()) {
				case "0" :  return;
				case "1" :  workplaceUsage = WorkplaceUsage.values()[0]; break;
				case "2" :  workplaceUsage = WorkplaceUsage.values()[1]; break;
				case "3" :  workplaceUsage = WorkplaceUsage.values()[2]; break;
				case "4" :  workplaceUsage = WorkplaceUsage.values()[3]; break;
				case "5" :  workplaceUsage = WorkplaceUsage.values()[4]; break;
				case "6" :  workplaceUsage = WorkplaceUsage.values()[5]; break;
				case "7" :  workplaceUsage = WorkplaceUsage.values()[6]; break;
				case "8" :  workplaceUsage = WorkplaceUsage.values()[7]; break;
				case "9" :  workplaceUsage = WorkplaceUsage.values()[8]; break;
				case "10" :  workplaceUsage = WorkplaceUsage.values()[9]; break;
				default : System.out.println("! 잘못된 입력입니다."); continue;
			}

			System.out.println("사업장 면적(㎡)을 입력하세요.");
			int minWorkplaceSquareMeter = WorkplaceSquareMeter.values()[WorkplaceSquareMeter.values().length-1].getMinSquareFeet();
			int maxWorkplaceSquareMeter = WorkplaceSquareMeter.values()[0].getMaxSquareFeet();
			System.out.println("입력가능범위는 ["
					+ minWorkplaceSquareMeter
					+ " ~ "
					+ maxWorkplaceSquareMeter
					+ "] 입니다.");
			int squareMeter;
			try {squareMeter = Integer.parseInt(userInput.readLine().trim());}
			catch (NumberFormatException e) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(squareMeter == 0) return;
			if(squareMeter < minWorkplaceSquareMeter || squareMeter > maxWorkplaceSquareMeter) {System.out.println("! 잘못된 입력입니다."); continue;}

			System.out.println("건물층수를 입력하세요.");
			int minFloor = Floor.values()[Floor.values().length-1].getMinFloor();
			int maxFloor = Floor.values()[0].getMaxFloor();
			System.out.println("입력가능범위는 ["
					+ minFloor
					+ " ~ "
					+ maxFloor
					+ "] 입니다.");
			int floor;
			try {floor = Integer.parseInt(userInput.readLine().trim());}
			catch (NumberFormatException e) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(floor == 0) return;
			if(floor < minFloor || floor > maxFloor) {System.out.println("! 잘못된 입력입니다."); continue;}

			System.out.println("기둥형태를 선택하세요.");
			for(int i = 0; i < PillarType.values().length; i++) {
				System.out.println((i + 1) + ". " + PillarType.values()[i].getName());
			}
			PillarType pillarType;
			switch(userInput.readLine().trim()) {
				case "0" :  return;
				case "1" :  pillarType = PillarType.values()[0]; break;
				case "2" :  pillarType = PillarType.values()[1]; break;
				default : System.out.println("! 잘못된 입력입니다."); continue;
			}

			System.out.println("지붕형태를 선택하세요.");
			for(int i = 0; i < RoofType.values().length; i++) {
				System.out.println((i + 1) + ". " + RoofType.values()[i].getName());
			}
			RoofType roofType;
			switch(userInput.readLine().trim()) {
				case "0" :  return;
				case "1" :  roofType = RoofType.values()[0]; break;
				case "2" :  roofType = RoofType.values()[1]; break;
				default : System.out.println("! 잘못된 입력입니다."); continue;
			}

			System.out.println("외벽형태를 선택하세요.");
			for(int i = 0; i < OutwallType.values().length; i++) {
				System.out.println((i + 1) + ". " + OutwallType.values()[i].getName());
			}
			OutwallType outwallType;
			switch(userInput.readLine().trim()) {
				case "0" :  return;
				case "1" :  outwallType = OutwallType.values()[0]; break;
				case "2" :  outwallType = OutwallType.values()[1]; break;
				case "3" :  outwallType = OutwallType.values()[2]; break;
				case "4" :  outwallType = OutwallType.values()[3]; break;
				default : System.out.println("! 잘못된 입력입니다."); continue;
			}
			DecimalFormat decFormat = new DecimalFormat("###,###");
			System.out.println("보상금(원)을 입력하세요.");
			int minCompensation;
			int maxCompensation;
			try {
				minCompensation = serviceContainer.getCalculationFormulaService().calculateMinCompensation(squareMeter, insurance.getCalculationFormulaId());
				maxCompensation = serviceContainer.getCalculationFormulaService().calculateMaxCompensation(squareMeter, insurance.getCalculationFormulaId());
			} catch (NoDataException e) {
				System.out.println(e.getMessage()); return;
			} catch (RemoteException | NullPointerException e) {
				System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;
			}

			System.out.println("입력가능범위는 ["
					+ decFormat.format(minCompensation)
					+ " ~ "
					+ decFormat.format(maxCompensation)
					+ "] 입니다.");
			int compensation;
			try {compensation = Integer.parseInt(userInput.readLine().trim());}
			catch (NumberFormatException e) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(compensation == 0) return;
			if(compensation < minCompensation || compensation > maxCompensation) {System.out.println("! 잘못된 입력입니다."); continue;}

			CustomerInfo customerInfo = new WorkplaceCustomerInfo(customerId, squareMeter, pillarType, roofType, outwallType, workplaceUsage, floor);

			System.out.println("보험료(원) 계산 결과를 알려드립니다.");
			int payment;
			try {
				payment = serviceContainer.getCalculationFormulaService().calculatePayment(customerInfo, compensation, insurance.getCalculationFormulaId());
			} catch (NoDataException e) {
				System.out.println(e.getMessage()); return;
			} catch (RemoteException | NullPointerException e) {
				System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;
			}

			System.out.println("월 보험료는 [" + decFormat.format(payment) + "]원 입니다.");

			System.out.println("보험 기간을 입력하세요.");
			for(int i = 0; i < ContractTerm.values().length; i++) {
				System.out.println((i + 1) + ". " + ContractTerm.values()[i].getYear() + "년");
			}
			ContractTerm contractTerm;
			switch(userInput.readLine().trim()) {
				case "0" :  return;
				case "1" :  contractTerm = ContractTerm.values()[0]; break;
				case "2" :  contractTerm = ContractTerm.values()[1]; break;
				case "3" :  contractTerm = ContractTerm.values()[2]; break;
				case "4" :  contractTerm = ContractTerm.values()[3]; break;
				case "5" :  contractTerm = ContractTerm.values()[4]; break;
				default : System.out.println("! 잘못된 입력입니다."); continue;
			}

			System.out.println("납입 주기를 입력하세요.");
			for(int i = 0; i < PaymentCycle.values().length; i++) {
				System.out.println((i + 1) + ". " + PaymentCycle.values()[i].getMonth() + "월납");
			}
			PaymentCycle paymentCycle;
			switch(userInput.readLine().trim()) {
				case "0" :  return;
				case "1" :  paymentCycle = PaymentCycle.values()[0]; break;
				case "2" :  paymentCycle = PaymentCycle.values()[1]; break;
				case "3" :  paymentCycle = PaymentCycle.values()[2]; break;
				default : System.out.println("! 잘못된 입력입니다."); continue;
			}
			payment = payment * paymentCycle.getMonth();
			System.out.println(paymentCycle.getMonth() + "개월 납부 시 보험료는 " + decFormat.format(payment) + "원 입니다.");

			System.out.println("해당 보험을 추천한 영업사원이 있다면 해당 영업사원의 아이디를 입력하세요.");
			System.out.println("없는 경우에는 빈칸을 입력하세요.");
			String salespersonId = userInput.readLine().trim();
			if(salespersonId.contains(" ")) {System.out.println("! 잘못된 입력입니다."); continue;}
			if (salespersonId.equals("0")) return;
			if(!salespersonId.isEmpty()){
				try {serviceContainer.getEmployeeService().getEmployee(salespersonId, Department.Salesperson);}
				catch (NoDataException e) {System.out.println(e.getMessage()); continue;}
				catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
			}

			Contract contract = new Contract(0, insurance.getId(), salespersonId, customerId, contractTerm, payment, paymentCycle, compensation, ContractStatus.Apply);
			int contractId = 0;
			try{contractId = serviceContainer.getContractService().applyInsurance(contract, customerInfo);}
			catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
			if(contractId==0) {System.out.println("가입신청이 실패하였습니다."); return;}
			System.out.println("가입신청이 완료되었습니다.");
			System.out.println("신청아이디는 " + contractId + "입니다.");
			return;
		}
	}
	private void printHomeFireApplyForm(Insurance insurance) throws IOException {
		while (true){
			System.out.println("******************** 주택화재보험 가입양식 *********************");
			System.out.println("뒤로가기를 원하시면 0을 입력하세요.");

			System.out.println("거주유형을 선택하세요.");
			for(int i = 0; i < ResidenceType.values().length; i++) {
				System.out.println((i + 1) + ". " + ResidenceType.values()[i].getName());
			}
			ResidenceType residenceType;
			switch(userInput.readLine().trim()) {
				case "0" :  return;
				case "1" :  residenceType = ResidenceType.values()[0]; break;
				case "2" :  residenceType = ResidenceType.values()[1]; break;
				default : System.out.println("! 잘못된 입력입니다."); continue;
			}

			System.out.println("주택유형을 선택하세요.");
			for(int i = 0; i < HouseType.values().length; i++) {
				System.out.println((i + 1) + ". " + HouseType.values()[i].getName());
			}
			HouseType houseType;
			switch(userInput.readLine().trim()) {
				case "0" :  return;
				case "1" :  houseType = HouseType.values()[0]; break;
				case "2" :  houseType = HouseType.values()[1]; break;
				default : System.out.println("! 잘못된 입력입니다."); continue;
			}

			System.out.println("주택 면적(㎡)을 입력하세요.");
			int minHomeSquareMeter = HomeSquareMeter.values()[HomeSquareMeter.values().length-1].getMinSquareFeet();
			int maxHomeSquareMeter = HomeSquareMeter.values()[0].getMaxSquareFeet();
			System.out.println("입력가능범위는 ["
					+ minHomeSquareMeter
					+ " ~ "
					+ maxHomeSquareMeter
					+ "] 입니다.");
			int squareMeter;
			try {squareMeter = Integer.parseInt(userInput.readLine().trim());}
			catch (NumberFormatException e) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(squareMeter == 0) return;
			if(squareMeter < minHomeSquareMeter || squareMeter > maxHomeSquareMeter) {System.out.println("! 잘못된 입력입니다."); continue;}

			System.out.println("기둥형태를 선택하세요.");
			for(int i = 0; i < PillarType.values().length; i++) {
				System.out.println((i + 1) + ". " + PillarType.values()[i].getName());
			}
			PillarType pillarType;
			switch(userInput.readLine().trim()) {
				case "0" :  return;
				case "1" :  pillarType = PillarType.values()[0]; break;
				case "2" :  pillarType = PillarType.values()[1]; break;
				default : System.out.println("! 잘못된 입력입니다."); continue;
			}

			System.out.println("지붕형태를 선택하세요.");
			for(int i = 0; i < RoofType.values().length; i++) {
				System.out.println((i + 1) + ". " + RoofType.values()[i].getName());
			}
			RoofType roofType;
			switch(userInput.readLine().trim()) {
				case "0" :  return;
				case "1" :  roofType = RoofType.values()[0]; break;
				case "2" :  roofType = RoofType.values()[1]; break;
				default : System.out.println("! 잘못된 입력입니다."); continue;
			}

			System.out.println("외벽형태를 선택하세요.");
			for(int i = 0; i < OutwallType.values().length; i++) {
				System.out.println((i + 1) + ". " + OutwallType.values()[i].getName());
			}
			OutwallType outwallType;
			switch(userInput.readLine().trim()) {
				case "0" :  return;
				case "1" :  outwallType = OutwallType.values()[0]; break;
				case "2" :  outwallType = OutwallType.values()[1]; break;
				case "3" :  outwallType = OutwallType.values()[2]; break;
				case "4" :  outwallType = OutwallType.values()[3]; break;
				default : System.out.println("! 잘못된 입력입니다."); continue;
			}
			DecimalFormat decFormat = new DecimalFormat("###,###");
			System.out.println("보상금(원)을 입력하세요.");
			int minCompensation;
			int maxCompensation;
			try {
				minCompensation = serviceContainer.getCalculationFormulaService().calculateMinCompensation(squareMeter, insurance.getCalculationFormulaId());
				maxCompensation = serviceContainer.getCalculationFormulaService().calculateMaxCompensation(squareMeter, insurance.getCalculationFormulaId());
			} catch (NoDataException e) {
				System.out.println(e.getMessage()); return;
			} catch (RemoteException | NullPointerException e) {
				System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;
			}

			System.out.println("입력가능범위는 ["
					+ decFormat.format(minCompensation)
					+ " ~ "
					+ decFormat.format(maxCompensation)
					+ "] 입니다.");
			int compensation;
			try {compensation = Integer.parseInt(userInput.readLine().trim());}
			catch (NumberFormatException e) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(compensation == 0) return;
			if(compensation < minCompensation || compensation > maxCompensation) {System.out.println("! 잘못된 입력입니다."); continue;}

			CustomerInfo customerInfo = new HomeCustomerInfo(customerId, squareMeter, pillarType, roofType, outwallType, houseType, residenceType);

			System.out.println("보험료(원) 계산 결과를 알려드립니다.");
			int payment;
			try {
				payment = serviceContainer.getCalculationFormulaService().calculatePayment(customerInfo, compensation, insurance.getCalculationFormulaId());
			} catch (NoDataException e) {
				System.out.println(e.getMessage()); return;
			} catch (RemoteException | NullPointerException e) {
				System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;
			}

			System.out.println("월 보험료는 [" + decFormat.format(payment) + "]원 입니다.");

			System.out.println("보험 기간을 입력하세요.");
			for(int i = 0; i < ContractTerm.values().length; i++) {
				System.out.println((i + 1) + ". " + ContractTerm.values()[i].getYear() + "년");
			}
			ContractTerm contractTerm;
			switch(userInput.readLine().trim()) {
				case "0" :  return;
				case "1" :  contractTerm = ContractTerm.values()[0]; break;
				case "2" :  contractTerm = ContractTerm.values()[1]; break;
				case "3" :  contractTerm = ContractTerm.values()[2]; break;
				case "4" :  contractTerm = ContractTerm.values()[3]; break;
				case "5" :  contractTerm = ContractTerm.values()[4]; break;
				default : System.out.println("! 잘못된 입력입니다."); continue;
			}

			System.out.println("납입 주기를 입력하세요.");
			for(int i = 0; i < PaymentCycle.values().length; i++) {
				System.out.println((i + 1) + ". " + PaymentCycle.values()[i].getMonth() + "월납");
			}
			PaymentCycle paymentCycle;
			switch(userInput.readLine().trim()) {
				case "0" :  return;
				case "1" :  paymentCycle = PaymentCycle.values()[0]; break;
				case "2" :  paymentCycle = PaymentCycle.values()[1]; break;
				case "3" :  paymentCycle = PaymentCycle.values()[2]; break;
				default : System.out.println("! 잘못된 입력입니다."); continue;
			}
			payment = payment * paymentCycle.getMonth();
			System.out.println(paymentCycle.getMonth() + "개월 납부 시 보험료는 " + decFormat.format(payment) + "원 입니다.");

			System.out.println("해당 보험을 추천한 영업사원이 있다면 해당 영업사원의 아이디를 입력하세요.");
			System.out.println("없는 경우에는 빈칸을 입력하세요.");
			String salespersonId = userInput.readLine().trim();
			if(salespersonId.contains(" ")) {System.out.println("! 잘못된 입력입니다."); continue;}
			if (salespersonId.equals("0")) return;
			if(!salespersonId.isEmpty()){
				try {serviceContainer.getEmployeeService().getEmployee(salespersonId, Department.Salesperson);}
				catch (NoDataException e) {System.out.println(e.getMessage()); continue;}
				catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
			}
			Contract contract = new Contract(0, insurance.getId(), salespersonId, customerId, contractTerm, payment, paymentCycle, compensation, ContractStatus.Apply);
			int contractId = 0;
			try{contractId = serviceContainer.getContractService().applyInsurance(contract, customerInfo);}
			catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
			if(contractId==0) {System.out.println("가입신청이 실패하였습니다."); return;}
			System.out.println("가입신청이 완료되었습니다.");
			System.out.println("신청아이디는 " + contractId + "입니다.");
			return;
		}
	}
	private void printSearchContractMenu() throws IOException {
		while (true) {
			ArrayList<Contract> contractList;
			try {contractList = serviceContainer.getContractService().getContractList(customerId, ContractStatus.Conclude);}
			catch (EmptyListException | TimeDelayException e) {System.out.println(e.getMessage()); return;}
			catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
			System.out.println("******************** 계약조회 메뉴 *********************");
			System.out.println("조회할 계약의 아이디를 입력하세요. 뒤로가려면 0을 입력하세요.");
			System.out.println("아이디\t고객아이디\t보험아이디\t보험유형");
			for(Contract contract : contractList) {
				InsuranceType insuranceType;
				try {insuranceType = serviceContainer.getInsuranceService().getInsurance(contract.getInsuranceId()).getType();}
				catch (NoDataException e) {System.out.println(e.getMessage()); return;}
				catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
				System.out.println(contract.getId()
						+ "\t" + contract.getCustomerId()
						+ "\t" + contract.getInsuranceId()
						+ "\t" + insuranceType.getName()
				);
			}
			System.out.print("계약 아이디 : ");
			int id;
			try {id = Integer.parseInt(userInput.readLine().trim());}
			catch (NumberFormatException e) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(id==0) return;
			Contract selectedContract = null;
			for(Contract contract : contractList) {if(contract.getId()==id) selectedContract = contract;}
			if(selectedContract==null){System.out.println("! 잘못된 입력입니다."); continue;}
			printContractDetail(selectedContract); continue;
		}
	}

	private void printContractDetail(Contract contract) {
		DecimalFormat decFormat = new DecimalFormat("###,###");
		System.out.println("******************** 계약 상세내용 *********************");
		System.out.println("아이디 : " + contract.getId()
				+ "\n고객 아이디 : " + contract.getCustomerId()
				+ "\n보험 아이디 : " + contract.getInsuranceId()
				+ "\n영업사원 아이디 : " + contract.getSaleEmployeeId()
				+ "\n계약 기간 : " + contract.getTerm().getYear() + "년"
				+ "\n계약 시작일 : " + contract.getStartDate()
				+ "\n계약 만료일 : " + contract.getExpirationDate()
				+ "\n보험료 : " + decFormat.format(contract.getPaymentFee()) + "원"
				+ "\n납부주기 : " + contract.getPaymentCycle().getMonth() + "월납"
				+ "\n납부기한 : " + contract.getPaymentDeadline()
				+ "\n보상금 : " + decFormat.format(contract.getCompensation()) + "원"
				+ "\n계약상태 : " + contract.getContractStatus().getName()
		);
	}

	private void printSearchExamineResultMenu() throws IOException {
		while(true) {
			System.out.println("******************** 심사결과조회 메뉴 *********************");
			System.out.println("1. 보험가입신청결과조회");
			System.out.println("2. 사고심사결과조회");
			System.out.println("0. 뒤로가기");
			switch(userInput.readLine().trim()) {
				case "1" : printApplyInsuranceResult(); return; 
				case "2" : printReportAccidentResult(); return;
				case "0" : return;
				default : System.out.println("! 잘못된 입력입니다.");
			}
		}
	}
	private void printApplyInsuranceResult()   {
		System.out.println("******************** 가입 요청 결과 *********************");
		ArrayList<Contract> applyInsuranceResultList = null;
		try {applyInsuranceResultList = serviceContainer.getContractService().getContractList(customerId);}
		catch (EmptyListException | TimeDelayException e) {System.out.println(e.getMessage()); return;}
		catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
		System.out.println("아이디\t보험아이디\t심사결과");
		for(Contract contract : applyInsuranceResultList){
			System.out.println(contract.getId()
					+ "\t" + contract.getInsuranceId()
					+ "\t" + contract.getContractStatus().getName());
		}
	}
	private void printReportAccidentResult() throws IOException {
		while(true) {
			ArrayList<Accident> allAccidentList = null;
			ArrayList<Accident> accidentList = new ArrayList<>();
			try {allAccidentList = serviceContainer.getAccidentService().getAccidentList();}
			catch (EmptyListException | TimeDelayException e) {System.out.println(e.getMessage()); return;}
			catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
			for(Accident accident : allAccidentList){
				String customerId= "";
				try {customerId = serviceContainer.getContractService().getContract(accident.getContractId()).getCustomerId();}
				catch (NoDataException e) {}
				catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
				if(customerId.equals(this.customerId)) accidentList.add(accident);
			}
			if(accidentList.isEmpty()) {
				System.out.println("! 목록이 존재하지 않습니다."); return;
			}
			System.out.println("******************** 사고심사 결과 *********************");
			System.out.println("보상내역을 확인하려면 상태가 '보상완료'인 사고아이디를 입력하세요. 뒤로가기를 원하시면 0을 입력하세요.");
			System.out.println("아이디\t사고일시\t심사결과");
			for(Accident accident : accidentList){
				System.out.println(accident.getId() + "\t" + accident.getDate() + "\t" + accident.getStatus().getName());
			}

			System.out.print("\n사고 아이디 : ");
			int id = 0;
			try {id = Integer.parseInt(userInput.readLine().trim());}
			catch (NumberFormatException e) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(id == 0) return;
			Accident selectedAccident = null;
			for(Accident accident : accidentList) {if(accident.getId()==id) selectedAccident = accident;}
			if(selectedAccident==null){System.out.println("! 잘못된 입력입니다."); continue;}
			Compensation compensation;
			try {compensation = serviceContainer.getCompensateService().getCompensation(selectedAccident.getId());}
			catch (NoDataException e) {System.out.println(e.getMessage()); continue;}
			catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}

			printCompensationDetail(compensation);
		}
	}

	private void printCompensationDetail(Compensation compensation) {
		DecimalFormat decFormat = new DecimalFormat("###,###");
		System.out.println("******************** 보상 내역 *********************");
		System.out.println("보상아이디 : " + compensation.getId()
				+ "\n사고아이디 : " + compensation.getAccidentId()
				+ "\n지급된보상금 : " + decFormat.format(compensation.getCompensation())
		);
	}

	private void printReportAccidentMenu() throws IOException {
		while (true) {
			ArrayList<Contract> contractList;
			try {contractList = serviceContainer.getContractService().getContractList(customerId, ContractStatus.Conclude);}
			catch (EmptyListException | TimeDelayException e) {System.out.println(e.getMessage()); return;}
			catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
			System.out.println("******************** 사고접수 메뉴 *********************");
			System.out.println("사고접수할 계약의 아이디를 입력하세요. 뒤로가려면 0을 입력하세요.");
			System.out.println("계약아이디\t고객아이디\t보험아이디");
			for(Contract contract : contractList) {
				System.out.println(contract.getId()
						+ "\t" + contract.getCustomerId()
						+ "\t" + contract.getInsuranceId()
				);
			}
			System.out.print("계약 아이디 : ");
			int id;
			try {id = Integer.parseInt(userInput.readLine().trim());}
			catch (NumberFormatException e) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(id == 0) return;
			Contract selectedContract = null;
			for(Contract contract : contractList) {if(contract.getId()==id) selectedContract = contract;}
			if(selectedContract==null){System.out.println("! 잘못된 입력입니다."); continue;}
			
			printContractDetail(selectedContract);

			System.out.println("\n[버튼]");
			System.out.println("1. 사고접수");
			System.out.println("0. 뒤로가기");
			String choice = userInput.readLine().trim();
			if (choice.equals("0")) {
				return;
			} else if(choice.equals("1")){
				printReportAccidentForm(selectedContract.getId()); return;
			} else {
				System.out.println("! 잘못된 입력입니다."); continue;
			}
		}
	}

	private void printReportAccidentForm(int contractId) throws IOException {
		while (true){
			System.out.println("******************** 사고접수 양식 *********************");
			System.out.println("사고 접수 양식을 입력해주세요. 뒤로가기를 원하시면 0을 입력하세요.");

			System.out.print("피해액(원) : ");
			long damage;
			try{damage = Long.parseLong(userInput.readLine());}
			catch(NumberFormatException e) { System.out.println("! 잘못된 입력입니다.");  continue;}
			if(damage==0) return;

			System.out.println("사고일시를 입력하세요.");
			System.out.println("[yyyy년 mm년 dd일 hh시 mm분] 형태로 입력해주세요.");
			System.out.println("(예) [2023년 06월 28일 15시 07분] 형태로 입력해주세요.");
			System.out.print("사고일시 : ");
			String stringDate = userInput.readLine().trim();
			if(stringDate.isEmpty()) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(stringDate.equals("0")) return;
			Timestamp accidentTimestamp = convertStringToTimestamp(stringDate);
			if(accidentTimestamp == null){System.out.println("! 잘못된 입력입니다."); continue;}
			System.out.println("입력하신 일시는 " + accidentTimestamp + "입니다.");

			System.out.print("장소 : ");
			String location = userInput.readLine().trim();
			if(location.isEmpty()) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(location.equals("0")) return;

			System.out.print("원인 : ");
			String cause = userInput.readLine().trim();
			if(cause.isEmpty()) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(cause.equals("0")) return;

			System.out.print("사고내용 : ");
			String content = userInput.readLine().trim();
			if(content.isEmpty()) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(content.equals("0")) return;

			System.out.println("계좌번호를 입력하세요. - 와 빈칸없이 써주세요.");
			System.out.print("계좌번호 : ");
			String accountNumber = userInput.readLine().trim();
			if(accountNumber.isEmpty()||accountNumber.contains(" ")) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(accountNumber.equals("0")) return;

			Accident accident = new Accident(contractId, accidentTimestamp, location, cause, content, damage, accountNumber, AccidentStatus.ReportAccident);
			int accidentId = 0;
			try{accidentId = serviceContainer.getAccidentService().reportAccident(accident);}
			catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
			if(accidentId == 0){
				System.out.println("! 사고접수가 실패했습니다.");
				return;
			}
			System.out.println("사고접수가 완료되었습니다.");
			System.out.println("사고 아이디는 " + accidentId + "입니다.");
			return;
		}
	}

	public Timestamp convertStringToTimestamp(String stringDate) {
		try {
			String pattern = "yyyy년 MM월 dd일 HH시 mm분";
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			Date date = formatter.parse(stringDate);
			Timestamp timeStampDate = new Timestamp(date.getTime());
			return timeStampDate;
		} catch (ParseException e) {
			return null;
		}
	}
	private void printPayMenu() throws IOException {
		while (true) {
			ArrayList<Contract> unpaidContractList;
			try {unpaidContractList = serviceContainer.getContractService().getUnpaidContractList(customerId);}
			catch (EmptyListException | TimeDelayException e) {System.out.println(e.getMessage()); return;}
			catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
			System.out.println("******************** 요금납부 메뉴 *********************");
			System.out.println("요금납부할 계약의 아이디를 입력하세요. 뒤로가려면 0을 입력하세요.");
			System.out.println("계약아이디\t보험아이디");
			for(Contract contract : unpaidContractList) {
				System.out.println(contract.getId()
						+ "\t" + contract.getCustomerId()
						+ "\t" + contract.getInsuranceId()
				);
			}
			System.out.print("계약 아이디 : ");
			int id;
			try {id = Integer.parseInt(userInput.readLine().trim());}
			catch (NumberFormatException e) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(id == 0) return;
			Contract selectedContract = null;
			for(Contract contract : unpaidContractList) {if(contract.getId()==id) selectedContract = contract;}
			if(selectedContract==null){System.out.println("! 잘못된 입력입니다."); continue;}

			printContractDetail(selectedContract);

			System.out.println("\n[버튼]");
			System.out.println("1. 요금납부");
			System.out.println("0. 뒤로가기");
			String choice = userInput.readLine().trim();
			if (choice.equals("0")) {
				return;
			} else if(choice.equals("1")){
				printPayForm(selectedContract); return;
			} else {
				System.out.println("! 잘못된 입력입니다."); continue;
			}
		}
	}

	private void printPayForm(Contract contract) throws IOException {
		while (true) {
			System.out.println("******************** 요금납부 양식 *********************");
			System.out.println("뒤로가기를 원하시면 0을 입력하세요.");

			System.out.print("카드번호(빈칸없이) : ");
			String cardNumber = userInput.readLine().trim();;
			if(cardNumber.isEmpty()||cardNumber.contains(" ")) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(cardNumber.equals("0")) return;

			System.out.print("만료일(mm/yy) : ");
			String  expirationDate = userInput.readLine().trim();
			if(expirationDate.isEmpty()||expirationDate.contains(" ")) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(expirationDate.equals("0")) return;

			System.out.print("cvc : ");
			String  cvc = userInput.readLine().trim();
			if(cvc.isEmpty()||cvc.contains(" ")) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(cvc.equals("0")) return;

			Pay pay = new Pay(contract.getId(), cardNumber);
			int payId = 0;
			try{payId = serviceContainer.getPayService().pay(contract, pay);}
			catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
			if(payId!=0) {System.out.println("납부가 완료되었습니다."); return;}
			else{System.out.println("납부가 실패하였습니다."); return;}
		}
	}

	private void printSearchOfferMenu()   {
		System.out.println("******************** 보험제안 목록 *********************");
		ArrayList<Sale> saleList = null;
		try {
			saleList = serviceContainer.getSaleService().getSaleList(customerId);
		} catch (EmptyListException | TimeDelayException e) {
			System.out.println(e.getMessage()); return;
		} catch (RemoteException | NullPointerException e) {
			System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;
		}

		System.out.println("아이디\t영업사원아이디\t추천보험아이디\t메시지");
		for(Sale sale : saleList){
			System.out.println(sale.getId()
					+ "\t" + sale.getSaleEmployeeId()
					+ "\t" + sale.getInsuranceId()
					+ "\t" + sale.getMessage()
			);
		}
	}
}
