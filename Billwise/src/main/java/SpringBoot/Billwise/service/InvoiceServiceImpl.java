package SpringBoot.Billwise.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpringBoot.Billwise.entity.Invoice;
import SpringBoot.Billwise.entity.InvoiceStatus;
import SpringBoot.Billwise.repository.InvoiceRepository;


@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public Invoice saveInvoice(Invoice invoice) {

        return invoiceRepository.save(invoice);
    }

    @Override
    public List<Invoice> getAllInvoices() {

        return invoiceRepository.findAll();
    }

    @Override
    public Invoice getInvoiceById(Long id) {

        return invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    @Override
    public Invoice updateInvoice(Long id, Invoice invoice) {

        Invoice existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        existingInvoice.setInvoiceNumber(invoice.getInvoiceNumber());
        existingInvoice.setCustomer(invoice.getCustomer());
        existingInvoice.setInvoiceDate(invoice.getInvoiceDate());
        existingInvoice.setDueDate(invoice.getDueDate());
        existingInvoice.setAmount(invoice.getAmount());
        existingInvoice.setStatus(invoice.getStatus());
        existingInvoice.setDescription(invoice.getDescription());

        return invoiceRepository.save(existingInvoice);
    }

    @Override
    public void deleteInvoice(Long id) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        invoiceRepository.delete(invoice);
    }

    @Override
    public List<Invoice> getInvoicesByStatus(String status) {

        InvoiceStatus invoiceStatus =
                InvoiceStatus.valueOf(status.toUpperCase());

        return invoiceRepository.findByStatus(invoiceStatus);
    }
}