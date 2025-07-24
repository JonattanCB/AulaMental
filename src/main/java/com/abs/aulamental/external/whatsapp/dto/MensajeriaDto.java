package com.abs.aulamental.external.whatsapp.dto;

public record MensajeriaDto(
        String phone,
        String message,
        String reply_message_id,
        Boolean is_forwarded
) {
}
