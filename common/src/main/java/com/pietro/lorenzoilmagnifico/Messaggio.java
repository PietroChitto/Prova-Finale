package com.pietro.lorenzoilmagnifico;

import java.io.Serializable;

/**
 * Created by Pietro on 16/05/2017.
 */
public class Messaggio implements Serializable {
    private String messasggio;

    public Messaggio(){}
    public Messaggio(String messasggio){
        this.messasggio=messasggio;
    }

    public String getMessasggio() {
        return messasggio;
    }

    public void setMessasggio(String messasggio) {
        this.messasggio = messasggio;
    }
}
