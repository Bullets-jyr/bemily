package kr.co.befamily.bemily.frgment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import kr.co.befamily.bemily.R
import kr.co.befamily.bemily.databinding.FragmentUserDetailBinding

class UserDetailFragment : Fragment() {
    private lateinit var binding: FragmentUserDetailBinding
    private val args: UserDetailFragmentArgs by navArgs()
    private lateinit var callback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        Log.e("UserDetailFragment", "${args.usersItem}")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireActivity())
            .load(args.usersItem.avatarUrl)
            .into(binding.imageView)

        binding.login.text = args.usersItem.login
        binding.htmlUrl.text = args.usersItem.htmlUrl
    }
}