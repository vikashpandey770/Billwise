package SpringBoot.Billwise.service;


import java.util.List;

import SpringBoot.Billwise.entity.Invoice;


public interface InvoiceService {

    Invoice saveInvoice(Invoice invoice);
    List<Invoice> getAllInvoices();
    Invoice getInvoiceById(Long id);
    Invoice updateInvoice(Long id, Invoice invoice);
    void deleteInvoice(Long id);
    List<Invoice> getInvoicesByStatus(String status);
}