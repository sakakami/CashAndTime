package mysoftstudio.cashandtime.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import mysoftstudio.cashandtime.R
import mysoftstudio.cashandtime.databinding.DialogAboutBinding
import mysoftstudio.cashandtime.databinding.FragmentChildMainVBinding
import mysoftstudio.cashandtime.gson.Cash2G
import mysoftstudio.cashandtime.gson.Time2G
import mysoftstudio.cashandtime.presenter.ChildMainP
import mysoftstudio.cashandtime.tool.Preferences
import mysoftstudio.cashandtime.view.vi.ChildMainVI

/**
 * Created by Joshua on 2021.07.31
 */
class ChildMainV : Fragment(), ChildMainVI {
    private var _binding: FragmentChildMainVBinding? = null
    private val binding get() = _binding!!
    private val p by lazy { ChildMainP(this) }
    private var check by Preferences("isChecked", false)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChildMainVBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun refreshData(cash: String, time: String) {
        binding.textCash.text = cash
        binding.textTime.text = time
    }

    override fun toCashPage(cashG: Cash2G) {
        val bundle = Bundle()
        bundle.putSerializable("cash", cashG.cashData)
        findNavController().navigate(R.id.action_childMainV_to_cashInfoListV, bundle)
    }

    override fun toTimePage(timeG: Time2G) {
        val bundle = Bundle()
        bundle.putSerializable("time", timeG.timeData)
        findNavController().navigate(R.id.action_childMainV_to_timeInfoListV, bundle)
    }

    override fun showMessage(msg: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(msg)
            .setPositiveButton(R.string.global_ok) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    override fun showAbout() {
        val view = DialogAboutBinding.inflate(LayoutInflater.from(requireContext()))
        val version = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0).versionName
        val project = Intent(Intent.ACTION_VIEW).apply { data = Uri.parse("https://github.com/sakakami/CashAndTime") }
        val rate = Intent(Intent.ACTION_VIEW).apply { data = Uri.parse("https://play.google.com/store/apps/details?id=mysoftstudio.cashandtime") }
        view.txtResultVersion.text = version
        view.txtResultProject.setOnClickListener { startActivity(project) }
        view.txtResultRate.setOnClickListener { startActivity(rate) }
        AlertDialog.Builder(requireContext())
            .setView(view.root)
            .setPositiveButton(R.string.global_ok) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun init() {
        Glide.with(requireContext())
            .asGif()
            .load(R.drawable.diamond)
            .into(binding.imageCash)
        Glide.with(requireContext())
            .asGif()
            .load(R.drawable.clock)
            .into(binding.imageTime)
        val time = "00:00"
        val cash = "0 NTD"
        binding.textCash.text = cash
        binding.textTime.text = time
        binding.imageCash.setOnClickListener { p.handleClickCash() }
        binding.textCash.setOnClickListener { p.handleClickCash() }
        binding.imageTime.setOnClickListener { p.handleClickTime() }
        binding.textTime.setOnClickListener { p.handleClickTime() }
        binding.imageRefresh.setOnClickListener { p.getData() }
        binding.imageMember.setOnClickListener { p.getMemberInfo() }
        //初始化menu
        binding.toolbar.inflateMenu(R.menu.menu)
        binding.toolbar.menu.findItem(R.id.menu_item_night).isChecked = check
        binding.toolbar.setOnMenuItemClickListener { p.handleMenuClick(it) }

        p.getData()
    }
}