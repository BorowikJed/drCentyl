package com.example.jakup.drcentyl;

import android.os.Bundle;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.jakup.drcentyl.lib.Calc;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.Years;

//----------------------------------------------------
//TODO New (optional) screen with charts and points - doctor should see it to trust our app
//----------------------------------------------------

//TODO Some code cleanup - it's a mess
public class DisplayChartsActivity extends AppCompatActivity {

	CharSequence text = "nie powinienes widziec tej wiadomosci";

	boolean sex;
	double weight;
	double height;
	int day;
	int month;
	int monthSmall;
	int year;
    String sex_text;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_charts);

        TextView title = (TextView)findViewById(R.id.textView);
        TextView patientDataTitle = (TextView)findViewById(R.id.textView8);
        TextView centylTitle = (TextView)findViewById(R.id.textView14);
        TextView centylHeight = (TextView)findViewById(R.id.textView10);
        TextView centylWeight = (TextView)findViewById(R.id.textView11);
        TextView centylBMI = (TextView)findViewById(R.id.textView12);
        TextView centylPressure = (TextView)findViewById(R.id.textView13);
        TextView centylCircum = (TextView)findViewById(R.id.textView15);

        title.setTextColor(getResources().getColor(R.color.purple));
        patientDataTitle.setTextColor(getResources().getColor(R.color.purple));
        centylTitle.setTextColor(getResources().getColor(R.color.purple));
		centylHeight.setTextColor(getResources().getColor(R.color.purple));
		centylWeight.setTextColor(getResources().getColor(R.color.purple));
		centylBMI.setTextColor(getResources().getColor(R.color.purple));
		centylPressure.setTextColor(getResources().getColor(R.color.purple));
		centylCircum.setTextColor(getResources().getColor(R.color.purple));

		Bundle extras = getIntent().getExtras();
		weight = extras.getDouble("weight");
		height = extras.getDouble("height");
		day = extras.getInt("day");
		month = extras.getInt("month");
		year = extras.getInt("year");
		sex = extras.getBoolean("ifBoy");

		LocalDate birthdate = new LocalDate (year, month, day);
		LocalDate now = new LocalDate();
		Years years = Years.yearsBetween(birthdate, now);
		Months months = Months.monthsBetween(birthdate, now);
		monthSmall = months.getMonths()-(years.getYears()*12);
		text = year + "-" + month + "-" + day + "\n" + weight + " kg, " + height + " cm";

		//TODO passing better age (with days - not only year + month)
		//String y = years.getYears()
		double age =  (double)monthSmall/12 + (double)years.getYears();
		childSumInfo();
		calculatingOutput(age);
	}


	public void childSumInfo (){

        //Joda time library
        LocalDate birthdate = new LocalDate (year, month, day);
        LocalDate now = new LocalDate();
        Years years = Years.yearsBetween(birthdate, now);
        Months months = Months.monthsBetween(birthdate, now);
        //Days days = Days.daysBetween(birthdate, now);

        Log.wtf("moje info ", birthdate.toString() + " i drugie " + now.toString() );
        if(sex)
            sex_text="męska";
        else
            sex_text="żeńska";

		Calc c = new Calc();

		double [] tablica = c.Calculate(6.02, true, "Height");


        TextView childData = (TextView)findViewById(R.id.child_data);
        childData.setText( "Waga: " + weight + "\n" + "Wzrost: " + height + "\n" + "Płeć: "
				+ sex_text + " \nWiek lata: "+ years.getYears() + " miesiące: " +
				(months.getMonths()-(years.getYears()*12)));// +"\nINFO: 3 centyl" + tablica[0] +
			//	" 10: " + tablica[1]+ " 25: " + tablica[2]+ " 50: " + tablica[3]+ " 75: " + tablica[4]+ " 90: " + tablica[5]+ " 97: " + tablica[6] );

	}

	public void calculatingOutput (double age) {

		//TODO adding somehow Pressure

		// TODO adding more exact values - maybe not only centyls

		//TODO adding head (Extras)
		Calc calc = new Calc();
		double BMI = weight/(height/100*height/100);

		TextView Height = (TextView)findViewById(R.id.centHeight);
		Height.setText(calc.Centyl(calc.Calculate(age, sex, "Height"), height) + "");

		TextView Weight = (TextView)findViewById(R.id.centWeight);
		Weight.setText(calc.Centyl(calc.Calculate(age, sex, "Weight"), weight)+ "");

		TextView Bmi = (TextView)findViewById(R.id.centBMI);
		TextView BmiValue = (TextView)findViewById(R.id.textView16);
		if(calc.Calculate(age, sex, "Bmi")[0] != 0) {
			Bmi.setText(calc.Centyl(calc.Calculate(age, sex, "Bmi"), BMI) + "");
			BmiValue.setText(String.format("%.2f", BMI));
		}
		else {
			Bmi.setText("Brak danych poniżej 4 r. ż.");
			BmiValue.setText("Brak danych poniżej 4 r. ż.");
		}

		//TextView Head = (TextView)findViewById(R.id.centHead);
		//Head.setText(calc.Centyl(calc.Calculate(age, sex, "Head"), weight)+ "");

	}
}







