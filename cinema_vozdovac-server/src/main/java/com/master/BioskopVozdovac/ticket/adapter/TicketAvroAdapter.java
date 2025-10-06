package com.master.BioskopVozdovac.ticket.adapter;

import com.cinema_platform.avro.Projection;
import com.cinema_platform.avro.Ticket;
import com.cinema_platform.avro.TicketItem;
import com.master.BioskopVozdovac.ticket.model.TicketEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public final class TicketAvroAdapter {

    private TicketAvroAdapter() {
        throw new AssertionError();
    }

    public static Ticket entityToAvro(TicketEntity entity) {
        final Ticket ticket = new Ticket();

        ticket.setId(entity.getId());
        ticket.setMemberName(entity.getMember().getFirstName() + " " + entity.getMember().getLastName());
        ticket.setTotal(entity.getTotal().intValue());
        ticket.setPayinTime(getTimeInMillis(entity.getPayinTime()));

        final List<TicketItem> items = entity.getTicketItems()
                .stream()
                .map(item -> new TicketItem(
                        new Projection(
                                item.getProject().getMovie().getName(),
                                item.getProject().getHall().getHallName(),
                                getTimeInMillis(item.getProject().getTime())
                        ),
                        item.getSeat().getSeatNumber()
                )).toList();
        ticket.setItems(items);

        return ticket;
    }

    private static long getTimeInMillis(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

}
