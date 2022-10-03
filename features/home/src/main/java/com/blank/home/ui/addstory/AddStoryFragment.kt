package com.blank.home.ui.addstory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blank.domain.model.Resource
import com.blank.home.databinding.FragmentAddStoryBinding
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.ReturnMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class AddStoryFragment : Fragment() {

    private var _binding: FragmentAddStoryBinding? = null
    private val binding get() = _binding
    private val viewModelAddStory: AddStoryViewModel by viewModel()
    private var imageUri: Image? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddStoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        initView()
    }

    private fun initObserve() {
        viewModelAddStory.uploadResult.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding?.apply {
                        loading.visibility = View.VISIBLE
                        buttonAdd.visibility = View.GONE
                    }
                }
                Resource.Status.SUCCESS -> {
                    findNavController().navigateUp()
                }
                Resource.Status.ERROR -> {
                    binding?.apply {
                        loading.visibility = View.GONE
                        buttonAdd.visibility = View.VISIBLE
                    }
                    Toast.makeText(context, it.error?.message ?: "", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initView() {
        binding?.apply {
            val imagePickerLauncher = registerImagePicker {
                imageUri = it.firstOrNull() ?: return@registerImagePicker
                Glide.with(ivAddPhoto)
                    .load(imageUri?.uri)
                    .into(ivAddPhoto)
            }
            ivAddPhoto.setOnClickListener {
                imagePickerLauncher.launch(
                    ImagePickerConfig {
                        mode = ImagePickerMode.SINGLE
                        returnMode = ReturnMode.ALL
                        isFolderMode = true
                        folderTitle = "Folder"
                        imageTitle = "Tap to select"
                        doneButtonText = "DONE"
                    }
                )
            }
            buttonAdd.setOnClickListener {
                val desc = edDesc.text.toString()
                if (imageUri != null && desc.isNotEmpty()) {
                    imageUri?.let {
                        viewModelAddStory.uploadStory(desc, File(it.path))
                    }
                } else
                    Toast.makeText(context, "Terdapat data yang belum di isi", Toast.LENGTH_SHORT)
                        .show()

            }

            toolbar.apply {
                setNavigationOnClickListener {
                    if (!findNavController().navigateUp())
                        requireActivity().finish()
                }
                setNavigationIcon(com.blank.ui.R.drawable.ic_baseline_arrow_back_ios_new_24)
                title = resources.getString(com.blank.ui.R.string.add_story)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModelAddStory.cancelJob()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}