package kr.co.befamily.bemily.frgment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.befamily.bemily.adpater.UsersRecyclerViewMainAdapter
import kr.co.befamily.bemily.databinding.FragmentUsersListBinding
import kr.co.befamily.bemily.db.UsersDatabase
import kr.co.befamily.bemily.db.entity.UsersEntity
import kr.co.befamily.bemily.network.RetrofitInstance
import kr.co.befamily.bemily.network.api.UsersApi
import kr.co.befamily.bemily.network.vo.UsersGroupVo
import kr.co.befamily.bemily.repository.UsersRepository
import kr.co.befamily.bemily.viewmodel.UsersViewModel
import kr.co.befamily.bemily.viewmodel.UsersViewModelFactory

class UsersListFragment : Fragment() {
    private lateinit var binding: FragmentUsersListBinding
    private lateinit var usersViewModel: UsersViewModel
    private lateinit var usersRecyclerViewMainAdapter: UsersRecyclerViewMainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val usersApi = RetrofitInstance.getRetrofitInstance().create(UsersApi::class.java)
        val usersDao = UsersDatabase.getInstance(requireActivity()).usersDao
        val usersRepository = UsersRepository(usersApi, usersDao)
        val usersViewModelFactory = UsersViewModelFactory(usersRepository)
        usersViewModel = ViewModelProvider(requireActivity(), usersViewModelFactory)[UsersViewModel::class.java]
        usersRecyclerViewMainAdapter = UsersRecyclerViewMainAdapter(requireActivity(), usersDao)
        binding = FragmentUsersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usersViewModel.reqUsersList("android")
        usersViewModel.usersLiveData.observe(viewLifecycleOwner, Observer {
            val usersGroupList = mutableListOf<UsersGroupVo>()
            val usersFavoriteGroupList = mutableListOf<UsersEntity>()
            val usersHeaderGroupList = mutableListOf<UsersEntity>()

            it.forEach { usersEntity ->
                if (usersEntity.is_like) {
                    usersFavoriteGroupList.add(usersEntity)
                } else {
                    usersHeaderGroupList.add(usersEntity)
                }
            }

            if (usersFavoriteGroupList.size > 0) {
                usersGroupList.add(UsersGroupVo("Favorite", usersFavoriteGroupList))
            }

            if (usersHeaderGroupList.size > 0) {
                usersGroupList.add(UsersGroupVo("Normal", usersHeaderGroupList))
            }

            usersRecyclerViewMainAdapter.submitList(usersGroupList)
        })

        binding.recyclerView.adapter = usersRecyclerViewMainAdapter
        val linearLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
    }
}