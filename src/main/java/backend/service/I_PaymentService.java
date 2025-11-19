package backend.service;

import backend.entity.Payment;
import backend.entity.User;

import java.util.List;
import java.util.Optional;

public interface I_PaymentService {


    /**
     * Create a new payment for a parent user.
     *
     * @param payment Payment entity
     * @param parent  Parent user
     * @return saved Payment
     */
    Payment createPayment(Payment payment, User parent);

    /**
     * Get all payments.
     *
     * @return list of payments
     */
    List<Payment> getAllPayments();

    /**
     * Get a payment by its ID.
     *
     * @param id payment ID
     * @return optional payment
     */
    Optional<Payment> getPaymentById(Long id);

    /**
     * Update a payment by ID.
     *
     * @param id      payment ID
     * @param payment updated payment data
     * @param parent  optional new parent
     * @return updated payment
     */
    Payment updatePayment(Long id, Payment payment, User parent);

    /**
     * Delete a payment by ID.
     *
     * @param id payment ID
     */
    void deletePayment(Long id);

    /**
     * Get all payments for a specific parent.
     *
     * @param parentEmail parent's email
     * @return list of payments
     */
    List<Payment> getPaymentsByParent(String parentEmail);
}
