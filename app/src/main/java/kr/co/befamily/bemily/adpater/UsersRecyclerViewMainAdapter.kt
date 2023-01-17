package kr.co.befamily.bemily.adpater

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.co.befamily.bemily.databinding.UsersListMainItemBinding
import kr.co.befamily.bemily.db.dao.UsersDao
import kr.co.befamily.bemily.db.entity.UsersEntity
import kr.co.befamily.bemily.network.vo.UsersGroupVo

class UsersRecyclerViewMainAdapter(private val context: Context, private val usersDao: UsersDao) : ListAdapter<UsersGroupVo, UsersRecyclerViewMainAdapter.UsersViewHolder>(UsersListMainDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = UsersListMainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

    inner class UsersViewHolder(private val binding: UsersListMainItemBinding) : ViewHolder(binding.root) {

        private var usersRecyclerViewSubAdapter = UsersRecyclerViewSubAdapter(context, usersDao)
        init {
            usersRecyclerViewSubAdapter.setUsetsItemSelectListener(object : UsersRecyclerViewSubAdapter.UsersItemSelectEventListener {
                override fun usersItemSelect(usersEntity: UsersEntity) {
                    Log.d("usersItemSelect", "$usersEntity")
                }
            })
        }

        fun bind(usersGroupVo: UsersGroupVo) {
            binding.header.text = usersGroupVo.groupName
            binding.recyclerView.adapter = usersRecyclerViewSubAdapter
            val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.recyclerView.layoutManager = linearLayoutManager
            usersRecyclerViewSubAdapter.submitList(usersGroupVo.groupList)
        }
    }
}

class UsersListMainDiffCallback : DiffUtil.ItemCallback<UsersGroupVo>() {
    override fun areItemsTheSame(oldItem: UsersGroupVo, newItem: UsersGroupVo): Boolean {
        return oldItem.groupList.size == newItem.groupList.size
    }

    override fun areContentsTheSame(oldItem: UsersGroupVo, newItem: UsersGroupVo): Boolean {
        return oldItem == newItem
    }
}
