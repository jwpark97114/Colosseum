package kr.co.namu.colosseum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kr.co.namu.colosseum.utils.ContextUtil
import kr.co.namu.colosseum.utils.ServerUtil
import org.json.JSONObject

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupEvents()
        setValues()
    }
    override fun setupEvents() {

        signUpBtn.setOnClickListener {
            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)
        }

        loginBtn.setOnClickListener {
//            입력한 아이디 비번 받아오기
            val inputId = idEdt.text.toString()
            val inputPw = pwEdt.text.toString()

//            서버에 로그인 요청 시도

            ServerUtil.postRequestLogin(mContext, inputId, inputPw, object :ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

//                    json - 제일 큰 껍데기 {} 그 내부의 값을 분석 상황에 따른 처리
//                    code: 에 적힌 int 값 받아오기 Json 파싱
                    val code = json.getInt("code")

//                    화면에 띄우는 코드는 UIThread에서 작업해야한다.

                    runOnUiThread {
                        //                    로그인 성공
                        if(code == 200){
                            Toast.makeText(mContext,"로그인에 성공했습니다.",Toast.LENGTH_SHORT).show()

//                            서버에서 내가 누구인지 구별하는데 사용하는 토큰값을 받아서 저장
//                            (기기에 저장해서 모든 화면에서 꺼내 쓰도록 할 예정+앱을 끄거나 폰을 꺼도 유지되도록 저장)
//                            자동로그인 기능에 활용

//                            JSON에서 중간 괄호 data를 추출
                            val data = json.getJSONObject("data")

//                            data 내부의 토큰값 추출
                            val loginUserToken = data.getString("token")

//                            기기 내부에 저장
                            ContextUtil.setUserToken(mContext,loginUserToken)

//                            메인화면으로 이동 후 종료
                            val myIntent = Intent(mContext, MainActivity::class.java)
                            startActivity(myIntent)
                            finish()

                        }
                        //                    로그인 실패
                        else{
                            Toast.makeText(mContext,"로그인 실패.",Toast.LENGTH_SHORT).show()
                        }
                    }



                }
            })


        }


    }

    override fun setValues() {
    }


}