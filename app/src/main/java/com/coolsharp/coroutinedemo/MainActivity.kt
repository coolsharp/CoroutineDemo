package com.coolsharp.coroutinedemo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.coolsharp.coroutinedemo.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var count: Int = 1
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar?, p1: Int, p2: Boolean) {
                count = p1
                binding.textView.text = "($count) coroutines"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })

        binding.button.setOnClickListener {
            (1..count).forEach { n->
                binding.statusText.text = "Started Coroutine $n"
                coroutineScope.launch(Dispatchers.Main) {
                    binding.statusText.text = performTask(n)
                }
            }
        }
    }

    private suspend fun performTask(taskNumber: Int): String = coroutineScope.async(Dispatchers.Main) {
        delay(5_000)
        return@async "Finished Coroutine $taskNumber"
    }.await()
}