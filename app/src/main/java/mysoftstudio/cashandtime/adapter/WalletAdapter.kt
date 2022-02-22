package mysoftstudio.cashandtime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mysoftstudio.cashandtime.databinding.HolderWalletBinding

/**
 * 錢包列表Adapter
 * 利用listener將item holder與position傳到fragment去做處理
 * 這樣可以不用額外使用listener來監聽item的點擊等事件
 * 數據也不需要傳進來做處理，直接在fragment就可以處理了
 */

class WalletAdapter(private val listener: (HolderWalletBinding, Int) -> Unit) : RecyclerView.Adapter<WalletAdapter.WalletHolder>() {
    private var size = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletHolder {
        val view = HolderWalletBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WalletHolder(view)
    }

    override fun onBindViewHolder(holder: WalletHolder, position: Int) { holder.bind(listener, position) }

    override fun getItemCount(): Int = size

    fun refresh(size: Int) {
        this.size = size
        notifyDataSetChanged()
    }

    class WalletHolder(private val itemHolder: HolderWalletBinding) : RecyclerView.ViewHolder(itemHolder.root) {
        fun bind(listener: (HolderWalletBinding, Int) -> Unit, position: Int) {
            listener(itemHolder, position)
        }
    }
}