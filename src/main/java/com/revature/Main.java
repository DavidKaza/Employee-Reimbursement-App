package com.revature;

import com.revature.controller.AuthController;
import com.revature.controller.ReimbursementController;
import io.javalin.Javalin;
import io.javalin.openapi.BasicAuth;
import io.javalin.openapi.plugin.OpenApiPlugin;
import io.javalin.openapi.plugin.OpenApiPluginConfiguration;
import io.javalin.openapi.plugin.SecurityComponentConfiguration;
import io.javalin.openapi.plugin.redoc.ReDocConfiguration;
import io.javalin.openapi.plugin.redoc.ReDocPlugin;
import io.javalin.openapi.plugin.swagger.SwaggerConfiguration;
import io.javalin.openapi.plugin.swagger.SwaggerPlugin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            OpenApiPluginConfiguration openApiConfiguration = new OpenApiPluginConfiguration();
            openApiConfiguration.withDocumentationPath("/openapi")
                    .withDefinitionConfiguration((version, definition) -> definition
                            .withOpenApiInfo(openApiInfo -> {
                                openApiInfo.setTitle("Employee Reimbursement System");
                                openApiInfo.setVersion("1.0.0");
                            }).withSecurity(new SecurityComponentConfiguration().withSecurityScheme("BasicAuth",
                                    new BasicAuth())));
            config.plugins.register(new OpenApiPlugin(openApiConfiguration));
            config.plugins.register(new SwaggerPlugin(new SwaggerConfiguration()));
            config.plugins.register(new ReDocPlugin(new ReDocConfiguration()));
        });
        // app.get("/", (ctx)->{ctx.result("hello");});
        AuthController ac = new AuthController();
        ReimbursementController rc = new ReimbursementController();
        ac.mapEndpoints(app);
        rc.mapEndpoints(app);
        app.start(8080);
    }
}
