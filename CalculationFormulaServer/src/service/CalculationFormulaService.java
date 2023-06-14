package service;

import dao.CalculationFormulaDao;
import domain.Insurance;
import domain.calculationFormula.CalculationFormula;
import domain.calculationFormula.HomeFormula;
import domain.calculationFormula.WorkplaceFormula;
import domain.customerInfo.CustomerInfo;
import domain.customerInfo.HomeCustomerInfo;
import domain.customerInfo.WorkplaceCustomerInfo;
import enumeration.calculationFormula.homeFormula.HomeCompensation;
import enumeration.calculationFormula.homeFormula.HomeSquareMeter;
import enumeration.calculationFormula.workplaceFormula.Floor;
import enumeration.calculationFormula.workplaceFormula.WorkplaceCompensation;
import enumeration.calculationFormula.workplaceFormula.WorkplaceSquareMeter;
import enumeration.insurance.InsuranceType;
import exception.DataDuplicationException;
import exception.EmptyListException;
import exception.NoDataException;
import exception.TimeDelayException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class CalculationFormulaService extends UnicastRemoteObject implements CalculationFormulaServiceIF{
    private final CalculationFormulaDao calculationFormulaDao = new CalculationFormulaDao();
    public CalculationFormulaService() throws RemoteException {
    }
    @Override
    public ArrayList<CalculationFormula> getCalculationFormulaList(InsuranceType insuranceType) throws RemoteException, EmptyListException, TimeDelayException {
        long beforeTime = System.currentTimeMillis();

        ArrayList<CalculationFormula> calculationFormulaList = this.calculationFormulaDao.findByType(insuranceType);
        if(calculationFormulaList.isEmpty()) throw new EmptyListException("! 계산식 목록이 존재하지 않습니다.");

//        try {Thread.sleep(7000);}
//        catch (InterruptedException e) {throw new RuntimeException(e);}
        long afterTime = System.currentTimeMillis();
        long secDiffTime = (afterTime - beforeTime)/1000;
        if(secDiffTime>=7) throw new TimeDelayException("! 시스템에 오류가 발생했습니다. 다시 시도해주세요.");

        return calculationFormulaList;
    }
    @Override
    public CalculationFormula getCalculationFormula(int id) throws RemoteException, NoDataException {
        CalculationFormula calculationFormula = calculationFormulaDao.findById(id);
        if(calculationFormula == null) throw new NoDataException("! 존재하지 않는 계산식입니다.");
        return calculationFormula;
    }
    @Override
    public int makeFormula(CalculationFormula calculationFormula) throws RemoteException, DataDuplicationException {
        CalculationFormula findByNameCalculationFormula = calculationFormulaDao.findByName(calculationFormula.getName());
        if(findByNameCalculationFormula != null) throw new DataDuplicationException("! 이미 존재하는 이름입니다.");
        return calculationFormulaDao.add(calculationFormula);
    }
    @Override
    public int calculateMaxCompensation(int squareMeter, int calculationFormulaId) throws RemoteException, NoDataException {
        CalculationFormula calculationFormula = calculationFormulaDao.findById(calculationFormulaId);
        if(calculationFormula==null){throw new NoDataException("! 존재하지 않는 계산식 아이디입니다.");}
        long maxCompensation = (long)squareMeter * (long)calculationFormula.getMultiplierForMaxCompensation();;
        if(calculationFormula instanceof HomeFormula){
            int homeMaxCompensation = HomeCompensation.values()[0].getMaxAmount();
            if(homeMaxCompensation < maxCompensation){maxCompensation = homeMaxCompensation;}
            int homeMinCompensation = HomeCompensation.values()[HomeCompensation.values().length-1].getMinAmount();
            if(homeMinCompensation > maxCompensation){maxCompensation = homeMinCompensation;}
        }
        if(calculationFormula instanceof WorkplaceFormula){
            int workplaceMaxCompensation = WorkplaceCompensation.values()[0].getMaxAmount();
            if(workplaceMaxCompensation < maxCompensation){maxCompensation = workplaceMaxCompensation;}
            int workplaceMinCompensation = WorkplaceCompensation.values()[WorkplaceCompensation.values().length-1].getMinAmount();
            if(workplaceMinCompensation > maxCompensation){maxCompensation = workplaceMinCompensation;}
        }
        return (int) maxCompensation;
    }

    @Override
    public int calculateMinCompensation(int squareMeter, int calculationFormulaId) throws RemoteException, NoDataException {
        CalculationFormula calculationFormula = calculationFormulaDao.findById(calculationFormulaId);
        if(calculationFormula==null){throw new NoDataException("! 존재하지 않는 계산식 아이디입니다.");}
        long minCompensation = (long)squareMeter * (long)calculationFormula.getMultiplierForMinCompensation();;
        if(calculationFormula instanceof HomeFormula){
            int homeMinCompensation = HomeCompensation.values()[HomeCompensation.values().length-1].getMinAmount();
            if(homeMinCompensation > minCompensation){minCompensation = homeMinCompensation;}
            int homeMaxCompensation = HomeCompensation.values()[0].getMaxAmount();
            if(homeMaxCompensation < minCompensation){minCompensation = homeMaxCompensation;}
        }
        if(calculationFormula instanceof WorkplaceFormula){
            int workplaceMinCompensation = WorkplaceCompensation.values()[WorkplaceCompensation.values().length-1].getMinAmount();
            if(workplaceMinCompensation > minCompensation){minCompensation = workplaceMinCompensation;}
            int workplaceMaxCompensation = WorkplaceCompensation.values()[0].getMaxAmount();
            if(workplaceMaxCompensation < minCompensation){minCompensation = workplaceMaxCompensation;}
        }
        return (int)minCompensation;
    }

    @Override
    public int calculatePayment(CustomerInfo customerInfo, int compensation, int calculationFormulaId) throws RemoteException, NoDataException {
        CalculationFormula calculationFormula = calculationFormulaDao.findById(calculationFormulaId);
        if(calculationFormula == null){throw new NoDataException("! 존재하지 않는 계산식 아이디입니다.");}
        int totalRisk = 0;
        totalRisk += calculationFormula
                .getRiskLevelAccordingToRoofType()
                .get(customerInfo.getRoofType())
                .getLevel();
        totalRisk += calculationFormula
                .getRiskLevelAccordingToOutwallType()
                .get(customerInfo.getOutwallType())
                .getLevel();
        totalRisk += calculationFormula
                .getRiskLevelAccordingToPillarType()
                .get(customerInfo.getPillarType())
                .getLevel();
        if(calculationFormula instanceof HomeFormula){
            totalRisk += ((HomeFormula) calculationFormula)
                    .getRiskLevelAccordingToResidenceType()
                    .get(((HomeCustomerInfo) customerInfo).getResidenceType())
                    .getLevel();
            totalRisk += ((HomeFormula) calculationFormula)
                    .getRiskLevelAccordingToHouseType()
                    .get(((HomeCustomerInfo) customerInfo).getHouseType())
                    .getLevel();
            totalRisk += ((HomeFormula) calculationFormula)
                    .getRiskLevelAccordingToSquareMeter()
                    .get(getHomeSquareMeter(customerInfo.getSquareMeter()))
                    .getLevel();
            totalRisk += ((HomeFormula) calculationFormula)
                    .getRiskLevelAccordingToCompensation()
                    .get(getHomeCompensation(compensation))
                    .getLevel();
        }
        if(calculationFormula instanceof WorkplaceFormula){
            totalRisk += ((WorkplaceFormula) calculationFormula)
                    .getRiskLevelAccordingToUsage()
                    .get(((WorkplaceCustomerInfo) customerInfo).getUsage())
                    .getLevel();
            totalRisk += ((WorkplaceFormula) calculationFormula)
                    .getRiskLevelAccordingToSquareMeter()
                    .get(getWorkplaceSquareMeter(customerInfo.getSquareMeter()))
                    .getLevel();
            totalRisk += ((WorkplaceFormula) calculationFormula)
                    .getRiskLevelAccordingToCompensation()
                    .get(getWorkplaceCompensation(compensation))
                    .getLevel();
            totalRisk += ((WorkplaceFormula) calculationFormula)
                    .getRiskLevelAccordingToFloor()
                    .get(getFloor(((WorkplaceCustomerInfo) customerInfo).getFloor()))
                    .getLevel();
        }
        return totalRisk * calculationFormula.getMultiplierForPayment();
    }

    @Override
    public boolean checkNameDuplication(String name) throws RemoteException, DataDuplicationException {
        CalculationFormula calculationFormula = calculationFormulaDao.findByName(name);
        if(calculationFormula != null) throw new DataDuplicationException("! 이미 존재하는 이름입니다.");
        return true;
    }

    public HomeSquareMeter getHomeSquareMeter(int squareMeter){
        for(int i = 0; i < HomeSquareMeter.values().length; i++){
            if(squareMeter >= HomeSquareMeter.values()[i].getMinSquareFeet()
                    && squareMeter <= HomeSquareMeter.values()[i].getMaxSquareFeet()) return HomeSquareMeter.values()[i];;
        }
        return null;
    }
    public HomeCompensation getHomeCompensation(int compensation){
        for(int i = 0; i < HomeCompensation.values().length; i++){
            if(compensation >= HomeCompensation.values()[i].getMinAmount()
                    && compensation <= HomeCompensation.values()[i].getMaxAmount()) return HomeCompensation.values()[i];
        }
        return null;
    }
    public WorkplaceSquareMeter getWorkplaceSquareMeter(int squareMeter){
        for(int i = 0; i < WorkplaceSquareMeter.values().length; i++){
            if(squareMeter >= WorkplaceSquareMeter.values()[i].getMinSquareFeet()
                    && squareMeter <= WorkplaceSquareMeter.values()[i].getMaxSquareFeet()) return WorkplaceSquareMeter.values()[i];;
        }
        return null;
    }
    public WorkplaceCompensation getWorkplaceCompensation(int compensation){
        for(int i = 0; i < WorkplaceCompensation.values().length; i++){
            if(compensation >= WorkplaceCompensation.values()[i].getMinAmount()
                    && compensation <= WorkplaceCompensation.values()[i].getMaxAmount()) return WorkplaceCompensation.values()[i];;
        }
        return null;
    }
    public Floor getFloor(int floor){
        for(int i = 0; i < Floor.values().length; i++){
            if(floor >= Floor.values()[i].getMinFloor()
                    && floor <= Floor.values()[i].getMaxFloor()) return Floor.values()[i];;
        }
        return null;
    }
}
