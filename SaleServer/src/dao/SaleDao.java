package dao;

import domain.Sale;

import java.sql.SQLException;
import java.util.ArrayList;

public class SaleDao extends Dao {

    public SaleDao() {
        super();
    }
    private boolean create(Sale sale) {
        String query ="insert into sale values ("+
                sale.getId()+", "+
                "'"+sale.getSaleEmployeeId()+"'"+", "+
                "'"+sale.getCustomerId()+"'"+", "+
                "'"+sale.getInsuranceId()+"'"+", "+
                "'"+sale.getMessage()+"'"+
                ");";
        return this.create(query);
    }
    public ArrayList<Sale> retrieve(){
        ArrayList<Sale> saleList = new ArrayList<Sale>();
        String query = "select * from sale;";
        resultSet=this.retrieve(query);
        try {
            while(resultSet.next()) {
                Sale sale = new Sale(resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5));
                sale.setId(resultSet.getInt(1));
                saleList.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return saleList;

    }
    public int add(Sale sale) {
        ArrayList<Sale> saleList = retrieve();
        if(saleList.size()==0) sale.setId(1);
        else {sale.setId(saleList.get(saleList.size()-1).getId()+1);}
        if(create(sale)) return sale.getId();
        else {return 0;}
    }

    public ArrayList<Sale> findByCustomerId(String customerId) {
        ArrayList<Sale> saleList = new ArrayList<>();
        for(Sale sale : retrieve()){
            if(customerId.equals(sale.getCustomerId()))saleList.add(sale);
        }
        return saleList;
    }
}
