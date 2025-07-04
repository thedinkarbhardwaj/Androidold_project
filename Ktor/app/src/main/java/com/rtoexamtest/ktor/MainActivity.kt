package com.rtoexamtest.ktor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.rtoexamtest.ktor.Ui.MainViewModel
import com.rtoexamtest.ktor.Util.ApiState
import com.rtoexamtest.ktor.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val mainViewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenCreated {
            mainViewModel.apiStateFlow.collect{

                binding.apply {
                    when(it){
                        is ApiState.Success ->{
                            Log.d("Dataaaaa", it.data.toString())
                        }
                        is ApiState.Failure ->{
                            Log.d("Dataaaaa", it.msg.toString())
                        }
                        else -> {}
                    }

                }
            }
        }
    }
}