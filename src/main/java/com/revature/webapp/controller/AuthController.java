package com.revature.webapp.controller;

import com.revature.webapp.exceptions.ExistingUserException;
import com.revature.webapp.model.User;
import com.revature.webapp.service.AuthService;
import io.javalin.Javalin;

import javax.servlet.http.HttpSession;

public class AuthController {

    private AuthService authService = new AuthService();
public void mapEndpoints(Javalin app){
    app.post("/register", (ctx)->{
        User newUser = ctx.bodyAsClass(User.class);
        try{
            User addedUser = authService.register(newUser);
            ctx.json(addedUser);
        }catch(ExistingUserException e){
            ctx.result(e.getMessage());
        }
        catch(Exception e){//to do: catch user exist error
            ctx.result(e.getMessage());
        }
    });
    app.post("/login", (ctx)->{
        User credentials = ctx.bodyAsClass(User.class);
        try{
            User user = authService.login(credentials.getUsername(), credentials.getPassword());
            HttpSession session = ctx.req.getSession();
            session.setAttribute("user", user);
            ctx.result("Logged in as "+ user.getUsername());
        }catch(Exception e){
            ctx.status(400);
            ctx.result(e.getMessage());
        }
    });
    app.post("/logout", (ctx) -> {
       ctx.req.getSession().invalidate();
    });
    app.get("/", (ctx)->{
        ctx.result("Start here");
    });
}
}
