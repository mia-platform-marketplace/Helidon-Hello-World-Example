/*
 * Copyright 2019 Mia srl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.miaplatform.helidon;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.helidon.common.LogConfig;
import io.helidon.config.Config;
import io.helidon.health.HealthSupport;
import io.helidon.health.checks.HealthChecks;
import io.helidon.media.jsonp.JsonpSupport;
import io.helidon.metrics.MetricsSupport;
import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;

/**
 * The application main class.
 */
public final class Main {
        
        private static final Logger logger = Logger.getLogger(Main.class.getName());

        /**
         * Cannot be instantiated.
         */
        private Main() {
        }

        /**
         * Application main entry point.
         * @param args command line arguments.
         */
        public static void main(final String[] args) {
                Handler handler = new ConsoleHandler();
                handler.setFormatter(new LoggerFormatter());
                logger.addHandler(handler);

                startServer();
        }

        /**
         * Start the server.
         * @return the created {@link WebServer} instance
         */
        static WebServer startServer() {
                // load logging configuration
                LogConfig.configureRuntime();

                // By default this will pick up application.yaml from the classpath
                Config config = Config.create();

                // Build server with JSONP support
                WebServer server = WebServer.builder(createRouting(config))
                        .config(config.get("server"))
                        .addMediaSupport(JsonpSupport.create())
                        .build();

                // Try to start the server. If successful, print some info and arrange to
                // print a message at shutdown. If unsuccessful, print the exception.
                server.start()
                        .thenAccept(ws -> {
                                logger.log(Level.INFO, "WEB server is up! http://localhost:" + ws.port() + "/hello");
                        ws.whenShutdown().thenRun(() -> 
                                logger.log(Level.INFO, "WEB server is DOWN. Good bye!"));
                        })
                        .exceptionally(t -> {
                                logger.log(Level.SEVERE, "Startup failed: " + t.getMessage());
                                t.printStackTrace(System.err);
                        return null;
                        });

                // Server threads are not daemon. No need to block. Just react.

                return server;
        }

        /**
         * Creates new {@link Routing}.
         *
         * @return routing configured with JSON support, a health check, and a service
         * @param config configuration of this server
         */
        private static Routing createRouting(Config config) {
                MetricsSupport metrics = MetricsSupport
                        .builder()
                        .webContext("/-/metrics")
                        .build();
                        
                HelloWorldService helloWorldService = new HelloWorldService(config);

                HealthSupport health = HealthSupport.builder()
                        .addLiveness(HealthChecks.healthChecks())
                        .webContext("/-/healthz")
                        .build();
                HealthSupport readiness = HealthSupport.builder()
                        .addLiveness(HealthChecks.healthChecks())
                        .webContext("/-/ready")
                        .build();

                return Routing.builder()
                        .register(health)                   // Health at "/-/healthz"
                        .register(metrics)                  // Metrics at "/-/metrics"
                        .register(readiness)                // Metrics at "/-/ready"
                        .register("/hello", helloWorldService)
                        .build();
        }
}
