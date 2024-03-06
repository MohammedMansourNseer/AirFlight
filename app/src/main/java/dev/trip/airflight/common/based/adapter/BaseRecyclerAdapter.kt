package dev.trip.airflight.common.based.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.util.*

abstract class BaseRecyclerAdapter<V : ViewBinding, D : Any?>(var list: ArrayList<D> = arrayListOf()) :
    RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder<V, D>>() {

      var positionSelect = -1

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<D>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }


    fun updateDataItem(position: Int, d: D) {
        if (list.isNotEmpty() && position > -1 && list.size > position) {
            list.set(position, d)
            notifyItemChanged(position)
        }
    }

    fun removeDataItem(position: Int) {
        if (list.isNotEmpty() && position > -1 && list.size > position) {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun updateDataItem(position: Int, d: D,callBackList:(List<D>)->Unit) {
        if (list.isNotEmpty() && position > -1&& list.size > position) {
            list.set(position, d)
            callBackList(list)
            notifyItemChanged(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAllDataItem(position: Int, d: D) {
        if (list.isNotEmpty() && position > -1&& list.size > position) {
            list.set(position, d)
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAllDataItem(blockOfCode: (ArrayList<D>) -> List<D>) {
        if (list.isNotEmpty()) {
            list = blockOfCode(list) as ArrayList<D>
            notifyDataSetChanged()
        }
    }

    fun getPosition(predicate: (Int, List<D>) -> Boolean): Int {
        for (i in list.indices) {
            if (predicate(i, list)) {
                return i
            }
        }
        return -1
    }

    fun RecyclerView.ViewHolder.isArabic(): Boolean {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            itemView.context.resources.configuration.locales.get(0).equals(Locale("ar"))
        } else {
            itemView.context.resources.configuration.locale.equals(Locale("ar"))
        }
    }

    fun RecyclerView.ViewHolder.context(): Context {
        return itemView.context
    }

    fun RecyclerView.ViewHolder.resources(): Resources {
        return itemView.context.resources
    }

    fun removeItem(position: Int) {
        if (list.isNotEmpty() && list.size > position) {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData() {
        this.list.clear()
        notifyDataSetChanged()
    }

    abstract fun bindObject(obj: D, v: V, holder: RecyclerView.ViewHolder)

    class BaseViewHolder<T : ViewBinding, D : Any?>(private val binding: T) : RecyclerView.ViewHolder(binding.root) {
        private var list: List<D>? = null
        fun onBind(position: Int, bind: (D, T) -> Unit) {
            list?.apply {
                val d: D = this[position]
                bind(d, binding)
            }
        }

        fun data(list: List<D>) {
            this.list = list
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<V, D>, position: Int) {
        holder.data(list)
        holder.onBind(position) { d, v ->
            bindObject(d, v, holder)
        }
    }

    override fun getItemCount(): Int = list.size
}

