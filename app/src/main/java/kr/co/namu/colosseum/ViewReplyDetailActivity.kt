package kr.co.namu.colosseum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_reply.*
import kotlinx.android.synthetic.main.activity_view_reply_detail.*
import kotlinx.android.synthetic.main.activity_view_reply_detail.sideTxt
import kotlinx.android.synthetic.main.reply_list_item.*
import kr.co.namu.colosseum.adapters.ReplyAdapter
import kr.co.namu.colosseum.datas.Reply
import kr.co.namu.colosseum.utils.ServerUtil
import org.json.JSONObject

class ViewReplyDetailActivity : BaseActivity() {

//    몇번 의견에 대한 조회인지 var로 남겨둘 것

    var mReplyId = 0

//    답글 목록을 저장할 배열을 만든다

    val mReReplyList = ArrayList<Reply>()

    lateinit var mAdapter : ReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reply_detail)
        setupEvents()
        setValues()
    }
    override fun setupEvents() {

        postBtn.setOnClickListener {
//            답글을 등록하고 입력한 내용 비우기

            val inputContent = contentsEdt.text.toString()

            if(inputContent.length<5){
                Toast.makeText(mContext,"글자는 5자 이상입니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ServerUtil.postRequestReReply(mContext, mReplyId, inputContent, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

//                    다시 답글 목록을 서버에서 불러오기
                    getTopciReplyFromServer()

                    runOnUiThread {
//                        입력한 내용 비워주기
                        contentsEdt.setText("")
                    }

                }

            })

        }

    }

    override fun setValues() {

//        몇번 댓글을 보러 온건지 저장

        mReplyId = intent.getIntExtra("replyId",0)

//        화면에 댓글 데이터 표시

        writerNickNameTxt.text = intent.getStringExtra("writerNick")
        sideTxt.text = "(${intent.getStringExtra("side")})"
        contentTxt.text=intent.getStringExtra("content")


//        어탭터 생성과 리스트뷰 연결

        mAdapter = ReplyAdapter(mContext, R.layout.reply_list_item,mReReplyList)
        reReplyListView.adapter = mAdapter

    }

    override fun onResume() {
        super.onResume()

        getTopciReplyFromServer()
    }

    fun getTopciReplyFromServer(){

        ServerUtil.getRequestReplyDetail(mContext, mReplyId, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")
                val reply = data.getJSONObject("reply")

//                새로 담아주기 전에 목록 내부 비워주기

                mReReplyList.clear()


//                답글 목록을 받아서 리스트뷰에 반영

                val replies = reply.getJSONArray("replies")

                for(i in 0..replies.length()-1){
                    val replyJson = replies.getJSONObject(i)

                    val reply = Reply.getReplyFromJson(replyJson)

                    mReReplyList.add(reply)


                }

                runOnUiThread {
//                    리스트뷰 내용 새로고침
                    mAdapter.notifyDataSetChanged()
                }

            }

        })

    }


}