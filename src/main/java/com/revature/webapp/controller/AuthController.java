package com.revature.webapp.controller;

import com.revature.webapp.model.User;
import com.revature.webapp.service.AuthService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;

public class AuthController {

    private AuthService authService = new AuthService();

    public void register(Context ctx) {
        User newUser = ctx.bodyAsClass(User.class);
        try {
            User addedUser = authService.register(newUser);
            ctx.json(addedUser);
        } catch (Exception e) {
            ctx.result(e.getMessage());
        }
    }

    public void login(Context ctx) {
        User credentials = ctx.bodyAsClass(User.class);
        try {
            User user = authService.login(credentials.getUsername(), credentials.getPassword());
            HttpSession session = ctx.req().getSession();
            session.setAttribute("user", user);
            ctx.result("Logged in as " + user.getUsername());
        } catch (Exception e) {
            ctx.status(400);
            ctx.result(e.getMessage());
        }
    }

    public void logout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.result("Logged out.");
    }

    public void start(Context ctx) {
        ctx.result("Start here");
    }

    public void mapEndpoints(Javalin app) {
        app.post("/register", this::register);
        app.post("/login", this::login);
        app.post("/logout", this::logout);
        app.get("/", this::start);
    }
}
