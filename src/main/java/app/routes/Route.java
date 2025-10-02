package app.routes;

import app.security.SecurityRoute;
import app.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.security.RouteRole;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class Route {

    private HotelRoute hotelRoute = new HotelRoute();
    private SecurityRoute securityRoute = new SecurityRoute();
    private static ObjectMapper jsonMapper = new Utils().getObjectMapper();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/hotel", hotelRoute.getRoutes());
            path("/auth", securityRoute.getSecurityRoutes());
            path("/protected", getSecuredRoutes());
        };
    }

    public static EndpointGroup getSecuredRoutes(){
        return ()->{
                get("/user_demo",(ctx)->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from USER Protected")),Role.USER);
                get("/admin_demo",(ctx)->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from ADMIN Protected")),Role.ADMIN);
        };
    }
    public enum Role implements RouteRole { ANYONE, USER, ADMIN }
}

