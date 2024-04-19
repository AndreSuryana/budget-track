package com.andresuryana.budgettrack.domain.model.account

import android.graphics.Color
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.andresuryana.budgettrack.R

enum class AccountType(
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int,
    private val bgColor: String,
) {
    // Bank
    MANDIRI(R.string.acc_mandiri, R.drawable.acc_mandiri, "#003D79"),
    BRI(R.string.acc_bri, R.drawable.acc_bri, "#00529C"),
    BCA(R.string.acc_bca, R.drawable.acc_bca, "#0060AF"),
    BNI(R.string.acc_bni, R.drawable.acc_bni, "#FFFFFF"),
    BTN(R.string.acc_btn, R.drawable.acc_btn, "#FEF200"),
    BSI(R.string.acc_bsi, R.drawable.acc_bsi, "#FFFFFF"),
    CIMB(R.string.acc_cimb, R.drawable.acc_cimb, "#EE3124"),
    PERMATA(R.string.acc_permata, R.drawable.acc_permata, "#FFFFFF"),
    OCBC(R.string.acc_ocbc, R.drawable.acc_ocbc, "#EC1F30"),
    PANIN(R.string.acc_panin, R.drawable.acc_panin, "#FFFFFF"),

    // E-Wallet
    GOPAY(R.string.acc_gopay, R.drawable.acc_gopay, "#FFFFFF"),
    OVO(R.string.acc_ovo, R.drawable.acc_ovo, "#4C3494"),
    DANA(R.string.acc_dana, R.drawable.acc_dana, "#108EE9"),
    SHOPEEPAY(R.string.acc_shopeepay, R.drawable.acc_shopeepay, "#F0592C"),
    PAYPAL(R.string.acc_paypal, R.drawable.acc_paypal, "#FFFFFF"),

    // Cash
    CASH(R.string.acc_cash, R.drawable.acc_cash, "#FFFFFF");

    fun getBackgroundColor(): Int {
        return try {
            Color.parseColor(bgColor)
        } catch (e: Exception) {
            Color.WHITE
        }
    }
}
