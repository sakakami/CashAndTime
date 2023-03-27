package mysoftstudio.cashandtime.view

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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
    ): View {
        _binding = FragmentTimeInfoListVBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun refreshAdapter(size: Int) { adapter.refresh(size) }

    override fun showMsg(msg: String) {
        AlertDialog.Builder(requireContext())
            .setMessage(msg)
            .setPositiveButton(R.string.global_ok) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    override fun delConfirm() {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.msg_del_confirm)
            .setPositiveButton(R.string.global_ok) { _, _ -> p.handleDelData() }
            .setNegativeButton(R.string.dialog_cancel) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun init() {
        adapter = DataListAdapter { holder, i -> p.handleHolder(holder, i) }
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
        val bundle = arguments
        if (bundle != null) {
            val list = bundle.parcelableArrayList<TimeData>("time")
            list?.let { p.init(it) }
        }
    }

    private inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? = when {
        Build.VERSION.SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
        else -> @Suppress ("DEPRECATION") getParcelableArrayList(key)
    }
}