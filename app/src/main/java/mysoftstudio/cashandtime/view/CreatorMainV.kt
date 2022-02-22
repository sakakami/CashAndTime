package mysoftstudio.cashandtime.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import mysoftstudio.cashandtime.R
import mysoftstudio.cashandtime.adapter.WalletAdapter
import mysoftstudio.cashandtime.databinding.*
import mysoftstudio.cashandtime.presenter.CreatorMainP
import mysoftstudio.cashandtime.tool.Preferences
import mysoftstudio.cashandtime.view.vi.CreatorMainVI

/**
 * Created by Joshua on 2021.08.01
 * 管理者專用頁面
 */
class CreatorMainV : Fragment(), CreatorMainVI {
    private var _binding: FragmentCreatorMainVBinding? = null
    private val binding get() = _binding!!
    private val p by lazy { CreatorMainP(this) }
    private lateinit var walletAdapter: WalletAdapter
    private var check by Preferences("isChecked", false)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreatorMainVBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun toShowCashPage(bundle: Bundle) {
        findNavController().navigate(R.id.action_creatorMainV_to_cashInfoListV, bundle)
    }

    override fun toShowTimePage(bundle: Bundle) {
        findNavController().navigate(R.id.action_creatorMainV_to_timeInfoListV, bundle)
    }

    override fun showMessage(msg: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(msg)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    //進入管理這頁面，如果並沒有連結任何錢包的時候會跳出此訊息
    override fun showAddMessage() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(R.string.creator_msg_no_wallet)
            .setPositiveButton(R.string.global_ok) { dialog, _ ->
                showAddChild()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.dialog_cancel) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    override fun refreshAdapter(size: Int) { walletAdapter.refresh(size) }

    override fun showAddCash(name: String, position: Int) {
        var checkA = true
        var editCash = "0"
        var editInfo = ""
        val viewBinding = DialogAddCashBinding.inflate(LayoutInflater.from(requireContext()))
        viewBinding.radioGroup.check(viewBinding.radioAdd.id)
        viewBinding.radioGroup.setOnCheckedChangeListener { _, checkedId -> checkA = checkedId == R.id.radioAdd }
        viewBinding.editCash.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) { editCash = s.toString() }
        })
        viewBinding.editInfo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) { editInfo = s.toString() }
        })

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(name)
            .setView(viewBinding.root)
            .setPositiveButton(R.string.dialog_send) { dialog, _ ->
                if (editCash.isEmpty() || editCash.toInt() == 0) {
                    Toast.makeText(requireContext(), R.string.dialog_cash_caution, Toast.LENGTH_SHORT).show()
                } else if (editInfo.isEmpty()) {
                    Toast.makeText(requireContext(), R.string.dialog_info_caution, Toast.LENGTH_SHORT).show()
                } else {
                    p.handleAddCash(checkA, editCash, editInfo, position)
                    dialog.dismiss()
                }
            }
            .setNegativeButton(R.string.dialog_cancel) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    override fun showAddTime(name: String, position: Int) {
        var checkA = true
        var editTime = "0"
        var editInfo = ""
        val viewBinding = DialogAddTimeBinding.inflate(LayoutInflater.from(requireContext()))
        viewBinding.radioGroup.check(viewBinding.radioAdd.id)
        viewBinding.radioGroup.setOnCheckedChangeListener { _, checkedId -> checkA = checkedId == R.id.radioAdd }
        viewBinding.editMin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) { editTime = s.toString() }
        })
        viewBinding.editInfo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) { editInfo = s.toString() }
        })

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(name)
            .setView(viewBinding.root)
            .setPositiveButton(R.string.dialog_send) { dialog, _ ->
                if (editTime.isEmpty() || editTime.toInt() == 0) {
                    Toast.makeText(requireContext(), R.string.dialog_time_caution, Toast.LENGTH_SHORT).show()
                } else if (editInfo.isEmpty()) {
                    Toast.makeText(requireContext(), R.string.dialog_info_caution, Toast.LENGTH_SHORT).show()
                } else {
                    p.handleAddTime(checkA, editTime, editInfo, position)
                    dialog.dismiss()
                }
            }
            .setNegativeButton(R.string.dialog_cancel) { dialog, _ -> dialog.dismiss() }
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
        binding.imageMember.setOnClickListener { p.getMemberInfo() }
        binding.textAddWallet.setOnClickListener { showAddChild() }
        binding.textMissionAdd.setOnClickListener { Toast.makeText(requireContext(), "Coming soon", Toast.LENGTH_SHORT).show() }
        walletAdapter = WalletAdapter { holder, i ->
            p.handleWalletHolder(holder, i)
            holder.textAddCash.setOnClickListener { p.getChildNameForCash(i) }
            holder.textAddTime.setOnClickListener { p.getChildNameForTime(i) }
            holder.textShowCash.setOnClickListener { p.handleShowCash(i) }
            holder.textShowTime.setOnClickListener { p.handleShowTime(i) }
        }
        binding.recyclerWallet.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerWallet.adapter = walletAdapter
        //初始化menu
        binding.toolbar.inflateMenu(R.menu.menu)
        binding.toolbar.menu.findItem(R.id.menu_item_night).isChecked = check
        binding.toolbar.setOnMenuItemClickListener { p.handleMenuClick(it) }

        p.initDayNightMode()
        p.getData()
    }

    private fun showAddChild() {
        var editId = ""
        val view = DialogAddChildBinding.inflate(LayoutInflater.from(requireContext()))
        view.editChild.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                editId = s.toString()
            }
        })
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(R.string.creator_wallet_add)
            .setView(view.root)
            .setPositiveButton(R.string.dialog_send) { dialog, _ ->
                p.handleAddWallet(editId)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.dialog_cancel) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}