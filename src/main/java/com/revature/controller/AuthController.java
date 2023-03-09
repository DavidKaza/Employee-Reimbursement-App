package com.revature.controller;

import com.revature.exceptions.model.User;
import com.revature.service.AuthService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.openapi.*;
import jakarta.servlet.http.HttpSession;

public class AuthController {

    private AuthService authService = new AuthService();

    @OpenApi(summary = "Register new user", operationId = "register", path = "/register", methods = HttpMethod.POST, tags = {
            "register, sign-up" }, requestBody = @OpenApiRequestBody(content = {
                    @OpenApiContent(from = User.class) }), responses = {
                            @OpenApiResponse(status = "200", content = { @OpenApiContent(from = User[].class) })
                    })
    public void register(Context ctx) {
        User newUser = ctx.bodyAsClass(User.class);
        try {
            User addedUser = authService.register(newUser);
            ctx.json(addedUser);
        } catch (Exception e) {
            ctx.result(e.getMessage());
        }
    }

    @OpenApi(summary = "Login", operationId = "login", path = "/login", methods = HttpMethod.POST, tags = {
            "login, sign-in, authenticate" }, requestBody = @OpenApiRequestBody(content = {
                    @OpenApiContent(from = User.class) }), responses = {
                            @OpenApiResponse(status = "200", content = { @OpenApiContent(from = User[].class) })
                    })
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

    @OpenApi(summary = "Logout", operationId = "logout", path = "/logout", methods = HttpMethod.POST, tags = {
            "logout" }, responses = {
                    @OpenApiResponse(status = "200", content = { @OpenApiContent(from = User[].class) })
            })
    public void logout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.result("Logged out.");
    }

    @OpenApi(summary = "Home", operationId = "start", path = "/", methods = HttpMethod.GET, tags = {
            "home" }, responses = {
                    @OpenApiResponse(status = "200", content = { @OpenApiContent(from = User[].class) })
            })
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
