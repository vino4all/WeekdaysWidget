package com.stackandroid.weekdayswidget.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.amulyakhare.textdrawable.TextDrawable;
import com.stackandroid.weekdayswidget.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Calendar.FRIDAY;
import static java.util.Calendar.MONDAY;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SUNDAY;
import static java.util.Calendar.THURSDAY;
import static java.util.Calendar.TUESDAY;
import static java.util.Calendar.WEDNESDAY;

/**
 * Created by Vinothkumar on 4/28/2016.
 */
public class WeekdaysWidget extends LinearLayout {

    private int selectedDayBackgroundColor;
    private int unSelectedDayBackgroundColor;
    private int selectedTextColor;
    private int unSelectedTextColor;


    private Context mContext;
    private String HIGHLIGHT_COLOR = "#303F9F";
    private int mHighlightColor = 0;
    private boolean mEditable = false;

    private ImageView sunday, monday, tuesday, wednesday, thursday, friday, saturday;
    private float dayViewPadding = 5;
    private float layoutWeight = 1.0f;

    private int height = 30;
    private int width = 30;

    TextDrawable.IBuilder selectedBuilder;
    TextDrawable.IBuilder unSelectedBuilder;
    private Set<Integer> selectedDays;
    private float fontSize = 14f;

    public WeekdaysWidget(Context context) {
        super(context);
        mContext = context;
        mHighlightColor = Color.parseColor(HIGHLIGHT_COLOR);
        initView();
    }

    public WeekdaysWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(attrs);
    }

    public WeekdaysWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WeekdaysWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initView(attrs);
    }

    public boolean isInEditMode()
    {
        return true;
    }

    private void initView(AttributeSet attrs)
    {
        TypedArray a = mContext.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.WeekdaysWidget,
                0, 0);

        try {
            mEditable = a.getBoolean(R.styleable.WeekdaysWidget_editable, false);
            mHighlightColor = a.getColor(R.styleable.WeekdaysWidget_highlight_color, Color.parseColor(HIGHLIGHT_COLOR));
        } finally {
            a.recycle();
        }
        initView();
    }

    private void initView() {
        selectedDays = new HashSet<>();

        setOrientation(LinearLayout.HORIZONTAL);
        selectedDayBackgroundColor = mHighlightColor;
        unSelectedDayBackgroundColor = Color.LTGRAY;
        selectedTextColor = Color.WHITE;
        unSelectedTextColor = mHighlightColor;

        sunday = createDayView(SUNDAY);
        monday = createDayView(MONDAY);
        tuesday = createDayView(TUESDAY);
        wednesday = createDayView(WEDNESDAY);
        thursday = createDayView(THURSDAY);
        friday = createDayView(FRIDAY);
        saturday = createDayView(SATURDAY);

        // declare the builder object once.
        selectedBuilder = TextDrawable.builder(mContext)
                .beginConfig()
                .textColor(selectedTextColor)
                .fontSize(getDpFromPx(fontSize))
                .bold()
                .width(getDpFromPx(width))
                .height(getDpFromPx(height))
                .endConfig()
                .round();

        unSelectedBuilder = TextDrawable.builder(mContext)
                .beginConfig()
                .textColor(unSelectedTextColor)
                .fontSize(getDpFromPx(fontSize))
                .bold()
                .width(getDpFromPx(width))
                .height(getDpFromPx(height))
                .endConfig()
                .round();


        sunday.setImageDrawable(unSelectedBuilder.build("S", unSelectedDayBackgroundColor));
        sunday.setSelected(false);
        selectedDays.remove(new Integer(SUNDAY));
        monday.setImageDrawable(selectedBuilder.build("M", selectedDayBackgroundColor));
        tuesday.setImageDrawable(selectedBuilder.build("T", selectedDayBackgroundColor));
        wednesday.setImageDrawable(selectedBuilder.build("W", selectedDayBackgroundColor));
        thursday.setImageDrawable(selectedBuilder.build("T", selectedDayBackgroundColor));
        friday.setImageDrawable(selectedBuilder.build("F", selectedDayBackgroundColor));
        saturday.setImageDrawable(unSelectedBuilder.build("S", unSelectedDayBackgroundColor));
        saturday.setSelected(false);
        selectedDays.remove(new Integer(SATURDAY));
    }

    private ImageView createDayView(int tag)
    {
        ImageView dayView = new ImageView(mContext);
        dayView.setTag(tag);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT, layoutWeight);
        dayView.setLayoutParams(layoutParams);

        int padding = getDpFromPx(dayViewPadding);

        dayView.setPadding(padding, padding, padding, padding);
        setDaySelected(dayView, true);
        dayView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEditable) {
                    toggleSelection(v);
                }
            }
        });
        addView(dayView);
        selectedDays.add(tag);
        return dayView;
    }

    private int getDpFromPx(float value) {

        float scale = getResources().getDisplayMetrics().density;
        return (int) (value *scale + 0.5f);
    }

    private void setDaySelected(ImageView dayView, boolean b) {
        dayView.setSelected(b);
        if(b) {
            selectedDays.add((Integer) dayView.getTag());
        }
        else
        {
            selectedDays.remove(dayView.getTag());
        }
    }

    private void toggleSelection(View v) {
        ImageView day = (ImageView) v;
        String dayText = getDayText(v);
        if(day.isSelected())
        {
            day.setImageDrawable(unSelectedBuilder.build(getDayText(day), unSelectedDayBackgroundColor));
            setDaySelected(day, false);
            System.out.println("Changing to unselected - "+day.getTag());
        }
        else
        {
            day.setImageDrawable(selectedBuilder.build(getDayText(day), selectedDayBackgroundColor));
            setDaySelected(day, true);
            System.out.println("day selected - "+day.getTag());
        }

        System.out.println("Selected Days - "+ getSelectedDays());
    }

    private String getDayText(View v) {
        int tag = (int) v.getTag();
        return getDayText(tag);
    }

    private String getDayText(int dayIndex)
    {
        switch (dayIndex)
        {
            case SUNDAY:
                return "S";
            case MONDAY:
                return "M";
            case TUESDAY:
                return "T";
            case WEDNESDAY:
                return "W";
            case THURSDAY:
                return "T";
            case FRIDAY:
                return "F";
            case SATURDAY:
                return "S";
        }

        return "";
    }

    public List<Integer> getSelectedDays()
    {
        List list = new ArrayList(selectedDays);
        Collections.sort(list);
        return list;
    }

    public List<String> getSelectedDaysText()
    {
        List dayTextList = new ArrayList();
        for(int dayIndex : getSelectedDays())
        {
            dayTextList.add(getDayText(dayIndex));
        }
        return dayTextList;
    }

    public boolean isAllDaysSelected()
    {
        return selectedDays.size() == 7;
    }

    public boolean isNoneSelected()
    {
        return selectedDays.size() == 0;
    }
}
