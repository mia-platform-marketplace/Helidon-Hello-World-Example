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

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class LoggerFormatter extends Formatter {

    ObjectMapper mapper;

    public LoggerFormatter(){
        mapper = JsonMapper.builder()
        .addModule(new JavaTimeModule())
        .build();
    }

    @Override
    public String format(LogRecord record) {
        
        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(record);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(LoggerFormatter.class.getName()).log(Level.SEVERE, "Error mapping log to json", ex);
        }
        return jsonInString;
    }
}