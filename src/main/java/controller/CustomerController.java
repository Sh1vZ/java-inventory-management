package controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dto.customer.CustomerDTO;
import entity.Customer;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mapper.Mapper;
import service.CustomerService;
import util.BodyValidationUtil;

import java.util.Collections;
import java.util.Map;


@Path("/customer")
public class CustomerController extends BaseController {
    private final CustomerService customerService;

    public CustomerController() {
        this.customerService = new CustomerService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        return buildResponse("ok", Response.Status.OK, Mapper.mapList(this.customerService.findAllCustomers(), CustomerDTO.class));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public Response createCustomer(@Valid CustomerDTO customerDto) {
        Response validationResponse = BodyValidationUtil.validateAndBuildResponse(customerDto);
        if (validationResponse != null) {
            return validationResponse;
        }
        Customer customer = Mapper.map(customerDto, Customer.class);
        customer = customerService.createCustomer(customer);
        customerDto = Mapper.map(customer, CustomerDTO.class);
        return buildResponse("created", Response.Status.CREATED, customerDto);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("id") Long id, @Valid CustomerDTO customerDto) {
        Customer existingCustomer = customerService.getCustomerById(id);
        if (existingCustomer == null) {
            Map<String, String> jsonResponse = Collections.singletonMap("error", "Customer not found");
            return buildResponse("not-found", Response.Status.NOT_FOUND, jsonResponse);
        }
        Response validationResponse = BodyValidationUtil.validateAndBuildResponse(customerDto);
        if (validationResponse != null) {
            return validationResponse;
        }

        existingCustomer.setName(customerDto.getName());
        customerDto = Mapper.map(customerService.updateCustomer(existingCustomer), CustomerDTO.class);
        return buildResponse("updated", Response.Status.OK, customerDto);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("id") Long id) {
        Customer existingCustomer = customerService.getCustomerById(id);
        if (existingCustomer == null) {
            return buildResponse("customer-not-found", Response.Status.NOT_FOUND, null);
        }

        return buildResponse("ok", Response.Status.OK, Mapper.map(customerService.updateCustomer(existingCustomer), CustomerDTO.class));
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
        return buildResponse("ok", Response.Status.OK, Mapper.map(existingCustomer, CustomerDTO.class));
    }
}


