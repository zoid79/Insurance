package service;

import dao.ContractDao;
import domain.Contract;
import domain.customerInfo.CustomerInfo;
import enumeration.contract.ContractStatus;
import exception.EmptyListException;
import exception.NoDataException;
import exception.TimeDelayException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;

public class ContractService extends UnicastRemoteObject implements ContractServiceIF{
    private final ContractDao contractDao = new ContractDao();
    private CustomerServiceIF customerService;

    public ContractService() throws RemoteException {
    }
    @Override
    public void setCustomerService(CustomerServiceIF customerService)throws RemoteException {
        this.customerService = customerService;
    }
    @Override
    public ArrayList<Contract> getContractList(ContractStatus status) throws RemoteException, EmptyListException, TimeDelayException {
        long beforeTime = System.currentTimeMillis();

        ArrayList<Contract> contractList = this.contractDao.findByStatus(status);
        if(contractList.isEmpty()) throw new EmptyListException("! 목록이 존재하지 않습니다.");

//        try {Thread.sleep(7000);}
//        catch (InterruptedException e) {throw new RuntimeException(e);}
        long afterTime = System.currentTimeMillis();
        long secDiffTime = (afterTime - beforeTime)/1000;
        if(secDiffTime>=7) throw new TimeDelayException("! 시스템에 오류가 발생했습니다. 다시 시도해주세요.");

        return contractList;
    }
    @Override
    public ArrayList<Contract> getContractList(String customerId) throws RemoteException, EmptyListException, TimeDelayException {
        long beforeTime = System.currentTimeMillis();

        ArrayList<Contract> contractList = new ArrayList<>();
        for(Contract contract : this.contractDao.retrieve()){
            if(contract.getCustomerId().equals(customerId)) contractList.add(contract);
        }
        if(contractList.isEmpty()) throw new EmptyListException("! 목록이 존재하지 않습니다.");

//        try {Thread.sleep(7000);}
//        catch (InterruptedException e) {throw new RuntimeException(e);}
        long afterTime = System.currentTimeMillis();
        long secDiffTime = (afterTime - beforeTime)/1000;
        if(secDiffTime>=7) throw new TimeDelayException("! 시스템에 오류가 발생했습니다. 다시 시도해주세요.");

        return contractList;
    }
    @Override
    public ArrayList<Contract> getContractList(String customerId, ContractStatus status) throws RemoteException, EmptyListException, TimeDelayException {
        long beforeTime = System.currentTimeMillis();

        ArrayList<Contract> contractList = new ArrayList<>();
        for(Contract contract : this.contractDao.findByStatus(status)){
            if(contract.getCustomerId().equals(customerId)) contractList.add(contract);
        }
        if(contractList.isEmpty()) throw new EmptyListException("! 목록이 존재하지 않습니다.");

//        try {Thread.sleep(7000);}
//        catch (InterruptedException e) {throw new RuntimeException(e);}
        long afterTime = System.currentTimeMillis();
        long secDiffTime = (afterTime - beforeTime)/1000;
        if(secDiffTime>=7) throw new TimeDelayException("! 시스템에 오류가 발생했습니다. 다시 시도해주세요.");

        return contractList;
    }

    @Override
    public ArrayList<Contract> getUnpaidContractList(String customerId) throws RemoteException, EmptyListException, TimeDelayException {
        long beforeTime = System.currentTimeMillis();

        ArrayList<Contract> contractList = this.contractDao.findByCustomerId(customerId);
        ArrayList<Contract> unpaidContractList = new ArrayList<>();
        if(contractList.isEmpty())  throw new EmptyListException("! 목록이 존재하지 않습니다.");
        Timestamp now=new Timestamp(System.currentTimeMillis());
        for(Contract contract:contractList) {
            if(contract.getContractStatus()==ContractStatus.Conclude){
                Timestamp deadlineStamp=contract.getPaymentDeadline();
                LocalDateTime deadline = deadlineStamp.toLocalDateTime();
                LocalDateTime nowTime = now.toLocalDateTime();
                long daysDifference = ChronoUnit.DAYS.between(nowTime,deadline);
                if(daysDifference<=7) unpaidContractList.add(contract);
            }
        }
        if(unpaidContractList.isEmpty()) throw new EmptyListException("! 목록이 존재하지 않습니다.");

//        try {Thread.sleep(7000);}
//        catch (InterruptedException e) {throw new RuntimeException(e);}
        long afterTime = System.currentTimeMillis();
        long secDiffTime = (afterTime - beforeTime)/1000;
        if(secDiffTime>=7) throw new TimeDelayException("! 시스템에 오류가 발생했습니다. 다시 시도해주세요.");

        return unpaidContractList;
    }
    @Override
    public Contract getContract(int contractId) throws RemoteException, NoDataException {
        Contract contract = this.contractDao.findById(contractId);
        if(contract == null){ throw new NoDataException("! 존재하지 않는 계약입니다.");}
        return contract;
    }
    @Override
    public int applyInsurance(Contract contract, CustomerInfo customerInfo) throws RemoteException {
        boolean isSuccess = false;
        int id = customerService.makeInfo(customerInfo);
        if(id==0) return 0;
        else contract.setCustomerInfoId(id);
        return contractDao.add(contract);
    }
    @Override
    public boolean conclude(int id) throws RemoteException, NoDataException {
        Contract contract = this.contractDao.findById(id);
        if(contract == null) throw new NoDataException("! 존재하지 않는 계약입니다.");

        Timestamp startDate = new Timestamp(System.currentTimeMillis());

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(startDate.getTime());
        cal.add(Calendar.YEAR, contract.getTerm().getYear());
        Timestamp expirationDate = new Timestamp(cal.getTime().getTime());

        cal.setTimeInMillis(startDate.getTime());
        cal.add(Calendar.WEEK_OF_MONTH, 1);
        Timestamp deadline = new Timestamp(cal.getTime().getTime());

        return this.contractDao.update(id,startDate,expirationDate,deadline, ContractStatus.Conclude);
    }

    @Override
    public boolean examineUnderwrite(int contractId, ContractStatus status) throws RemoteException {
        return contractDao.update(contractId, status);
    }

    @Override
    public boolean setPaymentDeadline(int id, LocalDateTime deadline) throws RemoteException{
        return contractDao.update(id, Timestamp.valueOf(deadline));
    }
}
