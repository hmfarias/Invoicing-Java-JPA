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
//public class DataLoader {
//
//    @Bean
//    CommandLineRunner initDatabase(ClientService clientService, ProductService productService,
//                                   InvoiceService invoiceService, InvoiceDetailService invoiceDetailService) {
//        return args -> {
//
//            // Inserto clientes en la tabla clients
//            ClientEntity client1 = clientService.save(new ClientEntity("Juan", "Pérez", "12345678"));
//            ClientEntity client2 = clientService.save(new ClientEntity("María", "Gómez", "87654321"));
//            ClientEntity client3 = clientService.save(new ClientEntity("Carlos", "López", "11223344"));
//            ClientEntity client4 = clientService.save(new ClientEntity("Ana", "Martínez", "22334455"));
//            ClientEntity client5 = clientService.save(new ClientEntity("Laura", "Sánchez", "33445566"));
//            ClientEntity client6 = clientService.save(new ClientEntity("Pedro", "Ramírez", "44556677"));
//            ClientEntity client7 = clientService.save(new ClientEntity("Lucía", "Fernández", "55667788"));
//
//            // Inserto productos en la tabla products
//            ProductEntity product1 = productService.save(new ProductEntity("Iphone 16 Pro Max - 256Gb", "PROD001", 100, 1500.00));
//            ProductEntity product2 = productService.save(new ProductEntity("Cover for Iphone 16 Pro Max", "PROD002", 150, 75.00));
//            ProductEntity product3 = productService.save(new ProductEntity("Ipad Pro 13' - 512Gb", "PROD003", 200, 1225.50));
//            ProductEntity product4 = productService.save(new ProductEntity("Liquid Silicone Case For iPhone", "PROD004", 250, 100.00));
//            ProductEntity product5 = productService.save(new ProductEntity("Macbook Air 15 Con Chip M2 (2023) 256gb Con 8gb De Ram Color Starlight", "PROD005", 300, 2200.00));
//            ProductEntity product6 = productService.save(new ProductEntity("Original Apple Fast Charger iPhone 13 13 Pro Max Usb-c 20w White", "PROD006", 400, 80.00));
//            ProductEntity product7 = productService.save(new ProductEntity("Apple AirPods (3rd generation) with MagSafe Charging Case", "PROD007", 500, 320.00));
//
//            // Inserto facturas en la tabla invoices
//            InvoiceEntity invoice1 = invoiceService.save(new InvoiceEntity(client1, LocalDateTime.now(), 0.0));
//            InvoiceEntity invoice2 = invoiceService.save(new InvoiceEntity(client2, LocalDateTime.now(), 0.0));
//            InvoiceEntity invoice3 = invoiceService.save(new InvoiceEntity(client3, LocalDateTime.now(), 0.0));
//            InvoiceEntity invoice4 = invoiceService.save(new InvoiceEntity(client4, LocalDateTime.now(), 0.0));
//            InvoiceEntity invoice5 = invoiceService.save(new InvoiceEntity(client5, LocalDateTime.now(), 0.0));
//            InvoiceEntity invoice6 = invoiceService.save(new InvoiceEntity(client6, LocalDateTime.now(), 0.0));
//            InvoiceEntity invoice7 = invoiceService.save(new InvoiceEntity(client7, LocalDateTime.now(), 0.0));
//
//            // Inserto detalles de facturas en la tabla invoice_details
//            InvoiceDetailEntity detail1 = new InvoiceDetailEntity(invoice1, product1, 2, product1.getPrice());
//            InvoiceDetailEntity detail2 = new InvoiceDetailEntity(invoice1, product2, 1, product2.getPrice());
//            InvoiceDetailEntity detail3 = new InvoiceDetailEntity(invoice2, product3, 3, product3.getPrice());
//            InvoiceDetailEntity detail4 = new InvoiceDetailEntity(invoice3, product4, 4, product4.getPrice());
//            InvoiceDetailEntity detail5 = new InvoiceDetailEntity(invoice3, product5, 1, product5.getPrice());
//            InvoiceDetailEntity detail6 = new InvoiceDetailEntity(invoice4, product6, 2, product6.getPrice());
//            InvoiceDetailEntity detail7 = new InvoiceDetailEntity(invoice4, product7, 3, product7.getPrice());
//            InvoiceDetailEntity detail8 = new InvoiceDetailEntity(invoice5, product1, 1, product1.getPrice());
//            InvoiceDetailEntity detail9 = new InvoiceDetailEntity(invoice6, product2, 5, product2.getPrice());
//            InvoiceDetailEntity detail10 = new InvoiceDetailEntity(invoice7, product3, 2, product3.getPrice());
//
//            // Guardar los detalles de facturas
//            invoiceDetailService.saveAll(Arrays.asList(detail1, detail2, detail3, detail4, detail5, detail6, detail7, detail8, detail9, detail10));
//
//            // Actualizo el total de cada factura sumando los detalles
//            invoice1.setTotal(detail1.getPrice() * detail1.getAmount() + detail2.getPrice() * detail2.getAmount());
//            invoice2.setTotal(detail3.getPrice() * detail3.getAmount());
//            invoice3.setTotal(detail4.getPrice() * detail4.getAmount() + detail5.getPrice() * detail5.getAmount());
//            invoice4.setTotal(detail6.getPrice() * detail6.getAmount() + detail7.getPrice() * detail7.getAmount());
//            invoice5.setTotal(detail8.getPrice() * detail8.getAmount());
//            invoice6.setTotal(detail9.getPrice() * detail9.getAmount());
//            invoice7.setTotal(detail10.getPrice() * detail10.getAmount());
//
//            // Guardar las facturas con los totales actualizados
//            invoiceService.saveAll(Arrays.asList(invoice1, invoice2, invoice3, invoice4, invoice5, invoice6, invoice7));
//
//            System.out.println("Initial data inserted in the Client, Product, Invoice and InvoiceDetail tables.");
//        };
//    }
//}
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
            ClientEntity client5 = clientService.save(new ClientEntity("Laura", "Sánchez", "33445566"));
            ClientEntity client6 = clientService.save(new ClientEntity("Pedro", "Ramírez", "44556677"));
            ClientEntity client7 = clientService.save(new ClientEntity("Lucía", "Fernández", "55667788"));

            // Inserto productos en la tabla products
            ProductEntity product1 = productService.save(new ProductEntity("Iphone 16 Pro Max - 256Gb", "PROD001", 100, 1500.00));
            ProductEntity product2 = productService.save(new ProductEntity("Cover for Iphone 16 Pro Max", "PROD002", 150, 75.00));
            ProductEntity product3 = productService.save(new ProductEntity("Ipad Pro 13' - 512Gb", "PROD003", 200, 1225.50));
            ProductEntity product4 = productService.save(new ProductEntity("Liquid Silicone Case For iPhone", "PROD004", 250, 100.00));
            ProductEntity product5 = productService.save(new ProductEntity("Macbook Air 15 Con Chip M2 (2023) 256gb Con 8gb De Ram Color Starlight", "PROD005", 300, 2200.00));
            ProductEntity product6 = productService.save(new ProductEntity("Original Apple Fast Charger iPhone 13 13 Pro Max Usb-c 20w White", "PROD006", 400, 80.00));
            ProductEntity product7 = productService.save(new ProductEntity("Apple AirPods (3rd generation) with MagSafe Charging Case", "PROD007", 500, 320.00));

            // Inserto facturas en la tabla invoices
            InvoiceEntity invoice1 = invoiceService.save(new InvoiceEntity(client1, LocalDateTime.now(), 0.0));
            InvoiceEntity invoice2 = invoiceService.save(new InvoiceEntity(client2, LocalDateTime.now(), 0.0));
            InvoiceEntity invoice3 = invoiceService.save(new InvoiceEntity(client3, LocalDateTime.now(), 0.0));
            InvoiceEntity invoice4 = invoiceService.save(new InvoiceEntity(client4, LocalDateTime.now(), 0.0));
            InvoiceEntity invoice5 = invoiceService.save(new InvoiceEntity(client5, LocalDateTime.now(), 0.0));
            InvoiceEntity invoice6 = invoiceService.save(new InvoiceEntity(client6, LocalDateTime.now(), 0.0));
            InvoiceEntity invoice7 = invoiceService.save(new InvoiceEntity(client7, LocalDateTime.now(), 0.0));

            // Facturas adicionales para algunos clientes
            InvoiceEntity invoice8 = invoiceService.save(new InvoiceEntity(client1, LocalDateTime.now(), 0.0));
            InvoiceEntity invoice9 = invoiceService.save(new InvoiceEntity(client3, LocalDateTime.now(), 0.0));
            InvoiceEntity invoice10 = invoiceService.save(new InvoiceEntity(client5, LocalDateTime.now(), 0.0));

            // Inserto detalles de facturas en la tabla invoice_details
            InvoiceDetailEntity detail1 = new InvoiceDetailEntity(invoice1, product1, 2, product1.getPrice());
            InvoiceDetailEntity detail2 = new InvoiceDetailEntity(invoice1, product2, 1, product2.getPrice());
            InvoiceDetailEntity detail3 = new InvoiceDetailEntity(invoice2, product3, 3, product3.getPrice());
            InvoiceDetailEntity detail4 = new InvoiceDetailEntity(invoice3, product4, 4, product4.getPrice());
            InvoiceDetailEntity detail5 = new InvoiceDetailEntity(invoice3, product5, 1, product5.getPrice());
            InvoiceDetailEntity detail6 = new InvoiceDetailEntity(invoice4, product6, 2, product6.getPrice());
            InvoiceDetailEntity detail7 = new InvoiceDetailEntity(invoice4, product7, 3, product7.getPrice());
            InvoiceDetailEntity detail8 = new InvoiceDetailEntity(invoice5, product1, 1, product1.getPrice());
            InvoiceDetailEntity detail9 = new InvoiceDetailEntity(invoice6, product2, 5, product2.getPrice());
            InvoiceDetailEntity detail10 = new InvoiceDetailEntity(invoice7, product3, 2, product3.getPrice());

            // Detalles adicionales para las facturas adicionales
            InvoiceDetailEntity detail11 = new InvoiceDetailEntity(invoice8, product4, 1, product4.getPrice());
            InvoiceDetailEntity detail12 = new InvoiceDetailEntity(invoice9, product5, 1, product5.getPrice());
            InvoiceDetailEntity detail13 = new InvoiceDetailEntity(invoice10, product6, 2, product6.getPrice());

            // Guardar los detalles de facturas
            invoiceDetailService.saveAll(Arrays.asList(detail1, detail2, detail3, detail4, detail5, detail6, detail7, detail8, detail9, detail10, detail11, detail12, detail13));

            // Actualizo el total de cada factura sumando los detalles
            invoice1.setTotal(detail1.getPrice() * detail1.getAmount() + detail2.getPrice() * detail2.getAmount());
            invoice2.setTotal(detail3.getPrice() * detail3.getAmount());
            invoice3.setTotal(detail4.getPrice() * detail4.getAmount() + detail5.getPrice() * detail5.getAmount());
            invoice4.setTotal(detail6.getPrice() * detail6.getAmount() + detail7.getPrice() * detail7.getAmount());
            invoice5.setTotal(detail8.getPrice() * detail8.getAmount());
            invoice6.setTotal(detail9.getPrice() * detail9.getAmount());
            invoice7.setTotal(detail10.getPrice() * detail10.getAmount());

            // Totales para las facturas adicionales
            invoice8.setTotal(detail11.getPrice() * detail11.getAmount());
            invoice9.setTotal(detail12.getPrice() * detail12.getAmount());
            invoice10.setTotal(detail13.getPrice() * detail13.getAmount());

            // Guardar las facturas con los totales actualizados
            invoiceService.saveAll(Arrays.asList(invoice1, invoice2, invoice3, invoice4, invoice5, invoice6, invoice7, invoice8, invoice9, invoice10));

            System.out.println("Initial data inserted in the Client, Product, Invoice and InvoiceDetail tables.");
        };
    }
}