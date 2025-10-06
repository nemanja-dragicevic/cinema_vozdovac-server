package com.master.BioskopVozdovac.kafka;

import com.cinema_platform.avro.Employee;
import com.cinema_platform.avro.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaProducer kafkaProducer;
    private final ConfirmationProducer confirmationProducer;
    private final KafkaJSONProducer kafkaJSONProducer;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String sendMessage(@RequestBody String message) {
        kafkaProducer.send("testing", message);
        return "Sent message: " + message;
    }

    @PostMapping("/avro")
    @ResponseStatus(HttpStatus.OK)
    public String sendAvroMessage(@RequestBody Employee employee) {
//        confirmationProducer.sendIt(employee);
        confirmationProducer.send(employee);
        return "Sent message: " + employee.toString();
    }

    @PostMapping("/json")
    @ResponseStatus(HttpStatus.OK)
    public String sendJSONMessage(@RequestBody Student message) {
        kafkaJSONProducer.send("testing", message);
        return "Sent JSON message: " + message;
    }

    @PostMapping("/ticket")
    public String sendTicketMessage(@RequestBody Ticket ticket) {
        confirmationProducer.sendConfirmation(ticket);
        return "Sent ticket message: " + ticket.toString();
    }

}
