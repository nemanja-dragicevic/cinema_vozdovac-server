package com.master.BioskopVozdovac.hall.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HallProjections {

    private HallDTO hall;

    private List<String> times;

}
