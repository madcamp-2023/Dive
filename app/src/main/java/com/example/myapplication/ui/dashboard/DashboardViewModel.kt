package com.example.myapplication.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class DashboardViewModel : ViewModel() {

    val json = """
        [
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/87d6fa16-15ac-42bc-bb32-94ce42b5f3cf";
            "name": "sihyun";
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/84761b34-84bb-42c0-bf66-a506498ef395",
            "name": "정해준",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/2c007cfc-65f7-476a-9951-472a8b4c83ec",
            "name": "권혁원",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/1537e3f1-f120-41bb-9b0e-26d149de8bd1",
            "name": "공영재",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/0532c18e-854f-49bb-b816-2f8ca4f50f25",
            "name": "박정민",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/60570a7c-1f2f-4a88-adbd-e26b39a136cb",
            "name": "전진우",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/df06aeef-c18f-4f12-aa06-19f60f7ce770",
            "name": "서재원",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/87df0154-1dc8-4ef6-8397-d6c0db45f41d",
            "name": "이하늘",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/3795585e-8727-4418-b0f1-f322192c2f36",
            "name": "이수민",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/d4e4e44c-5bb1-4775-bc0f-19287d834b5a",
            "name": "이형진",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/87c8e185-c0f1-40f3-9f9f-bb2f1bb68f40",
            "name": "김가연",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/50fae077-3a19-4ee8-bba7-d03f372d34cc",
            "name": "조수연",
            "phone": "010-0000-0000"
        },
        {
            "photo": "(https://github.com/madcamp-2023/w1/assets/79096116/883ed4f7-516f-4340-ad09-af6cbca083eb",
            "name": "박현규",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/ec17620b-7daf-45e3-8afd-a748328d8ee9",
            "name": "정민서",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/60042ad9-b96b-44f1-8f6d-3d63710902ec",
            "name": "박진석",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/aa2a1cce-cbcb-4fd0-954c-d11e1addb582",
            "name": "박은수",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/771ed23f-076f-48b0-a9ba-6d6ce58a6ea2",
            "name": "김수환",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/a46c1fa3-178d-4562-973a-09e35a287ce0",
            "name": "정우현",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/9a38c86e-9747-47df-8989-6dc8098ee7d5",
            "name": "강민구",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/69c0eb9c-7ac4-4b9f-9ab9-468161616b0b",
            "name": "차지원",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/4ebd62a2-5745-4f0b-b503-c5946d7db599",
            "name": "김윤서",
            "phone": "010-0000-0000"
        }
    ]
""".trimIndent()
    private val _profileList = MutableLiveData<ArrayList<Profile>>()
    val profileList: LiveData<ArrayList<Profile>> get() = _profileList

    fun parseJson(jsonString: String?): List<Profile>? {
        return try {
            val gson = Gson()
            val profileListType = object : TypeToken<List<Profile>>() {}.type
            gson.fromJson(jsonString, profileListType)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            null
        }
    }

    /*
    fun readJsonFromAssets(context: Context, fileName: String): String? {
        return try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
    */
    /*
    fun appendJSONToAssets(context: Context, fileName: String, data: Bundle) {
        Log.e("DashboardFragment", "Inside appendJSONToAssets")
        context.openFileInput(fileName).use {
            val content = it.readBytes().toString(Charsets.UTF_8)

            // Remove the last character (assuming the file ends with a '}'; adjust as needed)
            val modifiedContent =
                if (content.isNotEmpty()) content.substring(0, content.length - 1) else content

            // Append new data
            val newData =
                ",{\"photo\": \"${data.getString("photo")}\", \"name\": \"${data.getString("name")}\", \"phone\": \"${
                    data.getString("phone")
                }\"}]"

            val appendedContent = "$modifiedContent$newData"

            // Write the modified and appended content back to the file
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use { outputStream ->
                outputStream.write(appendedContent.toByteArray())
            }
        }
    }
     */
    fun fetchProfileList() {
        viewModelScope.launch {
            _profileList.value = ArrayList(parseJson(json))
        }
    }

    fun updateProfileList(newList: ArrayList<Profile>) {
        _profileList.value = newList
    }

    fun updateProfileByIndex(index: Int, updatedProfile: Profile) {
        // Get the current list from MutableLiveData
        val currentList = _profileList.value ?: ArrayList()

        // Ensure the index is valid
        if (index in 0 until currentList.size) {
            // Update the item at the specified index
            currentList[index] = updatedProfile

            // Update MutableLiveData with the modified list
            _profileList.value = currentList
        }
    }
    fun deleteProfileByIndex(index: Int) {
        // Get the current list from MutableLiveData
        val currentList = _profileList.value ?: ArrayList()
        Log.e("ViewModel", "DELETED!!")

        // Ensure the index is valid
        if (index in 0 until currentList.size) {
            // Remove the item at the specified index
            currentList.removeAt(index)

            // Update MutableLiveData with the modified list
            _profileList.value = currentList
        }
    }

    fun addProfile(newProfile: Profile) {
        // Get the current list from MutableLiveData
        val currentList = _profileList.value ?: ArrayList()

        // Add the new item to the list
        currentList.add(newProfile)

        // Update MutableLiveData with the modified list
        _profileList.value = currentList
    }
}