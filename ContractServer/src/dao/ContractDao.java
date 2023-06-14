package dao;

import domain.Contract;
import enumeration.contract.ContractStatus;
import enumeration.contract.ContractTerm;
import enumeration.contract.PaymentCycle;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ContractDao extends Dao {
    public ContractDao()   {
        super();
    }
    public boolean create(Contract contract) {
        String query ="insert into contract values ("+
                contract.getId()+", "+
                contract.getCustomerInfoId()+", "+
                "'"+contract.getCustomerId()+"'"+", "+
                contract.getInsuranceId()+", "+
                "'"+contract.getSaleEmployeeId()+"'"+", "+
                contract.getTerm().getYear()+", "+
                contract.getStartDate()+", "+
                contract.getExpirationDate()+", "+
                contract.getPaymentFee()+", "+
                contract.getPaymentCycle().getMonth()+", "+
                contract.getPaymentDeadline()+", "+
                contract.getCompensation()+", "+
                "'"+contract.getContractStatus()+"'"+");";
        return this.create(query);
    }
    public ArrayList<Contract> retrieve(){
        ArrayList<Contract> contractList = new ArrayList<Contract>();
        String query = "select * from contract;";
        resultSet = this.retrieve(query);
        ContractTerm contractTerm = null;
        PaymentCycle paymentCycle = null;
        ContractStatus contractStatus = null;
        try {
            while(resultSet.next()) {
                switch(resultSet.getInt(6)) {
                    case 1:
                        contractTerm=ContractTerm.OneYear;
                        break;
                    case 2:
                        contractTerm=ContractTerm.TwoYear;
                        break;
                    case 3:
                        contractTerm=ContractTerm.ThreeYear;
                        break;
                    case 5:
                        contractTerm=ContractTerm.FiveYear;
                        break;
                    case 10:
                        contractTerm=ContractTerm.TenYear;
                        break;
                }
                switch(resultSet.getInt(10)) {
                    case 1:
                        paymentCycle=PaymentCycle.AMonth;
                        break;
                    case 3:
                        paymentCycle=PaymentCycle.ThreeMonth;
                        break;
                    case 6:
                        paymentCycle=PaymentCycle.SixMonth;
                        break;
                }
                switch(resultSet.getString(13)) {
                    case "Apply":
                        contractStatus=ContractStatus.Apply;
                        break;
                    case "RefuseUnderwrite":
                        contractStatus=ContractStatus.RefuseUnderwrite;
                        break;
                    case "Underwrite":
                        contractStatus=ContractStatus.Underwrite;
                        break;
                    case "Conclude":
                        contractStatus=ContractStatus.Conclude;
                        break;
                }
                Contract contract = new Contract(resultSet.getInt(2), resultSet.getInt(4), resultSet.getString(3), resultSet.getString(3), contractTerm, resultSet.getInt(9), paymentCycle, resultSet.getInt(12), contractStatus);
                contract.setId(resultSet.getInt(1));
                contract.setStartDate(resultSet.getTimestamp("startDate"));
                contract.setExpirationDate(resultSet.getTimestamp("expirationDate"));
                contract.setPaymentDeadline(resultSet.getTimestamp("paymentDeadline"));
                contractList.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return contractList;
    }
    public boolean update(int id, ContractStatus status) {
        for(Contract contract : retrieve()) {
            if(contract.getId()==id) {
                String query = "update contract set contractStatus = "+"'"+status+"'"+" where id = "+id;
                return update(query);
            }
        }
        return false;
    }

    public boolean update(int id, Timestamp deadline) {
        for(Contract contract : retrieve()) {
            if(contract.getId()==id) {
                String query = "update contract SET paymentDeadline = '"+deadline+"' WHERE id="+id;
                return update(query);
            }
        }
        return false;
    }
    public boolean update(int id, Timestamp startDate, Timestamp expirationDate, Timestamp deadline, ContractStatus status) {
        for(Contract contract : retrieve()) {
            if(contract.getId()==id) {
                String query = "update contract SET startDate = '"+startDate+"' WHERE id="+id;
                update(query);
                query = "update contract SET expirationDate = '"+expirationDate+"' WHERE id="+id;
                update(query);
                update(id, deadline);
                update(id, status);
                return true;
            }
        }
        return false;
    }
    public int add(Contract contract) {
        ArrayList<Contract> contractList = retrieve();
        if(contractList.size()==0)contract.setId(1);
        else {contract.setId(contractList.get(contractList.size()-1).getId()+1);}
        if(create(contract)) return contract.getId();
        else {return 0;}
    }

    public Contract findById(int id) {
        for (Contract contract : retrieve()) {
            if (contract.getId() == id) return contract;
        }
        return null;
    }
	public ArrayList<Contract> findByCustomerId(String customerId) {
		ArrayList<Contract> contractList = new ArrayList<>();
		for(Contract contract: retrieve()) {
			if(contract.getCustomerId().equals(customerId))
				contractList.add(contract);
		}
		return contractList;
	}
    public ArrayList<Contract> findByStatus(ContractStatus status) {
        ArrayList<Contract> contractListByStatus = new ArrayList<>();
        for(Contract contract : retrieve()) {
            if(contract.getContractStatus()==status) contractListByStatus.add(contract);
        }
        return contractListByStatus;
    }
}
