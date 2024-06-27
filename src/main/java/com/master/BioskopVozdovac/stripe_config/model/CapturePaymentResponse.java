package com.master.BioskopVozdovac.stripe_config.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CapturePaymentResponse {
    private String sessionId;
    private String sessionStatus;
    private String paymentStatus;
}
