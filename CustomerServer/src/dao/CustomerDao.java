package dao;

import domain.Customer;
import domain.customerInfo.CustomerInfo;
import domain.customerInfo.HomeCustomerInfo;
import domain.customerInfo.WorkplaceCustomerInfo;
import enumeration.calculationFormula.OutwallType;
import enumeration.calculationFormula.PillarType;
import enumeration.calculationFormula.RoofType;
import enumeration.calculationFormula.homeFormula.HouseType;
import enumeration.calculationFormula.homeFormula.ResidenceType;
import enumeration.calculationFormula.workplaceFormula.WorkplaceUsage;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDao extends Dao {

	public CustomerDao()   {
		super();
	}
	public boolean create(Customer customer) {
		String query ="insert into customer values ("+
				"'"+customer.getId()+"'"+", "+
				"'"+customer.getPassword()+"'"+", "+
				"'"+customer.getName()+"'"+", "+
				"'"+customer.getEmail()+"'"+", "+
				"'"+customer.getPhoneNumber()+"'"+", "+
				"'"+customer.getAddress()+"'"+", "+
				customer.isHasHome()+", "+
				customer.isHasWorkplace()+");";
		return create(query);
	}
	public ArrayList<Customer> retrieve(){
		ArrayList<Customer> customerList = new ArrayList<>();
		String query = "select * from customer;";
		resultSet = retrieve(query);
		try {
			while(resultSet.next()) {
				Customer customer = new Customer(resultSet.getString(1),
						resultSet.getString(2),
						resultSet.getString(3),
						resultSet.getString(4),
						resultSet.getInt(5),
						resultSet.getString(6),
						resultSet.getBoolean(7),
						resultSet.getBoolean(8));
				customerList.add(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return customerList;
	}
	public boolean updateHasHome(String customerId, boolean hasHome) {
		String query = "update customer set hasHome = "+hasHome+" where id = '"+customerId+"';";
		return update(query);
	}
	public boolean updateHasWorkplace(String customerId, boolean hasWorkplace) {
		String query = "update customer set hasWorkplace = "+hasWorkplace+" where id = "+"'"+customerId+"'"+";";
		return update(query);
	}
	public boolean add(Customer customer){
		return create(customer);
	}

	public Customer findInfoById(String id) {
		for(Customer customer : retrieve()) {
			if(customer.getId().equals(id)) return customer;
		}
		return null;
	}

	/////// CustomerInfo ///////////

	public boolean create(CustomerInfo customerInfo) {
		String query="";
		if(customerInfo instanceof HomeCustomerInfo) {
			HomeCustomerInfo homecustomerInfo=(HomeCustomerInfo) customerInfo;
			query ="insert into customerinfo values ("+
					customerInfo.getId()+", "+
					"'"+customerInfo.getCustomerId()+"'"+", "+
					customerInfo.getSquareMeter()+", "+
					"'"+customerInfo.getPillarType()+"'"+", "+
					"'"+customerInfo.getRoofType()+"'"+", "+
					"'"+customerInfo.getOutwallType()+"'"+");";
			this.create(query);
			query ="insert into homecustomerinfo values ("+
					customerInfo.getId()+", "+
					"'"+homecustomerInfo.getHouseType()+"'"+", "+
					"'"+homecustomerInfo.getResidenceType()+"'"+");";
			return this.create(query);
		}else {
			WorkplaceCustomerInfo workplaceCustomerInfo=(WorkplaceCustomerInfo) customerInfo;
			query ="insert into customerinfo values ("+
					customerInfo.getId()+", "+
					"'"+customerInfo.getCustomerId()+"'"+", "+
					customerInfo.getSquareMeter()+", "+
					"'"+customerInfo.getPillarType()+"'"+", "+
					"'"+customerInfo.getRoofType()+"'"+", "+
					"'"+customerInfo.getOutwallType()+"'"+");";
			this.create(query);
			query ="insert into workplacecustomerinfo values ("+
					customerInfo.getId()+", "+
					"'"+workplaceCustomerInfo.getUsage()+"'"+", "+
					workplaceCustomerInfo.getFloor()+");";
			return this.create(query);
		}
	}
	public ArrayList<CustomerInfo> retrieveInfoList(){
		ArrayList<CustomerInfo> customerInfoList =new ArrayList<>();
		String homeInfoQuery = "select * from customerinfo, homecustomerinfo where customerinfo.id=homecustomerinfo.id;";
		String workplaceInfoQuery = "select * from customerinfo, workplacecustomerinfo where customerinfo.id=workplacecustomerinfo.id;";
		PillarType pillarType = null;
		RoofType roofType= null;
		OutwallType outwallType= null;
		HouseType houseType= null;
		ResidenceType residenceType= null;
		WorkplaceUsage workplaceUsage= null;
		resultSet = retrieve(homeInfoQuery);
		try {
			while(resultSet.next()) {
				switch(resultSet.getString(4)) {
					case "Concrete":
						pillarType = PillarType.Concrete;
						break;
					case "Brick":
						pillarType = PillarType.Brick;
						break;
				}
				switch(resultSet.getString(5)) {
					case "ConcreteSlab":
						roofType = RoofType.ConcreteSlab;
						break;
					case "Panel":
						roofType = RoofType.Panel;
						break;
				}
				switch(resultSet.getString(6)) {
					case "Concrete":
						outwallType = OutwallType.Concrete;
						break;
					case "Brick":
						outwallType = OutwallType.Brick;
						break;
					case "Block":
						outwallType = OutwallType.Block;
						break;
					case "Glass":
						outwallType = OutwallType.Glass;
						break;
				}
				switch(resultSet.getString(8)) {
					case "Multigenerational":
						houseType = HouseType.Multigenerational;
						break;
					case "multifamily":
						houseType = HouseType.multifamily;
						break;
				}
				switch(resultSet.getString(9)) {
					case "SelfOwned":
						residenceType = ResidenceType.SelfOwned;
						break;
					case "Rental":
						residenceType = ResidenceType.Rental;
						break;
				}
				HomeCustomerInfo homeCustomerInfo = new HomeCustomerInfo(resultSet.getString(2), resultSet.getInt(3), pillarType, roofType, outwallType, houseType, residenceType);
				homeCustomerInfo.setId(resultSet.getInt(1));
				customerInfoList.add(homeCustomerInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		resultSet = retrieve(workplaceInfoQuery);
		try {
			while(resultSet.next()) {
				switch(resultSet.getString(4)) {
					case "Concrete":
						pillarType = PillarType.Concrete;
						break;
					case "Brick":
						pillarType = PillarType.Brick;
						break;
				}
				switch(resultSet.getString(5)) {
					case "ConcreteSlab":
						roofType = RoofType.ConcreteSlab;
						break;
					case "Panel":
						roofType = RoofType.Panel;
						break;
				}
				switch(resultSet.getString(6)) {
					case "Concrete":
						outwallType = OutwallType.Concrete;
						break;
					case "Brick":
						outwallType = OutwallType.Brick;
						break;
					case "Block":
						outwallType = OutwallType.Block;
						break;
					case "Glass":
						outwallType = OutwallType.Glass;
						break;
				}
				switch(resultSet.getString(8)) {
					case "Restaurant":
						workplaceUsage = WorkplaceUsage.Restaurant;
						break;
					case "Store":
						workplaceUsage = WorkplaceUsage.Store;
						break;
					case "LivingFacility":
						workplaceUsage = WorkplaceUsage.LivingFacility;
						break;
					case "Office":
						workplaceUsage = WorkplaceUsage.Office;
						break;
					case "Officetels":
						workplaceUsage = WorkplaceUsage.Officetels;
						break;
					case "SportsFacility":
						workplaceUsage = WorkplaceUsage.SportsFacility;
						break;
					case "Hospital":
						workplaceUsage = WorkplaceUsage.Hospital;
						break;
					case "Accommodation":
						workplaceUsage = WorkplaceUsage.Accommodation;
						break;
					case "Academy":
						workplaceUsage = WorkplaceUsage.Academy;
						break;
					case "Church":
						workplaceUsage = WorkplaceUsage.Church;
						break;
				}
				WorkplaceCustomerInfo WorkplaceCustomerInfo = new WorkplaceCustomerInfo(resultSet.getString(2), resultSet.getInt(3), pillarType, roofType, outwallType, workplaceUsage, resultSet.getInt(9));
				WorkplaceCustomerInfo.setId(resultSet.getInt(1));
				customerInfoList.add(WorkplaceCustomerInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();

		}
		return customerInfoList;

	}
	public int add(CustomerInfo customerInfo) {
		ArrayList<CustomerInfo> customerInfoList = retrieveInfoList();
		if(customerInfoList.size()==0) customerInfo.setId(1);
		else {customerInfo.setId(getLastId(customerInfoList)+1);}
		if(create(customerInfo)) return customerInfo.getId();
		else {return 0;}
	}
	public CustomerInfo findInfoById(int infoId) {
		for(CustomerInfo customerInfo : retrieveInfoList()) {
			if(customerInfo.getId() == infoId) return customerInfo;
		}
		return null;
	}
	public int getLastId(ArrayList<CustomerInfo> customerInfoList){
		int id = 0;
		for(CustomerInfo customerInfo : customerInfoList){
			if(customerInfo.getId()>id) id = customerInfo.getId();
		}
		return id;
	}




}