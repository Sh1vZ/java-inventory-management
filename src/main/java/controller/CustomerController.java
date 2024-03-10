package controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import entity.Customer;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.CustomerService;
import util.BodyValidationUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;


@Path("/customer")
public class CustomerController extends BaseController {
    private final CustomerService customerService;

    public CustomerController() {
        this.customerService = new CustomerService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> findAll() {
        return this.customerService.findAllCustomers();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public Response createCustomer(@Valid Customer customer) {
        Response validationResponse = BodyValidationUtil.validateAndBuildResponse(customer);
        if (validationResponse != null) {
            return validationResponse;
        }

        customer = customerService.createCustomer(customer);
        return buildResponse("created", Response.Status.CREATED, customer);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("id") Long id, @Valid Customer customer) {
        Customer existingCustomer = customerService.getCustomerById(id);
        if (existingCustomer == null) {
            Map<String, String> jsonResponse = Collections.singletonMap("error", "Customer not found");
            return buildResponse("not-found", Response.Status.NOT_FOUND, jsonResponse);
        }

        existingCustomer.setName(customer.getName());

        Response validationResponse = BodyValidationUtil.validateAndBuildResponse(existingCustomer);
        if (validationResponse != null) {
            return validationResponse;
        }
        existingCustomer = customerService.updateCustomer(existingCustomer);
        return buildResponse("updated", Response.Status.OK, existingCustomer);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("id") Long id) {
        Customer existingCustomer = customerService.getCustomerById(id);
        if (existingCustomer == null) {
            return buildResponse("customer-not-found", Response.Status.NOT_FOUND, null);
        }

        customerService.updateCustomer(existingCustomer);
        return buildResponse("ok", Response.Status.OK, existingCustomer);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCustomer(@PathParam("id") Long id) {
        Customer existingCustomer = customerService.getCustomerById(id);
        if (existingCustomer == null) {
            return buildResponse("customer-not-found", Response.Status.NOT_FOUND, null);

        }
        existingCustomer = customerService.deleteCustomer(id);
        return buildResponse("ok", Response.Status.OK, existingCustomer);
    }
}


