package service;

import domain.calculationFormula.CalculationFormula;
import domain.customerInfo.CustomerInfo;
import enumeration.insurance.InsuranceType;
import exception.DataDuplicationException;
import exception.EmptyListException;
import exception.NoDataException;
import exception.TimeDelayException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface CalculationFormulaServiceIF extends Remote {

    ArrayList<CalculationFormula> getCalculationFormulaList(InsuranceType insuranceType) throws RemoteException, EmptyListException, TimeDelayException;

    CalculationFormula getCalculationFormula(int id) throws RemoteException, NoDataException;

    int makeFormula(CalculationFormula calculationFormula) throws RemoteException, DataDuplicationException;

    int calculateMaxCompensation(int squareMeter, int calculationFormulaId) throws RemoteException, NoDataException;

    int calculateMinCompensation(int squareMeter, int calculationFormulaId) throws RemoteException, NoDataException;

    int calculatePayment(CustomerInfo customerInfo, int compensation, int calculationFormulaId) throws RemoteException, NoDataException;
    boolean checkNameDuplication(String name) throws DataDuplicationException, RemoteException;

}
