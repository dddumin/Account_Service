package account.controller;

import account.model.jdbc.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PaymentController {


    @PostMapping(path = "/acct/payments")
    public ResponseEntity<?> addPayments(List<Payment> payments) {
        return null;
    }
}
