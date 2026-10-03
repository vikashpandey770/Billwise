package SpringBoot.Billwise.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import SpringBoot.Billwise.entity.Invoice;
import SpringBoot.Billwise.entity.InvoiceStatus;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
	

    List<Invoice> findByStatus(InvoiceStatus status);

    List<Invoice> findByDueDate(LocalDate dueDate);

    List<Invoice> findByStatusAndDueDate(
            InvoiceStatus status,
            LocalDate dueDate
    );

    List<Invoice> findByDueDateBeforeAndStatusNot(
            LocalDate date,
            InvoiceStatus status
    );
	
}
