package com.example.currenthub.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.currenthub.R
import com.example.currenthub.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    lateinit var binding : FragmentDetailBinding
    lateinit var viewModel: DetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container,false)
        val data = arguments?.getString("newsId")
        binding.textView6.text = data
        var viewModelFactory = DetailViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]
        val apiKey = requireContext().getString(R.string.api_key)
        data?.let { viewModel.getNewsResponce(apiKey, it) }
        viewModel.newsResponce.observe(viewLifecycleOwner){
            it?.let {
                binding.detailBinding = it.results[0]
            }
        }
        binding.textView7.text=viewModel.newsResponce.value.toString()
        return binding.root
    }
}