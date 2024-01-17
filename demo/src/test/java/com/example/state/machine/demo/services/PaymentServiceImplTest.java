package com.example.state.machine.demo.services;

import com.example.state.machine.demo.domain.Payment;
import com.example.state.machine.demo.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    PaymentService paymentService;
    @Autowired
    PaymentRepository paymentRepository;
    Payment payment;

    @BeforeEach
    void setUp() {
        payment = Payment.builder().amount(new BigDecimal("12.99")).build();
    }

    @Test
    void preAuth() {
        Payment savedPayment = paymentService.newPayment(this.payment);
        var sm = paymentService.preAuth(savedPayment.getId());
        Payment preAuthPayment = paymentRepository.findById(savedPayment.getId()).get();
        System.out.println(sm.getState());
        System.out.println(preAuthPayment);
    }
}