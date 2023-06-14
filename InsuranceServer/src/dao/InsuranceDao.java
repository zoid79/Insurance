package dao;

import domain.Insurance;
import enumeration.insurance.InsuranceStatus;
import enumeration.insurance.InsuranceType;

import java.sql.SQLException;
import java.util.ArrayList;

public class InsuranceDao extends Dao {
	public InsuranceDao() {
		super();
	}
	public boolean create(Insurance insurance) {
		String query ="insert into insurance values ("+
				insurance.getId()+", "+
				insurance.getCalculationFormulaId()+", "+
				"'"+insurance.getName()+"'"+", "+
				"'"+insurance.getType()+"'"+", "+
				"'"+insurance.getTarget()+"'"+", "+
				"'"+insurance.getCompensateCondition()+"'"+", "+
				"'"+insurance.getNotCompensateCondition()+"'"+", "+
				"'"+insurance.getStatus()+"'"+");";
		return this.create(query);
	}
	public ArrayList<Insurance> retrieve() {
		ArrayList<Insurance> insuranceList = new ArrayList<>();
		String query = "select * from insurance;";
		resultSet = this.retrieve(query);
		try {
			while(resultSet.next()) {
				InsuranceType type = null;
				InsuranceStatus status = null;
				switch(resultSet.getString(4)) {
					case "HomeFire":
						type=InsuranceType.HomeFire;
						break;
					case "WorkplaceFire":
						type=InsuranceType.WorkplaceFire;
						break;
				}
				switch(resultSet.getString(8)) {
					case "UnderAuthorize":
						status=InsuranceStatus.UnderAuthorize;
						break;
					case "RefuseAuthorize":
						status=InsuranceStatus.RefuseAuthorize;
						break;
					case "Authorize":
						status=InsuranceStatus.Authorize;
						break;
				}
				Insurance insurance = new Insurance(
						resultSet.getString(3),
						type,
						resultSet.getString(5),
						resultSet.getInt(2),
						resultSet.getString(6),
						resultSet.getString(7),
						status
				);
				insurance.setId(resultSet.getInt(1));
				insuranceList.add(insurance);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return insuranceList;
	}
	public boolean update(int id, InsuranceStatus status) {
		for(Insurance insurance : retrieve()) {
			if(insurance.getId()==id) {
				String query = "update insurance set status = '"+status+"' where id = "+id+";";
				return update(query);
			}
		}
		return false;
	}
	public int add(Insurance insurance) {
		ArrayList<Insurance> insuranceList = retrieve();
		if (insuranceList.size() == 0) insurance.setId(1);
		else {insurance.setId(insuranceList.get(insuranceList.size() - 1).getId() + 1);}
		if (create(insurance)) return insurance.getId();
		else {return 0;}
	}
	public ArrayList<Insurance> findByStatus(InsuranceStatus insuranceStatus) {
		ArrayList<Insurance> insuranceListByStatus = new ArrayList<>();
		for(Insurance insurance : retrieve()) {
			if(insurance.getStatus()==insuranceStatus) insuranceListByStatus.add(insurance);
		}
		return insuranceListByStatus;
	}
	public Insurance findById(int id) {
		for(Insurance insurance : retrieve()) {
			if(insurance.getId() == id) return insurance;
		}
		return null;
	}

	public Insurance findByName(String name) {
		for(Insurance insurance : retrieve()) {
			if(insurance.getName().equals(name)) return insurance;
		}
		return null;
	}
}