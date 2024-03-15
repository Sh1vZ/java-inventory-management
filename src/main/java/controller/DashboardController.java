package controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.CustomerService;
import service.ProductService;
import service.TransactionService;

import java.util.HashMap;
import java.util.Map;

@Path("/dashboard")
public class DashboardController extends BaseController {
    private final TransactionService transactionService;
    private final CustomerService customerService;
    private final ProductService productService;


    public DashboardController() {
        this.transactionService = new TransactionService();
        this.customerService = new CustomerService();
        this.productService = new ProductService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        Long txCount = transactionService.counTransactions();
        Long sumTx = transactionService.sumTransactions();
        Long customersCount = customerService.countAllCustomers();
        Long productsCount = productService.countAllProducts();
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("transactionCount", txCount);
        jsonResponse.put("customerCount", customersCount);
        jsonResponse.put("productsCount", productsCount);
        jsonResponse.put("sumTransactions", sumTx);

        return buildResponse("ok", Response.Status.OK, jsonResponse);
    }
}
