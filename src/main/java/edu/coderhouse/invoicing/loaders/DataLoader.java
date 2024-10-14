package edu.coderhouse.invoicing.loaders;

import edu.coderhouse.invoicing.entity.Client;
import edu.coderhouse.invoicing.entity.Invoice;
import edu.coderhouse.invoicing.entity.InvoiceDetail;
import edu.coderhouse.invoicing.entity.Product;
import edu.coderhouse.invoicing.service.ClientService;
import edu.coderhouse.invoicing.service.InvoiceDetailService;
import edu.coderhouse.invoicing.service.InvoiceService;
import edu.coderhouse.invoicing.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ClientService clientService, ProductService productService,
                                   InvoiceService invoiceService, InvoiceDetailService invoiceDetailService) {
        return args -> {

            // Inserto clientes en la tabla clients
            Client client1 = clientService.save(new Client("Juan", "Pérez", "12345678"));
            Client client2 = clientService.save(new Client("María", "Gómez", "87654321"));
            Client client3 = clientService.save(new Client("Carlos", "López", "11223344"));
            Client client4 = clientService.save(new Client("Ana", "Martínez", "22334455"));

            // Inserto productos en la tabla products
            Product product1 = productService.save(new Product("Producto A", "PROD001", 100, 50.00));
            Product product2 = productService.save(new Product("Producto B", "PROD002", 150, 75.00));
            Product product3 = productService.save(new Product("Producto C", "PROD003", 200, 25.50));
            Product product4 = productService.save(new Product("Producto D", "PROD004", 250, 100.00));

            // Inserto facturas en la tabla invoices
            Invoice invoice1 = invoiceService.save(new Invoice(client1, LocalDateTime.now(), 0.0));
            Invoice invoice2 = invoiceService.save(new Invoice(client2, LocalDateTime.now(), 0.0));
            Invoice invoice3 = invoiceService.save(new Invoice(client3, LocalDateTime.now(), 0.0));

            // Inserto detalles de facturas en la tabla invoice_details
            InvoiceDetail detail1 = new InvoiceDetail(invoice1, product1, 2, product1.getPrice());
            InvoiceDetail detail2 = new InvoiceDetail(invoice1, product2, 1, product2.getPrice());
            InvoiceDetail detail3 = new InvoiceDetail(invoice2, product3, 3, product3.getPrice());
            InvoiceDetail detail4 = new InvoiceDetail(invoice3, product4, 4, product4.getPrice());

            invoiceDetailService.saveAll(Arrays.asList(detail1, detail2, detail3, detail4));

            // Actualizo el total de cada factura sumando los detalles
            invoice1.setTotal(detail1.getPrice() * detail1.getAmount() + detail2.getPrice() * detail2.getAmount());
            invoice2.setTotal(detail3.getPrice() * detail3.getAmount());
            invoice3.setTotal(detail4.getPrice() * detail4.getAmount());

            invoiceService.saveAll(Arrays.asList(invoice1, invoice2, invoice3));

            System.out.println("Datos iniciales insertados en las tablas Client, Product, Invoice e InvoiceDetail.");
        };
    }
}