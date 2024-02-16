package com.master.BioskopVozdovac.hall.controller;

import com.master.BioskopVozdovac.hall.service.HallService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/hall")
@AllArgsConstructor
public class HallController {

    private final HallService hallService;

}
