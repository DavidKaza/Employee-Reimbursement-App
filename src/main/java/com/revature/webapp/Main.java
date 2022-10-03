package com.revature.webapp;

import com.revature.webapp.controller.AuthController;
import com.revature.webapp.controller.ReimbursementController;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create();
//        app.get("/", (ctx)->{ctx.result("hello");});
        AuthController ac = new AuthController();
        ReimbursementController rc = new ReimbursementController();
        ac.mapEndpoints(app);
        rc.mapEndpoints(app);
        app.start(8080);
    }
}
