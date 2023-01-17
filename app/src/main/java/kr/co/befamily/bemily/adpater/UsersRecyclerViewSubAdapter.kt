package kr.co.befamily.bemily.adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.befamily.bemily.R
import kr.co.befamily.bemily.databinding.UsersListSubItemBinding
import kr.co.befamily.bemily.db.dao.UsersDao
import kr.co.befamily.bemily.db.entity.UsersEntity
import kr.co.befamily.bemily.frgment.UsersListFragmentDirections

class UsersRecyclerViewSubAdapter(private val context: Context, private val usersDao: UsersDao) : ListAdapter<UsersEntity, UsersRecyclerViewSubAdapter.UsersViewHolder>(UsersListSubDiffCallback()) {
    private lateinit var usersItemSelectEventListener: UsersItemSelectEventListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = UsersListSubItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        return holder.bind(getItem(position), position)
    }

    inner class UsersViewHolder(private val binding: UsersListSubItemBinding) : ViewHolder(binding.root) {
        private lateinit var usersEntity: UsersEntity

        init {
            binding.list.setOnClickListener {
                val action = UsersListFragmentDirections.actionUserListFragmentToUserDetailFragment(usersEntity, usersEntity.login)
                it.findNavController().navigate(action)
            }
            binding.isLike.setOnClickListener {
                if (!usersEntity.is_like) {
                    binding.isLike.setImageResource(R.drawable.baseline_favorite_true_24)
                } else {
                    binding.isLike.setImageResource(R.drawable.baseline_favorite_false_24)
                }

                usersItemSelectEventListener.usersItemSelect(usersEntity)

                CoroutineScope(Dispatchers.IO).launch {
                    usersDao.updateIsLike(!usersEntity.is_like, usersEntity.login)
                }
            }
        }

        fun bind(usersEntity: UsersEntity, position: Int) {
            this.usersEntity = usersEntity

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

class UsersListSubDiffCallback : DiffUtil.ItemCallback<UsersEntity>() {
    override fun areItemsTheSame(oldItem: UsersEntity, newItem: UsersEntity): Boolean {
        return oldItem.login == newItem.login
    }

    override fun areContentsTheSame(oldItem: UsersEntity, newItem: UsersEntity): Boolean {
        return oldItem == newItem
    }
}
