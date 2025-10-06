package com.cinema.platform.emailsender.config;

import com.cinema_platform.avro.Ticket;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class EmailConfig {

    private final JavaMailSender mailSender;

    public EmailConfig(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(final Ticket ticket) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("vozdcone@gmail.com");
            helper.setTo("nemanja.dragicevic.meridian@gmail.com");
            helper.setSubject("Test 1");

            final StringBuilder htmlBuilder = new StringBuilder();

            String htmlContent = HtmlUtil.HTML_START_INFO
                    .replace("${memberName}", ticket.getMemberName())
                    .replace("${id}", String.valueOf(ticket.getId()))
                    .replace("${payinTime}", formatTime(ticket.getPayinTime()));
            htmlBuilder.append(htmlContent);

            ticket.getItems()
                    .forEach(item -> {
                        String formattedDiv = HtmlUtil.PROJECTIONS_DIV
                                .replace("${movieName}", item.getProjection().getMovieName())
                                .replace("${hallName}", item.getProjection().getHallName())
                                .replace("${projectionTime}", formatTime(item.getProjection().getProjectionTime()))
                                .replace("${seat}", String.valueOf(item.getSeat()));
                        htmlBuilder.append(formattedDiv);
                    });
            htmlBuilder.append(HtmlUtil.HTML_ENDING_AND_CLOSING_TAG
                    .replace("${total}", String.valueOf(ticket.getTotal())));

            helper.setText(htmlBuilder.toString(), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("sendConfirmationEmail", e);
        }
    }

    private String formatTime(long time) {
        LocalDateTime payinTime = Instant.ofEpochMilli(time)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return payinTime.format(formatter);
    }

}
