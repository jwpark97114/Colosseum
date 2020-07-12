package kr.co.namu.colosseum.datas

import org.json.JSONObject

class Reply {

    var id = 0
    var content =""
    lateinit var user : User
    lateinit var side : TopicSide

    companion object {

//        JSONObject => reply 로 변환

        fun getReplyFromJson(json:JSONObject) : Reply{

            val r = Reply()

            r.id = json.getInt("id")
            r.content = json.getString("content")

//            작성자 정보 추출
            val userJson = json.getJSONObject("user")

//            의견 작성자 정보 반영

            r.user = User.getUserFromJson(userJson)

//            작성자가 선택한 진영 정보 추출

            val sideJson = json.getJSONObject("selected_side")

            r.side = TopicSide.getTopicSideFromJson(sideJson)



            return r

        }
    }

}