package mysoftstudio.cashandtime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mysoftstudio.cashandtime.databinding.HolderWalletBinding

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