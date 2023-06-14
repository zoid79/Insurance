package service;

import domain.Insurance;
import enumeration.insurance.InsuranceStatus;
import enumeration.insurance.InsuranceType;
import exception.DataDuplicationException;
import exception.EmptyListException;
import exception.NoDataException;
import exception.TimeDelayException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface InsuranceServiceIF extends Remote {
    ArrayList<Insurance> getInsuranceList(InsuranceStatus insuranceStatus) throws RemoteException, EmptyListException, TimeDelayException;

    ArrayList<Insurance> getInsuranceList(InsuranceType type, InsuranceStatus status) throws RemoteException, EmptyListException, TimeDelayException;

    Insurance getInsurance(int selectedInsuranceId) throws RemoteException, NoDataException;

    ArrayList<Insurance> getInsuranceList() throws RemoteException, EmptyListException, TimeDelayException;

    int makeInsurance(Insurance insurance) throws RemoteException, DataDuplicationException;

    boolean examineAuthorization(int id, InsuranceStatus status) throws RemoteException, NoDataException;

    boolean checkNameDuplication(String name) throws DataDuplicationException, RemoteException;
}
