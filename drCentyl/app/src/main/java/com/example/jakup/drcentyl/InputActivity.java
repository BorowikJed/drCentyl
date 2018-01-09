package com.example.jakup.drcentyl;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.DatePicker.OnDateChangedListener;

import com.example.jakup.drcentyl.lib.Calc;

import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.Years;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
//TODO better layout of whole application - datespinner musi zostać, żeby można było zrealizować centylowanie bez wysuwania klawiatury!
//Także layout nie może zakłócić tego naszego głównego ficzera z niewysuwaniem klawiaturki
public class InputActivity extends AppCompatActivity{

	int year;
	int month;
	int day;
	Boolean moreData = false;	//czy uzytkownik rozwinal opcje cisnienia i obwodu glowy
	Toast toast;
	Spannable content;
	CharSequence text;
	Context context;
	int counter = 0;
	Calendar calendar = Calendar.getInstance();
	DatePicker DP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		DP = (DatePicker) findViewById(R.id.datePicker2);
		MyOnDateChangeListener dateChanged = new MyOnDateChangeListener();
        DP.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), dateChanged);

		long tempInt = 262974383;
		tempInt *= 10; //od około miesięcznych dzieci
		DP.setMaxDate(new Date().getTime()-(tempInt));

		tempInt = 568024668;
		tempInt=tempInt*1000;
		DP.setMinDate(new Date().getTime()-tempInt); // minus 18 years

		Button passData = (Button)findViewById(R.id.button);
		Button weightplus = (Button)findViewById(R.id.weightplus);
		Button weightminus = (Button)findViewById(R.id.weightminus);
		Button heightplus = (Button)findViewById(R.id.heightplus);
		Button heightminus = (Button)findViewById(R.id.heightminus);
		Button pressureplus = (Button)findViewById(R.id.pressureplus);
		Button pressureminus = (Button)findViewById(R.id.pressureminus);
		Button circumplus = (Button)findViewById(R.id.circumplus);
		Button circumminus = (Button)findViewById(R.id.circumminus);
		TextView patientData = (TextView)findViewById(R.id.textView3);
		TextView circumText = (TextView)findViewById(R.id.textView7);
		TextView heightText = (TextView)findViewById(R.id.textView5);
		TextView weightText = (TextView)findViewById(R.id.textView4);
		TextView pressureText = (TextView)findViewById(R.id.textView6);
		ToggleButton isBoy = (ToggleButton) findViewById(R.id.toggleButton);

		passData.setTextColor(getResources().getColor(R.color.white));
		weightplus.setTextColor(getResources().getColor(R.color.purple));
		weightminus.setTextColor(getResources().getColor(R.color.purple));
		heightplus.setTextColor(getResources().getColor(R.color.purple));
		heightminus.setTextColor(getResources().getColor(R.color.purple));
		pressureplus.setTextColor(getResources().getColor(R.color.purple));
		pressureminus.setTextColor(getResources().getColor(R.color.purple));
		circumplus.setTextColor(getResources().getColor(R.color.purple));
		circumminus.setTextColor(getResources().getColor(R.color.purple));
		passData.setBackgroundColor(getResources().getColor(R.color.purple));

		patientData.setTextColor(getResources().getColor(R.color.purple));
		circumText.setTextColor(getResources().getColor(R.color.purple));
		heightText.setTextColor(getResources().getColor(R.color.purple));
		weightText.setTextColor(getResources().getColor(R.color.purple));
		pressureText.setTextColor(getResources().getColor(R.color.purple));

		isBoy.setBackgroundColor(getResources().getColor(R.color.blue));
		isBoy.setTextColor(getResources().getColor(R.color.white));
    }

	public class MyOnDateChangeListener implements OnDateChangedListener {
		@Override
		public void onDateChanged(DatePicker view, int newYear, int newMonth, int newDay) {

			year = newYear;
			month = newMonth + 1;
			day = newDay;

			Log.w("Wywali sie na", day + " " + month + " " + year);

			if(day == 6 && month == 10 && year == 2016)
			{
				System.out.println("caught");
			}

			EditText weightField = (EditText) findViewById(R.id.weight);
			EditText heightField = (EditText) findViewById(R.id.height);
			EditText pressureField = (EditText) findViewById(R.id.pressure);
			EditText CircumferenceField = (EditText) findViewById(R.id.head);

			int[] defaults = calcDefaultValues();

			weightField.setText(Integer.toString(defaults[0]));
			heightField.setText(Integer.toString(defaults[1]));
			pressureField.setText(Integer.toString(defaults[2]));
			CircumferenceField.setText(Integer.toString(defaults[3]));

		}
	}
	public void onGenderChange(View v) {
		ToggleButton isBoy = (ToggleButton) findViewById(R.id.toggleButton);
		Boolean ifBoy = isBoy.isChecked();

		if(ifBoy) {
			isBoy.setBackgroundColor(getResources().getColor(R.color.blue));
		} else {
			isBoy.setBackgroundColor(getResources().getColor(R.color.pink));
		}

		EditText weightField = (EditText) findViewById(R.id.weight);
		EditText heightField = (EditText) findViewById(R.id.height);
		EditText pressureField = (EditText) findViewById(R.id.pressure);
		EditText CircumferenceField = (EditText) findViewById(R.id.head);

		int[] defaults = calcDefaultValues();

		weightField.setText(Integer.toString(defaults[0]));
		heightField.setText(Integer.toString(defaults[1]));
		pressureField.setText(Integer.toString(defaults[2]));
		CircumferenceField.setText(Integer.toString(defaults[3]));
	}

	private int[] calcDefaultValues()
	{
		int[] localDefault = {0,0,0,0};
		ToggleButton isBoy = (ToggleButton) findViewById(R.id.toggleButton);
		Boolean ifBoy = isBoy.isChecked();

		LocalDate birthdate = new LocalDate (year, month, day);
		LocalDate now = new LocalDate();
		Years years = Years.yearsBetween(birthdate, now);
		Months months = Months.monthsBetween(birthdate, now);
		int monthSmall = months.getMonths()-(years.getYears()*12);
		double age = (double)monthSmall/12 + (double)years.getYears();

		localDefault[0] = (int)Calc.Calculate(age, ifBoy, "Weight")[3];
		localDefault[1] = (int)Calc.Calculate(age, ifBoy, "Height")[3];
		//localDefault[2] = (int) Calc.Calculate(age, ifBoy, "Pressure")[3]; TODO
		localDefault[3] = (int)Calc.Calculate(age, ifBoy, "Head")[3];


		if(counter == 11)
		{
			for (double huj : localDefault ) {
				System.out.println(huj);
			}

		}

		//liczenie wagi:
		//localDefault[0]

		//liczenie wzrostu:
		//localDefault[1]

		//liczenie cisnienia:
		//localDefault[2]

		//liczenie obwodu
		//localDefault[3]

		return localDefault;
	}

	/*
		Podaje sczytane dane do klasy liczącej centyle
 	*/
    public void passData(View v)
    {
        EditText getWeight = (EditText) findViewById(R.id.weight);
        EditText getHeight = (EditText) findViewById(R.id.height);
		ToggleButton isBoy = (ToggleButton) findViewById(R.id.toggleButton);

        if(getWeight.getText().length() == 0 || getHeight.getText().length() == 0){

			String ddd = getWeight.getText().toString();
			Log.d("weight", ddd);
			CharSequence dddd = getHeight.getText().toString();
			Log.d("height", (String) dddd);
            text = "Nie wypelniono wszystkich pol";
			context = getApplicationContext();
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
			toast.show();

        }else{

			DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker2);
      		day = datePicker.getDayOfMonth();
      		month = datePicker.getMonth() + 1;
      		year = datePicker.getYear();

            double weight = Double.parseDouble(getWeight.getText().toString());
            double height = Double.parseDouble(getHeight.getText().toString());
			Boolean ifBoy = isBoy.isChecked();

			Intent newIntent = new Intent(this, DisplayChartsActivity.class);
			Bundle extras = new Bundle();
			extras.putDouble("weight",weight);
			extras.putDouble("height",height);
			extras.putInt("year",year);
			extras.putInt("day",day);
			extras.putInt("month",month);
			extras.putBoolean("ifBoy",ifBoy);
			extras.putBoolean("ifMoreData", moreData);
			newIntent.putExtras(extras);
			startActivity(newIntent);

			text = year + "-" + month + "-" + day + "\n" + weight + " kg, " + height + " cm";
			context = getApplicationContext();
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
			toast.show();
        }
    }

    public void more(View v) {
        EditText head = (EditText) findViewById(R.id.head);
        EditText pressure = (EditText) findViewById(R.id.pressure);
		Button options = (Button) findViewById(R.id.moreOptions);
		Button premin = (Button) findViewById(R.id.pressureminus);
		Button preplus = (Button) findViewById(R.id.pressureplus);
		Button cirmin = (Button) findViewById(R.id.circumminus);
		Button cirplus = (Button) findViewById(R.id.circumplus);

        if (head.getVisibility() == View.GONE) {
            head.setVisibility(View.VISIBLE);
            pressure.setVisibility(View.VISIBLE);
			premin.setVisibility(View.VISIBLE);
			preplus.setVisibility(View.VISIBLE);
			cirmin.setVisibility(View.VISIBLE);
			cirplus.setVisibility(View.VISIBLE);
			moreData = true;
            content = new SpannableString(getString(R.string.zwin));
        } else {
            head.setVisibility(View.GONE);
            pressure.setVisibility(View.GONE);
			premin.setVisibility(View.GONE);
			preplus.setVisibility(View.GONE);
			cirmin.setVisibility(View.GONE);
			cirplus.setVisibility(View.GONE);
			moreData = false;
            content = new SpannableString(getString(R.string.rozwin));
        }

        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        options.setText(content);
    }

	private int newValue(int oldValue){
		if(oldValue <= 0)
			return 0;
		else
			return oldValue-1;
	}

	private double newValueDouble(double oldValue, double age){
		if(oldValue <= 0)
			return 0;
		else
			if(age < 1) {
				return oldValue-0.1;
			} else {
				return oldValue-1;
			}

	}

	public void valueChange(View v){
		int currentValue;
		double currentValueDouble;
		EditText value;

		LocalDate birthdate = new LocalDate (year, month, day);
		LocalDate now = new LocalDate();
		Years years = Years.yearsBetween(birthdate, now);
		Months months = Months.monthsBetween(birthdate, now);
		int monthSmall = months.getMonths()-(years.getYears()*12);
		double age = (double)monthSmall/12 + (double)years.getYears();

		switch(v.getId()){
			case R.id.weightminus:
				value = (EditText)findViewById(R.id.weight);
				currentValueDouble = Double.parseDouble((value).getText().toString());
				value.setText(""+ BigDecimal.valueOf(newValueDouble(currentValueDouble, age)).setScale(1, RoundingMode.HALF_UP).doubleValue());
				break;
			case R.id.heightminus:
				value = (EditText)findViewById(R.id.height);
				currentValue = Integer.parseInt((value).getText().toString());
				value.setText(""+newValue(currentValue));
				break;
			case R.id.pressureminus:
				value = (EditText)findViewById(R.id.pressure);
				currentValue = Integer.parseInt((value).getText().toString());
				value.setText(""+newValue(currentValue));
				break;
			case R.id.circumminus:
				value = (EditText)findViewById(R.id.head);
				currentValue = Integer.parseInt((value).getText().toString());
				value.setText(""+newValue(currentValue));
				break;
			case R.id.weightplus:
				value = (EditText)findViewById(R.id.weight);
				currentValueDouble = Double.parseDouble((value).getText().toString());
				if (age < 1) {
					value.setText(""+BigDecimal.valueOf(currentValueDouble+0.1).setScale(1, RoundingMode.HALF_UP).doubleValue());
				} else {
					value.setText(""+BigDecimal.valueOf(currentValueDouble+1).setScale(1, RoundingMode.HALF_UP).doubleValue());
				}

				break;
			case R.id.heightplus:
				value = (EditText)findViewById(R.id.height);
				currentValue = Integer.parseInt((value).getText().toString());
				value.setText(""+(currentValue+1));
				break;
			case R.id.pressureplus:
				value = (EditText)findViewById(R.id.pressure);
				currentValue = Integer.parseInt((value).getText().toString());
				value.setText(""+(currentValue+1));
				break;
			case R.id.circumplus:
				value = (EditText)findViewById(R.id.head);
				currentValue = Integer.parseInt((value).getText().toString());
				value.setText(""+(currentValue+1));
				break;
		}
	}
}

