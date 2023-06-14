package service;

import domain.Accident;
import domain.Compensation;
import enumeration.accident.AccidentStatus;
import exception.NoDataException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CompensateServiceIF  extends Remote {
    void setAccidentService(AccidentServiceIF accidentService) throws RemoteException;

    Compensation getCompensation(int id) throws RemoteException, NoDataException;
    boolean examineCompensation(Accident accident, int contractCompensation, AccidentStatus status) throws RemoteException;
}
