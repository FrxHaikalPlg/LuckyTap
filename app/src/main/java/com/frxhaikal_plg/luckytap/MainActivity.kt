package com.frxhaikal_plg.luckytap

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.frxhaikal_plg.luckytap.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CounterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.counter.observe(this) { counter ->
            binding.tvCounter.text = "Counter: $counter"
        }
        viewModel.status.observe(this) { status ->
            binding.tvStatus.text = status
        }
        viewModel.jackpotProbability.observe(this) { probability ->
            if (probability == -1.0) {
                showJackpotDialog(viewModel.counter.value ?: 0)
            }
        }

        binding.btnIncrement.setOnClickListener {
            viewModel.increment()
        }

        binding.btnRetry.setOnClickListener {
            viewModel.reset()
            binding.btnRetry.visibility = View.GONE
            binding.btnIncrement.isEnabled = true
            binding.btnIncrement.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_blue_dark))
        }
    }

    private fun showJackpotDialog(value: Int) {
        AlertDialog.Builder(this)
            .setTitle("Selamat!")
            .setMessage("Anda 'Jackpot' pada angka $value")
            .setPositiveButton("OK") { _, _ -> }
            .setCancelable(false)
            .show()
        
        binding.btnIncrement.isEnabled = false
        binding.btnIncrement.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        binding.btnRetry.visibility = View.VISIBLE
    }
}
