package kr.co.namu.colosseum

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_view_topic_detail.*
import kr.co.namu.colosseum.adapters.ReplyAdapter
import kr.co.namu.colosseum.datas.Reply
import kr.co.namu.colosseum.datas.Topic
import kr.co.namu.colosseum.utils.ServerUtil
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {

//    메인화면에서 전달해준 클릭된 토픽의 id값
    var mTopicId = 0

//    서버에서 받아온 토론 topic 상세 정보를 담을 변수
    lateinit var mTopicData : Topic

    lateinit var mReplyAdapter : ReplyAdapter

    val mReplyList = ArrayList<Reply>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_topic_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        replyListView.setOnItemClickListener { adapterView, view, position, l ->

//            클릭된 의견을 추출
            val clickedReply = mReplyList[position]

//            해당 의견 조회 화면으로 이동

            val myIntent = Intent(mContext,ViewReplyDetailActivity::class.java)

//            의견 조회시 필요한 데이터 첨부

            myIntent.putExtra("writerNick",clickedReply.user.nickName)
            myIntent.putExtra("side",clickedReply.side.title)
            myIntent.putExtra("content", clickedReply.content)
            myIntent.putExtra("replyId",clickedReply.id)

            startActivity(myIntent)


        }

        replyBtn.setOnClickListener {

            if(mTopicData.mySide == null){
                Toast.makeText(mContext, "투표를 참가해야 의견을 게시할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val myIntent = Intent(mContext, EditReplyActivity::class.java)
            myIntent.putExtra("topicId", mTopicId)
            myIntent.putExtra("mySideTitle",mTopicData.mySide?.title)
            startActivity(myIntent)
        }

        voteToFirstSideBtn.setOnClickListener {
            ServerUtil.postRequestVote(mContext, mTopicData.sideList[0].id, object  : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

                    runOnUiThread {
                        val message  = json.getString("message")
                        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show()

                        getTopicDetailFromServer()
                    }

                }


            })
        }

        voteToSecondSideBtn.setOnClickListener {
            ServerUtil.postRequestVote(mContext, mTopicData.sideList[1].id, object  : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

                    runOnUiThread {
                        val message  = json.getString("message")
                        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show()

                        getTopicDetailFromServer()
                    }

                }


            })
        }
    }

    override fun setValues() {

//        메인에서 보내준 토픽 id값을 저장

        mTopicId = intent.getIntExtra("topicId",0)

//        리스트뷰와 어댑터 연결
        mReplyAdapter = ReplyAdapter(mContext, R.layout.reply_list_item, mReplyList)
        replyListView.adapter = mReplyAdapter

        getTopicDetailFromServer()

//        서버에서 해당 토픽의 진행상황 상세 정보 확인


    }


    override fun onResume() {
        super.onResume()
        getTopicDetailFromServer()
    }

    fun getTopicDetailFromServer(){

        ServerUtil.getRequestTopicDetail(mContext, mTopicId, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")

                val topicJson = data.getJSONObject("topic")

//                멤버변수 mTopicData에 서버에서 받아온 내용을 저장

                mTopicData = Topic.getTopicFromJson(topicJson)

//                의견목록 받아 리스트뷰에 반영

                val replies = topicJson.getJSONArray("replies")

                mReplyList.clear()

                for(i in 0..replies.length()-1){

//                    의견 하나하나를 replies 클래스 형태로 변환

                    val replyJson = replies.getJSONObject(i)

                    val reply = Reply.getReplyFromJson(replyJson)

//                    만들어낸 의견을 목록에 추가
                    mReplyList.add(reply)


                }
                runOnUiThread {

                    //                의견이 모두 추가되었으면 어댑터 새로고침
                    mReplyAdapter.notifyDataSetChanged()

                }


                runOnUiThread {

                    titleTxt.text = mTopicData.title

                    Glide.with(mContext).load(mTopicData.imageUrl).into(topicImg)

//                    선택 진영의 정보도 뿌려주자
                    firstSideTitleTxt.text = mTopicData.sideList[0].title
                    secondSideTitleTxt.text = mTopicData.sideList[1].title

                    firstSideVoteCountTxt.text = "${mTopicData.sideList[0].voteCount}표"
                    secondSideVoteCountTxt.text = "${mTopicData.sideList[1].voteCount}표"


                }


            }
        })

    }


}