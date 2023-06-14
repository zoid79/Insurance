package service;

import dao.AccidentDao;
import domain.Accident;
import enumeration.accident.AccidentStatus;
import exception.EmptyListException;
import exception.NoDataException;
import exception.TimeDelayException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class AccidentService extends UnicastRemoteObject implements AccidentServiceIF{
    private final AccidentDao accidentDao = new AccidentDao();

    public AccidentService() throws RemoteException {
    }

    @Override
    public ArrayList<Accident> getAccidentList(AccidentStatus status) throws RemoteException, EmptyListException, TimeDelayException {
        long beforeTime = System.currentTimeMillis();

        ArrayList<Accident> accidentList = this.accidentDao.findByStatus(status);
        if(accidentList.isEmpty()) throw new EmptyListException("! 목록이 존재하지 않습니다.");

//        try {Thread.sleep(7000);}
//        catch (InterruptedException e) {throw new RuntimeException(e);}
        long afterTime = System.currentTimeMillis();
        long secDiffTime = (afterTime - beforeTime)/1000;
        if(secDiffTime>=7) throw new TimeDelayException("! 시스템에 오류가 발생했습니다. 다시 시도해주세요.");

        return accidentList;
    }
    @Override
    public ArrayList<Accident> getAccidentList() throws RemoteException, EmptyListException, TimeDelayException {
        long beforeTime = System.currentTimeMillis();

        ArrayList<Accident> accidentList = this.accidentDao.retrieve();
        if(accidentList.isEmpty()) throw new EmptyListException("! 목록이 존재하지 않습니다.");

//        try {Thread.sleep(7000);}
//        catch (InterruptedException e) {throw new RuntimeException(e);}
        long afterTime = System.currentTimeMillis();
        long secDiffTime = (afterTime - beforeTime)/1000;
        if(secDiffTime>=7) throw new TimeDelayException("! 시스템에 오류가 발생했습니다. 다시 시도해주세요.");

        return accidentList;
    }
    @Override
    public Accident getAccident(int id) throws RemoteException, NoDataException {
        Accident accident = this.accidentDao.findById(id);
        if(accidentDao == null) throw new NoDataException("! 존재하지 않는 사고입니다.");
        return accidentDao.findById(id);
    }
    @Override
    public int reportAccident(Accident accident) throws RemoteException {
        return this.accidentDao.add(accident);
    }
    @Override
    public boolean setStatus(int accidentId, AccidentStatus status) throws RemoteException{
        return this.accidentDao.update(accidentId, status);
    }
}
