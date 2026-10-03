package SpringBoot.Billwise.service;



import org.springframework.stereotype.Service;

import SpringBoot.Billwise.entity.Invoice;

@Service
public class NotificationService {

    public boolean sendReminder(Invoice invoice, String reminderType) {
        System.out.println("--------------------------------");
        System.out.println("REMINDER SENT");
        System.out.println("Invoice Number :"+ invoice.getInvoiceNumber());
        System.out.println("Customer       :" + invoice.getCustomer().getName());
        System.out.println("Email 			:" + invoice.getCustomer().getEmail());
        System.out.println("Reminder Type:" + reminderType);
        System.out.println("--------------------------------");

        return true;
    }
}