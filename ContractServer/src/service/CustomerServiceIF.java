package service;

import domain.Customer;
import domain.customerInfo.CustomerInfo;
import exception.DataDuplicationException;
import exception.EmptyListException;
import exception.NoDataException;
import exception.TimeDelayException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface CustomerServiceIF  extends Remote {
    boolean registerCustomer(Customer customer) throws RemoteException, DataDuplicationException;

    Customer loginCustomer(String id, String password) throws RemoteException, NoDataException;

    Customer getCustomer(String selectedCustomerId) throws RemoteException, NoDataException;

    ArrayList<Customer> getCustomerList() throws RemoteException, EmptyListException, TimeDelayException;

    CustomerInfo getInfo(int infoId) throws RemoteException, NoDataException;

    int makeInfo(CustomerInfo customerInfo) throws RemoteException;
}
