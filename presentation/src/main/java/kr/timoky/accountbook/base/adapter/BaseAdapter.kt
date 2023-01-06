package kr.timoky.accountbook.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import kr.timoky.domain.model.base.BaseModel
import java.lang.reflect.ParameterizedType

abstract class BaseAdapter<T : Any, VB : ViewBinding>(itemClick: ((T) -> Unit)? = null) :
    ListAdapter<T, BaseViewHolder>(
        object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                return (oldItem as BaseModel).key == (newItem as BaseModel).key
            }

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                return (oldItem as BaseModel)::class == (newItem as BaseModel)::class
            }
        }
    ) {

    protected val listener: AdapterItemListener?

    init {
        listener = itemClick?.let {
            object : AdapterItemListener {
                override fun onRootClick(arg: Any) {
                    itemClick.invoke(arg as T)
                }

            }
        } ?: kotlin.run {
            null
        }
    }

    abstract fun getHolder(): BaseViewHolder

    protected lateinit var binding: VB
    private val type = (javaClass.genericSuperclass as ParameterizedType)
    private val classVB = type.actualTypeArguments[1] as Class<VB>

    private val inflateMethod = classVB.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        binding =
            inflateMethod.invoke(null, LayoutInflater.from(parent.context), parent, false) as VB
        return getHolder()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }
}