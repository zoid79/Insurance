package domain.customerInfo;

import enumeration.calculationFormula.OutwallType;
import enumeration.calculationFormula.PillarType;
import enumeration.calculationFormula.RoofType;

import java.io.Serializable;

public abstract class CustomerInfo implements Serializable {
    private int id;
    private String customerId;
    private int squareMeter;
    private PillarType pillarType;
    private RoofType roofType;
    private OutwallType outwallType;

    public CustomerInfo(String customerId, int squareMeter, PillarType pillarType, RoofType roofType, OutwallType outwallType) {
        this.customerId = customerId;
        this.squareMeter = squareMeter;
        this.pillarType = pillarType;
        this.roofType = roofType;
        this.outwallType = outwallType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getSquareMeter() {
        return squareMeter;
    }

    public void setSquareMeter(int squareMeter) {
        this.squareMeter = squareMeter;
    }

    public PillarType getPillarType() {
        return pillarType;
    }

    public void setPillarType(PillarType pillarType) {
        this.pillarType = pillarType;
    }

    public RoofType getRoofType() {
        return roofType;
    }

    public void setRoofType(RoofType roofType) {
        this.roofType = roofType;
    }

    public OutwallType getOutwallType() {
        return outwallType;
    }

    public void setOutwallType(OutwallType outwallType) {
        this.outwallType = outwallType;
    }
}
