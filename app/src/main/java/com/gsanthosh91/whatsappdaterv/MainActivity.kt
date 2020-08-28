package com.gsanthosh91.whatsappdaterv

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gsanthosh91.whatsappdaterv.databinding.ActivityMainBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list = loadJSONFromAssets("conversation.json")

        binding.chatRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = list.let { ChatAdapter(it, 1) }
        }

        binding.chatRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d("DERE", "onScrollStateChanged: "+getCurrentItem())
                    val position: Int = getCurrentItem()
                    setDateLabel(list[position])
                }
            }
        })
    }


    private fun setDateLabel(item: MyChat) {
        val dd = convertDateFormat("yyyy-MM-dd HH:mm:ss", "yyyy MMM dd", item.timestamp!!)
        binding.dateLabel.text = dd
        binding.dateLabel.visibility = View.VISIBLE


        Looper.myLooper()?.let {
            Handler(it).postDelayed({
                binding.dateLabel.visibility = View.INVISIBLE
            }, 3000)
        }

    }


    fun convertDateFormat(FormFormat: String, ToFormat: String, value: String): String {
        var returnValue = value
        val formFormat = SimpleDateFormat(FormFormat, Locale.getDefault())
        val toFormat = SimpleDateFormat(ToFormat, Locale.getDefault())
        returnValue = toFormat.format(formFormat.parse(value))
        return returnValue
    }

    private fun getCurrentItem(): Int {
        return (binding.chatRv.getLayoutManager() as LinearLayoutManager).findFirstVisibleItemPosition()
    }


    fun Context.loadJSONFromAssets(fileName: String): List<MyChat> {
        val json = applicationContext.assets.open(fileName).bufferedReader().use { reader ->
            reader.readText()
        }

        val gson = Gson()
        return gson.fromJson(json, object : TypeToken<List<MyChat>>() {}.getType())
    }
}