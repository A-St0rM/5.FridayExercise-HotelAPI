package app.routes;

import app.controllers.RoomController;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public class RoomRoute {

    private RoomController roomController = new RoomController();

    public EndpointGroup getRoutes(){
        return () -> {
            delete("/{id}", roomController::removeRoomFromHotel);
            post("/{id}", roomController::addRoomToHotel);
        };


    }
    //GET /hotel/{id}/rooms
    //response json: [{id: 1, hotelId: 1, number: 1, price: 100}, {id: 2, hotelId: 1, number: 2, price: 200}]

    //In above api, if no request json is specified, then the request body should be empty and an appropriate status code should be used.
    //
    //Use an http file to test the endpoints.
}
