package com.group2.bottomapp;

import android.util.Log;

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
    public int ozTocl(double oz) {
        double cl = noComma(oz/0.35195);
        return roundTwodecimals(cl);
    }

    public int clTooz(double cl) {
        double oz = noComma(cl*0.35195);
        return roundTwodecimals(oz);
    }


    public int ozToml(double oz) {
        double ml = noComma(oz/0.033814);
        return roundTwodecimals(ml);
    }
    //0.033814
    public int mlTocl(double ml) {
        double cl = noComma(ml/10);
        return roundTwodecimals(cl);
    }
    public int mlTooz(double ml) {
        double oz = noComma(ml*0.035195);
        return roundTwodecimals(oz);
    }

    public int clToml(double cl) {
        double ml = noComma(cl*10);
        return roundTwodecimals(ml);
    }

    public int roundTwodecimals(double d){
        double noDecimal = Math.ceil(d);
        Log.i("round before", ""+noDecimal);
        int noDecimalInt = (int) noDecimal;
        Log.i("round after", ""+noDecimalInt);
        return noDecimalInt;
        /*DecimalFormat twoDForm = new DecimalFormat("#");
        String valueStr = String.format(Locale.US, "%.1f", d);
        double oneDecimal = Double.valueOf(valueStr);
        String noDecimalStr = twoDForm.format(oneDecimal);
        double noDecimal = Double.valueOf(noDecimalStr);
        
        return Double.valueOf(valueStr);*/
    }

    public String convertString(Ingredient i, String format){
        String mesurementStr = i.getMeasurement();
        String result = "";
        if(mesurementStr.contains("cl") || mesurementStr.contains("oz") || mesurementStr.contains("ml")){
            String[] mesurement = mesurementStr.split(" ");
            double mesurementDouble = Double.valueOf(mesurement[0]);
            int mesurmentInt = 0;
            result += i.getName() + ", ";
            if(mesurementStr.contains("oz")){
                if(format.equals("cl")){
                    mesurmentInt = ozTocl(mesurementDouble);
                    result +=  mesurmentInt + " cl\n";
                } else if(format.equals("ml")){
                    mesurmentInt = ozToml(mesurementDouble);
                    result +=  mesurmentInt + " ml \n";
                } else {
                    mesurmentInt = roundTwodecimals(mesurementDouble);
                    result +=  mesurmentInt + " fl oz \n";
                }
            }else if(mesurementStr.contains("cl")){
                if(format.equals("oz")){
                    mesurmentInt = clTooz(mesurementDouble);
                    result +=  mesurmentInt + " fl oz \n";
                } else if(format.equals("ml")){
                    mesurmentInt = clToml(mesurementDouble);
                    result +=  mesurmentInt + " ml \n";
                } else {
                    mesurmentInt = roundTwodecimals(mesurementDouble);
                    result +=  mesurmentInt + " cl \n";
                }
            }else if(mesurementStr.contains("ml")){
                if(format.equals("oz")){
                    mesurmentInt = mlTooz(mesurementDouble);
                    result +=  mesurmentInt + " fl oz \n";
                } else if(format.equals("cl")){
                    mesurmentInt = mlTocl(mesurementDouble);
                    result +=  mesurmentInt + " cl \n";
                } else {
                    mesurmentInt = roundTwodecimals(mesurementDouble);
                    result +=  mesurmentInt  + " ml\n";
                }
            } else{
                mesurmentInt = roundTwodecimals(mesurementDouble);
                result +=  mesurmentInt  + " cl\n";
            }
        }else {
            result += i.getName() + ", " + mesurementStr + "\n";
        }
        return result;
    }


}
