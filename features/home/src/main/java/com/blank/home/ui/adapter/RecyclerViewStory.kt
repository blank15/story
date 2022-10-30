package com.blank.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blank.home.databinding.ItemStoryBinding
import com.blank.model.database.StoryModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RecyclerViewStory(
    private val itemClicked: StoriesClicked
) : PagingDataAdapter<StoryModel, RecyclerViewStory.StoryViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryModel>() {
            override fun areItemsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        return StoryViewHolder(
            ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClicked
        )
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }

    }

    class StoryViewHolder(
        private val itemStoryBinding: ItemStoryBinding,
        private val itemClicked: StoriesClicked
    ) : RecyclerView.ViewHolder(itemStoryBinding.root) {
        fun bind(story: StoryModel) {
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
        fun onItemClicked(item: StoryModel, img: AppCompatImageView)
    }
}