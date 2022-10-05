package com.revature.webapp.service;

import com.revature.webapp.exceptions.AlreadyDeniedOrApprovedException;
import com.revature.webapp.exceptions.InvalidIDException;
import com.revature.webapp.exceptions.MissingRequiredFieldException;
import com.revature.webapp.model.Reimbursement;
import com.revature.webapp.repository.ReimbursementRepository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ReimbursementService {
    private final ReimbursementRepository ticketRepo = new ReimbursementRepository();
    public Reimbursement createTicket(Reimbursement ticket) throws SQLException, MissingRequiredFieldException {
        if(ticket.getReason().isEmpty()){
            throw new MissingRequiredFieldException("Reason is required");
        }
        if(ticket.getAmount() <= 0){
            throw new MissingRequiredFieldException("Check the amount!");
        }
        return ticketRepo.createTicket(ticket);
    }
    public Reimbursement getTicket(int ticketID) throws SQLException, InvalidIDException {
        if(ticketRepo.getTicket(ticketID) == null){
            throw new InvalidIDException("Reimbursement ID not fount.");
        };
        return ticketRepo.getTicket(ticketID);
    }
    public Reimbursement getUserTicket(int ticketID, int userID) throws SQLException, InvalidIDException {
        if(ticketRepo.getTicket(ticketID, userID) == null){
            throw new InvalidIDException("Reimbursement not fount.");
        };
        return ticketRepo.getTicket(ticketID);
    }
    public List<Reimbursement> getAllTickets() throws SQLException{
       return ticketRepo.getAllTickets();
    }
    public List<Reimbursement> getPendingTickets() throws SQLException{
        return ticketRepo.getPendingTickets();
    }
    public List<Reimbursement> getTicketsForUser(int userId) throws SQLException{
         return ticketRepo.getTicketsForUser(userId);
    }
    public List<Reimbursement> getPendingTicketsForUser(int userId) throws SQLException{
        return ticketRepo.getPendingTicketsForUser(userId);
    }
    public List<Reimbursement> getTicketsForUser(String username) throws SQLException{
        return ticketRepo.getTicketsForUser(username);
    }
    public Reimbursement manageReimbursement(int id, String approval, int managerId) throws SQLException, InvalidIDException, AlreadyDeniedOrApprovedException {
        Reimbursement ticket = getTicket(id);
        String ts = ticket.getDecisionDate();
        if(ts != null){
            throw new AlreadyDeniedOrApprovedException("Reimbursement decisions are final!");
        }
        return ticketRepo.manageTicket(id, approval, managerId);
    }
}
