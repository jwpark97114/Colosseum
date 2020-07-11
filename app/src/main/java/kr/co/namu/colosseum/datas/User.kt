package kr.co.namu.colosseum.datas

import org.json.JSONObject

class User {

//    사용자(user가 가지는) 하위정보 3가지

    var id = 0
    var email = ""
    var nickName = ""

    companion object{

//        JSONObject를 적당한걸 넣었을 경우 User 객체로 바꿔주는 기능

        fun getUserFromJson(json:JSONObject) : User {

            val user = User()

//            JSON의 내용을 분석해 user의 데이터에 대입

            user.id = json.getInt("id")
            user.email = json.getString("email")
            user.nickName = json.getString("nick_name")

            return user
        }

    }
}