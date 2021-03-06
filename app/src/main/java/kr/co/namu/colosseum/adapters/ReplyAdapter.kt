package kr.co.namu.colosseum.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kr.co.namu.colosseum.R
import kr.co.namu.colosseum.datas.Reply



class ReplyAdapter(val mContext:Context, val resId : Int, val mList:List<Reply>)
    : ArrayAdapter<Reply>(mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView

//        tempRow가 비어있는지? null인지? => 돌려막기 (재사용성 활용) 할 재료가 없다.
        if (tempRow == null) {
//            써먹을 재료가 없으면 새로 xml을 그려줘야함
            tempRow = inf.inflate(R.layout.reply_list_item, null)

        }

//        tempRow는 더이상 null일 가능성이 없다. => row에게 전달.
        val row = tempRow!!

        val writerNameTxt = row.findViewById<TextView>(R.id.writerNameTxt)
        val sideTxt = row.findViewById<TextView>(R.id.sideTxt)
        val contentTxt = row.findViewById<TextView>(R.id.contentTxt)

        val data = mList[position]


        writerNameTxt.text = data.user.nickName
        sideTxt.text = "(${data.side.title})"
        contentTxt.text = data.content



        return row
    }

}