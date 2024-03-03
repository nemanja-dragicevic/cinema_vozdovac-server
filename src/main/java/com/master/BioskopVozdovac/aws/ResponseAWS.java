package com.master.BioskopVozdovac.aws;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAWS {

    private String name;
//    private InputStreamResource content;
    private byte[] pictureContent;

}
