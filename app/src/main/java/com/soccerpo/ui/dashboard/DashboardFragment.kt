package com.soccerpo.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soccerpo.databinding.FragmentDashboardBinding
import com.soccerpo.ui.MainViewModel
import com.soccerpo.ui.adapter.MultiAdapter
import com.soccerpo.ui.adapter.VideoAdapter
import com.soccerpo.ui.home.HomeListener
import com.soccerpo.utils.*

class DashboardFragment : Fragment(), HomeListener, VideoAdapter.onItemClicked {

    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var dashBoardRecyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var root_layout: ConstraintLayout

    private val myDashBoard: VideoAdapter by lazy { VideoAdapter(requireContext(),this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        mainViewModel.mainListener = this
        dashBoardRecyclerView = binding.dashboardRecyclerView
        progressBar = binding.progressDashboard
        root_layout = binding.rootLayout

        dashBoardRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = myDashBoard
        }



        callNetworkConnection()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun callNetworkConnection() {
        val checkNetworkConnection = activity?.let { CheckNetworkConnection(it.application) }
        checkNetworkConnection?.observe(requireActivity(), { isConnected ->

            if(isConnected){
                buildUI()
                dashBoardRecyclerView.show()
            }else{
                dashBoardRecyclerView.hide()
            }
        })
    }

    private fun buildUI() = Coroutines.main {
        Coroutines.main {
            mainViewModel.videoData.await()
                .observe(requireActivity(), androidx.lifecycle.Observer {
                    myDashBoard.setVideoData(it)
                    progressBar.visibility = View.GONE
                    dashBoardRecyclerView.visibility = View.VISIBLE
                })
        }
    }

    override fun filterData(message: String, language: String) {
    }

    override fun onItemClicked(context: Context, message: String) {
        isClickable(message)

    }

    private fun isClickable(message: String) = Coroutines.main {
        mainViewModel.videoIsActive.await().observe(requireActivity(), Observer {
            if( it == true) {
                activity?.popUpAds(requireContext(), message)
            }else{
                root_layout.snackbar("Thank you")
            }
        })
    }

    override fun onClickedImage(context: Context, url: String) {
    }

}