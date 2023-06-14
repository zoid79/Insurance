package service;

import domain.Contract;
import domain.customerInfo.CustomerInfo;
import enumeration.contract.ContractStatus;
import exception.EmptyListException;
import exception.NoDataException;
import exception.TimeDelayException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ContractServiceIF  extends Remote {

    void setCustomerService(CustomerServiceIF customerService) throws RemoteException;

    ArrayList<Contract> getContractList(ContractStatus status) throws EmptyListException, RemoteException, TimeDelayException;

    ArrayList<Contract> getContractList(String customerId) throws RemoteException, EmptyListException, TimeDelayException;

    ArrayList<Contract> getContractList(String customerId, ContractStatus status) throws RemoteException, EmptyListException, TimeDelayException;

    Contract getContract(int contractId) throws RemoteException, NoDataException;

    int applyInsurance(Contract contract, CustomerInfo customerInfo) throws RemoteException;

    boolean conclude(int id) throws NoDataException, RemoteException;

    boolean examineUnderwrite(int contractId, ContractStatus status) throws RemoteException;

    ArrayList<Contract> getUnpaidContractList(String customerId) throws RemoteException, EmptyListException, TimeDelayException;

    boolean setPaymentDeadline(int id, LocalDateTime deadline) throws RemoteException;
}
