package account.service;

import account.model.jdbc.Payment;

import java.util.List;

public interface PaymentService {
    void add(Payment payment);
    void addAll(List<Payment> payments);
    Payment get(Long id);
}
