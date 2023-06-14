package domain;

import enumeration.insurance.InsuranceStatus;
import enumeration.insurance.InsuranceType;

import java.io.Serializable;

public class Insurance implements Serializable {

	private int id;
	private String name;
	private InsuranceType type;
	private String target;
	private int calculationFormulaId;
	private String compensateCondition;
	private String notCompensateCondition;
	private InsuranceStatus status;

	public Insurance(String name, InsuranceType type, String target, int calculationFormulaId,
					 String compensateCondition, String notCompensateCondition, InsuranceStatus status) {
		this.name = name;
		this.type = type;
		this.target = target;
		this.calculationFormulaId = calculationFormulaId;
		this.compensateCondition = compensateCondition;
		this.notCompensateCondition = notCompensateCondition;
		this.status = status;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InsuranceType getType() {
		return type;
	}

	public void setType(InsuranceType type) {
		this.type = type;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getCalculationFormulaId() {
		return calculationFormulaId;
	}

	public void setCalculationFormulaId(int calculationFormulaId) {
		this.calculationFormulaId = calculationFormulaId;
	}

	public String getCompensateCondition() {
		return compensateCondition;
	}

	public void setCompensateCondition(String compensateCondition) {
		this.compensateCondition = compensateCondition;
	}

	public String getNotCompensateCondition() {
		return notCompensateCondition;
	}

	public void setNotCompensateCondition(String notCompensateCondition) {
		this.notCompensateCondition = notCompensateCondition;
	}

	public InsuranceStatus getStatus() {
		return status;
	}

	public void setStatus(InsuranceStatus status) {
		this.status = status;
	}

}