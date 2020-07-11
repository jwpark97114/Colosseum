package kr.co.namu.colosseum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kr.co.namu.colosseum.utils.ContextUtil

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        Handler().postDelayed({
            if(ContextUtil.getUserToken(mContext)==""){
//                login 안한 상태 => 로그인 화면 이동
                val myIntent = Intent(mContext, LoginActivity::class.java)
                startActivity(myIntent)
            }
            else{
//                토큰이 저장되어 있어서 로그인이 성공한 상태 => 메인으로 이동
                val myIntent = Intent(mContext, MainActivity::class.java)
                startActivity(myIntent)
            }

            finish()
        }, 2500)
    }


}