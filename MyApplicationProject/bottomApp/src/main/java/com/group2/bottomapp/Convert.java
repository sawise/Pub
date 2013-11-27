package com.group2.bottomapp;

import java.text.DecimalFormat;

/**
 * Created by sam on 11/27/13.
 */
public class Convert {
    public double ozTocl(double oz) {
        double cl = oz/0.35195;
        return roundTwodecimals(cl);
    }

    public double clTooz(double cl) {
        double oz = cl*0.35195;
        return roundTwodecimals(oz);
    }


    public double ozToml(double oz) {
        double ml = oz/0.033814;
        return roundTwodecimals(ml);
    }
    //0.033814
    public double mlTocl(double ml) {
        double cl = ml/10;
        return roundTwodecimals(cl);
    }
    public double mlTooz(double ml) {
        double oz = ml*0.035195;
        return roundTwodecimals(oz);
    }

    public double clToml(double cl) {
        double ml = cl*10;
        return roundTwodecimals(ml);
    }

    public double roundTwodecimals(double d){
        DecimalFormat twoDForm = new DecimalFormat("#.#");
        return Double.valueOf(twoDForm.format(d));
    }
}
