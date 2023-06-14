package service;

import dao.EmployeeDao;
import domain.Employee;
import enumeration.employee.Department;
import exception.DataDuplicationException;
import exception.NoDataException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class EmployeeService extends UnicastRemoteObject implements EmployeeServiceIF{
    private final EmployeeDao employeeDao = new EmployeeDao();
    public EmployeeService() throws RemoteException {
    }
    @Override
    public boolean registerEmployee(Employee employee) throws RemoteException, DataDuplicationException {
        if(employeeDao.findById(employee.getId()) != null) throw new DataDuplicationException("! 이미 존재하는 아이디입니다.");
        return employeeDao.add(employee);
    }
    @Override
    public Employee loginEmployee(String id, String password) throws RemoteException, NoDataException {
        Employee employee = employeeDao.findById(id);
        if(employee == null || !employee.getPassword().equals(password)) throw new NoDataException("! 존재하지 않는 계정입니다.");
        return employee;
    }
    @Override
    public Employee getEmployee(String id, Department department) throws RemoteException, NoDataException {
        Employee employee = employeeDao.findById(id);
        if(employee==null || employee.getDepartment() != department) throw new NoDataException("! 존재하지 않는 직원입니다.");
        return employee;
    }
}
