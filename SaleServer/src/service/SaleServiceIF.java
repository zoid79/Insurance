package service;

import domain.Sale;
import exception.EmptyListException;
import exception.TimeDelayException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface SaleServiceIF  extends Remote {
    int offerInsurance(String saleEmployeeId, String customerId, int insuranceId, String message) throws RemoteException;

    ArrayList<Sale> getSaleList(String customerId) throws RemoteException, EmptyListException, TimeDelayException;
}
