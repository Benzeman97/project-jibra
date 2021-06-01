package com.benz.authorization.server.api.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomOAuthExceptionSerializer extends StdSerializer<CustomOAuthException> {

    public CustomOAuthExceptionSerializer() {
        super(CustomOAuthException.class);
    }

    @Override
    public void serialize(CustomOAuthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {

        gen.writeStartObject();
        gen.writeNumberField("status", value.getHttpErrorCode());
        gen.writeStringField("error", value.getOAuth2ErrorCode());
        gen.writeStringField("message", value.getMessage());
        gen.writeStringField("documentation", "www.benz.com");
        gen.writeEndObject();
    }
}
