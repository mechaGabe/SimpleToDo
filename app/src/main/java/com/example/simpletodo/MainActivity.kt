package com.example.simpletodo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //remove item from list and notify adapter
                listOfTasks.removeAt(position)
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }


        //1. detect when user clicks on add button

//        findViewById<Button>(R.id.button).setOnClickListener{
//            //code here executes when user clicks button
//            val tag
//            Log.i(tag: "Caren", msg: "User clicked on button") //tag and msg done wrong see taskitemadapter
//            
//        }

        loadItems()

        //look up recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //set up button and input field so user can enter task and append to list
        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        //grab reference to button and set onclicklistener
        findViewById<Button>(R.id.button).setOnClickListener{
            val userInputtedTask = findViewById<EditText>(R.id.addTaskField).text.toString() //get text that user has inputted into taskfield
            listOfTasks.add(userInputtedTask) //add string to list of tasks
            adapter.notifyItemInserted(listOfTasks.size - 1 ) //notify adapter that data has been updates
            inputTextField.setText("")

            saveItems()
        }
    }

    fun getDataFile():File{ //get file
        //save individual items as tasks
        return File(filesDir, "userData.txt")
    }

    fun loadItems(){
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }

    fun saveItems(){
        try{
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }

    }



}



