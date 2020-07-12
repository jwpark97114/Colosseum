package kr.co.namu.colosseum.datas

import org.json.JSONObject

class Reply {

    var id = 0
    var title = ""
    var imageUrl = ""

    val sideList = ArrayList<TopicSide>()

    companion object {

        fun getTopicFromJson(json:JSONObject) : Reply {
            val topic = Reply()

            topic.id = json.getInt("id")
            topic.title = json.getString("title")
            topic.imageUrl = json.getString("img_url")

//            선택 진영 목록을 parsing 해 sideList에 담는 기능

            val sides = json.getJSONArray("sides")

            for(i in 0..sides.length()-1){

                val sideJson = sides.getJSONObject(i)

                val topicSide = TopicSide.getTopicSideFromJson(sideJson)

                topic.sideList.add(topicSide)

            }

            return topic
        }
    }

}