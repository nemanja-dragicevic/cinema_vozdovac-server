package com.master.BioskopVozdovac.kafka;

import com.cinema_platform.avro.Employee;
import com.cinema_platform.avro.Ticket;
//import com.javatechie.dto.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class ConfirmationProducer {

    private final KafkaTemplate<String, Employee> employeeKafkaTemplate;
    private final KafkaTemplate<String, Ticket> ticketKafkaTemplate;

    @Value("${topic.name}")
    private String topicName;

    @Value("${topic.confirmation}")
    private String confirmationTopic;

    public void send(Employee employee) {
        System.out.println("Producing to ..." + topicName);
        employeeKafkaTemplate.send(topicName, employee);
    }

    public void sendConfirmation(Ticket ticket) {
        System.out.println("Sending confirmation ..." + confirmationTopic);
        ticketKafkaTemplate.send(confirmationTopic, ticket);
    }

    public void sendIt(Employee employee){
        CompletableFuture<SendResult<String, Employee>> future = employeeKafkaTemplate.send(topicName, UUID.randomUUID().toString(),employee);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + employee +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        employee + "] due to : " + ex.getMessage());
            }
        });
    }

}
