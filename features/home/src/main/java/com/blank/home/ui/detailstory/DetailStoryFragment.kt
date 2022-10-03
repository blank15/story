package com.blank.home.ui.detailstory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.blank.home.databinding.FragmentDetailStoryBinding
import com.blank.model.story.StoryResult
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailStoryFragment : Fragment() {

    private var _binding: FragmentDetailStoryBinding? = null
    private val binding get() = _binding
    private lateinit var story: StoryResult
    private val args: DetailStoryFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailStoryBinding.inflate(inflater, container, false)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        story = args.story
        binding?.apply {

            toolbar.apply {
                setNavigationOnClickListener {
                    if (!findNavController().navigateUp())
                        requireActivity().finish()
                }
                setNavigationIcon(com.blank.ui.R.drawable.ic_baseline_arrow_back_ios_new_24)
                title = resources.getString(com.blank.ui.R.string.detail_story)
            }
            tvDetailDescription.text = story.description
            tvDetailName.text = story.name
            context?.let {
                Glide.with(it)
                    .load(story.photoUrl)
                    .apply(RequestOptions.centerCropTransform())
                    .into(ivDetailPhoto)
            }
            ivDetailPhoto.transitionName = story.photoUrl
        }
    }
}