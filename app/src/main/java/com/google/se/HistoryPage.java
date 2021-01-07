package com.google.se;

import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class HistoryPage extends AppCompatActivity {
    TextView water,sleep,bcolori,colori,water1,sleep1,bcolori1,colori1,hdate,hdate1,water2,sleep2,bcolori2,colori2,hdate2;
    DailyIDataDBHelper dailyIDataDBHelper;
    StepsDBHelper stepsDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_layout);
        water=findViewById(R.id.hwater);
        water1=findViewById(R.id.hwater1);
        water2=findViewById(R.id.hwater2);
        dailyIDataDBHelper=new DailyIDataDBHelper(this);
        stepsDBHelper=new StepsDBHelper(this);
        sleep=findViewById(R.id.hsleep);
        sleep1=findViewById(R.id.hsleep1);
        sleep2=findViewById(R.id.hsleep2);
        colori=findViewById(R.id.hcolori);
        colori1=findViewById(R.id.hcolori1);
        colori2=findViewById(R.id.hcolori2);
        bcolori=findViewById(R.id.hbcolori);
        hdate=findViewById(R.id.hdate);
        hdate1=findViewById(R.id.hdate1);
        hdate2=findViewById(R.id.hdate1);
        bcolori1=findViewById(R.id.hbcolori1);
        bcolori2=findViewById(R.id.hbcolori2);
        hdate.setText(getYesterdayDate(1));
        hdate1.setText(getYesterdayDate(2));
        hdate2.setText(getYesterdayDate(3));
        dailyIDataDBHelper.GetPastFood(-1,this);
        water.setText(dailyIDataDBHelper.GetPastWater(-1));
        water1.setText(dailyIDataDBHelper.GetPastWater(-2));
        water2.setText(dailyIDataDBHelper.GetPastWater(-3));
        colori.setText(dailyIDataDBHelper.GetPastFood(-1,this));
        colori1.setText(dailyIDataDBHelper.GetPastFood(-2,this));
        colori2.setText(dailyIDataDBHelper.GetPastFood(-3,this));
        sleep.setText(dailyIDataDBHelper.GetPastSleep(-1));
        sleep1.setText(dailyIDataDBHelper.GetPastSleep(-2));
        sleep1.setText(dailyIDataDBHelper.GetPastSleep(-3));
        bcolori.setText(stepsDBHelper.readStepsPast(this,-1));
        bcolori1.setText(stepsDBHelper.readStepsPast(this,-2));
        bcolori1.setText(stepsDBHelper.readStepsPast(this,-3));

    }
    
    public String getYesterdayDate(int amunt){
        String date = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, amunt);
        PersianCalendar persianCalendar=new PersianCalendar();
        persianCalendar.addPersianDate(PersianCalendar.DATE,-amunt);
        date=persianCalendar.getPersianLongDate();
        return date;
    }


    double usedColori(){
        double colori = 0;
        List<FoodModel> list;
        list=dailyIDataDBHelper.GetTodayFood("");
        for (int i=0;i<list.size();i++){
            colori=colori+list.get(i).getColorie();
        }
        return colori;
    }
}
