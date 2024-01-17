package com.example.state.machine.demo.services;

import com.example.state.machine.demo.domain.Payment;
import com.example.state.machine.demo.domain.PaymentEvent;
import com.example.state.machine.demo.domain.PaymentState;
import org.springframework.statemachine.StateMachine;

public interface PaymentService {
    Payment newPayment(Payment payment);
    StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId);
    StateMachine<PaymentState, PaymentEvent> declineAuth(Long paymentId);
    StateMachine<PaymentState, PaymentEvent> authorize(Long paymentId);
}
