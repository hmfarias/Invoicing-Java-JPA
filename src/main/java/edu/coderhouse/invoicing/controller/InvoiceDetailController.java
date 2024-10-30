package edu.coderhouse.invoicing.controller;

import edu.coderhouse.invoicing.dto.ErrorResponseDto;
import edu.coderhouse.invoicing.dto.InvoiceDetailDTO;
import edu.coderhouse.invoicing.entity.InvoiceDetailEntity;
import edu.coderhouse.invoicing.entity.ProductEntity;
import edu.coderhouse.invoicing.service.InvoiceDetailService;
import edu.coderhouse.invoicing.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
    //AL AGREGAR UN DETALLE, SE ACTUALIZA EL STOCK DEL PRODUCTO ASOCIADO
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Create a new invoice detail", description = "Adds a new invoice detail to the system, without associating an invoice or client initially.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Invoice detail successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetailDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data or insufficient stock",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Invoice detail data to create, excluding invoice and client",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = InvoiceDetailDTO.class),
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "  \"amount\": 10,\n" +
                                            "  \"product\": {\n" +
                                            "    \"id\": 1\n" +
                                            "  }\n" +
                                            "}"
                            )
                    )
            )
            @Valid @RequestBody InvoiceDetailDTO invoiceDetailDTO) {
        try {
            // Mapeao de DTO a entidad
            InvoiceDetailEntity invoiceDetail = new InvoiceDetailEntity();
            invoiceDetail.setAmount(invoiceDetailDTO.getAmount());

            // Obtengo y verifico el producto
            ProductEntity product = productService.getById(invoiceDetailDTO.getProduct().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            // Verifico que el stock sea suficiente
            if (invoiceDetailDTO.getAmount() > product.getStock()) {
                ErrorResponseDto errorResponse = new ErrorResponseDto(
                        String.valueOf(HttpStatus.BAD_REQUEST.value()),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "Insufficient stock for the requested amount.",
                        "amount"
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            // Calculo el precio multiplicando la cantidad por el precio del producto
            invoiceDetail.setPrice(invoiceDetailDTO.getAmount() * product.getPrice());

            // Reduzco el stock y guardo el detalle de factura
            product.setStock(product.getStock() - invoiceDetailDTO.getAmount());
            productService.updateProductStock(product);

            invoiceDetail.setProduct(product);
            InvoiceDetailEntity newInvoiceDetail = invoiceDetailService.save(invoiceDetail);

            // Mapeao de nuevo a DTO para la respuesta
            InvoiceDetailDTO responseDTO = new InvoiceDetailDTO(newInvoiceDetail);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO); // 201 Created
        } catch (IllegalArgumentException e) {
            ErrorResponseDto errorResponse = new ErrorResponseDto(
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    e.getMessage(),
                    "productId"
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build(); // 500 Error interno del servidor
        }
    }

    // PARA MODIFICAR UN DETALLE EXISTENTE
    // AL MODIFICAR UN DETALLE SE ACTUALIZA EL STOCK DEL PRODUCTO ASOCIADO
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update an existing invoice detail", description = "Updates an existing invoice detail with new information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice detail successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetailDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data or insufficient stock",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Invoice detail not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID of the invoice detail to update", required = true)
            @PathVariable long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated invoice detail data",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = InvoiceDetailDTO.class),
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "  \"amount\": 10,\n" +
                                            "  \"product\": {\n" +
                                            "    \"id\": 1\n" +
                                            "  }\n" +
                                            "}"
                            )
                    )
            )
            @Valid @RequestBody InvoiceDetailDTO invoiceDetailDTO) {

        try {
            // Verifico si el detalle de factura existe
            Optional<InvoiceDetailEntity> existingInvoiceDetailOpt = Optional.ofNullable(invoiceDetailService.getById(id));

            if (existingInvoiceDetailOpt.isEmpty()) {
                ErrorResponseDto errorResponse = new ErrorResponseDto(
                        String.valueOf(HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "Invoice detail not found.",
                        "id: " + id
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }

            InvoiceDetailEntity existingInvoiceDetail = existingInvoiceDetailOpt.get();

            // Obtengo y verifico el producto
            ProductEntity product = productService.getById(invoiceDetailDTO.getProduct().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            // Verifico que el stock sea suficiente (considerando la diferencia en cantidad)
            int stockAdjustment = invoiceDetailDTO.getAmount() - existingInvoiceDetail.getAmount();
            if (stockAdjustment > product.getStock()) {
                ErrorResponseDto errorResponse = new ErrorResponseDto(
                        String.valueOf(HttpStatus.BAD_REQUEST.value()),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "Insufficient stock for the requested amount.",
                        "amount"
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            // Reduzco el stock en función de la nueva cantidad
            product.setStock(product.getStock() - stockAdjustment);
            productService.updateProductStock(product);

            // Actualizo los valores del detalle de factura
            existingInvoiceDetail.setAmount(invoiceDetailDTO.getAmount());
            existingInvoiceDetail.setPrice(invoiceDetailDTO.getAmount() * product.getPrice()); // Calcular el precio
            existingInvoiceDetail.setProduct(product);

            InvoiceDetailEntity updatedInvoiceDetail = invoiceDetailService.save(existingInvoiceDetail);

            // Mapeo de nuevo a DTO para la respuesta
            InvoiceDetailDTO responseDTO = new InvoiceDetailDTO(updatedInvoiceDetail);
            return ResponseEntity.ok(responseDTO); // 200 OK
        } catch (IllegalArgumentException e) {
            ErrorResponseDto errorResponse = new ErrorResponseDto(
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    e.getMessage(),
                    "productId"
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build(); // 500 Error interno del servidor
        }
    }

    //PARA ELIMINAR UN DETALLE
    //AL ELIMINAR EL DETALLE SE RECUPERA EL STOCK EN EL PRODUCTO
    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Delete an invoice detail",
            description = "Deletes an existing invoice detail based on the provided ID, and adjusts the stock of the associated product."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Invoice detail successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Invoice detail not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the invoice detail to delete", required = true)
            @PathVariable long id) {

        // Obtengo el detalle de factura por ID
        InvoiceDetailEntity invoiceDetail = invoiceDetailService.getById(id);

        if (invoiceDetail == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }

        // Obtengo el producto asociado y ajusto el stock
        ProductEntity product = invoiceDetail.getProduct();
        if (product != null) {
            product.setStock(product.getStock() + invoiceDetail.getAmount()); // Restaurar el stock del producto
            productService.updateProductStock(product);
        }

        // Elimino el detalle de factura
        boolean deleted = invoiceDetailService.delete(id);

        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.internalServerError().build(); // 500 Error interno del servidor
        }
    }

}
