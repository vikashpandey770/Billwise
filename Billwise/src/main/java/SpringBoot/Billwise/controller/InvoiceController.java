package SpringBoot.Billwise.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import SpringBoot.Billwise.entity.Invoice;
import SpringBoot.Billwise.service.InvoiceService;


@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;
    @PostMapping
    public Invoice saveInvoice(@RequestBody Invoice invoice) {
        return invoiceService.saveInvoice(invoice);
    }
    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }
    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id);
    }
    @PutMapping("/{id}")
    public Invoice updateInvoice(
            @PathVariable Long id,
            @RequestBody Invoice invoice) {
        return invoiceService.updateInvoice(id, invoice);
    }
    @DeleteMapping("/{id}")
    public String deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return "Invoice deleted successfully";
    }
    @GetMapping("/status/{status}")
    public List<Invoice> getInvoicesByStatus(
            @PathVariable String status) {
        return invoiceService.getInvoicesByStatus(status);
    }
}