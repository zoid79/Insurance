package domain;

import java.io.Serializable;

public class Pay implements Serializable {
    private int id;
    private int contractId;
    private String cardNumber;

    public Pay(int contractId, String cardNumber) {
        this.contractId = contractId;
        this.cardNumber = cardNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
