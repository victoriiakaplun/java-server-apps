package ru.omsu.imit.server;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsu.imit.server.config.AddressBookResourceConfig;
import ru.omsu.imit.server.config.Settings;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class AddressBookServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressBookServer.class);

    private static Server jettyServer;

    private static void attachShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                stopServer();
            }
        });
    }

    public static void createServer() {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(Settings.getRestHTTPPort()).build();
        AddressBookResourceConfig config = new AddressBookResourceConfig();
        jettyServer = JettyHttpContainerFactory.createServer(baseUri, config);
        LOGGER.info("Server started at port " + Settings.getRestHTTPPort());
    }

    public static void stopServer() {
        LOGGER.info("Stopping server");
        try {
            jettyServer.stop();
            jettyServer.destroy();
        } catch (Exception e) {
            LOGGER.error("Error stopping service", e);
            System.exit(1);
        }
        LOGGER.info("Server stopped");
    }


    public static void main(String[] args) {
        attachShutDownHook();
        createServer();
    }
}
