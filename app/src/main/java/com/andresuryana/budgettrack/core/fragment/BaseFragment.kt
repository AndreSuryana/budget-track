package com.andresuryana.budgettrack.core.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.andresuryana.budgettrack.R

open class BaseFragment : Fragment() {

    private var loadingDialog: AlertDialog? = null

    @SuppressLint("InflateParams")
    fun showLoadingOverlay(show: Boolean) {
        if (show) {
            if (loadingDialog == null) {
                val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.layout_loading_overlay, null)
                dialogView.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent))

                loadingDialog = AlertDialog.Builder(requireContext(), R.style.Theme_BudgetTrack_Dialog_LoadingDialog)
                    .setView(dialogView)
                    .setCancelable(false)
                    .create()
            }
            loadingDialog?.show()
        } else {
            loadingDialog?.dismiss()
        }
    }

    fun showToast(
        context: Context,
        @StringRes resId: Int,
        duration: Int = Toast.LENGTH_SHORT,
    ) {
        showToast(context, getString(resId), duration)
    }

    fun showToast(
        context: Context,
        message: String,
        duration: Int = Toast.LENGTH_SHORT,
    ) {
        val toast = Toast.makeText(context, message, duration)
        toast.show()
    }
}