package com.coding.stackexchange.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coding.stackexchange.R
import com.coding.stackexchange.model.Question

/**
 * [RecyclerView.Adapter] to display generate a paginated list of [Questions] list.
 */
class QuestionsListAdapter(private val list: ArrayList<Question>) :
    RecyclerView.Adapter<QuestionsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsHolder {
        // Instantiating ViewHolder.
        val inflater = LayoutInflater.from(parent.context)
        return QuestionsHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(questionList: ArrayList<Question>) {
        //updating the list
        list.addAll(questionList)
    }

    override fun onBindViewHolder(holder: QuestionsHolder, position: Int) {
        // Binding the required data to the question item.
        val question: Question = list[position]
        holder.bind(question)
    }
}

/**
 * [RecyclerView.ViewHolder] to recycle the item layouts during the population of data.
 */
class QuestionsHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_question, parent, false)) {
    private var titleView: TextView
    private var viewCount: TextView
    private var answerCount: TextView
    private var voteCount: TextView

    init {
        titleView = itemView.findViewById(R.id.tv_question_title)
        viewCount = itemView.findViewById(R.id.tv_view_count)
        answerCount = itemView.findViewById(R.id.tv_answer_count)
        voteCount = itemView.findViewById(R.id.tv_vote_count)
    }

    /**
     * Function to bind the [Question] details to the item layout.
     */
    fun bind(question: Question) {
        titleView.text = question.title
        viewCount.text = question.viewCount.toString()
        answerCount.text = question.answerCount.toString()
        voteCount.text = question.score.toString()
    }

}

