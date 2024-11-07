package edu.coderhouse.invoicing.controller;

import edu.coderhouse.invoicing.dto.ErrorResponseDto;
import edu.coderhouse.invoicing.dto.InvoiceDTO;
import edu.coderhouse.invoicing.entity.ClientEntity;
import edu.coderhouse.invoicing.entity.InvoiceDetailEntity;
import edu.coderhouse.invoicing.entity.InvoiceEntity;
import edu.coderhouse.invoicing.entity.ProductEntity;
import edu.coderhouse.invoicing.service.ClientService;
import edu.coderhouse.invoicing.service.InvoiceService;
import edu.coderhouse.invoicing.service.ProductService;
import edu.coderhouse.invoicing.service.TimeApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.Optional;

@RestController //Transforma la clase en un controlador de Spring
@RequestMapping("/api/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;

    @Autowired
    private TimeApiService timeApiService;

    //Constructor
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    //PARA TRAER TODAS LAS FACTURAS
    @Operation(summary = "Gets all invoices", description = "Fetches all the invoices from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<InvoiceEntity>> getAll() {
        Iterable<InvoiceEntity> invoices = invoiceService.getInvoices();
        return ResponseEntity.ok(invoices);
    }

    //PARA TRAER UNA FACTURA POR ID
    @Operation(summary = "Gets an invoice by ID", description = "Fetches an invoice by its unique ID from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceEntity.class))),
            @ApiResponse(responseCode = "404", description = "Invoice not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Optional<InvoiceEntity>> getById(
            @Parameter(description = "ID of the invoice to retrieve", required = true)
            @PathVariable long id) {
        Optional<InvoiceEntity> invoice = invoiceService.getById(id);

        if (invoice.isPresent()) {
            return ResponseEntity.ok(invoice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PARA TRAER UNA FACTURA CON EL CLIENTE RELACIONADO Y LOS DETALLES CORRESPONDIENTES
    @GetMapping(value = "/{id}/with-client", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Retrieve an invoice with client details", description = "Fetches an invoice by its ID and includes associated client and invoice details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invoice not found",
                    content = @Content)
    })
    public ResponseEntity<InvoiceDTO> getInvoiceWithClient(
            @Parameter(description = "ID of the invoice to be retrieved", required = true)
            @PathVariable long id) {
        Optional<InvoiceEntity> invoice = invoiceService.getById(id);
        return invoice.map(invoiceEntity -> ResponseEntity.ok(new InvoiceDTO(invoiceEntity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PARA CREAR UNA NUEVA FACTURA
    @Operation(
            summary = "Add a new invoice",
            description = "Creates a new invoice and returns the created invoice entity."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid parameters provided",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(
            @Parameter(description = "Invoice entity to be created", required = true)
            @RequestBody
            @Schema(description = "Invoice data to create a new invoice",
                    example = "{\n  \"client\": { \"id\": 8 },\n  \"invoiceDetails\": [\n    {\n      \"product\": { \"id\": 1 },\n      \"amount\": 1\n    }\n  ]\n}")
            InvoiceEntity invoice) {
        try {
            // Verificar existencia del cliente
            Optional<ClientEntity> clientOpt = clientService.getById(invoice.getClient().getId());
            if (clientOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDto("400", "Bad Request", "Client not found", "clientId"));
            }

            double invoiceTotal = 0;

            // Procesar y validar los detalles de la factura
            for (InvoiceDetailEntity detail : invoice.getInvoiceDetails()) {
                ProductEntity product = productService.getById(detail.getProduct().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Product not found"));


                // Fuerzo la carga de description y code
                product.getDescription();
                product.getCode();

                // Verificar que el stock sea suficiente
                if (detail.getAmount() > product.getStock()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ErrorResponseDto("400", "Bad Request",
                                    "Insufficient stock for product ID: " + product.getId(), "amount"));
                }

                // Reducir el stock del producto
                product.setStock(product.getStock() - detail.getAmount());
                productService.updateProductStock(product);

                // Calcular el precio del detalle y actualizar el total de la factura
                double detailTotal = detail.getAmount() * product.getPrice();
                detail.setPrice(detailTotal);
                invoiceTotal += detailTotal;
            }

            // Asignar el total calculado a la factura
            invoice.setTotal(invoiceTotal);

            // Asigno la hora desde la API
            LocalDateTime createdAtAPI = timeApiService.getCurrentDateTime();
            invoice.setCreatedAt(createdAtAPI);

            // Guardar la factura
            InvoiceEntity newInvoice = invoiceService.save(invoice);
            return ResponseEntity.ok(newInvoice);


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
            return ResponseEntity.internalServerError().build();
        }
    }

    //PARA ELIMINAR UNA FACTURA
    @Operation(summary = "Remove an invoice", description = "Deletes an invoice by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Invoice successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Invoice not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the invoice to delete", required = true)
            @PathVariable long id) {

        boolean deleted = invoiceService.delete(id);

        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }



}
