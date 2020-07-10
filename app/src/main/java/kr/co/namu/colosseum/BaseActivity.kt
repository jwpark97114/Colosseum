package kr.co.namu.colosseum

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity:AppCompatActivity() {

//    this(어떤 화면에서 쓰는지)를 대입해야할 상황에 대신 대입할 변수

    val mContext = this

//    다른 화면들이 두 함수를 반드시 구현해야 하도록 강제
    abstract fun setupEvents()
    abstract fun setValues()
}