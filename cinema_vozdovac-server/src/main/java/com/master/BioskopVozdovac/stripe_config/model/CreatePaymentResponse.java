package com.master.BioskopVozdovac.stripe_config.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePaymentResponse {

    private String sessionId;
    private String sessionUrl;

}
