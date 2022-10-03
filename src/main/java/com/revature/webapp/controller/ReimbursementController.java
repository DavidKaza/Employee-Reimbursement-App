package com.revature.webapp.controller;

import com.revature.webapp.model.Reimbursement;
import com.revature.webapp.model.User;
import com.revature.webapp.service.ReimbursementService;
import io.javalin.Javalin;

import javax.servlet.http.HttpSession;
import java.util.List;

public class ReimbursementController {

    private final ReimbursementService rs = new ReimbursementService();

    public void mapEndpoints(Javalin app){
        app.post("/requestReimbursement", (ctx) -> {
            HttpSession httpSession = ctx.req.getSession();
            User user = (User) httpSession.getAttribute("user");
            if(user != null){
                Reimbursement ticket = ctx.bodyAsClass(Reimbursement.class);
                ticket.setEmployeeId(user.getId());
                try{
                    Reimbursement newTicket = rs.createTicket(ticket);
                    ctx.result("New ticket submitted.\n"+ "Amount: "+
                            newTicket.getAmount() + "\nReason: "+
                            newTicket.getReason() + "\nStatus: " + newTicket.getStatus());
                }catch(Exception e){
                    ctx.result(e.getMessage());
                }
            }else{
                ctx.result("You must be logged in to submit a ticket.");
            }
        });
        app.get("/reimbursementTickets", (ctx)->{
            HttpSession httpSession = ctx.req.getSession();
            User user = (User) httpSession.getAttribute("user");
            if(user != null){
                if(user.getRoleId() == 2){
                    try{
                        List<Reimbursement> tickets = rs.getAllTickets();
                        ctx.json(tickets);
                        ctx.status(200);
                    }catch(Exception e){
                        ctx.result(e.getMessage());
                    }
                }else if(user.getRoleId() == 1){
                    try{
                        List<Reimbursement> tickets = rs.getTicketsForUser(user.getId());
                        if(tickets.size() == 0){
                            ctx.result("You have not submitted any tickets.");
                        }else {
                            ctx.json(tickets);
                            ctx.status(200);
                        }
                    }catch(Exception e){
                        ctx.result(e.getMessage());
                    }
                }
                else{
                    ctx.result("Access Denied.");
                    ctx.status(403);
                }
            } else {
            ctx.result("You are not logged in!");
            ctx.status(401);
        }
        });

        // using path parameter for ticketID -------------------------------------------------------
        app.patch("/reimbursementTicket/{ticketId}", (ctx)->{
            HttpSession httpSession = ctx.req.getSession();
            User user = (User) httpSession.getAttribute("user");
            if(user != null){
                if(user.getRoleId() == 2){
                    String ticketIDString = ctx.pathParam("ticketId");
                    String decision = ctx.queryParam("decision");
                    if(decision != null){
                        try{
                            int ticketID = Integer.parseInt(ticketIDString);
                            int managerId = user.getId();
                            Reimbursement ticket = rs.manageReimbursement(ticketID, decision, managerId);
                            ctx.json(ticket);
                        }catch(NumberFormatException e){
                            ctx.result(e.getMessage());
                        }
                    }else{
                        ctx.result("Missing ticket ID and approval decision");
                    }
                }else{
                    ctx.result("You are not authorized for reimbursement decisions!");
                }

            }else{
                ctx.result("You are not logged in!");
            }
        });
        // using query parameter for ticketID -------------------------------------------------------
        app.patch("/reimbursementTicket", (ctx)->{
            HttpSession httpSession = ctx.req.getSession();
            User user = (User) httpSession.getAttribute("user");
            if(user != null){
                if(user.getRoleId() == 2){
                    String ticketIDString = ctx.queryParam("ticketId");
                    String decision = ctx.queryParam("decision");
                    if(ticketIDString != null && decision != null){
                        try{
                            int ticketID = Integer.parseInt(ticketIDString);
                            int managerId = user.getId();
                            Reimbursement ticket = rs.manageReimbursement(ticketID, decision, managerId);
                            ctx.json(ticket);
                        }catch(NumberFormatException e){
                            ctx.result(e.getMessage());
                        }
                    }else{
                        ctx.result("Missing ticket ID and approval decision");
                    }
                }else{
                    ctx.result("You are not authorized for reimbursement decisions!");
                }

            }else{
                ctx.result("You are not logged in!");
            }
        });
    }
}
