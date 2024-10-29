package edu.coderhouse.invoicing.controller;

import edu.coderhouse.invoicing.dto.InvoiceDetailDTO;
import edu.coderhouse.invoicing.entity.InvoiceDetailEntity;
import edu.coderhouse.invoicing.entity.ProductEntity;
import edu.coderhouse.invoicing.service.InvoiceDetailService;
import edu.coderhouse.invoicing.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController //Transforma la clase en un controlador de Spring
@RequestMapping("/api/invoice-details")
public class InvoiceDetailController {
    @Autowired
    private InvoiceDetailService invoiceDetailService;
    private ProductService productService;

    //Constructor
    public InvoiceDetailController(InvoiceDetailService invoiceDetailService, ProductService productService) {
        this.invoiceDetailService = invoiceDetailService;
        this.productService = productService;
    }
    //PARA TRAER TODOS LOS DETALLES
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Retrieve all invoice details", description = "Fetches all invoice details from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice details retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetailDTO.class))),
            @ApiResponse(responseCode = "204", description = "No invoice details found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<InvoiceDetailDTO>> getAll() {
        List<InvoiceDetailEntity> invoiceDetails = invoiceDetailService.getInvoiceDetails();

        if (invoiceDetails.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content si no hay detalles
        }

        // Convertir cada InvoiceDetailEntity en un InvoiceDetailDTO
        List<InvoiceDetailDTO> invoiceDetailDTOs = invoiceDetails.stream()
                .map(InvoiceDetailDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(invoiceDetailDTOs); // 200 OK con la lista de DTOs
    }


    //PARA TRAER UN DETALLE POR ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieve invoice detail by ID", description = "Fetches a specific invoice detail by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice detail found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetailDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invoice detail not found",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<InvoiceDetailDTO> getById(
            @Parameter(description = "ID of the invoice detail to retrieve", required = true)
            @PathVariable long id) {

        // Obtengo el detalle de factura por ID y verifico si está presente
        Optional<InvoiceDetailEntity> invoiceDetail = Optional.ofNullable(invoiceDetailService.getById(id));

        // Mapear InvoiceDetailEntity a InvoiceDetailDTO si existe
        return invoiceDetail
                .map(detail -> ResponseEntity.ok(new InvoiceDetailDTO(detail)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //PARA AGREGAR UN NUEVO DETALLE
//    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    @Operation(summary = "Create a new invoice detail", description = "Adds a new invoice detail to the system.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Invoice detail successfully created",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetailEntity.class))),
//            @ApiResponse(responseCode = "400", description = "Invalid request data",
//                    content = @Content(mediaType = "application/json")),
//            @ApiResponse(responseCode = "500", description = "Internal server error",
//                    content = @Content(mediaType = "application/json"))
//    })
//    public ResponseEntity<InvoiceDetailEntity> create(
//            @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    description = "Invoice detail data to create", required = true,
//                    content = @Content(schema = @Schema(implementation = InvoiceDetailEntity.class)))
//            @RequestBody InvoiceDetailEntity invoiceDetail) {
//
//        try {
//            InvoiceDetailEntity newInvoiceDetail = invoiceDetailService.save(invoiceDetail);
//            return ResponseEntity.ok(newInvoiceDetail); // 200 OK al crear el detalle de factura
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().build(); // 500 Error interno del servidor
//        }
//    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Create a new invoice detail", description = "Adds a new invoice detail to the system, without associating an invoice or client initially.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Invoice detail successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetailDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })

    public ResponseEntity<InvoiceDetailDTO> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Invoice detail data to create, excluding invoice and client", required = true,
                    content = @Content(schema = @Schema(implementation = InvoiceDetailDTO.class)))
            @Valid @RequestBody InvoiceDetailDTO invoiceDetailDTO) {

        try {
            // Verifico existencia del producto
            ProductEntity product = productService.getById(invoiceDetailDTO.getProduct().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            // Verifico que la cantidad solicitada sea menor o igual al stock disponible
            if (invoiceDetailDTO.getAmount() > product.getStock()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null); // O devolver un mensaje adecuado sobre stock insuficiente
            }
            // VOY ACA Y DEBO CAMBIAR EL CODIGO PARA QUE DEVUELVA UN MENSAJE ADECUADO

            // Reducir el stock del producto según la cantidad solicitada
            product.setStock(product.getStock() - invoiceDetailDTO.getAmount());
            productService.updateProductStock(product); // Llamar al servicio para actualizar el stock en la base de datos

            // Mapear de DTO a entidad
            InvoiceDetailEntity invoiceDetail = new InvoiceDetailEntity();
            invoiceDetail.setAmount(invoiceDetailDTO.getAmount());
            invoiceDetail.setPrice(invoiceDetailDTO.getPrice());
            invoiceDetail.setProduct(product);

            // Guardar el detalle de la factura
            InvoiceDetailEntity newInvoiceDetail = invoiceDetailService.save(invoiceDetail);

            // Mapear de nuevo a DTO para la respuesta
            InvoiceDetailDTO responseDTO = new InvoiceDetailDTO(newInvoiceDetail);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO); // 201 Created

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 Bad Request si el producto no se encuentra
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build(); // 500 Error interno del servidor
        }
    }


       //PARA ACTUALIZAR UN DETALLE
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<InvoiceDetailEntity> update(@PathVariable long id, @RequestBody InvoiceDetailEntity invoiceDetail) {
        Optional<InvoiceDetailEntity> updatedInvoiceDetail = invoiceDetailService.update(id, invoiceDetail);

        // Verifico si el detalle fue encontrada y actualizada
        if (updatedInvoiceDetail.isPresent()) {
            return ResponseEntity.ok(updatedInvoiceDetail.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //PARA ELIMINAR UN DETALLE
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        boolean deleted = invoiceDetailService.delete(id);

        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}
