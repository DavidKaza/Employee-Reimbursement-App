package com.revature.webapp.repository;

import com.revature.webapp.model.Reimbursement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementRepository {

    public Reimbursement createTicket(Reimbursement ticket) throws SQLException{
        try(Connection connectionObject = ConnectionFactory.createConnection()){
            String sql = "insert into project1.reimbursements (amount, reason, employee_id) values (?, ?, ?)";
            PreparedStatement pstmt = connectionObject.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, ticket.getAmount());
            pstmt.setString(2, ticket.getReason());
            pstmt.setInt(3, ticket.getEmployeeId());

            int numberOfRecords = pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt("reimbursement_id");
            String status = "pending";
            String submitDate = rs.getString("submit_date");

            return new Reimbursement(id, ticket.getAmount(), status, ticket.getReason(), ticket.getEmployeeId(), submitDate);
        }
    }

    public List<Reimbursement> getAllTickets() throws SQLException{
        try(Connection connectionObject = ConnectionFactory.createConnection()){
            List<Reimbursement> tickets = new ArrayList<>();
            String sql = "select * from project1.reimbursements";
            Statement stmt = connectionObject.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("reimbursement_id");
                int amount = rs.getInt("amount");
                String status = rs.getString("status");
                String reason = rs.getString("reason");
                int employeeId = rs.getInt("employee_id");
                int managerId = rs.getInt("manager_id");
                String submitDate = rs.getString("submit_date");
                String decisionDate = rs.getString("decision_date");
                Reimbursement reimbursement = new Reimbursement(id, amount, status, reason, employeeId, managerId, submitDate, decisionDate);
                tickets.add(reimbursement);
            }
            return tickets;
        }
    }

    public List<Reimbursement> getTicketsForUser(int userId) throws SQLException{
        try(Connection connectionObject = ConnectionFactory.createConnection()){
            List<Reimbursement> tickets = new ArrayList<>();
            String sql = "Select * from project1.reimbursements where employee_id = ?";
            PreparedStatement pstmt = connectionObject.prepareStatement(sql);
            pstmt.setInt(1,userId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("reimbursement_id");
                int amount = rs.getInt("amount");
                String status = rs.getString("status");
                String reason = rs.getString("reason");
                int employeeId = rs.getInt("employee_id");
                int managerId = rs.getInt("manager_id");
                String submitDate = rs.getString("submit_date");
                String decisionDate = rs.getString("decision_date");
                Reimbursement reimbursement = new Reimbursement(id, amount, status, reason, employeeId, managerId, submitDate, decisionDate);
                tickets.add(reimbursement);
            }
            return tickets;
        }
    }
    public Reimbursement manageTicket(int id, String decision, int managerId) throws SQLException{
        try(Connection connectionObject = ConnectionFactory.createConnection()){
            String sql = "update project1.reimbursements set status = ?, manager_id = ?, decision_date = ? where reimbursement_id = ?"; //to do: set decision date
            PreparedStatement pstmt = connectionObject.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            pstmt.setString(1, decision);
            pstmt.setInt(2, managerId);
            pstmt.setTimestamp(3, timestamp);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            rs.next();
            int ticketId = rs.getInt(1);
            int amount = rs.getInt(2);
            String status = rs.getString(3);
            String reason = rs.getString(4);
            int employeeId = rs.getInt("employee_id");
            int manager = rs.getInt("manager_id");
            String submitDate = rs.getString("submit_date");
            String decisionDate = rs.getString("decision_date");
            Reimbursement ticket = new Reimbursement(ticketId, amount, status, reason, employeeId, manager, submitDate, decisionDate);
            return ticket;
        }
    }
}
