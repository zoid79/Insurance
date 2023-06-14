package dao;

import domain.Compensation;

import java.sql.SQLException;
import java.util.ArrayList;

public class CompensationDao extends Dao {
    public CompensationDao()   {
        super();
    }

    public boolean create(Compensation compensation) {
        String query ="insert into compensation values ("+
                compensation.getId()+", "+
                compensation.getAccidentId()+", "+
                compensation.getCompensation()+");";
        return create(query);
    }

    public ArrayList<Compensation> retrieve() {
        ArrayList<Compensation> compensationList = new ArrayList<>();
        String query = "select * from compensation;";
        resultSet = this.retrieve(query);
        try {
            while(resultSet.next()) {
                Compensation compensation = new Compensation(resultSet.getInt(2), resultSet.getInt(3));
                compensation.setId(resultSet.getInt(1));
                compensationList.add(compensation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return compensationList;
    }

    public int add(Compensation compensation) {
        ArrayList<Compensation> compensationList = retrieve();
        if(compensationList.size()==0)compensation.setId(1);
        else {compensation.setId(compensationList.get(compensationList.size()-1).getId()+1);}
        if(create(compensation)) return compensation.getId();
        else {return 0;}
    }

    public Compensation findByAccidentId(int id) {
        for(Compensation compensation : retrieve()){
            if(compensation.getAccidentId()==id) return compensation;
        }
        return null;
    }
}
