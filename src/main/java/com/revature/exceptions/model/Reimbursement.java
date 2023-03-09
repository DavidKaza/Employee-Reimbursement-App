package com.revature.exceptions.model;

import java.util.Objects;

public class Reimbursement {
    private int id;
    private double amount;
    private String status;

    private String category;
    private String reason;
    private int employeeId;
    private int managerId;

    private String timestamp;

    private String decisionDate;

    public Reimbursement(){}

    public Reimbursement(int id, double amount, String status, String category, String reason, int employeeId, int managerId, String timestamp, String decisionDate) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.category = category;
        this.reason = reason;
        this.employeeId = employeeId;
        this.managerId = managerId;
        this.timestamp = timestamp;
        this.decisionDate = decisionDate;
    }

    public Reimbursement(int id, double amount, String status, String reason, int employeeId, int managerId, String timestamp, String decisionDate) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.reason = reason;
        this.employeeId = employeeId;
        this.managerId = managerId;
        this.timestamp = timestamp;
        this.decisionDate = decisionDate;
    }

    public Reimbursement(int id, double amount, String status, String reason, int employeeId, String timestamp) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.reason = reason;
        this.employeeId = employeeId;
        this.timestamp = timestamp;
    }

    public Reimbursement(double amount, String reason) {
        this.amount = amount;
        this.reason = reason;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(String decisionDate) {
        this.decisionDate = decisionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reimbursement that = (Reimbursement) o;
        return id == that.id && amount == that.amount && employeeId == that.employeeId && managerId == that.managerId && Objects.equals(status, that.status) && Objects.equals(category, that.category) && Objects.equals(reason, that.reason) && Objects.equals(timestamp, that.timestamp) && Objects.equals(decisionDate, that.decisionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, status, category, reason, employeeId, managerId, timestamp, decisionDate);
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id=" + id +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", category='" + category + '\'' +
                ", reason='" + reason + '\'' +
                ", employeeId=" + employeeId +
                ", managerId=" + managerId +
                ", timestamp='" + timestamp + '\'' +
                ", decisionDate='" + decisionDate + '\'' +
                '}';
    }
}
