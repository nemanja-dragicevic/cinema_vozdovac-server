package com.master.BioskopVozdovac.actor.model;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Testovanje {

    static Logger logger = Logger.getLogger(Testovanje.class.getName());

    public static void main(String[] args) {

        String mail = "yansehamisi@gmail.com";
        String extracted = mail.substring(mail.lastIndexOf("@") + 1).toLowerCase();
        logger.log(Level.INFO, extracted);

    }

}
