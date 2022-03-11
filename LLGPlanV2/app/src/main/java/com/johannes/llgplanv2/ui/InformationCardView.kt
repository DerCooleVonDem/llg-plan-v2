package com.johannes.llgplanv2.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.marginTop
import com.johannes.llgplanv2.R

class InformationCardView : LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        orientation = VERTICAL

        for (type in Type.values()) {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.view_information_card_item,
                    this, false) as CardView

            (view.getChildAt(0) as TextView).text = resources.getString(
                when(type) {
                    Type.NO_CONNECTION -> R.string.network_no_connection
                    Type.SUBSTITUTION_OUTDATED -> R.string.outdated_substitution
                    Type.TIMETABLE_OUTDATED -> R.string.outdated_timetable
                    Type.EVENT_LIST_OUTDATED -> R.string.outdated_event_list
                    Type.BETA_WARNING -> R.string.beta_warning
                }
            )
            addView(view)
        }
    }

    fun setEnabled(type: Type, b: Boolean) {
        getChildAt(type.ordinal).visibility = if (b) View.VISIBLE else View.GONE
    }

    enum class Type {
        NO_CONNECTION,
        SUBSTITUTION_OUTDATED,
        TIMETABLE_OUTDATED,
        EVENT_LIST_OUTDATED,
        BETA_WARNING
    }
}