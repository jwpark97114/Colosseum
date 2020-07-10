package kr.co.namu.colosseum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.namu.colosseum.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }
    override fun setupEvents() {

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