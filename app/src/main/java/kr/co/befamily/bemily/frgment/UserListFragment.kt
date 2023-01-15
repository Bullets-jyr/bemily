package kr.co.befamily.bemily.frgment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.befamily.bemily.adpater.UsersAdapter
import kr.co.befamily.bemily.databinding.FragmentUserListBinding
import kr.co.befamily.bemily.network.vo.UsersItem
import kr.co.befamily.bemily.viewmodel.UsersViewModel

class UserListFragment : Fragment() {
    private lateinit var binding: FragmentUserListBinding
    private lateinit var usersViewModel: UsersViewModel
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        usersViewModel = ViewModelProvider(requireActivity())[UsersViewModel::class.java]
        usersAdapter = UsersAdapter(requireActivity())
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usersViewModel.reqUsersList("android")
        usersViewModel.usersListLiveData.observe(viewLifecycleOwner, Observer {
            usersAdapter.submitList(it)
        })
        binding.recyclerView.adapter = usersAdapter
        usersAdapter.setUsetsItemSelectListener(object : UsersAdapter.UsersItemSelectEventListener {
            override fun usersItemSelect(usersItem: UsersItem) {
                Log.e("usersItemSelect", "$usersItem")
            }
        })

        val linearLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        val decoration = DividerItemDecoration(requireActivity(), linearLayoutManager.orientation)
        binding.recyclerView.addItemDecoration(decoration)
    }
}