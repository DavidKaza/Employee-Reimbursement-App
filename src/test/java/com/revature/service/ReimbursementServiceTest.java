package com.revature.service;

import com.revature.webapp.exceptions.AlreadyDeniedOrApprovedException;
import com.revature.webapp.exceptions.InvalidIDException;
import com.revature.webapp.exceptions.MissingRequiredFieldException;
import com.revature.webapp.model.Reimbursement;
import com.revature.webapp.repository.ReimbursementRepository;
import com.revature.webapp.service.ReimbursementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReimbursementServiceTest {
    @Mock
    private ReimbursementRepository ticketRepo;

    @InjectMocks
    private ReimbursementService rs;

    @Test
    public void testCreateTicketMissingReason(){
        Assertions.assertThrows(MissingRequiredFieldException.class, () -> {
            rs.createTicket(new Reimbursement(20, ""));
        });
    }

    @Test
    public void testCreateTicketInvalidAmount(){
        Assertions.assertThrows(MissingRequiredFieldException.class, () -> {
            rs.createTicket(new Reimbursement(20, ""));
        });
    }
    @Test
    public void testCreateTicketPositive() throws SQLException, MissingRequiredFieldException {

        when(ticketRepo.createTicket(new Reimbursement(20, "Gas"))).thenReturn(new Reimbursement(20, "Gas"));

        Reimbursement ticket = rs.createTicket(new Reimbursement(20, "Gas"));

        Assertions.assertEquals(new Reimbursement(20, "Gas"), ticket);
    }
    @Test
    public void testGetTicketInvalidID() throws SQLException{
        when(ticketRepo.getTicket(256)).thenReturn(null);
        Assertions.assertThrows(InvalidIDException.class, ()->{
           rs.getTicket(256);
        });
    }
    @Test
    public void testGetTicketPositive() throws SQLException, InvalidIDException {
        when(ticketRepo.getTicket(1)).thenReturn(new Reimbursement(1,35, "pending", "gas", 1, "2022-10-05 21:10:51.263"));
        Reimbursement ticket = rs.getTicket(1);
        Assertions.assertEquals(new Reimbursement(1,35, "pending", "gas", 1, "2022-10-05 21:10:51.263"), ticket);
    }

    @Test
    public void testManageReimbursementAlreadyDecided() throws SQLException, AlreadyDeniedOrApprovedException{
        when(ticketRepo.getTicket(1)).thenReturn(new Reimbursement(1, 200, "approved", "because", 1, 1, "2022-10-05 21:10:51.263", "2022-10-05 21:10:51.263"));
        Assertions.assertThrows(AlreadyDeniedOrApprovedException.class, ()->{
            rs.manageReimbursement(1, "denied", 1);
        });
    }

    @Test
    public void testManageReimbursementPositive() throws SQLException, InvalidIDException, AlreadyDeniedOrApprovedException {
        when(ticketRepo.getTicket(3)).thenReturn(new Reimbursement(30,"because"));
        when(ticketRepo.manageTicket(3, "approved", 2)).thenReturn(new Reimbursement(30,"because"));
        Reimbursement ticket = rs.manageReimbursement(3, "approved", 2);
        Assertions.assertEquals(new Reimbursement(30, "because"), ticket);
    }
}
