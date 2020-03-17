package com.coding.stackexchange.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coding.stackexchange.BaseActivity
import com.coding.stackexchange.Utilities.OnLoadMoreListener
import com.coding.stackexchange.R
import com.coding.stackexchange.Utilities.RecyclerViewLoadMoreScroll
import com.coding.stackexchange.adapter.QuestionsListAdapter
import com.coding.stackexchange.model.Question
import com.coding.stackexchange.viewmodel.QuestionViewModel

/*
* The activity that display the [Questions] information list
*
* The questions information that is displayed is always fetched from the internet
* */
class QuestionsListActivity : BaseActivity() {

    private lateinit var mViewModel: QuestionViewModel
    private lateinit var mRecycleListView: RecyclerView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var adapter: QuestionsListAdapter
    private var mQuestionList = ArrayList<Question>()
    lateinit var scrollListener: RecyclerViewLoadMoreScroll
    lateinit var mLayoutManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_list)
        mRecycleListView = findViewById(R.id.recycler_view)
        mProgressBar = findViewById(R.id.progress_bar)
        mRecycleListView.setLayoutManager(LinearLayoutManager(this));

        mViewModel =
            ViewModelProvider(this@QuestionsListActivity).get(QuestionViewModel::class.java)

        setupAdapter()
        setupObserables()
        setRecyclerViewScrollListener()
        getQuestionList()

    }

    /*
    * Scroll listener for RecyclerView.
    * */
    fun setRecyclerViewScrollListener() {
        mLayoutManager = LinearLayoutManager(this)
        mRecycleListView.layoutManager = mLayoutManager
        mRecycleListView.setHasFixedSize(true)
        scrollListener =
            RecyclerViewLoadMoreScroll(
                mLayoutManager as LinearLayoutManager
            )
        //Detects when scroll reached bottom
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                getQuestionList()
            }
        })
        mRecycleListView.addOnScrollListener(scrollListener)

    }

    /*
    * Function to setup the adapter to RecyclerView.
    * */
    fun setupAdapter() {
        adapter =
            QuestionsListAdapter(mQuestionList)
        mRecycleListView.adapter = adapter
    }

    /*
    * Function to retrieve the Question list
    * */
    fun setupObserables() {
        mViewModel.questionsListObservable.observe(this, Observer {
            // Updating the Questions list data.
            mQuestionList = it as ArrayList<Question>
            adapter.setList(mQuestionList)
            adapter.notifyDataSetChanged()
            scrollListener.setLoaded()
            hideProgressBar()
        })
        // Observing for failure messages.
        mViewModel.onFailureObservable.observe(this, Observer {
            hideProgressBar()
            Toast.makeText(this@QuestionsListActivity, it, Toast.LENGTH_SHORT).show()
        })
    }

    fun showProgressBar() {
        // Showing progress bar when API call is going
        mProgressBar.visibility = View.VISIBLE
        mProgressBar.bringToFront()
        disableView()
    }

    fun hideProgressBar() {
        //Hinding the progress bar
        mProgressBar.visibility = View.INVISIBLE
        enableView()
    }

    fun getQuestionList() {
        // Making sure that the latest data is fetched only when
        // the internet connection is available.
        if (getNetworkState()) {
            showProgressBar()
            mViewModel.getQuestionList()
        } else {
            Toast.makeText(
                this@QuestionsListActivity,
                getString(R.string.no_inter_msg),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getNetworkAvailability(isConnected: Boolean) {
        // Nothing to do.
    }
}
