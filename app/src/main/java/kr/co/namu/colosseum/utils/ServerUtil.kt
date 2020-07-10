package kr.co.namu.colosseum.utils

import android.content.Context
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

//    액티비티에서 서버의 응답을 받았을 때 처리할 코드를 담아두는 도구

    interface JsonResponseHandler{
        fun onResponse(json : JSONObject)

    }
    companion object{
//        서버 접근 주소 담는 변수
        private val BASE_URL = "http://15.165.177.142"

        //            로그인 요청을 해주는 함수 => 화면에서 입력한 아이디 비번 받아야함

        fun postRequestLogin(context: Context,id:String,pw:String, handler:JsonResponseHandler?){

//            서버 통신 담당 변수
            val client = OkHttpClient()

//            어느 주소로 통신할지
            val urlStr = "${BASE_URL}/user"

//            서버에 들고갈 짐을 FormData 에 담자 (POST / PUT / PATCH에서 이 방식)

            val formData = FormBody.Builder()
                .add("email", id)
                .add("password",pw)
                .build()

//            요청 정보를 종합하는 변수
            val request = Request.Builder()
                .url(urlStr)
                .post(formData)
                .build()

//            실제로 api 호출
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {

//                    서버 응답이 돌아왔을때 실행되는 영역
//                    응답 내용을 저장
                    val bodyStr =response.body?.string()

//                    이 내용을 기반으로 Json 객체 생성
                    val json = JSONObject(bodyStr)

//                    handler 변수에 응답처리 코드가 들어있다면 실행시킬 것
                    handler?.onResponse(json)
                }
            })


        }
    }
}