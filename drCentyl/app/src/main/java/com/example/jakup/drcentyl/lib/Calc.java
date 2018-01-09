package com.example.jakup.drcentyl.lib;

import android.os.Bundle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.Math.abs;

/**
 * Created by jkijo on 30.10.2017.
 */


public class Calc {

    //function for calculating exact value from centyl chart on the basis of Poly class
    //Uses reflection to get needed function -> "prefix + what + "18_" + centyl[i]"
    //Allowed "what" variables are Height, Weight, Bmi, Head  // TODO I GUESS WE HAVE NOTHING FOR PRESSURE
    //Allowed sex are 'girls', 'boys'



    public static double[] Calculate (double age, boolean sex, String what)
    {
        Poly p = new Poly();
        p.boysHead18_25(12.44);

        double [] tab = {0,0,0,0,0,0,0};
        int [] centyl = {3, 10, 25, 50, 75, 90, 97};
        String prefix = new String();

        if(sex==true)
            prefix = "boys";
        else
            prefix = "girls";

        if(what == "Bmi" && age < 4.0)
        {
            return tab;
        }
        else
        {
            if(age>=1)
            {
                for (int i =0; i<7 ; i++)
                {
                    Method m = null;
                    try {
                        m = p.getClass().getDeclaredMethod(prefix + what + "18_" + centyl[i], Double.TYPE);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    try {
                        tab[i]=(double) m.invoke(null, age);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

            }
            //children 1-11 months
            else
            {
                //dziedziną są miesiące
                age= age*12;

                for (int i =0; i<7 ; i++)
                {
                    Method m = null;
                    try {
                        m = p.getClass().getDeclaredMethod(prefix + what + "12_" + centyl[i], Double.TYPE);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    try {
                        tab[i]=(double) m.invoke(null, age);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        return tab;
    }

    //function for getting centyl from exact value calculated in Calc.Calculate
    //So the invocation can be nested like in DisplayChartsActivity
    public static int Centyl (double[] tab, double input)
    {
        double diff=100000000;
        double temp;
        int winning=0;
        int [] centyl = {3, 10, 25, 50, 75, 90, 97};

        for (int i= 0; i<7; i++)
        {
            temp = abs(tab[i]-input);
            if (temp < diff) {
                diff = temp;
                winning = i;
            }

        }

        return centyl[winning];

    }
}
