package com.andresuryana.budgettrack.ui.component.dialog.bottomsheet

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andresuryana.budgettrack.databinding.FragmentDatePickerBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class DatePickerBottomSheetDialog(private val initialDate: Calendar) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDatePickerBottomSheetBinding
    private var onDateChangedListener: ((year: Int, month: Int, day: Int) -> Unit)? = null

    var hideDayPicker: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDatePickerBottomSheetBinding.inflate(inflater, container, false)
        setupResetButton()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hide the day picker if needed
        if (hideDayPicker) {
            @SuppressLint("DiscouragedApi")
            val daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android")
            if (daySpinnerId != 0) {
                val daySpinner = binding.datePicker.findViewById<View>(daySpinnerId)
                daySpinner?.let { it.visibility = View.GONE }
            }
        }

        // Init the date picker with the initial date
        binding.datePicker.updateDate(
            initialDate.get(Calendar.YEAR),
            initialDate.get(Calendar.MONTH),
            initialDate.get(Calendar.DAY_OF_MONTH)
        )
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        // Get the selected date from the date picker
        val year = binding.datePicker.year
        val month = binding.datePicker.month
        val dayOfMonth = binding.datePicker.dayOfMonth
        val selectedDate = Calendar.getInstance()
        selectedDate.set(year, month, dayOfMonth)

        // Check if the selected date is different from the initial date
        if (!isDatesSameIgnoreTime(initialDate, selectedDate)) {
            onDateChangedListener?.invoke(year, month, dayOfMonth)
        }
    }

    fun setOnDateChangedListener(listener: (year: Int, month: Int, day: Int) -> Unit) {
        this.onDateChangedListener = listener
    }

    private fun setupResetButton() {
        binding.btnReset.setOnClickListener {
            // Reset date picker into current month and year
            val today = Calendar.getInstance()
            binding.datePicker.updateDate(
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)
            )
        }
    }

    private fun isDatesSameIgnoreTime(date1: Calendar, date2: Calendar): Boolean {
        val d1 = Calendar.getInstance().apply {
            set(
                date1.get(Calendar.YEAR),
                date1.get(Calendar.MONTH),
                date1.get(Calendar.DAY_OF_MONTH)
            )
        }
        val d2 = Calendar.getInstance().apply {
            set(
                date2.get(Calendar.YEAR),
                date2.get(Calendar.MONTH),
                date2.get(Calendar.DAY_OF_MONTH)
            )
        }
        return d1.compareTo(d2) == 0
    }
}