package controller;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

public class BaseController {
    protected <T> Response buildResponse(String message, jakarta.ws.rs.core.Response.Status status, T data) {
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("message", message);
        jsonResponse.put("data", data);
        return Response.status(status)
                .entity(jsonResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
