package ui;

import domain.Insurance;
import domain.calculationFormula.CalculationFormula;
import domain.calculationFormula.HomeFormula;
import domain.calculationFormula.WorkplaceFormula;
import enumeration.calculationFormula.OutwallType;
import enumeration.calculationFormula.PillarType;
import enumeration.calculationFormula.RiskLevel;
import enumeration.calculationFormula.RoofType;
import enumeration.calculationFormula.homeFormula.HomeCompensation;
import enumeration.calculationFormula.homeFormula.HomeSquareMeter;
import enumeration.calculationFormula.homeFormula.HouseType;
import enumeration.calculationFormula.homeFormula.ResidenceType;
import enumeration.calculationFormula.workplaceFormula.Floor;
import enumeration.calculationFormula.workplaceFormula.WorkplaceCompensation;
import enumeration.calculationFormula.workplaceFormula.WorkplaceSquareMeter;
import enumeration.calculationFormula.workplaceFormula.WorkplaceUsage;
import enumeration.insurance.InsuranceStatus;
import enumeration.insurance.InsuranceType;
import exception.DataDuplicationException;
import exception.EmptyListException;
import exception.TimeDelayException;
import service.ServiceContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class InsuranceDeveloperUi {

	private final String employeeId;
	private final ServiceContainer serviceContainer;
	private final BufferedReader userInput;

	public InsuranceDeveloperUi(String employeeId, ServiceContainer serviceContainer, BufferedReader userInput) {
		this.employeeId = employeeId;
		this.serviceContainer = serviceContainer;
		this.userInput = userInput;
	}

	public void printMenu() throws IOException {
		while(true) {
			System.out.println("******************** 상품개발자 메인 메뉴 *********************");
			System.out.println("1. 보험료&보상금 계산식 수립");
			System.out.println("2. 상품 개발");
			System.out.println("3. 인가요청결과 조회");
			System.out.println("0. 로그아웃");
			System.out.println("x. 종료");
			switch(userInput.readLine().trim()) {
				case "1" : printFormulaMenu(); break;
				case "2" : printDevelopeMenu();; break;
				case "3" : printExamineResult(); break;
				case "0" : return;
				case "x" : System.exit(0);
				default : System.out.println("! 잘못된 입력입니다."); continue;
			}
		}
	}

	private void printFormulaMenu() throws IOException {
		while(true) {
			System.out.println("******************** 계산식 수립 메뉴 *********************");
			System.out.println("보험 종류를 선택하세요.");
			System.out.println("1. 주택화재보험");
			System.out.println("2. 사업장화재보험");
			System.out.println("0. 뒤로가기");
			switch(userInput.readLine().trim()) {
				case "1" : printHomeFormulaForm(); return;
				case "2" : printWorkplaceFormulaForm(); return;
				case "0" : return;
				default : System.out.println("! 잘못된 입력입니다."); continue;
			}
		}
	}

	private void printHomeFormulaForm() throws IOException {
		while (true){
			String calculationFormulaName;
			HashMap<ResidenceType, RiskLevel> riskLevelAccordingToResidenceType = new HashMap<>();
			HashMap<OutwallType, RiskLevel> riskLevelAccordingToOutwallType = new HashMap<>();
			HashMap<PillarType, RiskLevel> riskLevelAccordingToPillarType = new HashMap<>();
			HashMap<RoofType, RiskLevel> riskLevelAccordingToRoofType = new HashMap<>();
			HashMap<HouseType, RiskLevel> riskLevelAccordingToHouseType = new HashMap<>();
			HashMap<HomeSquareMeter, RiskLevel> riskLevelAccordingToSquareMeter = new HashMap<>();
			HashMap<HomeCompensation, RiskLevel> riskLevelAccordingToCompensation = new HashMap<>();
			int numToMultiplyForMinCompensation;
			int numToMultiplyForMaxCompensation;
			int numToMultiplyForPayment;
			String riskLevelChoice;
			RiskLevel riskLevel = null;

			System.out.println("******************** 주택화재보험 계산식 수립 *********************");
			System.out.println("뒤로가기를 원하시면 0을 입력하세요.");

			System.out.println("계산식의 이름을 입력하세요.");
			calculationFormulaName = userInput.readLine().trim();
			if(calculationFormulaName.contains(" ")||calculationFormulaName.isEmpty()) {
				System.out.println("! 잘못된 입력입니다."); continue;
			}
			if(calculationFormulaName.equals("0")) return;
			try {serviceContainer.getCalculationFormulaService().checkNameDuplication(calculationFormulaName);}
			catch (DataDuplicationException e) {System.out.println(e.getMessage()); continue;}

			System.out.println("거주 유형에 따른 위험도(1~10)를 입력하세요.");
			for (int i = 0; i<ResidenceType.values().length; i++) {
				System.out.print(ResidenceType.values()[i].getName() + " : ");
				String choice = userInput.readLine().trim();
				if(choice.equals("0")) return;
				riskLevel = choiceRiskLevel(choice);
				if(riskLevel==null) {System.out.println("! 잘못된 입력입니다."); break;}
				riskLevelAccordingToResidenceType.put(ResidenceType.values()[i], riskLevel);
			}
			if(riskLevel==null) continue;

			System.out.println("주택 유형에 따른 위험도(1~10)를 입력하세요.");
			for (int i = 0; i<HouseType.values().length; i++) {
				System.out.print(HouseType.values()[i].getName() + " : ");
				String choice = userInput.readLine().trim();
				if(choice.equals("0")) return;
				riskLevel = choiceRiskLevel(choice);
				if(riskLevel==null) {System.out.println("! 잘못된 입력입니다."); break;}
				riskLevelAccordingToHouseType.put(HouseType.values()[i], riskLevel);
			}
			if(riskLevel==null) continue;

			System.out.println("평수에 따른 위험도(1~10)를 입력하세요.");
			for (int i = HomeSquareMeter.values().length-1; i>=0; i--) {
				System.out.print(HomeSquareMeter.values()[i].getName() + " : ");
				String choice = userInput.readLine().trim();
				if(choice.equals("0")) return;
				riskLevel = choiceRiskLevel(choice);
				if(riskLevel==null) {System.out.println("! 잘못된 입력입니다."); break;}
				riskLevelAccordingToSquareMeter.put(HomeSquareMeter.values()[i], riskLevel);
			}
			if(riskLevel==null) continue;

			System.out.println("기둥 유형에 따른 위험도(1~10)를 입력하세요.");
			for (int i = 0; i<PillarType.values().length; i++) {
				System.out.print(PillarType.values()[i].getName() + " : ");
				String choice = userInput.readLine().trim();
				if(choice.equals("0")) return;
				riskLevel = choiceRiskLevel(choice);
				if(riskLevel==null) {System.out.println("! 잘못된 입력입니다."); break;}
				riskLevelAccordingToPillarType.put(PillarType.values()[i], riskLevel);
			}
			if(riskLevel==null) continue;

			System.out.println("지붕 유형에 따른 위험도(1~10)를 입력하세요.");
			for (int i = 0; i<RoofType.values().length; i++) {
				System.out.print(RoofType.values()[i].getName() + " : ");
				String choice = userInput.readLine().trim();
				if(choice.equals("0")) return;
				riskLevel = choiceRiskLevel(choice);
				if(riskLevel==null) {System.out.println("! 잘못된 입력입니다."); break;}
				riskLevelAccordingToRoofType.put(RoofType.values()[i], riskLevel);
			}
			if(riskLevel==null) continue;

			System.out.println("외벽 유형에 따른 위험도(1~10)를 입력하세요.");
			for (int i = 0; i<OutwallType.values().length; i++) {
				System.out.print(OutwallType.values()[i].getName() + " : ");
				String choice = userInput.readLine().trim();
				if(choice.equals("0")) return;
				riskLevel = choiceRiskLevel(choice);
				if(riskLevel==null) {System.out.println("! 잘못된 입력입니다."); break;}
				riskLevelAccordingToOutwallType.put(OutwallType.values()[i], riskLevel);
			}
			if(riskLevel==null) continue;

			System.out.println("보상금에 따른 위험도(1~10)를 입력하세요.");
			for (int i = HomeCompensation.values().length-1; i>=0; i--) {
				System.out.print(HomeCompensation.values()[i].getName() + " : ");
				String choice = userInput.readLine().trim();
				if(choice.equals("0")) return;
				riskLevel = choiceRiskLevel(choice);
				if(riskLevel==null) {System.out.println("! 잘못된 입력입니다."); break;}
				riskLevelAccordingToCompensation.put(HomeCompensation.values()[i], riskLevel);
			}
			if(riskLevel==null) continue;

			System.out.println("보험료 계산식을 수립하세요.");

			System.out.println("보험료 계산식은 [고객의 총 위험도] X [보험료 산출 상수]입니다.");
			System.out.println("[보험료 산출 상수]를 입력하세요.");
			try {numToMultiplyForPayment = Integer.parseInt(userInput.readLine());}
			catch (NumberFormatException e) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(numToMultiplyForPayment==0) return;

			System.out.println("최소 보상금 계산식을 수립하세요.");
			System.out.println("최소 보상금 계산식 [면적(㎡)] X [최소 보상금 산출 상수]입니다.");
			System.out.println("[최소 보상금 산출 상수]를 입력하세요.");
			try {numToMultiplyForMinCompensation = Integer.parseInt(userInput.readLine());}
			catch (NumberFormatException e) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(numToMultiplyForMinCompensation==0) return;

			System.out.println("최대 보상금 계산식을 수립하세요.");
			System.out.println("최대 보상금 계산식은 [면적(㎡)] X [최대 보상금 산출 상수]입니다.");
			System.out.println("[최대 보상금 산출 상수]를 입력하세요. [최대 보상금 산출 상수]는 [최소 보상금 산출 상수]보다 커야 합니다." );
			try {numToMultiplyForMaxCompensation = Integer.parseInt(userInput.readLine());}
			catch (NumberFormatException e) {System.out.println("! 잘못된 입력입니다.");  continue;}
			if(numToMultiplyForMaxCompensation==0) return;
			if(numToMultiplyForMaxCompensation <= numToMultiplyForMinCompensation) {
				System.out.println("! 잘못된 입력입니다.");
				System.out.println("[최대 보상금 산출 상수]는 [최소 보상금 산출 상수]보다 커야 합니다.");
				continue;
			}
			HomeFormula homeFormula = new HomeFormula(calculationFormulaName, riskLevelAccordingToPillarType,
					riskLevelAccordingToRoofType, riskLevelAccordingToOutwallType,
					numToMultiplyForMinCompensation, numToMultiplyForMaxCompensation,
					numToMultiplyForPayment, riskLevelAccordingToResidenceType,
					riskLevelAccordingToHouseType, riskLevelAccordingToSquareMeter,
					riskLevelAccordingToCompensation);
			int id = 0;
			try{id = serviceContainer.getCalculationFormulaService().makeFormula(homeFormula);}
			catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
			catch (DataDuplicationException e) {System.out.println(e.getMessage()); return;}
			if(id == 0) {System.out.println("계산식 수립에 실패하였습니다."); return;}
			System.out.println("계산식 수립이 완료되었습니다. 계산식 아이디는 " + id + "입니다.");
			return;
		}
	}

	private void printWorkplaceFormulaForm() throws IOException {
		while (true) {
			String calculationFormulaName;
			HashMap<PillarType, RiskLevel> riskLevelAccordingToPillarType = new HashMap<>();
			HashMap<RoofType, RiskLevel> riskLevelAccordingToRoofType = new HashMap<>();
			HashMap<OutwallType, RiskLevel> riskLevelAccordingToOutwallType = new HashMap<>();
			HashMap<WorkplaceUsage, RiskLevel> riskLevelAccordingToUsage = new HashMap<>();
			HashMap<Floor, RiskLevel> riskLevelAccordingToNumOfFloors = new HashMap<>();
			HashMap<WorkplaceSquareMeter, RiskLevel> riskLevelAccordingToSquareFeet = new HashMap<>();
			HashMap<WorkplaceCompensation, RiskLevel> riskLevelAccordingToCompensation = new HashMap<>();
			int numToMultiplyForMinCompensation;
			int numToMultiplyForMaxCompensation;
			int numToMultiplyForPayment;
			RiskLevel riskLevel = null;

			System.out.println("******************** 사업장화재보험 계산식 수립 *********************");
			System.out.println("뒤로가기를 원하시면 0을 입력하세요.");

			System.out.println("계산식의 이름을 입력하세요.");
			calculationFormulaName = userInput.readLine().trim();
			if(calculationFormulaName.contains(" ")||calculationFormulaName.isEmpty()) {
				System.out.println("! 잘못된 입력입니다."); continue;
			}
			if(calculationFormulaName.equals("0")) return;
			try {serviceContainer.getCalculationFormulaService().checkNameDuplication(calculationFormulaName);}
			catch (DataDuplicationException e) {System.out.println(e.getMessage()); continue;}

			System.out.println("건물용도에 따른 위험도(1~10)를 입력하세요.");
			for (int i = 0; i<WorkplaceUsage.values().length; i++) {
				System.out.print(WorkplaceUsage.values()[i].getName() + " : ");
				String choice = userInput.readLine().trim();
				if(choice.equals("0")) return;
				riskLevel = choiceRiskLevel(choice);
				if(riskLevel==null) {System.out.println("! 잘못된 입력입니다."); break;}
				riskLevelAccordingToUsage.put(WorkplaceUsage.values()[i], riskLevel);
			}
			if(riskLevel==null) continue;

			System.out.println("평수에 따른 위험도(1~10)를 입력하세요.");
			for (int i = WorkplaceSquareMeter.values().length-1; i>=0; i--) {
				System.out.print(WorkplaceSquareMeter.values()[i].getName() + " : ");
				String choice = userInput.readLine().trim();
				if(choice.equals("0")) return;
				riskLevel = choiceRiskLevel(choice);
				if(riskLevel==null) {System.out.println("! 잘못된 입력입니다."); break;}
				riskLevelAccordingToSquareFeet.put(WorkplaceSquareMeter.values()[i], riskLevel);
			}
			if(riskLevel==null) continue;

			System.out.println("층수에 따른 위험도(1~10)를 입력하세요.");
			for (int i = Floor.values().length-1; i>=0; i--) {
				System.out.print(Floor.values()[i].getName() + " : ");
				String choice = userInput.readLine().trim();
				if(choice.equals("0")) return;
				riskLevel = choiceRiskLevel(choice);
				if(riskLevel==null) {System.out.println("! 잘못된 입력입니다."); break;}
				riskLevelAccordingToNumOfFloors.put(Floor.values()[i], riskLevel);
			}
			if(riskLevel==null) continue;

			System.out.println("기둥 유형에 따른 위험도(1~10)를 입력하세요.");
			for (int i = 0; i<PillarType.values().length; i++) {
				System.out.print(PillarType.values()[i].getName() + " : ");
				String choice = userInput.readLine().trim();
				if(choice.equals("0")) return;
				riskLevel = choiceRiskLevel(choice);
				if(riskLevel==null) {System.out.println("! 잘못된 입력입니다."); break;}
				riskLevelAccordingToPillarType.put(PillarType.values()[i], riskLevel);
			}
			if(riskLevel==null) continue;

			System.out.println("지붕 유형에 따른 위험도(1~10)를 입력하세요.");
			for (int i = 0; i<RoofType.values().length; i++) {
				System.out.print(RoofType.values()[i].getName() + " : ");
				String choice = userInput.readLine().trim();
				if(choice.equals("0")) return;
				riskLevel = choiceRiskLevel(choice);
				if(riskLevel==null) {System.out.println("! 잘못된 입력입니다."); break;}
				riskLevelAccordingToRoofType.put(RoofType.values()[i], riskLevel);
			}
			if(riskLevel==null) continue;

			System.out.println("외벽 유형에 따른 위험도(1~10)를 입력하세요.");
			for (int i = 0; i<OutwallType.values().length; i++) {
				System.out.print(OutwallType.values()[i].getName() + " : ");
				String choice = userInput.readLine().trim();
				if(choice.equals("0")) return;
				riskLevel = choiceRiskLevel(choice);
				if(riskLevel==null) {System.out.println("! 잘못된 입력입니다."); break;}
				riskLevelAccordingToOutwallType.put(OutwallType.values()[i], riskLevel);
			}
			if(riskLevel==null) continue;

			System.out.println("보상금에 따른 위험도(1~10)를 입력하세요");
			for (int i = WorkplaceCompensation.values().length-1; i>=0; i--) {
				System.out.print(WorkplaceCompensation.values()[i].getName() + " : ");
				String choice = userInput.readLine().trim();
				if(choice.equals("0")) return;
				riskLevel = choiceRiskLevel(choice);
				if(riskLevel==null) {System.out.println("! 잘못된 입력입니다."); break;}
				riskLevelAccordingToCompensation.put(WorkplaceCompensation.values()[i], riskLevel);
			}
			if(riskLevel==null) continue;

			System.out.println("보험료 계산식을 수립하세요.");

			System.out.println("보험료 계산식은 [고객의 총 위험도] X [보험료 산출 상수]입니다.");
			System.out.println("[보험료 산출 상수]를 입력하세요.");
			try {numToMultiplyForPayment = Integer.parseInt(userInput.readLine());}
			catch (NumberFormatException e) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(numToMultiplyForPayment==0) return;

			System.out.println("최소 보상금 계산식을 수립하세요.");
			System.out.println("최소 보상금 계산식은 [면적(㎡)] X [최소 보상금 산출 상수]입니다.");
			System.out.println("[최소 보상금 산출 상수]를 입력하세요.");
			try {numToMultiplyForMinCompensation = Integer.parseInt(userInput.readLine());}
			catch (NumberFormatException e) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(numToMultiplyForPayment==0) return;

			System.out.println("최대 보상금 계산식을 수립하세요.");
			System.out.println("최대 보상금 계산식은 [면적(㎡)] X [최대 보상금 산출 상수]입니다.");
			System.out.println("[최대 보상금 산출 상수]를 입력하세요. [최대 보상금 산출 상수]는 [최소 보상금 산출 상수]보다 커야 합니다." );
			try {numToMultiplyForMaxCompensation = Integer.parseInt(userInput.readLine());}
			catch (NumberFormatException e) {System.out.println("! 잘못된 입력입니다.");  continue;}
			if(numToMultiplyForPayment==0) return;
			if(numToMultiplyForMaxCompensation <= numToMultiplyForMinCompensation) {
				System.out.println("! 잘못된 입력입니다.");
				System.out.println("[최대 보상금 산출 상수]는 [최소 보상금 산출 상수]보다 커야 합니다.");
				continue;
			}
			WorkplaceFormula workFormula = new WorkplaceFormula(calculationFormulaName, riskLevelAccordingToPillarType,
					riskLevelAccordingToRoofType, riskLevelAccordingToOutwallType,
					numToMultiplyForMinCompensation, numToMultiplyForMaxCompensation,
					numToMultiplyForPayment, riskLevelAccordingToUsage,
					riskLevelAccordingToNumOfFloors, riskLevelAccordingToSquareFeet,
					riskLevelAccordingToCompensation);
			int id = 0;
			try{id = serviceContainer.getCalculationFormulaService().makeFormula(workFormula);}
			catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
			catch (DataDuplicationException e) {System.out.println(e.getMessage()); return;}
			if(id == 0) {System.out.println("게산식 수립에 실패하였습니다."); return;}
			System.out.println("계산식 수립이 완료되었습니다. 계산식 아이디는 " + id + "입니다.");
			return;
		}
	}

	private RiskLevel choiceRiskLevel(String choice)   {
		switch(choice) {
			case "1" : return RiskLevel.One;
			case "2" : return RiskLevel.Two;
			case "3" : return RiskLevel.Three;
			case "4" : return RiskLevel.Four;
			case "5" : return RiskLevel.Five;
			case "6" : return RiskLevel.Six;
			case "7" : return RiskLevel.Seven;
			case "8" : return RiskLevel.Eight;
			case "9" : return RiskLevel.Nine;
			case "10" : return RiskLevel.Ten;
			default : return null;
		}
	}

	private void printDevelopeMenu() throws IOException {
		while(true) {
			System.out.println("******************** 상품 개발 메뉴 *********************");
			System.out.println("보험 종류를 선택하세요.");
			for(int i = 0; i < InsuranceType.values().length; i++) {
				System.out.println((i + 1) + ". " + InsuranceType.values()[i].getName());
			}
			System.out.println("0. 뒤로가기");
			switch(userInput.readLine().trim()) {
				case "1" : printDevelopeForm(InsuranceType.values()[0]); return;
				case "2" : printDevelopeForm(InsuranceType.values()[1]); return;
				case "0": return;
				default : System.out.println("! 잘못된 입력입니다."); continue;
			}
		}
	}

	private void printDevelopeForm(InsuranceType insuranceType) throws IOException {
		while (true) {
			ArrayList<CalculationFormula> calculationFormulaList;
			try {calculationFormulaList = serviceContainer.getCalculationFormulaService().getCalculationFormulaList(insuranceType);}
			catch (EmptyListException | TimeDelayException e) {System.out.println(e.getMessage()); return;}
			catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
			System.out.println("******************** 상품 개발 양식 *********************");
			System.out.println("뒤로가기를 원하시면 0을 입력하세요.");

			System.out.println("보험료&보상금 계산식 목록을 조회하고 계산식을 선택하세요.");
			System.out.println("아이디\t이름");
			for(CalculationFormula calculationFormula : calculationFormulaList) {
				System.out.println(calculationFormula.getId() +
						"\t" + calculationFormula.getName());
			}
			System.out.print("계산식 아이디 : ");
			int formulaId;
			try {formulaId = Integer.parseInt(userInput.readLine().trim());}
			catch (NumberFormatException e) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(formulaId==0) return;
			boolean isExistId = false;
			for(CalculationFormula calculationFormula : calculationFormulaList) {
				if(calculationFormula.getId()==formulaId)isExistId = true;
			}
			if(!isExistId){System.out.println("! 잘못된 입력입니다."); continue;}

			System.out.print("이름 : ");
			String name = userInput.readLine().trim();
			if(name.contains(" ")||name.isEmpty()) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(name.equals("0")) return;
			try {serviceContainer.getInsuranceService().checkNameDuplication(name);}
			catch (DataDuplicationException e) {System.out.println(e.getMessage()); continue;}

			System.out.print("가입대상자 : ");
			String target = userInput.readLine().trim();
			if(target.isEmpty()) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(target.equals("0")) return;

			System.out.print("보상 조건 : ");
			String compensateCondition = userInput.readLine().trim();
			if(compensateCondition.isEmpty()) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(compensateCondition.equals("0")) return;

			System.out.print("비보상 조건 : ");
			String notCompensateCondition = userInput.readLine().trim();
			if(notCompensateCondition.isEmpty()) {System.out.println("! 잘못된 입력입니다."); continue;}
			if(notCompensateCondition.equals("0")) return;

			Insurance insurance = new Insurance(name,insuranceType,target,formulaId, compensateCondition, notCompensateCondition, InsuranceStatus.UnderAuthorize);
			int id = 0;
			try{id = serviceContainer.getInsuranceService().makeInsurance(insurance);}
			catch (DataDuplicationException e) {System.out.println(e.getMessage()); return;}
			catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
			if(id==0) {System.out.println("개발 및 인가요청이 실패되었습니다."); return;}
			System.out.println("개발 및 인가요청이 완료되었습니다. 상품 아이디는 " + id + "입니다." );
			return;
		}
	}

	private void printExamineResult()   {
		System.out.println("******************** 인가 요청 결과 *********************");
		ArrayList<Insurance> authorizationResultList = null;
		try {authorizationResultList = serviceContainer.getInsuranceService().getInsuranceList();}
		catch (EmptyListException | TimeDelayException e) {System.out.println(e.getMessage()); return;}
		catch (RemoteException | NullPointerException e) {System.out.println("! 해당 서비스는 현재 이용하실 수 없습니다."); return;}
		System.out.println("아이디\t이름\t유형\t심사결과");
		for(Insurance insurance : authorizationResultList){
			System.out.println(insurance.getId()
					+ "\t" + insurance.getName()
					+ "\t" + insurance.getType().getName()
					+ "\t" + insurance.getStatus().getName());
		}
	}
}
