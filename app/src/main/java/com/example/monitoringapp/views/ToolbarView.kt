package com.example.monitoringapp.views

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.monitoringapp.R
import com.example.monitoringapp.databinding.ToolbarBinding
import com.example.monitoringapp.util.hideOrShow

/**
 * Custom BZ toolbar used on activities and fragments on the app
 */
class ToolbarView : ConstraintLayout {

    private lateinit var binding: ToolbarBinding

    private var _toolbarTitleValue: String = resources.getString(R.string.text_default_empty)
    private var _hasHamburguerButton: Boolean = true

    /**
     * Sets the toolbar title.
     */
    var toolbarTitleValue: String
        get() = _toolbarTitleValue
        set(value) {
            _toolbarTitleValue = value
            updateToolbarTitle(value)
        }

    /**
     * Defines whether the toolbar should include Hamburguer button or not.
     * Default value is true.
     */
    var hasHamburguerButton: Boolean
        get() = _hasHamburguerButton
        set(value) {
            _hasHamburguerButton = value
            updateViewBackButton(value)
        }


    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    fun setHamburgerClickListener(listener: () -> Unit) {
        binding.imageHamburguer.setOnClickListener {
            listener()
        }
    }

    fun isTitleVisible(value: Boolean) {
        binding.toolbarTitle.hideOrShow(value)
    }


    private fun updateToolbarTitle(value: String) {
        binding.toolbarTitle.text = value
    }


    private fun updateViewBackButton(value: Boolean) {
        binding.imageHamburguer.hideOrShow(value)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        binding = ToolbarBinding.bind(inflate(context, R.layout.toolbar, this))

        val a = context.obtainStyledAttributes(
            attrs, R.styleable.ToolbarView, defStyle, 0
        )

        _toolbarTitleValue = a.getString(
            R.styleable.ToolbarView_toolbarTitleValue
        ) ?: toolbarTitleValue


        _hasHamburguerButton = a.getBoolean(
            R.styleable.ToolbarView_hasHamburguerButton,
            hasHamburguerButton
        )

        updateToolbarTitle(_toolbarTitleValue)
        updateViewBackButton(_hasHamburguerButton)
        a.recycle()
    }

}