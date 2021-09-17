package mysoftstudio.cashandtime.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import mysoftstudio.cashandtime.R
import mysoftstudio.cashandtime.adapter.DataListAdapter
import mysoftstudio.cashandtime.data.TimeData
import mysoftstudio.cashandtime.databinding.FragmentTimeInfoListVBinding
import mysoftstudio.cashandtime.presenter.TimeInfoListP
import mysoftstudio.cashandtime.view.vi.TimeInfoListVI

/**
 * Created by Joshua on 2021.08.04
 * show time data of time list
 */
class TimeInfoListV : Fragment(), TimeInfoListVI {
    private var _binding: FragmentTimeInfoListVBinding? = null
    private val binding get() = _binding!!
    private val p by lazy { TimeInfoListP(this) }
    private lateinit var adapter: DataListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimeInfoListVBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun refreshAdapter(size: Int) { adapter.refresh(size) }

    private fun init() {
        adapter = DataListAdapter { holder, i -> p.handleHolder(holder, i) }
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
        val bundle = arguments
        if (bundle != null) {
            val list = bundle.getSerializable("time") as ArrayList<TimeData>
            p.init(list)
        }
    }
}