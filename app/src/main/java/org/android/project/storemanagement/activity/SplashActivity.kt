package org.android.project.storemanagement.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import org.android.project.storemanagement.R
import org.android.project.storemanagement.util.Constants

class SplashActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val pref = getSharedPreferences(Constants.SHARE_PREF_NAME, MODE_PRIVATE)
        val userId = pref.getInt(Constants.KEY_USER_ID, -1)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = if(userId > 0){
                MainActivity.newIntent(this)
            }else{
                LoginActivity.newIntent(this)
            }
            startActivity(intent)
            finish()
        }, 1000)
    }
}