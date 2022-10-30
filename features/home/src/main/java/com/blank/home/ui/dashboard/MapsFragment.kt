package com.blank.home.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.blank.domain.model.Resource
import com.blank.home.R
import com.blank.model.database.StoryModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MapsFragment : Fragment(), OnMapReadyCallback {

    private val viewModelDashBoard: DashboardViewModel by sharedViewModel()

    private lateinit var mMap: GoogleMap
    private val boundsBuilder = LatLngBounds.Builder()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        viewModelDashBoard.getStoriesMap()
        initObserver()
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map.apply {
            uiSettings.apply {
                isZoomControlsEnabled = true
                isCompassEnabled = true
                isMyLocationButtonEnabled = true
            }
            setMapStyle(context?.let {
                MapStyleOptions.loadRawResourceStyle(
                    it,
                    com.blank.ui.R.raw.map_style
                )
            })
        }
    }


    private fun initObserver() {
        viewModelDashBoard.storiesMap.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                }
                Resource.Status.SUCCESS -> {
                    it.data?.let { data ->
                        for (story: StoryModel in data) {
                            val latLng = LatLng(
                                story.lat ?: 0.0,
                                story.lon ?: 0.0
                            )
                            mMap.addMarker(
                                MarkerOptions()
                                    .position(latLng)
                                    .title(story.name)
                                    .snippet(story.description)
                            )
                            boundsBuilder.include(latLng)
                        }

                        val bounds: LatLngBounds = boundsBuilder.build()
                        mMap.animateCamera(
                            CameraUpdateFactory.newLatLngBounds(
                                bounds,
                                resources.displayMetrics.widthPixels,
                                resources.displayMetrics.heightPixels,
                                300
                            )
                        )
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(context, it.error?.message ?: "", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}