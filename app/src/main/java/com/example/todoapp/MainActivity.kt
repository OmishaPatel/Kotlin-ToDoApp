package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.DividerItemDecoration
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
        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // remove item from the list
                listOfTasks.removeAt(position)
                // notify adapter of the change in data
                adapter.notifyDataSetChanged()
                saveItems()
            }

        }

        loadItems()
        // Look up recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        // Set up the button and input field so that user can add task
        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        // Set a reference for the button
        findViewById<Button>(R.id.button).setOnClickListener {
            // get the text from user's input field
            val userInputTask = inputTextField.text.toString()


            // add the text to list of tasks
            listOfTasks.add(userInputTask)
            // Notify data adapter
            adapter.notifyItemInserted(listOfTasks.size -1)


            // Reset the textfield for user's input
            inputTextField.setText("")
            saveItems()
        }

    }


    // Save user's data
    // Save data by writing and reading from a file

    // Create a method to get the file we need
    fun getDataFile() : File {
        return File(filesDir, "data.txt")
    }
    // Load the items by reading every line from file
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }


    }
    // Save items by writing into our file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }

    }

}