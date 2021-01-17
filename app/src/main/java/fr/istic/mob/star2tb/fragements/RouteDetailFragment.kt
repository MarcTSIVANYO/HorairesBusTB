package fr.istic.mob.star2tb.fragements

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.istic.mob.star2tb.R

class RouteDetailFragment : Fragment() {

    companion object {
        fun newInstance() = RouteDetailFragment()
    }

    private lateinit var viewModel: RouteDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.route_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RouteDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}