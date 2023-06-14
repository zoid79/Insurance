package domain;

import java.io.Serializable;

public class Compensation implements Serializable {
    private int id;
    private int accidentId;
    private int compensation;

    public Compensation(int accidentId, int compensation) {
        this.accidentId = accidentId;
        this.compensation = compensation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccidentId() {
        return accidentId;
    }

    public void setAccidentId(int accidentId) {
        this.accidentId = accidentId;
    }

    public int getCompensation() {
        return compensation;
    }

    public void setCompensation(int compensation) {
        this.compensation = compensation;
    }
}
