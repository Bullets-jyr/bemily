package kr.co.befamily.bemily.network.vo

import kr.co.befamily.bemily.db.entity.UsersEntity

data class UsersGroupVo(
    val groupName: String,
    val groupList: List<UsersEntity>
)
