package domain;

import java.io.Serializable;

public class Sale implements Serializable {
    private int id;
    private String saleEmployeeId;
    private String customerId;
    private int insuranceId;
    private String message;

    public Sale(String saleEmployeeId, String customerId, int insuranceId, String message) {
        this.saleEmployeeId = saleEmployeeId;
        this.customerId = customerId;
        this.insuranceId = insuranceId;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSaleEmployeeId() {
        return saleEmployeeId;
    }

    public void setSaleEmployeeId(String saleEmployeeId) {
        this.saleEmployeeId = saleEmployeeId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(int insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
