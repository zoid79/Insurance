package domain;

import enumeration.contract.ContractStatus;
import enumeration.contract.ContractTerm;
import enumeration.contract.PaymentCycle;

import java.io.Serializable;
import java.sql.Timestamp;

public class Contract implements Serializable {
    private int id;
    private int customerInfoId;
    private String customerId;
    private int insuranceId;
    private String saleEmployeeId;
    private ContractTerm term;
    private Timestamp startDate;
    private Timestamp expirationDate;
    private int paymentFee;
    private PaymentCycle paymentCycle;
    private Timestamp paymentDeadline;
    private int compensation;
    private ContractStatus contractStatus;

    public Contract(int customerInfoId, int insuranceId, String saleEmployeeId, String customerId, ContractTerm term, int paymentFee, PaymentCycle paymentCycle, int compensation, ContractStatus contractStatus) {
        this.customerInfoId = customerInfoId;
        this.insuranceId = insuranceId;
        this.saleEmployeeId = saleEmployeeId;
        this.customerId=customerId;
        this.term = term;
        this.paymentFee = paymentFee;
        this.paymentCycle = paymentCycle;
        this.compensation = compensation;
        this.contractStatus = contractStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerInfoId() {
        return customerInfoId;
    }

    public void setCustomerInfoId(int customerInfoId) {
        this.customerInfoId = customerInfoId;
    }

    public int getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(int insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getSaleEmployeeId() {
        return saleEmployeeId;
    }

    public void setSaleEmployeeId(String saleEmployeeId) {
        this.saleEmployeeId = saleEmployeeId;
    }

    public ContractTerm getTerm() {
        return term;
    }

    public void setTerm(ContractTerm term) {
        this.term = term;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getPaymentFee() {
        return paymentFee;
    }

    public void setPaymentFee(int paymentFee) {
        this.paymentFee = paymentFee;
    }

    public PaymentCycle getPayCycle() {
        return paymentCycle;
    }

    public void setPayCycle(PaymentCycle paymentCycle) {
        this.paymentCycle = paymentCycle;
    }

    public Timestamp getPaymentDeadline() {
        return paymentDeadline;
    }

    public void setPaymentDeadline(Timestamp paymentDeadline) {
        this.paymentDeadline = paymentDeadline;
    }

    public int getCompensation() {
        return compensation;
    }

    public void setCompensation(int compensation) {
        this.compensation = compensation;
    }

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

    public PaymentCycle getPaymentCycle() {
        return paymentCycle;
    }

    public void setPaymentCycle(PaymentCycle paymentCycle) {
        this.paymentCycle = paymentCycle;
    }
}
