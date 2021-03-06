package kr.co.namu.colosseum

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.namu.colosseum.adapters.TopicAdapter
import kr.co.namu.colosseum.datas.Topic
import kr.co.namu.colosseum.datas.User
import kr.co.namu.colosseum.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    val mTopicList = ArrayList<Topic>()

    lateinit var  mTopicAdapter: TopicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        topicListView.setOnItemClickListener { adapterView, view, position, l ->

            val myIntent = Intent(mContext, ViewTopicDetailActivity::class.java)

//            어떤 주제를 눌렀는지 전달

            val clickedTopic = mTopicList[position]

            myIntent.putExtra("topicId", clickedTopic.id)

            startActivity(myIntent)

        }

    }

    override fun setValues() {

        mTopicAdapter = TopicAdapter(mContext,R.layout.reply_list_item,mTopicList)
        topicListView.adapter = mTopicAdapter


//        getUserInfoFromServer()
        getTopicListFromServer()
    }

//    서버에서 토론 목록 받아오기

    fun getTopicListFromServer() {

        ServerUtil.getRequestMainInfo(mContext, object  : ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")
                val topics = data.getJSONArray("topics")

                for(i in 0..topics.length()-1){
                    val topicJson = topics.getJSONObject(i)

//                    JSONArray 내부 {}중괄호로 묶인 JSONObject들을 가지고 실제 Topic 객체로 전환
                    val topic = Topic.getTopicFromJson(topicJson)

//                    바꿔준 topic을 목록에 추가
                    mTopicList.add(topic)
                }

                runOnUiThread {

//                목록을 모두 추가한 경우 리스트뷰 새로고침
                    mTopicAdapter.notifyDataSetChanged()
                }


            }

        })
    }

//    임시로 하는 작업 => 서버에서 내 정보를 받아와서 닉네임 뿌려주기

    fun getUserInfoFromServer(){
        ServerUtil.getRequestMyInfo(mContext, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {

                runOnUiThread {
//                    사용자의 닉네임을 받아서 textview에 반영할 계획
                    val data = json.getJSONObject("data")
                    val user = data.getJSONObject("user")
                    val nickName = user.getString("nick_name")

                    val loginUser = User.getUserFromJson(user)

//                    userNickTxt.text = loginUser.nickName
//                    userEmailTxt.text = loginUser.email
                }

            }

        })
    }


}