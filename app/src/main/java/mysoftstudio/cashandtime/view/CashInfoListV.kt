package mysoftstudio.cashandtime.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import mysoftstudio.cashandtime.adapter.DataListAdapter
import mysoftstudio.cashandtime.data.CashData
import mysoftstudio.cashandtime.databinding.FragmentCashInfoListVBinding
import mysoftstudio.cashandtime.presenter.CashInfoListP
import mysoftstudio.cashandtime.view.vi.CashInfoListVI

/**
 * Created by Joshua on 2021.08.04
 * show all the cash of the data list
 */
class CashInfoListV : Fragment(), CashInfoListVI {
    private var _binding: FragmentCashInfoListVBinding? = null
    private val binding get() = _binding!!
    private val p by lazy { CashInfoListP(this) }
    private lateinit var adapter: DataListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCashInfoListVBinding.inflate(inflater, container, false)
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
            val list = bundle.getSerializable("cash") as ArrayList<CashData>
            p.init(list)
        }
    }
}