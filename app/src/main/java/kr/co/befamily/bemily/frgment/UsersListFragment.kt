package kr.co.befamily.bemily.frgment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.befamily.bemily.adpater.db.UsersRecyclerViewDbAdapter
import kr.co.befamily.bemily.databinding.FragmentUsersListBinding
import kr.co.befamily.bemily.db.UsersDatabase
import kr.co.befamily.bemily.db.entity.UsersEntity
import kr.co.befamily.bemily.network.RetrofitInstance
import kr.co.befamily.bemily.network.api.UsersApi
import kr.co.befamily.bemily.repository.UsersRepository
import kr.co.befamily.bemily.viewmodel.UsersViewModel
import kr.co.befamily.bemily.viewmodel.UsersViewModelFactory

class UsersListFragment : Fragment() {
    private lateinit var binding: FragmentUsersListBinding
    private lateinit var usersViewModel: UsersViewModel
    private lateinit var usersRecyclerViewDbAdapter: UsersRecyclerViewDbAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val usersApi = RetrofitInstance.getRetrofitInstance().create(UsersApi::class.java)
        val dao = UsersDatabase.getInstance(requireActivity()).usersDao
        val usersRepository = UsersRepository(usersApi, dao)
        val usersViewModelFactory = UsersViewModelFactory(usersRepository)
        usersViewModel = ViewModelProvider(requireActivity(), usersViewModelFactory)[UsersViewModel::class.java]
        usersRecyclerViewDbAdapter = UsersRecyclerViewDbAdapter(requireActivity())
        binding = FragmentUsersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usersViewModel.reqUsersList("android")
        usersViewModel.usersListLiveData.observe(viewLifecycleOwner, Observer {
//            usersRecyclerViewAdapter.submitList(it)
        })

        usersViewModel.usersLiveData.observe(viewLifecycleOwner, Observer {
            usersRecyclerViewDbAdapter.submitList(it)
        })

        binding.recyclerView.adapter = usersRecyclerViewDbAdapter
        usersRecyclerViewDbAdapter.setUsetsItemSelectListener(object : UsersRecyclerViewDbAdapter.UsersItemSelectEventListener {
            override fun usersItemSelect(usersEntity: UsersEntity) {
                Log.e("usersItemSelect", "$usersEntity")
            }
        })

        val linearLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        val decoration = DividerItemDecoration(requireActivity(), linearLayoutManager.orientation)
        binding.recyclerView.addItemDecoration(decoration)
    }
}