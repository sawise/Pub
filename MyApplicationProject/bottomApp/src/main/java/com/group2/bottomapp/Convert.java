package com.group2.bottomapp;

import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.Locale;

/**
 * Created by sam on 11/27/13.
 */
public class Convert {
    public double noComma(double value){
        String valueStr = String.format(Locale.US, "%.2f", value);
        return Double.valueOf(valueStr);
    }
    public double ozTocl(double oz) {

        double cl = noComma(oz/0.35195);
        return roundTwodecimals(cl);
    }

    public double clTooz(double cl) {
        double oz = noComma(cl*0.35195);
        return roundTwodecimals(oz);
    }


    public double ozToml(double oz) {
        double ml = noComma(oz/0.033814);
        return roundTwodecimals(ml);
    }
    //0.033814
    public double mlTocl(double ml) {
        double cl = noComma(ml/10);
        return roundTwodecimals(cl);
    }
    public double mlTooz(double ml) {
        double oz = noComma(ml*0.035195);
        return roundTwodecimals(oz);
    }

    public double clToml(double cl) {
        double ml = noComma(cl*10);
        return roundTwodecimals(ml);
    }

    public double roundTwodecimals(double d){
        DecimalFormat twoDForm = new DecimalFormat("#.#");
        //double valueDouble = twoDForm.format(d);
        String valueStr = String.format(Locale.US, "%.1f", d);
        //return Double.valueOf(valueStr);
        return Double.valueOf(valueStr);
    }
}
