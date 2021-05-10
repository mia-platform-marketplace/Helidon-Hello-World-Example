/*
 * Copyright 2021 Mia srl
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

import javax.json.Json;
import javax.json.JsonObject;

import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

public class HelloWorldService implements Service {

    private static final Logger logger = Logger.getLogger(HelloWorldService.class.getName());
    private String name;

    HelloWorldService(Config config) {
        name = config.get("app.name").asString().orElse("");
        Handler handler = new ConsoleHandler();
        handler.setFormatter(new LoggerFormatter());
        logger.addHandler(handler);
    }

    /**
     * A service registers itself by updating the routing rules.
     * @param rules the routing rules.
     */
    @Override
    public void update(Routing.Rules rules) {
        rules
            .get("/", this::getDefaultMessageHandler)
            .get("/{name}", this::getMessageHandler);
    }

    /**
     * Return a worldly greeting message.
     * @param request the server request
     * @param response the server response
     */
    private void getDefaultMessageHandler(ServerRequest request, ServerResponse response) {
        sendResponse(response, name);
    }

    /**
     * Return a greeting message using the name that was provided.
     * @param request the server request
     * @param response the server response
     */
    private void getMessageHandler(ServerRequest request, ServerResponse response) {
        String customName = request.path().param("name");
        sendResponse(response, customName);
    }

    private void sendResponse(ServerResponse response, String name) {
        String msg = String.format("Hello %s!", name);
        logger.log(Level.INFO, "Greeting " + name);

        JsonObject returnObject = Json.createObjectBuilder()
                .add("message", msg)
                .build();
        response.send(returnObject);
    }

}