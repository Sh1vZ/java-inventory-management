package controller;

import dto.inventory.InventoryDTO;
import dto.inventory.UpdateInventoryDTO;
import entity.Inventory;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mapper.Mapper;
import service.InventoryService;
import util.BodyValidationUtil;

import java.util.Collections;
import java.util.Map;

@Path("/inventory")
public class InventoryController extends BaseController {
    private final InventoryService inventoryService;

    public InventoryController() {
        this.inventoryService = new InventoryService();
    }

    @OPTIONS
    public Response options() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
                .build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        return buildResponse("ok", Response.Status.OK, Mapper.mapList(this.inventoryService.getInventories(), InventoryDTO.class));
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateInventory(@PathParam("id") Long id, @Valid UpdateInventoryDTO updateInventoryDto) {
        Inventory existingInventory = inventoryService.getInventoryById(id);
        if (existingInventory == null) {
            Map<String, String> jsonResponse = Collections.singletonMap("error", "Inventory not found");
            return buildResponse("inventory-not-found", Response.Status.NOT_FOUND, jsonResponse);
        }
        Response validationResponse = BodyValidationUtil.validateAndBuildResponse(updateInventoryDto);
        if (validationResponse != null) {
            return validationResponse;
        }

        existingInventory.setAmount(updateInventoryDto.getAmount());
        InventoryDTO inventory = Mapper.map(inventoryService.updateInventory(existingInventory), InventoryDTO.class);
        return buildResponse("updated", Response.Status.OK, inventory);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getInventory(@PathParam("id") Long id) {
        Inventory existingInventory = inventoryService.getInventoryById(id);

        if (existingInventory == null) {
            Map<String, String> jsonResponse = Collections.singletonMap("error", "Inventory not found");
            return buildResponse("inventory-not-found", Response.Status.NOT_FOUND, jsonResponse);
        }

        InventoryDTO inventory = Mapper.map(existingInventory, InventoryDTO.class);
        return buildResponse("updated", Response.Status.OK, inventory);
    }
}
