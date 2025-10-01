package app.config;

import app.routes.Route;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;

public class ApplicationConfig {
    private static Route routes = new Route();

    public static void configuration(JavalinConfig config){
        config.showJavalinBanner = false;
        config.bundledPlugins.enableRouteOverview("/routes");
        config.router.contextPath = "/api/v1"; // base path for all endpoints
        config.router.apiBuilder(routes.getRoutes());
    }

    public static Javalin startServer(int port) {
        routes = new Route();
        var app = Javalin.create(ApplicationConfig::configuration);
        app.start(port);
        return app;
    }

    public static void stopServer(Javalin app) {
        app.stop();
    }
}
