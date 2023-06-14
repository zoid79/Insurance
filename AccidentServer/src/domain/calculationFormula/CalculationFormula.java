package domain.calculationFormula;

import enumeration.calculationFormula.OutwallType;
import enumeration.calculationFormula.PillarType;
import enumeration.calculationFormula.RiskLevel;
import enumeration.calculationFormula.RoofType;

import java.io.Serializable;
import java.util.HashMap;

public abstract class CalculationFormula implements Serializable {

	private int id;
	private String name;
	private HashMap<PillarType, RiskLevel> riskLevelAccordingToPillarType;
	private HashMap<RoofType, RiskLevel> riskLevelAccordingToRoofType;
	private HashMap<OutwallType, RiskLevel> riskLevelAccordingToOutwallType;
	private int multiplierForMinCompensation;
	private int multiplierForMaxCompensation;
	private int multiplierForPayment;
	
	public CalculationFormula(String calculationFormulaName, HashMap<PillarType, RiskLevel> riskLevelAccordingToPillarType,
							  HashMap<RoofType, RiskLevel> riskLevelAccordingToRoofType,
							  HashMap<OutwallType, RiskLevel> riskLevelAccordingToOutwallType, int multiplierForMinCompensation,
							  int multiplierForMaxCompensation, int multiplierForPayment) {
		this.name = calculationFormulaName;
		this.riskLevelAccordingToPillarType = riskLevelAccordingToPillarType;
		this.riskLevelAccordingToRoofType = riskLevelAccordingToRoofType;
		this.riskLevelAccordingToOutwallType = riskLevelAccordingToOutwallType;
		this.multiplierForMinCompensation = multiplierForMinCompensation;
		this.multiplierForMaxCompensation = multiplierForMaxCompensation;
		this.multiplierForPayment = multiplierForPayment;
	}	

	public int getId() {
		return id;
	}

	public void setId(int calculationFormulaId) {
		this.id = calculationFormulaId;
	}

	public HashMap<PillarType, RiskLevel> getRiskLevelAccordingToPillarType() {
		return riskLevelAccordingToPillarType;
	}

	public void setRiskLevelAccordingToPillarType(HashMap<PillarType, RiskLevel> riskLevelAccordingToPillarType) {
		this.riskLevelAccordingToPillarType = riskLevelAccordingToPillarType;
	}

	public HashMap<RoofType, RiskLevel> getRiskLevelAccordingToRoofType() {
		return riskLevelAccordingToRoofType;
	}

	public void setRiskLevelAccordingToRoofType(HashMap<RoofType, RiskLevel> riskLevelAccordingToRoofType) {
		this.riskLevelAccordingToRoofType = riskLevelAccordingToRoofType;
	}

	public HashMap<OutwallType, RiskLevel> getRiskLevelAccordingToOutwallType() {
		return riskLevelAccordingToOutwallType;
	}

	public void setRiskLevelAccordingToOutwallType(HashMap<OutwallType, RiskLevel> riskLevelAccordingToOutwallType) {
		this.riskLevelAccordingToOutwallType = riskLevelAccordingToOutwallType;
	}

	public int getMultiplierForMinCompensation() {
		return multiplierForMinCompensation;
	}

	public void setMultiplierForMinCompensation(int multiplierForMinCompensation) {
		this.multiplierForMinCompensation = multiplierForMinCompensation;
	}

	public int getMultiplierForMaxCompensation() {
		return multiplierForMaxCompensation;
	}

	public void setMultiplierForMaxCompensation(int multiplierForMaxCompensation) {
		this.multiplierForMaxCompensation = multiplierForMaxCompensation;
	}

	public int getMultiplierForPayment() {
		return multiplierForPayment;
	}

	public void setMultiplierForPayment(int multiplierForPayment) {
		this.multiplierForPayment = multiplierForPayment;
	}

	public String getName() {
		return name;
	}

	public void setName(String calculationFormulaName) {
		this.name = calculationFormulaName;
	}

}