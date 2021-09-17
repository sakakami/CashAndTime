package mysoftstudio.cashandtime.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import mysoftstudio.cashandtime.R
import mysoftstudio.cashandtime.databinding.FragmentHomeVBinding
import mysoftstudio.cashandtime.presenter.HomeP
import mysoftstudio.cashandtime.view.vi.HomeVI

/**
 * Created by Joshua on 2021.08.01
 * 創建帳號或者繼承帳號，再依據帳號的分類前往特定的頁面
 */
class HomeV : Fragment(), HomeVI {
    private val ERROR_MSG = "HomeV"
    private var _binding: FragmentHomeVBinding? = null
    private val binding get() = _binding!!
    private val p by lazy { HomeP(this) }
    private var isFirst = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeVBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun toChildPage() { findNavController().navigate(R.id.action_homeV_to_childMainV) }

    override fun toCreatorPage() { findNavController().navigate(R.id.action_homeV_to_creatorMainV) }

    override fun cleanEditText() { binding.editType.setText("") }

    override fun showMessage(string: String) {
        Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT).show()
    }

    private fun init() {
        if (isFirst) {
            var isCreate = true
            var name = ""
            var isParent = true
            binding.radioGroupMode.check(binding.radioCreate.id)
            binding.radioGroupMode.setOnCheckedChangeListener { _, checkedId ->
                isCreate = checkedId == binding.radioCreate.id
                if (checkedId == binding.radioCreate.id) {
                    binding.textType.visibility = View.VISIBLE
                    binding.radioGroupType.visibility = View.VISIBLE
                    val title = requireContext().getString(R.string.home_name_title)
                    binding.textView.text = title
                } else {
                    binding.textType.visibility = View.INVISIBLE
                    binding.radioGroupType.visibility = View.INVISIBLE
                    val title = requireContext().getString(R.string.home_inherit_title)
                    binding.textView.text = title
                }
            }
            binding.editType.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) { name = s.toString() }
            })
            binding.radioGroupType.check(binding.radioParent.id)
            binding.radioGroupType.setOnCheckedChangeListener { _, checkedId -> isParent = checkedId == binding.radioParent.id }
            binding.textCheck.setOnClickListener { p.handleSendData(isCreate, name, isParent) }
            isFirst = false
            p.checkData()
        } else requireActivity().finish()
    }
}