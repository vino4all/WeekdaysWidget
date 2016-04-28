package com.stackandroid.weekdayswidget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.stackandroid.weekdayswidget.customview.WeekdaysWidget;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void queryWidget1(View v)
    {
        WeekdaysWidget widget = (WeekdaysWidget) findViewById(R.id.widget1);
        Toast.makeText(this, "Widget 1\n"+widget.getSelectedDays()+"\n"+widget.getSelectedDaysText()+"\nAll days Selected ? - "+widget.isAllDaysSelected()+"\nNo day Selected ? - "+widget.isNoneSelected(), Toast.LENGTH_SHORT).show();
    }

    public void queryWidget2(View v)
    {
        WeekdaysWidget widget = (WeekdaysWidget) findViewById(R.id.widget2);
        Toast.makeText(this, "Widget 2\n"+widget.getSelectedDays()+"\n"+widget.getSelectedDaysText()+"\nAll days Selected ? - "+widget.isAllDaysSelected()+"\nNo day Selected ? - "+widget.isNoneSelected(), Toast.LENGTH_SHORT).show();
    }

    public void queryWidget3(View v)
    {
        WeekdaysWidget widget = (WeekdaysWidget) findViewById(R.id.widget3);
        Toast.makeText(this, "Widget 3\n"+widget.getSelectedDays()+"\n"+widget.getSelectedDaysText()+"\nAll days Selected ? - "+widget.isAllDaysSelected()+"\nNo day Selected ? - "+widget.isNoneSelected(), Toast.LENGTH_SHORT).show();
    }

    public void queryWidget4(View v)
    {
        WeekdaysWidget widget = (WeekdaysWidget) findViewById(R.id.widget4);
        Toast.makeText(this, "Widget 4\n"+widget.getSelectedDays()+"\n"+widget.getSelectedDaysText()+"\nAll days Selected ? - "+widget.isAllDaysSelected()+"\nNo day Selected ? - "+widget.isNoneSelected(), Toast.LENGTH_SHORT).show();
    }
}
