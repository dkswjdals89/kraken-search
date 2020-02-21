package com.dkswjdals89.krakensearch.config.jackson;

import com.dkswjdals89.krakensearch.constant.StatusCode;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class StatusCodeSerialize extends JsonSerializer<StatusCode>
{
    @Override
    public void serialize(StatusCode statusCode, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
    {
        jsonGenerator.writeNumber(statusCode.getCode());
    }
}
