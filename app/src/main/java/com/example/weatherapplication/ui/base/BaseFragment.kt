package com.example.weatherapplication.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.example.weatherapplication.App
import com.example.weatherapplication.R
import com.example.weatherapplication.di.AppComponent
import com.example.weatherapplication.ui.activity.MainActivity

/**
 * @author Kalmykova Natalia
 */
open class BaseFragment : Fragment() {

    val appComponent: AppComponent
        get() = (requireActivity().application as App).appComponent

    // Надо ли скрывать нижнее меню
    open val isActionBarVisible: Boolean = true

    /*
    * Надо ли отображать стрелку "назад" в тулбаре
    */
    open val isNavigateBackVisible: Boolean = true

    /*
    * Заголовок тулбара
    */
    open val titleRes: Int = R.string.app_name

    /*
    * Заголовок тулбара строка
    */
    open val titleCharSequence: CharSequence by lazy { requireContext().getString(titleRes) }

    /*
    * Меню тулбара
    */
    open val menuRes: Int = R.menu.menu_default

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(isOptionsMenuDefined)
        mainActivity.supportActionBar?.apply {
            title = titleCharSequence
            setDisplayHomeAsUpEnabled(isNavigateBackVisible)
            setShowHideAnimationEnabled(false)
            if (isActionBarVisible) {
                show()
            } else {
                hide()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(menuRes, menu)
    }

    private val isOptionsMenuDefined: Boolean
        get() = menuRes != R.menu.menu_default

    private val mainActivity: MainActivity
        get() = requireActivity() as MainActivity
}