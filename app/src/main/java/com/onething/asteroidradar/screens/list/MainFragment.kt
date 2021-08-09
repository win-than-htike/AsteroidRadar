package com.onething.asteroidradar.screens.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.onething.asteroidradar.R
import com.onething.asteroidradar.databinding.FragmentMainBinding
import com.onething.asteroidradar.domain.model.Asteroid
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentMainBinding.inflate(inflater).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        binding.adapter = AsteroidListAdapter(object : AsteroidItemClickListener {
            override fun onClick(item: Asteroid) {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(item))
            }
        })
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_next_week_menu -> {
                viewModel.setFilterType(AsteroidFilterType.WEEKLY)
            }
            R.id.show_today_menu -> {
                viewModel.setFilterType(AsteroidFilterType.TODAY)
            }
            R.id.show_saved_menu -> {
                viewModel.setFilterType(AsteroidFilterType.ALL)
            }
        }
        return true
    }

}