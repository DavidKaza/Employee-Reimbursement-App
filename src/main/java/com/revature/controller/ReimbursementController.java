package com.revature.controller;

import com.revature.exceptions.model.Reimbursement;
import com.revature.exceptions.model.User;
import com.revature.service.ReimbursementService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.openapi.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ReimbursementController {

    private final ReimbursementService rs = new ReimbursementService();

    @OpenApi(summary = "Create Reimbursement Ticket", operationId = "requestReimbursement", path = "/requestReimbursement", methods = HttpMethod.POST, tags = {
            "requestReimbursement" })
    public void requestReimbursement(Context ctx) {
        HttpSession httpSession = ctx.req().getSession();
        User user = (User) httpSession.getAttribute("user");
        if (user != null) {
            Reimbursement ticket = ctx.bodyAsClass(Reimbursement.class);
            ticket.setEmployeeId(user.getId());
            try {
                Reimbursement newTicket = rs.createTicket(ticket);
                ctx.result("New ticket submitted.\n" + "Ticket ID: " + newTicket.getId() + "\n" + "Amount: " +
                        newTicket.getAmount() + "\nReason: " +
                        newTicket.getReason() + "\nStatus: " + newTicket.getStatus());
            } catch (Exception e) {
                ctx.result(e.getMessage());
            }
        } else {
            ctx.result("You must be logged in to submit a ticket.");
        }
    }

    @OpenApi(summary = "See reimbursement tickets for user", operationId = "reimbursementTickets", path = "/reimbursementTickets/{filter}", pathParams = {
            @OpenApiParam(name = "filter", type = String.class, description = "All or Pending") }, methods = HttpMethod.GET, tags = {
                    "reimbursementTickets" })
    public void reimbursementTickets(Context ctx) {
        HttpSession httpSession = ctx.req().getSession();
        User user = (User) httpSession.getAttribute("user");
        if (user != null) {
            if (user.getRoleId() == 2) {
                if (ctx.pathParam("filter").equals("all")) {
                    try {
                        List<Reimbursement> tickets = rs.getAllTickets();
                        if (tickets.size() == 0) {
                            ctx.result("No reimbursement tickets.");
                            ctx.status(404);
                        }
                        ctx.json(tickets);
                        ctx.status(200);
                    } catch (Exception e) {
                        ctx.result(e.getMessage());
                    }
                } else if (ctx.pathParam("filter").equals("pending")) {
                    try {
                        List<Reimbursement> tickets = rs.getPendingTickets();
                        ctx.json(tickets);
                        ctx.status(200);
                    } catch (Exception e) {
                        ctx.result(e.getMessage());
                    }
                } else {
                    try {
                        List<Reimbursement> tickets = rs.getTicketsForUser(ctx.pathParam("filter"));
                        ctx.json(tickets);
                        ctx.status(200);
                    } catch (Exception e) {
                        ctx.result(e.getMessage());
                    }
                }
            } else if (user.getRoleId() == 1) {
                if (ctx.pathParam("filter").equals("all")) {
                    try {
                        List<Reimbursement> tickets = rs.getTicketsForUser(user.getId());
                        if (tickets.size() == 0) {
                            ctx.result("You have not submitted any tickets.");
                        } else {
                            ctx.json(tickets);
                            ctx.status(200);
                        }
                    } catch (Exception e) {
                        ctx.result(e.getMessage());
                    }
                }
                if (ctx.pathParam("filter").equals("pending")) {
                    try {
                        List<Reimbursement> tickets = rs.getPendingTicketsForUser(user.getId());
                        if (tickets.size() == 0) {
                            ctx.result("You have no pending tickets.");
                        } else {
                            ctx.json(tickets);
                            ctx.status(200);
                        }
                    } catch (Exception e) {
                        ctx.result(e.getMessage());
                    }
                }
            } else {
                ctx.result("Access Denied.");
                ctx.status(403);
            }
        } else {
            ctx.result("You are not logged in!");
            ctx.status(401);
        }
    }

    @OpenApi(summary = "Manage reimbursement by ticket ID via path parameter", operationId = "reimbursementTicket", path = "/reimbursementTicket/{ticketId}", methods = HttpMethod.PATCH, pathParams = {
            @OpenApiParam(name = "ticketId", type = Integer.class, description = "Ticket ID") }, queryParams = {
                    @OpenApiParam(name = "decision", description = "Approve or Deny") }, tags = {
                            "reimbursementTicket, manage, approve" })
    public void reimbursementTicketID(Context ctx) {
        HttpSession httpSession = ctx.req().getSession();
        User user = (User) httpSession.getAttribute("user");
        if (user != null) {
            if (user.getRoleId() == 2) {
                String ticketIDString = ctx.pathParam("ticketId");
                String decision = ctx.queryParam("decision");
                if (decision != null) {
                    try {
                        int ticketID = Integer.parseInt(ticketIDString);
                        int managerId = user.getId();
                        Reimbursement ticket = rs.manageReimbursement(ticketID, decision, managerId);
                        ctx.json(ticket);
                    } catch (Exception e) {
                        ctx.result(e.getMessage());
                    }
                } else {
                    ctx.result("Missing ticket ID and approval decision");
                }
            } else {
                ctx.result("You are not authorized for reimbursement decisions!");
            }

        } else {
            ctx.result("You are not logged in!");
        }
    }

    @OpenApi(summary = "Manage reimbursement by ticket ID via query parameter", operationId = "reimbursementTicketQ", path = "/reimbursementTicket", methods = HttpMethod.PATCH, tags = {
            "reimbursementTicket, manage, approve" }, queryParams = {
                    @OpenApiParam(name = "ticketId", type = Integer.class, description = "Ticket ID"),
                    @OpenApiParam(name = "decision", type = String.class, description = "Approve or Deny") }, responses = {
                            @OpenApiResponse(status = "200", content = {
                                    @OpenApiContent(from = Reimbursement[].class) })
                    })
    public void reimbursementTicketQ(Context ctx) {
        HttpSession httpSession = ctx.req().getSession();
        User user = (User) httpSession.getAttribute("user");
        if (user != null) {
            if (user.getRoleId() == 2) {
                String ticketIDString = ctx.queryParam("ticketId");
                String decision = ctx.queryParam("decision");
                if (ticketIDString != null && decision != null) {
                    try {
                        int ticketID = Integer.parseInt(ticketIDString);
                        int managerId = user.getId();
                        Reimbursement ticket = rs.manageReimbursement(ticketID, decision, managerId);
                        ctx.json(ticket);
                    } catch (Exception e) {
                        ctx.result(e.getMessage());
                    }
                } else {
                    ctx.result("Missing ticket ID and approval decision");
                }
            } else {
                ctx.result("You are not authorized for reimbursement decisions!");
            }

        } else {
            ctx.result("You are not logged in!");
        }
    }

    @OpenApi(summary = "View reimbursement ticket by ticket ID", operationId = "reimbursementTicket", path = "/reimbursementTicket/{ticketID}", methods = HttpMethod.GET, tags = {
            "reimbursementTicket" }, pathParams = {
                    @OpenApiParam(name = "ticketID", type = Integer.class, description = "Ticket ID") })
    public void reimbursementTicket(Context ctx) {
        HttpSession httpSession = ctx.req().getSession();
        User user = (User) httpSession.getAttribute("user");
        if (user != null) {
            if (user.getRoleId() == 2) {
                try {
                    Reimbursement ticket = rs.getTicket(Integer.parseInt(ctx.pathParam("ticketID")));
                    ctx.json(ticket);
                } catch (Exception e) {
                    ctx.result(e.getMessage());
                }
            } else {
                try {
                    Reimbursement ticket = rs.getUserTicket(Integer.parseInt(ctx.pathParam("ticketID")), user.getId());
                    ctx.json(ticket);
                } catch (Exception e) {
                    ctx.result(e.getMessage());
                }
            }
        } else {
            ctx.result("You must be logged in to view tickets.");
        }
    }

    public void mapEndpoints(Javalin app) {
        app.post("/requestReimbursement", this::requestReimbursement);
        app.get("/reimbursementTickets/{filter}", this::reimbursementTickets);
        // using path parameter for ticketID
        // -------------------------------------------------------
        app.patch("/reimbursementTicket/{ticketId}", this::reimbursementTicketID);
        // using query parameter for ticketID
        // -------------------------------------------------------
        app.patch("/reimbursementTicket", this::reimbursementTicketQ);
        app.get("/reimbursementTicket/{ticketID}", this::reimbursementTicket);
    }
}