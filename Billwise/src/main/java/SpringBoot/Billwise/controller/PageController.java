package SpringBoot.Billwise.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard";
    }
    @GetMapping("/customers-page")
    public String customersPage() {
        return "customer";
    }
    @GetMapping("/invoices-page")
    public String invoicesPage() {
        return "invoice";
    }
    
    
}
