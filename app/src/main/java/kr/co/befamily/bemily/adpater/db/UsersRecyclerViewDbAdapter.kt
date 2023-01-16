package kr.co.befamily.bemily.adpater.db

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kr.co.befamily.bemily.R
import kr.co.befamily.bemily.databinding.UsersListItemBinding
import kr.co.befamily.bemily.db.dao.UsersDao
import kr.co.befamily.bemily.db.entity.UsersEntity
import kr.co.befamily.bemily.frgment.UsersListFragmentDirections
import kr.co.befamily.bemily.network.vo.UsersItemVo

class UsersRecyclerViewDbAdapter(private val context: Context, private val usersDao: UsersDao) : ListAdapter<UsersEntity, UsersRecyclerViewDbAdapter.UsersViewHolder>(UsersListDiffCallback()) {
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
        private lateinit var usersEntity: UsersEntity

        init {
            binding.list.setOnClickListener {
                usersItemSelectEventListener.usersItemSelect(usersEntity)
                val action = UsersListFragmentDirections.actionUserListFragmentToUserDetailFragment(usersEntity, usersEntity.login)
                it.findNavController().navigate(action)
            }
            binding.isLike.setOnClickListener {
                GlobalScope.launch {
                    usersDao.updateIsLike(!usersEntity.is_like, usersEntity.login)
                }
                if (!usersEntity.is_like) {
                    binding.isLike.setImageResource(R.drawable.baseline_favorite_true_24)
                } else {
                    binding.isLike.setImageResource(R.drawable.baseline_favorite_false_24)
                }
            }
        }

        fun bind(usersEntity: UsersEntity, position: Int) {
            this.usersEntity = usersEntity

            if (position == 0) binding.header.visibility = View.VISIBLE else binding.header.visibility = View.GONE

            Glide.with(context)
                .load(usersEntity.avatar_url)
                .into(binding.circleImageView)

            binding.login.text = usersEntity.login
            binding.htmlUrl.text = usersEntity.html_url

            if (usersEntity.is_like) {
                binding.isLike.setImageResource(R.drawable.baseline_favorite_true_24)
            } else {
                binding.isLike.setImageResource(R.drawable.baseline_favorite_false_24)
            }
        }
    }

    fun setUsetsItemSelectListener(usersItemSelectEventListener: UsersItemSelectEventListener) {
        this.usersItemSelectEventListener = usersItemSelectEventListener
    }

    interface UsersItemSelectEventListener {
        fun usersItemSelect(usersEntity: UsersEntity)
    }
}

class UsersListDiffCallback : DiffUtil.ItemCallback<UsersEntity>() {
    override fun areItemsTheSame(oldItem: UsersEntity, newItem: UsersEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UsersEntity, newItem: UsersEntity): Boolean {
        return oldItem == newItem
    }
}
