package com.omniwyse.firdous.view.base

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.omniwyse.firdous.R
import com.omniwyse.firdous.util.Logger
import com.omniwyse.firdous.util.MessageType
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_toolbar.*
import javax.inject.Inject


abstract class BaseActivity : AppCompatActivity(),
    HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    fun initToolbar(toolbar: Toolbar?) {
        if (toolbar == null) {
            return
        }
        setSupportActionBar(toolbar)
        ivBack.setOnClickListener { finish()}
    }


    fun displaySnackbar(
        activity: Activity?,
        message: String,
        messageType: MessageType
    ) {

        if (activity != null) {

            var view: View? = activity.window.decorView.rootView ?: return

            try {
                view =
                    (activity.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(
                        0
                    )
            } catch (e: Exception) {
                Logger.error(e.toString())
            }

            val snackbar = Snackbar.make(view!!, message, Snackbar.LENGTH_SHORT)

            val tvSnackBar =
                snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)

            tvSnackBar.textAlignment = View.TEXT_ALIGNMENT_CENTER

            when (messageType) {

                MessageType.ERROR -> tvSnackBar.setBackgroundColor(
                    ContextCompat.getColor(activity, R.color.red_color)
                )
                MessageType.SUCCESS -> tvSnackBar.setBackgroundColor(
                    ContextCompat.getColor(activity, R.color.green_color)
                )
                else -> tvSnackBar.setBackgroundColor(
                    ContextCompat.getColor(activity, R.color.accent_color)
                )
            }

            snackbar.view.setPadding(0, 0, 0, 0)

            snackbar.show()

        }
    }


}