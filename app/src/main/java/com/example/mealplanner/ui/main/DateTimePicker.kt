package com.example.mealplanner.ui.main

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.text.format.DateFormat
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import com.example.mealplanner.data.state.DateFilterState
import java.util.*


class DateTimePicker(
    private val context: Context,
    private val field: EditText,
    private val type: String
): DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener {
    private val dateFilter = DateFilterState
    private var year:Int=0
    private var month:Int=0
    private var day:Int=0
    private var hour:Int=0
    private var minute:Int=0

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        this.year = year
        this.month = month + 1
        this.day = dayOfMonth

        val c = Calendar.getInstance()
        hour = c[Calendar.HOUR]
        minute = c[Calendar.MINUTE]
        val timePickerDialog = TimePickerDialog(
            context,
            this,
            hour,
            minute,
            DateFormat.is24HourFormat(context)
        )
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        this.hour = hourOfDay
        this.minute = minute
        val date = String.format("%d-%02d-%02d %02d:%02d", year, month, day, hour, minute)
        field.setText(date)
        val dateParser= SimpleDateFormat("yyyy-MM-dd hh:mm")
        when(type){
            "START" -> dateFilter.startDateTime = dateParser.parse(date)
            "END" -> dateFilter.endDateTime = dateParser.parse(date)
        }
    }

    override fun onClick(v: View?) {
        val calendar = Calendar.getInstance()
        year = calendar[Calendar.YEAR]
        month = calendar[Calendar.MONTH]
        day = calendar[Calendar.DAY_OF_MONTH]
        val datePickerDialog =
            DatePickerDialog(context, this, year, month, day)
        datePickerDialog.show()
    }

}