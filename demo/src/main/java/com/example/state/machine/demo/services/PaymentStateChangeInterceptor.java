package com.example.state.machine.demo.services;

import com.example.state.machine.demo.domain.Payment;
import com.example.state.machine.demo.domain.PaymentEvent;
import com.example.state.machine.demo.domain.PaymentState;
import com.example.state.machine.demo.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class PaymentStateChangeInterceptor extends StateMachineInterceptorAdapter<PaymentState, PaymentEvent> {

    private final PaymentRepository paymentRepository;
    @Override
    public void preStateChange(State<PaymentState, PaymentEvent> state, Message<PaymentEvent> message, Transition<PaymentState, PaymentEvent> transition, StateMachine<PaymentState, PaymentEvent> stateMachine, StateMachine<PaymentState, PaymentEvent> rootStateMachine) {
        Optional.ofNullable(message)
                .ifPresent(msg -> {
                    Optional.ofNullable(Long.class.cast(msg.getHeaders().getOrDefault(PaymentServiceImpl.PAYMENT_ID_HEADER, -1L)))
                            .ifPresent(paymentId -> {
                                Payment payment = this.paymentRepository.getReferenceById(paymentId);
                                payment.setState(state.getId());
                                this.paymentRepository.save(payment);
                            });
                });
    }
}
