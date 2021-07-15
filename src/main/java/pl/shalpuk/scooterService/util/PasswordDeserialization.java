package pl.shalpuk.scooterService.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;

public class PasswordDeserialization extends JsonDeserializer<String> {

    private final PasswordEncoder passwordEncoder;

    public PasswordDeserialization(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String password = p.getText();
        return passwordEncoder.encode(password);
    }

}
