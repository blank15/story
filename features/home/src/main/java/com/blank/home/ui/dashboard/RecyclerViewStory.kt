package com.blank.home.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.blank.home.databinding.ItemStoryBinding
import com.blank.model.story.StoryResult
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RecyclerViewStory(
    private val listStory: List<StoryResult>,
    private val itemClicked: StoriesClicked
) : RecyclerView.Adapter<RecyclerViewStory.StoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        return StoryViewHolder(
            ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClicked
        )
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val item = listStory[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int = listStory.size

    class StoryViewHolder(
        private val itemStoryBinding: ItemStoryBinding,
        private val itemClicked: StoriesClicked
    ) : RecyclerView.ViewHolder(itemStoryBinding.root) {
        fun bind(story: StoryResult) {
            itemStoryBinding.apply {
                tvItemDesc.text = story.description
                tvItemName.text = story.name
                ivItemPhoto.transitionName = story.photoUrl
                Glide.with(itemStoryBinding.root.context)
                    .load(story.photoUrl)
                    .apply(RequestOptions.centerCropTransform())
                    .into(ivItemPhoto)

                itemStoryBinding.root.setOnClickListener {
                    itemClicked.onItemClicked(story, ivItemPhoto)
                }
            }
        }
    }

    interface StoriesClicked {
        fun onItemClicked(item: StoryResult, img: AppCompatImageView)
    }
}