package controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import entity.Customer;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.CustomerService;
import util.BodyValidationUtil;

import java.util.List;


@Path("/customer")
public class CustomerController {
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
        return Response.status(Response.Status.CREATED)
                .entity(customer)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("id") Long id, @Valid Customer customer) {
        Customer existingCustomer = customerService.getCustomerById(id);
        if (existingCustomer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer not found")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        existingCustomer.setName(customer.getName());

        Response validationResponse = BodyValidationUtil.validateAndBuildResponse(existingCustomer);
        if (validationResponse != null) {
            return validationResponse;
        }
        customerService.updateCustomer(existingCustomer);
        return Response.ok(existingCustomer, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("id") Long id) {
        Customer existingCustomer = customerService.getCustomerById(id);
        if (existingCustomer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer not found")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        customerService.updateCustomer(existingCustomer);
        return Response.ok(existingCustomer, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCustomer(@PathParam("id") Long id) {
        Customer existingCustomer = customerService.getCustomerById(id);
        if (existingCustomer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer not found")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        customerService.deleteCustomer(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}


