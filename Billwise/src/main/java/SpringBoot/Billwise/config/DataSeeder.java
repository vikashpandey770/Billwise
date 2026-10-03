package SpringBoot.Billwise.config;



import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import SpringBoot.Billwise.entity.Admin;
import SpringBoot.Billwise.entity.Customer;
import SpringBoot.Billwise.entity.Invoice;
import SpringBoot.Billwise.entity.InvoiceStatus;
import SpringBoot.Billwise.entity.ScheduledJob;
import SpringBoot.Billwise.repository.AdminRepository;
import SpringBoot.Billwise.repository.CustomerRepository;
import SpringBoot.Billwise.repository.InvoiceRepository;
import SpringBoot.Billwise.repository.ScheduledJobRepository;


@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(
            AdminRepository adminRepository,
            CustomerRepository customerRepository,
            InvoiceRepository invoiceRepository,
            ScheduledJobRepository scheduledJobRepository) {

        return args -> {

            BCryptPasswordEncoder encoder =
                    new BCryptPasswordEncoder();

            // ==========================
            // ADMIN
            // ==========================

            if (adminRepository.findByEmail("admin@billwise.com").isEmpty()) {

                Admin admin = new Admin();

                admin.setEmail("admin@billwise.com");

                admin.setPassword(
                        encoder.encode("admin123")
                );

                adminRepository.save(admin);
            }


            // ==========================
            // CUSTOMER 1
            // ==========================

            Customer customer1 = new Customer();

            customer1.setName("Aarav Sharma");
            customer1.setEmail("aarav@gmail.com");

            customer1 = customerRepository.save(customer1);


            // ==========================
            // CUSTOMER 2
            // ==========================

            Customer customer2 = new Customer();

            customer2.setName("Diya Patel");
            customer2.setEmail("diya@gmail.com");

            customer2 = customerRepository.save(customer2);


            // ==========================
            // CUSTOMER 3
            // ==========================

            Customer customer3 = new Customer();

            customer3.setName("Kabir Shah");
            customer3.setEmail("kabir@gmail.com");

            customer3 = customerRepository.save(customer3);


            // ==========================
            // SAMPLE INVOICES
            // ==========================

            if (invoiceRepository.count() == 0) {

                Invoice invoice1 = new Invoice();

                invoice1.setInvoiceNumber("INV-1001");
                invoice1.setCustomer(customer1);
                invoice1.setInvoiceDate(LocalDate.of(2026, 9, 1));
                invoice1.setDueDate(LocalDate.of(2026, 9, 7));
                invoice1.setAmount(new BigDecimal("12500.00"));
                invoice1.setStatus(InvoiceStatus.PENDING);
                invoice1.setDescription("Website development invoice");

                invoiceRepository.save(invoice1);


                Invoice invoice2 = new Invoice();

                invoice2.setInvoiceNumber("INV-1002");
                invoice2.setCustomer(customer2);
                invoice2.setInvoiceDate(LocalDate.of(2026, 9, 2));
                invoice2.setDueDate(LocalDate.of(2026, 9, 8));
                invoice2.setAmount(new BigDecimal("8000.00"));
                invoice2.setStatus(InvoiceStatus.PENDING);
                invoice2.setDescription("Software development invoice");

                invoiceRepository.save(invoice2);


                Invoice invoice3 = new Invoice();

                invoice3.setInvoiceNumber("INV-1003");
                invoice3.setCustomer(customer3);
                invoice3.setInvoiceDate(LocalDate.of(2026, 9, 5));
                invoice3.setDueDate(LocalDate.of(2026, 9, 15));
                invoice3.setAmount(new BigDecimal("22000.00"));
                invoice3.setStatus(InvoiceStatus.PENDING);
                invoice3.setDescription("Application development invoice");

                invoiceRepository.save(invoice3);
            }


            // ==========================
            // SCHEDULED JOB
            // ==========================

            if (scheduledJobRepository
                    .findByJobName("INVOICE_REMINDER_JOB")
                    .isEmpty()) {

                ScheduledJob job = new ScheduledJob();

                job.setJobName("INVOICE_REMINDER_JOB");
                job.setEnabled(true);
                job.setSchedule("0 0 * * * *");

                scheduledJobRepository.save(job);
            }

            System.out.println("=================================");
            System.out.println("BillWise seed data inserted");
            System.out.println("Admin Email: admin@billwise.com");
            System.out.println("Admin Password: admin123");
            System.out.println("=================================");
        };
    }
}