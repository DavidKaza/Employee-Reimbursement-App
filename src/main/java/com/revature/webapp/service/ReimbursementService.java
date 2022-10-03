package com.revature.webapp.service;

import com.revature.webapp.exceptions.MissingRequiredFieldException;
import com.revature.webapp.model.Reimbursement;
import com.revature.webapp.repository.ReimbursementRepository;

import java.sql.SQLException;
import java.util.List;

public class ReimbursementService {
    private final ReimbursementRepository ticketRepo = new ReimbursementRepository();
    public Reimbursement createTicket(Reimbursement ticket) throws SQLException, MissingRequiredFieldException {
        if(ticket.getReason().isEmpty()){
            throw new MissingRequiredFieldException("Reason is required");
        }
        return ticketRepo.createTicket(ticket);
    }
    public List<Reimbursement> getAllTickets() throws SQLException{
       return ticketRepo.getAllTickets();
    }
    public List<Reimbursement> getTicketsForUser(int userId) throws SQLException{
         return ticketRepo.getTicketsForUser(userId);
    }
    public Reimbursement manageReimbursement(int id, String approval, int managerId) throws SQLException{
        return ticketRepo.manageTicket(id, approval, managerId);
    }
}
