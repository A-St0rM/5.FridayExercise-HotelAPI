package app.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class Route {

    private HotelRoute hotelRoute = new HotelRoute();
    private RoomRoute roomRoute = new RoomRoute();


    public EndpointGroup getRoutes() {
        return () -> {
            path("/hotel", hotelRoute.getRoutes());
            path("/room",roomRoute.getRoutes());
        };
    }
}
