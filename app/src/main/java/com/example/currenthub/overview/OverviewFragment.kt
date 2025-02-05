package com.example.currenthub.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.currenthub.R
import com.example.currenthub.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment() {
    private lateinit var binding: FragmentOverviewBinding
    private lateinit var viewModel: OverviewViewModel
    private lateinit var newsDataAdapter: NewsDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_overview, container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[OverviewViewModel::class.java]
        val apikey = requireContext().getString(R.string.api_key)

        newsDataAdapter = NewsDataAdapter(NewsDataListener { newsId ->
            viewModel.onDetailClicked(newsId)
        })

        binding.editTextText.addTextChangedListener {
            val query = it.toString().trim()
            if (query.isNotBlank()) {
                viewModel.initializePagination(apikey, query)
            }
        }

        binding.newData.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = newsDataAdapter
        }

        binding.newData.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val layoutManager = recyclerView.layoutManager as? StaggeredGridLayoutManager
                    layoutManager?.let {
                        val visibleItemCount = it.childCount
                        val totalItemCount = it.itemCount
                        val lastVisibleItems = it.findLastVisibleItemPositions(null)
                        val maxPosition = lastVisibleItems.maxOrNull() ?: 0

                        if (visibleItemCount + maxPosition >= totalItemCount) {
                            Log.d("Pagination", "End reached, loading next page...")
                            viewModel.loadMoreData()
                        }
                    }
                }
            }
        })

        viewModel.OutputResult.observe(viewLifecycleOwner) { news ->
            news?.let {
                newsDataAdapter.submitList(it)
                newsDataAdapter.notifyDataSetChanged()
            }
        }

        return binding.root
    }
}
