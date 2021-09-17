package mysoftstudio.cashandtime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mysoftstudio.cashandtime.databinding.HolderItemBinding

class DataListAdapter(private val listener: (HolderItemBinding, Int) -> Unit) : RecyclerView.Adapter<DataListAdapter.ListHolder>() {
    private var size = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val view = HolderItemBinding.inflate(LayoutInflater.from(parent.context))
        return ListHolder(view)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) { holder.bind(listener, position) }

    override fun getItemCount(): Int = size

    fun refresh(size: Int) {
        this.size = size
        notifyDataSetChanged()
    }

    class ListHolder(private val itemHolder: HolderItemBinding) : RecyclerView.ViewHolder(itemHolder.root) {
        fun bind(listener: (HolderItemBinding, Int) -> Unit, position: Int) {
            listener(itemHolder, position)
        }
    }
}