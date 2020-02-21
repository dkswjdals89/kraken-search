package com.dkswjdals89.krakensearch.config.jackson;

import com.dkswjdals89.krakensearch.constant.ServiceError;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class StatusCodeSerialize extends JsonSerializer<ServiceError>
{
    @Override
    public void serialize(ServiceError serviceError, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
    {
        jsonGenerator.writeNumber(serviceError.getStatusCode());
    }
}
