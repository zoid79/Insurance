package dao;

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
import enumeration.insurance.InsuranceType;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class CalculationFormulaDao extends Dao {
	public CalculationFormulaDao() {
		super();
	}
	public boolean create(CalculationFormula calculationFormula) {
		try {
			PreparedStatement ps = null;
			ps = connection.prepareStatement("insert into calculationformula values (?, ?, ?, ?, ?, ?, ?, ?) ");
			ps.setInt(1, calculationFormula.getId());
			ps.setString(2, calculationFormula.getName());
			ps.setBytes(3, serialize(calculationFormula.getRiskLevelAccordingToPillarType()));
			ps.setBytes(4, serialize(calculationFormula.getRiskLevelAccordingToRoofType()));
			ps.setBytes(5, serialize(calculationFormula.getRiskLevelAccordingToOutwallType()));
			ps.setInt(6, calculationFormula.getMultiplierForMinCompensation());
			ps.setInt(7, calculationFormula.getMultiplierForMaxCompensation());
			ps.setInt(8,calculationFormula.getMultiplierForPayment());
			ps.executeUpdate();
			ps.close();
			if(calculationFormula instanceof HomeFormula){
				HomeFormula homeFormula = (HomeFormula) calculationFormula;
				ps = connection.prepareStatement("insert into homeformula values (?, ?, ?, ?, ?) ");
				ps.setInt(1, homeFormula.getId());
				ps.setBytes(2, serialize(homeFormula.getRiskLevelAccordingToResidenceType()));
				ps.setBytes(3, serialize(homeFormula.getRiskLevelAccordingToHouseType()));
				ps.setBytes(4, serialize(homeFormula.getRiskLevelAccordingToSquareMeter()));
				ps.setBytes(5, serialize(homeFormula.getRiskLevelAccordingToCompensation()));
				ps.executeUpdate();
				ps.close();
			}else{
				WorkplaceFormula workplaceFormula = (WorkplaceFormula) calculationFormula;
				ps = connection.prepareStatement("insert into workplaceformula values (?, ?, ?, ?, ?) ");
				ps.setInt(1, workplaceFormula.getId());
				ps.setBytes(2, serialize(workplaceFormula.getRiskLevelAccordingToUsage()));
				ps.setBytes(3, serialize(workplaceFormula.getRiskLevelAccordingToFloor()));
				ps.setBytes(4, serialize(workplaceFormula.getRiskLevelAccordingToSquareMeter()));
				ps.setBytes(5, serialize(workplaceFormula.getRiskLevelAccordingToCompensation()));
				ps.executeUpdate();
				ps.close();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public ArrayList<CalculationFormula> retrieve(){
		try {
			ArrayList<CalculationFormula> calculationFormulaList = new ArrayList<>();
			PreparedStatement ps = null;
			ps = connection.prepareStatement("select * from calculationformula, homeformula where calculationformula.id=homeformula.id;");
			resultSet = ps.executeQuery();
			while (resultSet.next()){
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				HashMap<PillarType, RiskLevel> riskLevelAccordingToPillarType
						= (HashMap<PillarType, RiskLevel>) deSerialize(resultSet.getBytes("riskLevelAccordingToPillarType"));
				HashMap<RoofType, RiskLevel> riskLevelAccordingToRoofType
						= (HashMap<RoofType, RiskLevel>) deSerialize(resultSet.getBytes("riskLevelAccordingToRoofType"));
				HashMap<OutwallType, RiskLevel> riskLevelAccordingToOutwallType
						= (HashMap<OutwallType, RiskLevel>) deSerialize(resultSet.getBytes("riskLevelAccordingToOutwallType"));
				int multiplierForMinCompensation = resultSet.getInt("multiplierForMinCompensation");
				int multiplierForMaxCompensation = resultSet.getInt("multiplierForMaxCompensation");
				int multiplierForPayment = resultSet.getInt("multiplierForPayment");
				HashMap<ResidenceType, RiskLevel> riskLevelAccordingToResidenceType
						= (HashMap<ResidenceType, RiskLevel>) deSerialize(resultSet.getBytes("riskLevelAccordingToResidenceType"));
				HashMap<HouseType, RiskLevel> riskLevelAccordingToHouseType
						= (HashMap<HouseType, RiskLevel>) deSerialize(resultSet.getBytes("riskLevelAccordingToHouseType"));
				HashMap<HomeSquareMeter, RiskLevel> riskLevelAccordingToSquareMeter
						= (HashMap<HomeSquareMeter, RiskLevel>) deSerialize(resultSet.getBytes("riskLevelAccordingToSquareMeter"));
				HashMap<HomeCompensation, RiskLevel> riskLevelAccordingToCompensation
						= (HashMap<HomeCompensation, RiskLevel>) deSerialize(resultSet.getBytes("riskLevelAccordingToCompensation"));
				HomeFormula homeFormula = new HomeFormula(
						name,riskLevelAccordingToPillarType,riskLevelAccordingToRoofType,
						riskLevelAccordingToOutwallType,multiplierForMinCompensation,
						multiplierForMaxCompensation,multiplierForPayment,
						riskLevelAccordingToResidenceType, riskLevelAccordingToHouseType,
						riskLevelAccordingToSquareMeter, riskLevelAccordingToCompensation
				);
				homeFormula.setId(id);
				calculationFormulaList.add(homeFormula);
			}
			ps.close();
			resultSet.close();

			ps = connection.prepareStatement("select * from calculationformula, workplaceformula where calculationformula.id=workplaceformula.id;");
			resultSet = ps.executeQuery();
			while (resultSet.next()){
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				HashMap<PillarType, RiskLevel> riskLevelAccordingToPillarType
						= (HashMap<PillarType, RiskLevel>) deSerialize(resultSet.getBytes("riskLevelAccordingToPillarType"));
				HashMap<RoofType, RiskLevel> riskLevelAccordingToRoofType
						= (HashMap<RoofType, RiskLevel>) deSerialize(resultSet.getBytes("riskLevelAccordingToRoofType"));
				HashMap<OutwallType, RiskLevel> riskLevelAccordingToOutwallType
						= (HashMap<OutwallType, RiskLevel>) deSerialize(resultSet.getBytes("riskLevelAccordingToOutwallType"));
				int multiplierForMinCompensation = resultSet.getInt("multiplierForMinCompensation");
				int multiplierForMaxCompensation = resultSet.getInt("multiplierForMaxCompensation");
				int multiplierForPayment = resultSet.getInt("multiplierForPayment");
				HashMap<WorkplaceUsage, RiskLevel> riskLevelAccordingToUsage
						= (HashMap<WorkplaceUsage, RiskLevel>) deSerialize(resultSet.getBytes("riskLevelAccordingToUsage"));
				HashMap<Floor, RiskLevel> riskLevelAccordingToFloor
						= (HashMap<Floor, RiskLevel>) deSerialize(resultSet.getBytes("riskLevelAccordingToFloor"));
				HashMap<WorkplaceSquareMeter, RiskLevel> riskLevelAccordingToSquareFeet
						= (HashMap<WorkplaceSquareMeter, RiskLevel>) deSerialize(resultSet.getBytes("riskLevelAccordingToSquareFeet"));
				HashMap<WorkplaceCompensation, RiskLevel> riskLevelAccordingToCompensation
						= (HashMap<WorkplaceCompensation, RiskLevel>) deSerialize(resultSet.getBytes("riskLevelAccordingToCompensation"));
				WorkplaceFormula workplaceFormula = new WorkplaceFormula(
						name,riskLevelAccordingToPillarType,riskLevelAccordingToRoofType,
						riskLevelAccordingToOutwallType,multiplierForMinCompensation,
						multiplierForMaxCompensation,multiplierForPayment,
						riskLevelAccordingToUsage, riskLevelAccordingToFloor,
						riskLevelAccordingToSquareFeet, riskLevelAccordingToCompensation
				);
				workplaceFormula.setId(id);
				calculationFormulaList.add(workplaceFormula);
			}
			ps.close();
			resultSet.close();
			return calculationFormulaList;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	public int add(CalculationFormula calculationFormula) {
		ArrayList<CalculationFormula> calculationFormulaList = retrieve();
		if(calculationFormulaList.size()==0) calculationFormula.setId(1);
		else {calculationFormula.setId(getLastId(calculationFormulaList)+1);}
		if(create(calculationFormula)) return calculationFormula.getId();
		else {return 0;}
	}
	public CalculationFormula findById(int id) {
		for(CalculationFormula calculationformula : retrieve()) {
			if(calculationformula.getId() == id) return calculationformula;
		}
		return null;
	}
	public CalculationFormula findByName(String name) {
		for(CalculationFormula calculationformula : retrieve()) {
			if(calculationformula.getName().equals(name)) return calculationformula;
		}
		return null;
	}
	public ArrayList<CalculationFormula> findByType(InsuranceType insuranceType) {
		ArrayList<CalculationFormula> formulaListByType = new ArrayList<>();
		for(CalculationFormula calculationFormula : retrieve()) {
			if(insuranceType==InsuranceType.HomeFire) {
				if(calculationFormula instanceof HomeFormula) {formulaListByType.add(calculationFormula);}}
			if(insuranceType==InsuranceType.WorkplaceFire) {
				if(calculationFormula instanceof WorkplaceFormula) {formulaListByType.add(calculationFormula);}}
		}
		return formulaListByType;
	}
	public byte[] serialize(Object object) {
		byte[] byteArray = null;
		ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutStream;
		try {
			objectOutStream = new ObjectOutputStream(byteOutStream);
			objectOutStream.writeObject(object);
			byteArray = byteOutStream.toByteArray();
			objectOutStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteArray;
	}

	public Object deSerialize(byte[] byteArray) throws IOException {
		Object object = null;
		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
		ObjectInputStream objectInStream;
		try {
			objectInStream = new ObjectInputStream(byteInputStream);
			object = objectInStream.readObject();
			objectInStream.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return object ;
	}

	public int getLastId(ArrayList<CalculationFormula> calculationFormulaList){
		int id = 0;
		for(CalculationFormula calculationFormula : calculationFormulaList){
			if(calculationFormula.getId() > id) id = calculationFormula.getId();
		}
		return id;
	}
}