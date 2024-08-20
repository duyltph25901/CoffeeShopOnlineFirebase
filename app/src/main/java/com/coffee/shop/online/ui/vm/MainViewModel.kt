package com.coffee.shop.online.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffee.shop.online.model.CategoryDTO
import com.coffee.shop.online.model.ItemsDTO
import com.coffee.shop.online.ui.MainActivity.Companion.FLAG_LOAD_POPULAR
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val _category = MutableLiveData<MutableList<CategoryDTO>>()
    private val _popular = MutableLiveData<MutableList<ItemsDTO>>()
    private val _offer = MutableLiveData<MutableList<ItemsDTO>>()

    val category: LiveData<MutableList<CategoryDTO>> = _category
    val popular: LiveData<MutableList<ItemsDTO>> = _popular
    val offer: LiveData<MutableList<ItemsDTO>> = _offer

    fun loadCategory() =
        viewModelScope.launch(Dispatchers.IO) {
            val ref = firebaseDatabase.getReference("Category")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val lists = mutableListOf<CategoryDTO>()

                    for (childSnapshot in snapshot.children) {
                        val list = childSnapshot.getValue(CategoryDTO::class.java)
                        if (list != null) {
                            lists.add(list)
                        }
                    }

                    _category.postValue(lists)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

    fun loadPopularOrOffer(flag: Int) = viewModelScope.launch(Dispatchers.IO) {
        val ref = firebaseDatabase.getReference(
            when (flag) {
                FLAG_LOAD_POPULAR -> "Items"
                else -> "Offers"
            }
        )
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemsDTO>()

                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(ItemsDTO::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }

                when (flag) {
                    FLAG_LOAD_POPULAR -> _popular.postValue(lists)
                    else -> _offer.postValue(lists)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


}