package com.thuatnguyen.tindersample.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.thuatnguyen.tindersample.R
import com.thuatnguyen.tindersample.databinding.ItemUserBinding
import com.thuatnguyen.tindersample.model.User
import com.thuatnguyen.tindersample.util.toDate

class UserCardAdapter : RecyclerView.Adapter<UserCardAdapter.UserViewHolder>() {
    private var userList = emptyList<User>()

    fun addUserList(data: List<User>) {
        userList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_user, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User) {
            binding.apply {
                user = item
                setDefaultContent(item)
                executePendingBindings()
                listOf(imgName, imgMail, imgBirthday, imgAddress, imgPhone, imgPassword)
                    .forEach {
                        it.setOnClickListener {
                            setTitleAndContent(it, item)
                        }
                    }
            }
        }

        private fun setDefaultContent(item: User) {
            setTitleAndContent(binding.imgName, item)
        }

        private fun setTitleAndContent(view: View, user: User) {
            binding.apply {
                selectedId = view.id
                val (title, content) = when (view.id) {
                    imgName.id -> "Hi, My name is" to user.name.getFullName()
                    imgMail.id -> "My email address is" to user.email
                    imgBirthday.id -> "My birthday is" to user.dob.toDate()
                    imgAddress.id -> "My address is" to user.location.street
                    imgPhone.id -> "My phone number is" to user.phone
                    imgPassword.id -> "My password is" to user.password
                    else -> "" to ""
                }
                tvTitle.text = title
                tvContent.text = content
            }
        }
    }
}