package com.omniwyse.firdous.view.users

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.omniwyse.firdous.R
import com.omniwyse.firdous.model.UserModel
import com.omniwyse.firdous.util.*
import com.omniwyse.firdous.view.NavigationManager
import com.omniwyse.firdous.view.details.UserDetailsActivity
import kotlinx.android.synthetic.main.item_user_grid.view.*
import kotlinx.android.synthetic.main.item_user_list.view.*

class UserAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var userList: ArrayList<UserModel> = ArrayList()
    private var isLoadingAdded: Boolean = false
    private var viewType = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        var viewHolder: RecyclerView.ViewHolder? = null
        when (viewType) {
            LIST_VIEW -> {
                val holder =
                    inflater.inflate(R.layout.item_user_list, parent, false)
                   viewHolder = UserListViewHolder(holder)
            }

            GRID_VIEW -> {
                val holder =
                    inflater.inflate(R.layout.item_user_grid, parent, false)
                viewHolder = UserGridViewHolder(holder)
            }

            LOADING -> {
                val loadingViewHolder = inflater.inflate(R.layout.item_progress, parent, false)
                viewHolder = LoadingViewHolder(loadingViewHolder)
            }
            else -> {
                Logger.debug("Nothing")
            }
        }
        return viewHolder!!

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            LIST_VIEW -> if (0 <= position && position < userList.size && holder is UserListViewHolder) {
                holder.setView(userList[position])
            }
            GRID_VIEW -> if (0 <= position && position < userList.size && holder is UserGridViewHolder) {
                holder.setView(userList[position])
            }
            LOADING -> {
                Logger.debug("None")
            }
            else -> {
                Logger.debug("None")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val type : Int = if(viewType != LIST_VIEW && viewType != GRID_VIEW){
            LIST_VIEW
        }else{
            viewType
        }
        return if (position == userList.size - 1 && isLoadingAdded) LOADING else type
    }

    fun setViewType(viewType: Int) {
        this.viewType = viewType
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(UserModel())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position = userList.size - 1
        val item = getItem(position)

        if (item != null) {
            userList.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    private fun add(userModel: UserModel) {
        userList.add(userModel)
        notifyItemInserted(userList.size - 1)
    }

    private fun getItem(position: Int): UserModel? {
        return userList[position]
    }

    inner class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivImage: AppCompatImageView = itemView.ivImage
        private val tvName: AppCompatTextView = itemView.tvName

        init {
            itemView.setOnClickListener {
                gotoDetailsScreen(adapterPosition)
            }
        }



        fun setView(userModel: UserModel) {
            userModel.avatarUrl?.let { ivImage.loadImageFromUrl(context, it) }
            tvName.text = userModel.login
        }

    }

    inner class UserGridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivImage: AppCompatImageView = itemView.ivImageGrid
        private val tvName: AppCompatTextView = itemView.tvNameGrid

        init {
            itemView.setOnClickListener {
                gotoDetailsScreen(adapterPosition)
            }
        }

        fun setView(userModel: UserModel) {
            userModel.avatarUrl?.let { ivImage.loadImageFromUrl(context, it) }
            tvName.text = userModel.login
        }
    }
    private fun gotoDetailsScreen(position: Int) {
        val bundle = Bundle()
        bundle.putParcelable(USER_MODEL, userList[position])
        NavigationManager.openClass(context, UserDetailsActivity::class.java, bundle)
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun addAll(imageList: List<UserModel>) {
        userList.addAll(imageList)
        notifyDataSetChanged()
    }

    fun deleteAll() {
        userList.clear()
        notifyDataSetChanged()
    }

}