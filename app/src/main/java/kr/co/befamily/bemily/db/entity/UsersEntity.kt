package kr.co.befamily.bemily.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "users")
data class UsersEntity(
    @PrimaryKey
    @ColumnInfo(name = "login")
    val login: String,
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "node_id")
    val node_id: String,
    @ColumnInfo(name = "avatar_url")
    val avatar_url: String,
    @ColumnInfo(name = "gravatar_id")
    val gravatar_id: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "html_url")
    val html_url: String,
    @ColumnInfo(name = "followers_url")
    val followers_url: String,
    @ColumnInfo(name = "following_url")
    val following_url: String,
    @ColumnInfo(name = "gists_url")
    val gists_url: String,
    @ColumnInfo(name = "starred_url")
    val starred_url: String,
    @ColumnInfo(name = "subscriptions_url")
    val subscriptions_url: String,
    @ColumnInfo(name = "organizations_url")
    val organizations_url: String,
    @ColumnInfo(name = "repos_url")
    val repos_url: String,
    @ColumnInfo(name = "events_url")
    val events_url: String,
    @ColumnInfo(name = "received_events_url")
    val received_events_url: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "site_admin")
    val site_admin: Boolean,
    @ColumnInfo(name = "score")
    val score: Double,
    @ColumnInfo(name = "is_like")
    val is_like: Boolean = false
) : Parcelable
