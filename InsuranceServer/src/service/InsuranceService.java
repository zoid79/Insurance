package service;

import dao.InsuranceDao;
import domain.Insurance;
import enumeration.insurance.InsuranceStatus;
import enumeration.insurance.InsuranceType;
import exception.DataDuplicationException;
import exception.EmptyListException;
import exception.NoDataException;
import exception.TimeDelayException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class InsuranceService extends UnicastRemoteObject implements InsuranceServiceIF{
    private final InsuranceDao insuranceDao = new InsuranceDao();
    public InsuranceService() throws RemoteException {
    }

    @Override
    public ArrayList<Insurance> getInsuranceList() throws RemoteException, EmptyListException, TimeDelayException {
        long beforeTime = System.currentTimeMillis();

        ArrayList<Insurance> insuranceList = this.insuranceDao.retrieve();
        if(insuranceList.isEmpty()) throw new EmptyListException("! 목록이 존재하지 않습니다.");

//        try {Thread.sleep(7000);}
//        catch (InterruptedException e) {throw new RuntimeException(e);}
        long afterTime = System.currentTimeMillis();
        long secDiffTime = (afterTime - beforeTime)/1000;
        if(secDiffTime>=7) throw new TimeDelayException("! 시스템에 오류가 발생했습니다. 다시 시도해주세요.");

        return insuranceList;
    }
    @Override
    public ArrayList<Insurance> getInsuranceList(InsuranceStatus insuranceStatus) throws RemoteException, EmptyListException, TimeDelayException {
        long beforeTime = System.currentTimeMillis();

        ArrayList<Insurance> insuranceList = this.insuranceDao.findByStatus(insuranceStatus);
        if(insuranceList.isEmpty()) throw new EmptyListException("! 목록이 존재하지 않습니다.");

//        try {Thread.sleep(7000);}
//        catch (InterruptedException e) {throw new RuntimeException(e);}
        long afterTime = System.currentTimeMillis();
        long secDiffTime = (afterTime - beforeTime)/1000;
        if(secDiffTime>=7) throw new TimeDelayException("! 시스템에 오류가 발생했습니다. 다시 시도해주세요.");

        return insuranceList;
    }
    @Override
    public ArrayList<Insurance> getInsuranceList(InsuranceType type, InsuranceStatus status) throws RemoteException, EmptyListException, TimeDelayException {
        long beforeTime = System.currentTimeMillis();

        ArrayList<Insurance> insuranceList = new ArrayList<>();
        for(Insurance insurance : this.insuranceDao.findByStatus(status)){
            if(insurance.getType()==type) insuranceList.add(insurance);
        }
        if(insuranceList.isEmpty()) throw new EmptyListException("! 목록이 존재하지 않습니다.");

//        try {Thread.sleep(7000);}
//        catch (InterruptedException e) {throw new RuntimeException(e);}
        long afterTime = System.currentTimeMillis();
        long secDiffTime = (afterTime - beforeTime)/1000;
        if(secDiffTime>=7) throw new TimeDelayException("! 시스템에 오류가 발생했습니다. 다시 시도해주세요.");

        return insuranceList;
    }
    @Override
    public Insurance getInsurance(int selectedInsuranceId) throws RemoteException, NoDataException {
        Insurance insurance = this.insuranceDao.findById(selectedInsuranceId);
        if(insurance == null) throw new NoDataException("! 존재하지 않는 보험입니다.");
        return insurance;
    }

    @Override
    public int makeInsurance(Insurance insurance) throws RemoteException, DataDuplicationException {
        Insurance findByNameInsurance = this.insuranceDao.findByName(insurance.getName());
        if(findByNameInsurance != null) throw new DataDuplicationException("! 이미 존재하는 이름입니다.");
        return insuranceDao.add(insurance);
    }
    @Override
    public boolean examineAuthorization(int id, InsuranceStatus status) throws RemoteException, NoDataException {
        this.getInsurance(id);
        return insuranceDao.update(id, status);
    }

    @Override
    public boolean checkNameDuplication(String name) throws RemoteException, DataDuplicationException {
        Insurance findByNameInsurance = this.insuranceDao.findByName(name);
        if(findByNameInsurance != null) throw new DataDuplicationException("! 이미 존재하는 이름입니다.");
        return true;
    }
}
