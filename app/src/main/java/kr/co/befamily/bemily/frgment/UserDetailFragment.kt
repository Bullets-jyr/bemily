package kr.co.befamily.bemily.frgment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.befamily.bemily.R
import kr.co.befamily.bemily.databinding.FragmentUserDetailBinding
import kr.co.befamily.bemily.db.UsersDatabase
import kr.co.befamily.bemily.db.dao.UsersDao

class UserDetailFragment : Fragment() {
    private lateinit var binding: FragmentUserDetailBinding
    private val args: UserDetailFragmentArgs by navArgs()
    private lateinit var usersDao: UsersDao
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        usersDao = UsersDatabase.getInstance(requireActivity()).usersDao
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireActivity())
            .load(args.usersEntity.avatar_url)
            .into(binding.imageView)

        binding.login.text = args.usersEntity.login
        binding.htmlUrl.text = args.usersEntity.html_url

        if (args.usersEntity.is_like) {
            binding.isLike.setImageResource(R.drawable.baseline_favorite_true_24)
        } else {
            binding.isLike.setImageResource(R.drawable.baseline_favorite_false_24)
        }

        binding.isLike.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                usersDao.updateIsLike(!args.usersEntity.is_like, args.usersEntity.login)
            }

            if (!args.usersEntity.is_like) {
                binding.isLike.setImageResource(R.drawable.baseline_favorite_true_24)
            } else {
                binding.isLike.setImageResource(R.drawable.baseline_favorite_false_24)
            }
        }
    }
}