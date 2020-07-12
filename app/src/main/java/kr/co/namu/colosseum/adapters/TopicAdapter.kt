package kr.co.namu.colosseum.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kr.co.namu.colosseum.R

import kr.co.namu.colosseum.datas.Topic


class TopicAdapter(val mContext:Context, val resId : Int, val mList:List<Topic>)
    : ArrayAdapter<Topic>(mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView

//        tempRow가 비어있는지? null인지? => 돌려막기 (재사용성 활용) 할 재료가 없다.
        if (tempRow == null) {
//            써먹을 재료가 없으면 새로 xml을 그려줘야함
            tempRow = inf.inflate(R.layout.topic_list_item, null)

        }

//        tempRow는 더이상 null일 가능성이 없다. => row에게 전달.
        val row = tempRow!!

        val topicImg = row.findViewById<ImageView>(R.id.topicImg)

        val titleTxt = row.findViewById<TextView>(R.id.titleTxt)

        val data = mList[position]

        titleTxt.text = data.title

//        인터넷 주소의 이미지를 Glide 라이브러리로 다운받아서 이미지뷰 적용

        Glide.with(mContext).load(data.imageUrl).into(topicImg)





        return row
    }

}