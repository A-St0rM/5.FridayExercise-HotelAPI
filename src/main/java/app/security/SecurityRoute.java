package app.security;


import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class SecurityRoute {

    ISecurityController securityController = new SecurityController();

    public EndpointGroup getSecurityRoutes = () ->{
        path("/auth", () -> {
            post("/login", securityController.login());
        });

    };
}
