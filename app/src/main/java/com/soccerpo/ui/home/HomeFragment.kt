package com.soccerpo.ui.home

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soccerpo.data.db.entity.Soccer
import com.soccerpo.data.db.entity.SoccerChinese
import com.soccerpo.databinding.FragmentHomeBinding
import com.soccerpo.ui.MainViewModel
import com.soccerpo.ui.adapter.MultiAdapter
import com.soccerpo.utils.*
import java.util.*

class HomeFragment : Fragment(),HomeListener{

    private var _binding: FragmentHomeBinding? = null
    private lateinit var mainViewModel: MainViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var fragmentRecyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    private val myAdapter: MultiAdapter by lazy { MultiAdapter(requireContext(), MultiAdapter.VIEW_TYPE_ONE) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container,
            false)

        mainViewModel.mainListener = this
        fragmentRecyclerView = binding.homeRecyclerView
        progressBar = binding.progressHome

        fragmentRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,
                false)
            adapter = myAdapter
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callNetworkConnection()
        mainViewModel.currentLang().observe(requireActivity(), Observer {
            myAdapter.setIsChinese(it.language)
        })

    }


    private fun callNetworkConnection() {
        val checkNetworkConnection = activity?.let { CheckNetworkConnection(it.application) }
        checkNetworkConnection?.observe(requireActivity(), { isConnected ->

            if(isConnected){
                buildUI()
                fragmentRecyclerView.show()
            }else{
                fragmentRecyclerView.hide()
            }
        })
    }


    private fun buildUI() = Coroutines.main {
        mainViewModel.soccerData.await().observe(requireActivity(), androidx.lifecycle.Observer {
            myAdapter.setData(it)
            progressBar.visibility = View.GONE
            fragmentRecyclerView.visibility = View.VISIBLE
        })
    }

    override fun filterData(message: String,language: String) {
        Coroutines.main {
            mainViewModel.soccerData.await().observe(requireActivity(), androidx.lifecycle.Observer { data ->
                var filtered = data.filter{ it.teamLeague == message }
                if (message == "All"){
                    myAdapter.setData(data)
                }else{myAdapter.setData(filtered)}
            })
        }
    }

}




