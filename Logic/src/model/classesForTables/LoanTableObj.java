package model.classesForTables;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class LoanTableObj {

    private String id;


    private String owner;


    private String category;


    private String amount;


    private String time;


    private String interest;


    private String rate;


    private String status;


    private String pending;


    private String active;


    private String risk;


    private String finished;

    public LoanTableObj(String id, String owner, String category, String amount, String time, String interest, String rate, String status, String pending, String active, String risk, String finished) {
        this.id = id;
        this.owner = owner;
        this.category = category;
        this.amount = amount;
        this.time = time;
        this.interest = interest;
        this.rate = rate;
        this.status = status;
        this.pending = pending;
        this.active = active;
        this.risk = risk;
        this.finished = finished;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPending() {
        return pending;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }
}
