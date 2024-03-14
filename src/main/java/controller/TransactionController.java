package controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dto.transaction.CreateTransactionDTO;
import dto.transaction.LineItemDTO;
import dto.transaction.TransactionDTO;
import entity.Customer;
import entity.LineItem;
import entity.Product;
import entity.Transaction;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mapper.Mapper;
import service.CustomerService;
import service.ProductService;
import service.TransactionService;
import util.BodyValidationUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/transaction")
public class TransactionController extends BaseController {

    private final TransactionService transactionService;
    private final CustomerService customerService;
    private final ProductService productService;

    public TransactionController() {
        this.transactionService = new TransactionService();
        this.customerService = new CustomerService();
        this.productService = new ProductService();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public Response createTransaction(CreateTransactionDTO createTransactionDTO) {
        Response validationResponse = BodyValidationUtil.validateAndBuildResponse(createTransactionDTO);
        if (validationResponse != null) {
            return validationResponse;
        }
        Customer existingCustomer = customerService.getCustomerById(createTransactionDTO.getCustomerId());
        if (existingCustomer == null) {
            return buildResponse("customer-not-found", Response.Status.NOT_FOUND, null);
        }
        List<LineItemDTO> lineItemDTOList = createTransactionDTO.getLineItems();
        Set<Long> uniqueProductIds = new HashSet<>();
        List<LineItemDTO> uniqueLineItemDTOList = lineItemDTOList.stream().filter(lineItemDTO -> uniqueProductIds.add(lineItemDTO.getProductId())).collect(Collectors.toList());

        Set<LineItem> lineItems = new HashSet<>();
        for (LineItemDTO lineItemDTO : uniqueLineItemDTOList) {
            Product existingProduct = productService.getProductById(lineItemDTO.getProductId());
            if (existingProduct == null) continue;
            LineItem lineItem = new LineItem(existingProduct, lineItemDTO.getQuantity());
            lineItems.add(lineItem);
        }
        Transaction tx = transactionService.createTransaction(existingCustomer, lineItems);
        return buildResponse("created", Response.Status.CREATED, Mapper.map(tx, TransactionDTO.class));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransaction() {
        List<Transaction> tx = transactionService.getAllTransactions();
        return buildResponse("created", Response.Status.CREATED, Mapper.mapList(tx, TransactionDTO.class));
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("id") Long id) {
        Transaction existingTx = transactionService.getTxById(id);
        if (existingTx == null) {
            return buildResponse("transaction-not-found", Response.Status.NOT_FOUND, null);
        }
        return buildResponse("ok", Response.Status.OK, Mapper.map(existingTx, TransactionDTO.class));
    }
}
