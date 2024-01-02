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
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/6596e100-ba83-4645-9a26-c807391289a2";
            "name": "sihyun";
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/8de896a4-d2e2-4a7c-93eb-23faae44c5d9",
            "name": "정해준",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/253398bb-a62b-4ec3-aa2a-57dbf9978d9b",
            "name": "권혁원",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/76166ca6-018f-4f93-90f2-78063aa28548",
            "name": "공영재",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/5ffa94af-d43f-4add-8c96-baa4f3b5c537",
            "name": "박정민",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/da8d84c9-0155-4ebd-b858-ce2c654163a2",
            "name": "전진우",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/85482a1e-51d9-49ec-8af8-990f816b1dbe",
            "name": "서재원",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/793a4a21-6d7a-470b-92b2-6a33312d888f",
            "name": "이하늘",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/286e4452-a230-49eb-af25-bae5b17c447a",
            "name": "이수민",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/33c70882-bfbe-4a1a-8873-131785d76c92",
            "name": "이형진",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/386b5045-5323-4878-8bfe-81b0e8f6eaad",
            "name": "김가연",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/08439684-5ed0-4ebb-92f6-fe3b06c3b22a",
            "name": "조수연",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/76acb803-4f08-46af-951d-0d749eb37025",
            "name": "박현규",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/ff94d570-fb38-44eb-9048-a266a82e399c",
            "name": "정민서",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/66c90fdf-f206-4e66-a35b-d1af3bb09fa2",
            "name": "박진석",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/4193915d-6f31-43e9-9b75-0140982ef966",
            "name": "박은수",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/749b94c8-dbf5-4685-b739-266a095ce03c",
            "name": "김수환",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/924b49de-9f06-4a67-8d5f-4ddcaebb9180",
            "name": "정우현",
            "phone": "010-0000-0000"
        },
        {
            "photo": "https://github.com/madcamp-2023/w1/assets/79096116/890ac262-35e8-4378-8dde-8ccf491edbce",
            "name": "강민구",
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