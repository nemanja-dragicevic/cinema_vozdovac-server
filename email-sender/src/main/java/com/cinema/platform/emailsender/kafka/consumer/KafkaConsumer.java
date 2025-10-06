package com.cinema.platform.emailsender.kafka.consumer;

import com.cinema.platform.emailsender.service.TicketService;
import com.cinema_platform.avro.Employee;
import com.cinema_platform.avro.Ticket;
//import com.javatechie.dto.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final TicketService ticketService;

    @KafkaListener(topics = "testing", groupId = "myGroup")
    public void consumeMessage(String message) {
        log.info("Consuming message: {}", message);
    }

    @KafkaListener(topics = "${topic.ticket}", groupId = "myGroup2")
    public void consumeAvroMessage(ConsumerRecord<String, Employee> consumerRecord) {
        Employee employee = consumerRecord.value();
        log.info("Consuming message: {}", employee.toString());
    }

    @KafkaListener(topics = "${topic.confirmation}", groupId = "ticket-confirmation-avro-cg")
    public void consumeConfirmationMessage(ConsumerRecord<String, Ticket> consumerRecord) {
        Ticket ticket = consumerRecord.value();
        log.info("Consuming message: {}", ticket.toString());
        try {
            ticketService.sendConfirmationEmail(ticket);
        } catch (Exception e) {
            log.error("Error while sending confirmation email", e);
        }
    }

}
