package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.*
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import kotlinx.coroutines.*
import java.nio.file.Paths.get
import java.util.*

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        //The ViewModelProviders (plural) is deprecated.
        //ViewModelProviders.of(this, DevByteViewModel.Factory(activity.application)).get(DevByteViewModel::class.java)
        ViewModelProvider(this, MainViewModel.Factory(requireContext())).get(MainViewModel::class.java)

    }
    lateinit  var binding: FragmentMainBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                      savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)
        binding.statusLoadingWheel.visibility=View.VISIBLE

        viewModel.list.observe(viewLifecycleOwner, Observer {
            it?.let {
                val adapter= AsteroidAdapter(AsteroidAdapter.AsteroidClickListener {
                    findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                })

                adapter.submitList(it)
                binding.asteroidRecycler.adapter = adapter
                binding.statusLoadingWheel.visibility=View.GONE
            }
        })
        viewModel.photo.observe(viewLifecycleOwner, Observer {
            it?.let{
                Picasso.with(requireActivity().application.applicationContext)
                    .load(it.url).into(binding.activityMainImageOfTheDay)
                binding.textView.text=it.title
            }
        })

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId.equals(R.id.show_buy_menu)){
            viewModel.getSavedList()
        }else if(item.itemId.equals(R.id.show_all_menu)){
            //week
            val week = ThisLocalizedWeek(Locale.FRANCE);
            viewModel.getWeekList(week.firstDay.toString(),week.lastDay.toString())
        }
        else if(item.itemId.equals(R.id.show_rent_menu)){
            //today
            viewModel.getTodayList()
        }
        viewModel.list.observe(viewLifecycleOwner, Observer {
            it?.let {
                val adapter= AsteroidAdapter(AsteroidAdapter.AsteroidClickListener {
                    findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                })

                adapter.submitList(it)
                binding.asteroidRecycler.adapter = adapter
            }
        })
        return true
    }

}
