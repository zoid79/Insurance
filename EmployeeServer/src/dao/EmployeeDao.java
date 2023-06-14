package dao;

import domain.Employee;
import enumeration.employee.Department;
import enumeration.employee.Rank;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDao extends Dao {

	public EmployeeDao() {
		super();
	}

	public boolean create(Employee employee) {
		String query ="insert into employee values ("+
				"'"+employee.getId()+"'"+", "+
				"'"+employee.getPassword()+"'"+", "+
				"'"+employee.getDepartment()+"'"+", "+
				"'"+employee.getName()+"'"+", "+
				"'"+employee.getEmail()+"'"+", "+
				"'"+employee.getPhoneNumber()+"'"+", "+
				"'"+employee.getRank()+"'"+");";
		return this.create(query);

	}
	public ArrayList<Employee> retrieve(){
		ArrayList<Employee> employeeList = new ArrayList<>();
		String query = "select * from employee;";
		resultSet = this.retrieve(query);
		try {
			while(this.resultSet.next()) {
				Department department = null;
				Rank rank = null;
				switch(resultSet.getString(3)) {
					case "CompensationManager":
						department=Department.CompensationManager;
						break;
					case "InsuranceDeveloper":
						department=Department.InsuranceDeveloper;
						break;
					case "InsuranceManager":
						department=Department.InsuranceManager;
						break;
					case "UW":
						department=Department.UW;
						break;
					case "Salesperson":
						department=Department.Salesperson;
						break;
				}
				switch(resultSet.getString(7)) {
					case "Staff":
						rank=Rank.Staff;
						break;
					case "SeniorStaff":
						rank=Rank.SeniorStaff;
						break;
					case "AssisantManager":
						rank=Rank.AssisantManager;
						break;
					case "Manager":
						rank=Rank.Manager;
						break;
					case "DeputyGeneralManager":
						rank=Rank.DeputyGeneralManager;
						break;
					case "GeneralManager":
						rank=Rank.GeneralManager;
						break;
				}
				Employee employee = new Employee(
						resultSet.getString(1),
						resultSet.getString(2),
						department,
						resultSet.getString(4),
						resultSet.getString(5),
						resultSet.getInt(6),
						rank);
				employeeList.add(employee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return employeeList;

	}
	public boolean add(Employee employee){
		return create(employee);
	}

	public Employee findById(String id) {
		for(Employee employee : retrieve()) {
			if(employee.getId().equals(id)) return employee;
		}
		return null;
	}
}