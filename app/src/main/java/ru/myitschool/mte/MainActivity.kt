package ru.myitschool.mte

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.coroutines.*
import ru.myitschool.mte.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var job: Job
    private var counter = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btnStart = binding.content.startBtn
        btnStop = binding.content.stopBtn
        val frManager = supportFragmentManager

        val fragments = arrayOf(FirstFragment(), ProceedingFragment())

        btnStart.setOnClickListener {

            // launch a new coroutine in background and continue

            job = CoroutineScope(Dispatchers.IO).launch{
                while(true) {
                    val transaction: FragmentTransaction = frManager.beginTransaction()
                    val currentFragment: Fragment = fragments.get(counter++ % 2)
                    transaction.replace(R.id.output_fragment, currentFragment)
                    transaction.commit()
                    delay(3000)

                }
            }
        }
        btnStop.setOnClickListener {
            job.cancel()
        }
    }
}