package edu.coderhouse.invoicing.loaders;

import edu.coderhouse.invoicing.entity.ClientEntity;
import edu.coderhouse.invoicing.entity.InvoiceEntity;
import edu.coderhouse.invoicing.entity.InvoiceDetailEntity;
import edu.coderhouse.invoicing.entity.ProductEntity;
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
            ClientEntity client1 = clientService.save(new ClientEntity("Juan", "Pérez", "12345678"));
            ClientEntity client2 = clientService.save(new ClientEntity("María", "Gómez", "87654321"));
            ClientEntity client3 = clientService.save(new ClientEntity("Carlos", "López", "11223344"));
            ClientEntity client4 = clientService.save(new ClientEntity("Ana", "Martínez", "22334455"));

            // Inserto productos en la tabla products
            ProductEntity product1 = productService.save(new ProductEntity("Producto A", "PROD001", 100, 50.00));
            ProductEntity product2 = productService.save(new ProductEntity("Producto B", "PROD002", 150, 75.00));
            ProductEntity product3 = productService.save(new ProductEntity("Producto C", "PROD003", 200, 25.50));
            ProductEntity product4 = productService.save(new ProductEntity("Producto D", "PROD004", 250, 100.00));

            // Inserto facturas en la tabla invoices
            InvoiceEntity invoice1 = invoiceService.save(new InvoiceEntity(client1, LocalDateTime.now(), 0.0));
            InvoiceEntity invoice2 = invoiceService.save(new InvoiceEntity(client2, LocalDateTime.now(), 0.0));
            InvoiceEntity invoice3 = invoiceService.save(new InvoiceEntity(client3, LocalDateTime.now(), 0.0));

            // Inserto detalles de facturas en la tabla invoice_details
            InvoiceDetailEntity detail1 = new InvoiceDetailEntity(invoice1, product1, 2, product1.getPrice());
            InvoiceDetailEntity detail2 = new InvoiceDetailEntity(invoice1, product2, 1, product2.getPrice());
            InvoiceDetailEntity detail3 = new InvoiceDetailEntity(invoice2, product3, 3, product3.getPrice());
            InvoiceDetailEntity detail4 = new InvoiceDetailEntity(invoice3, product4, 4, product4.getPrice());

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