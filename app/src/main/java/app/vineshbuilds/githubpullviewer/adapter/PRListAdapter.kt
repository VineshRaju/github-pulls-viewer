package app.vineshbuilds.githubpullviewer.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import app.vineshbuilds.githubpullviewer.databinding.PrItemLayoutBinding
import app.vineshbuilds.githubpullviewer.model.PR

class PRListAdapter(val prs: List<PR>) : RecyclerView.Adapter<PRViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, pos: Int) = PRViewHolder(
            PrItemLayoutBinding.inflate(LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false)
    )

    override fun getItemCount() = prs.size

    override fun onBindViewHolder(vh: PRViewHolder, pos: Int) {
        //vh.binding.setVariable(PR,prs[pos])
        vh.binding.pr = prs[pos]
    }

}

class PRViewHolder(val binding: PrItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)