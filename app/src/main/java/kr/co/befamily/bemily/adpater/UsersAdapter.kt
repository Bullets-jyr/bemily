package kr.co.befamily.bemily.adpater

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import kr.co.befamily.bemily.R
import kr.co.befamily.bemily.databinding.UsersListItemBinding
import kr.co.befamily.bemily.frgment.UserListFragmentDirections
import kr.co.befamily.bemily.network.vo.UsersItem

class UsersAdapter(private val context: Context) : ListAdapter<UsersItem, UsersAdapter.UsersViewHolder>(UsersListDiffCallback()) {
    private lateinit var usersItemSelectEventListener: UsersItemSelectEventListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = UsersListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        Log.e("onBindViewHolder", "$position")
        return holder.bind(getItem(position), position)
    }

    inner class UsersViewHolder(private val binding: UsersListItemBinding) : ViewHolder(binding.root) {
        private lateinit var usersItem: UsersItem

        init {
            binding.list.setOnClickListener {
                usersItemSelectEventListener.usersItemSelect(usersItem)
            }
        }

        fun bind(usersItem: UsersItem, position: Int) {
            this.usersItem = usersItem

            if (position == 0) binding.header.visibility = View.VISIBLE else binding.header.visibility = View.GONE

            Glide.with(context)
                .load(usersItem.avatarUrl)
                .into(binding.circleImageView)

            binding.login.text = usersItem.login
            binding.htmlUrl.text = usersItem.htmlUrl
        }
    }

    fun setUsetsItemSelectListener(usersItemSelectEventListener: UsersItemSelectEventListener) {
        this.usersItemSelectEventListener = usersItemSelectEventListener
    }

    interface UsersItemSelectEventListener {
        fun usersItemSelect(usersItem: UsersItem)
    }
}

class UsersListDiffCallback : DiffUtil.ItemCallback<UsersItem>() {
    override fun areItemsTheSame(oldItem: UsersItem, newItem: UsersItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UsersItem, newItem: UsersItem): Boolean {
        return oldItem == newItem
    }
}
