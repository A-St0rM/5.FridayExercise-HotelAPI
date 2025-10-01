package app.api;

import app.config.ApplicationConfig;
import app.config.HibernateConfig;
import app.entities.Hotel;
import app.entities.Room;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

class hotelApiTest {

    private static Javalin app;
    private static EntityManagerFactory emf;

    @BeforeAll
    static void setUpAll() {
        HibernateConfig.setTest(true);
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        app = ApplicationConfig.startServer(7777);

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 7777;
        RestAssured.basePath = "/api/v1";
    }

    @AfterAll
    static void tearDownAll() {
        HibernateConfig.setTest(false);
        ApplicationConfig.stopServer(app);
    }

    @BeforeEach
    void setUpEach() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Room").executeUpdate();
            em.createQuery("DELETE FROM Hotel").executeUpdate();

            Hotel h1 = new Hotel("TestHotel1", "Street 1");
            Hotel h2 = new Hotel("TestHotel2", "Street 2");

            Room r1 = new Room("101", 100.0, h1);
            Room r2 = new Room("102", 150.0, h1);
            h1.getRooms().add(r1);
            h1.getRooms().add(r2);

            em.persist(h1);
            em.persist(h2);
            em.getTransaction().commit();
        }
    }

    @Test
    void testGetAllHotels() {
        given()
                .when().get("/hotel")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2));
    }

    @Test
    void testGetSpecificHotel() {
        given()
                .when().get("/hotel/1")
                .then()
                .statusCode(200)
                .body("hotelName", equalTo("TestHotel1"))
                .body("hotelAddress", equalTo("Street 1"));
    }

    @Test
    void testGetRoomsForHotel() {
        given()
                .when().get("/hotel/1/rooms")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2))
                .body("[0].number", equalTo("101"))
                .body("[1].number", equalTo("102"));
    }

    @Test
    void testCreateHotel() {
        String json = """
            {
              "hotelName": "CreatedHotel",
              "hotelAddress": "New Street",
              "rooms": []
            }
            """;

        given()
                .contentType("application/json")
                .body(json)
                .when().post("/hotel")
                .then()
                .statusCode(201)
                .body("hotelName", equalTo("CreatedHotel"));
    }

    @Test
    void testUpdateHotel() {
        String json = """
            {
              "hotelName": "UpdatedHotel",
              "hotelAddress": "Updated Street",
              "rooms": []
            }
            """;

        given()
                .contentType("application/json")
                .body(json)
                .when().put("/hotel/1")
                .then()
                .statusCode(200)
                .body("hotelName", equalTo("UpdatedHotel"));
    }

    @Test
    void testDeleteHotel() {
        given()
                .when().delete("/hotel/2")
                .then()
                .statusCode(200)
                .body(equalTo("Hotel deleted"));

        given()
                .when().get("/hotel/2")
                .then()
                .statusCode(404);
    }
}