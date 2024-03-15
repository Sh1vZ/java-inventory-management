package controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dto.product.ProductDTO;
import dto.product.UpdateProductDTO;
import entity.Product;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mapper.Mapper;
import service.ProductService;
import util.BodyValidationUtil;

import java.util.Collections;
import java.util.Map;

@Path("/product")
public class ProductController extends BaseController {

    private final ProductService productService;

    public ProductController() {
        this.productService = new ProductService();
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
        return buildResponse("ok", Response.Status.OK, Mapper.mapList(this.productService.findAllProducts(), ProductDTO.class));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public Response createProduct(@Valid ProductDTO productDto) {
        Response validationResponse = BodyValidationUtil.validateAndBuildResponse(productDto);
        if (validationResponse != null) {
            return validationResponse;
        }
        Product product = Mapper.map(productDto, Product.class);
        productDto = Mapper.map(productService.saveProduct(product), ProductDTO.class);
        return buildResponse("created", Response.Status.CREATED, productDto);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("id") Long id, @Valid UpdateProductDTO productDto) {
        Product existingProduct = productService.getProductById(id);
        if (existingProduct == null) {
            Map<String, String> jsonResponse = Collections.singletonMap("error", "Product not found");
            return buildResponse("product-not-found", Response.Status.NOT_FOUND, jsonResponse);
        }
        Response validationResponse = BodyValidationUtil.validateAndBuildResponse(productDto);
        if (validationResponse != null) {
            return validationResponse;
        }

        existingProduct.setName(productDto.getName() != null && !productDto.getName().isEmpty() ? productDto.getName() : existingProduct.getName());
        existingProduct.setColor(productDto.getColor() != null && !productDto.getColor().isEmpty() ? productDto.getColor() : existingProduct.getColor());
        existingProduct.setPrice(productDto.getPrice() != null ? productDto.getPrice() : existingProduct.getPrice());
        existingProduct.setSize(productDto.getSize() != null && !productDto.getSize().isEmpty() ? productDto.getSize() : existingProduct.getSize());
        existingProduct.setSku(productDto.getSku() != null && !productDto.getSku().isEmpty() ? productDto.getSku() : existingProduct.getSku());
        existingProduct.setSupplier(productDto.getSupplier() != null && !productDto.getSupplier().isEmpty() ? productDto.getSupplier() : existingProduct.getSupplier());
        existingProduct.setType(productDto.getType() != null && !productDto.getType().isEmpty() ? productDto.getType() : existingProduct.getType());

        productDto = Mapper.map(productService.updateProduct(existingProduct), UpdateProductDTO.class);
        return buildResponse("updated", Response.Status.OK, productDto);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("id") Long id) {
        Product existingProduct = productService.getProductById(id);
        if (existingProduct == null) {
            Map<String, String> jsonResponse = Collections.singletonMap("error", "Product not found");
            return buildResponse("product-not-found", Response.Status.NOT_FOUND, jsonResponse);
        }
        ProductDTO productDto = Mapper.map(existingProduct, ProductDTO.class);
        return buildResponse("found", Response.Status.OK, productDto);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("id") Long id) {
        Product existingProduct = productService.getProductById(id);
        if (existingProduct == null) {
            Map<String, String> jsonResponse = Collections.singletonMap("error", "Product not found");
            return buildResponse("product-not-found", Response.Status.NOT_FOUND, jsonResponse);
        }
        Product deletedProduct = productService.deleteProduct(id);
        ProductDTO productDto = Mapper.map(deletedProduct, ProductDTO.class);
        return buildResponse("deleted", Response.Status.OK, productDto);
    }

}
