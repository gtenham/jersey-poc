package nl.gertontenham.rest.app;

import nl.gertontenham.rest.app.config.ApplicationBinder;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

public class JerseyAppRunner {

    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI;
    public static final String protocol;
    public static final String host;
    public static final String path;
    public static final Optional<String> port;

    static{
        protocol = "http://";
        host = "0.0.0.0";
        port = Optional.ofNullable(System.getenv("PORT"));
        path = "api";
        BASE_URI = protocol + host + ":" + port.orElse("8080") + "/" + path + "/";
    }

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {

        ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();

        // create a resource config that scans for JAX-RS resources and providers
        // in com.example.rest package
        final ResourceConfig rc = new ResourceConfig()
                                            .packages("nl.gertontenham.rest.resource")
                                            .register(new ApplicationBinder());

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc, locator);
        // Bind /static/ classpath resource to docroot
        httpServer.getServerConfiguration().addHttpHandler(
                new CLStaticHttpHandler(HttpServer.class.getClassLoader()), "/static/");

        return httpServer;
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.shutdownNow();
    }
}
