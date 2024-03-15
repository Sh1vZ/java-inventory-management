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
        return Response.status(status).header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
                .entity(jsonResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
