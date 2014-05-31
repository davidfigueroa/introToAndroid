package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ToDoActivity extends Activity {
	private final static int EDIT_TODO_REQUEST = 20;
	private final static String FILE_NAME = "todo.txt";
	
	ArrayAdapter<String> todoListAdapter;
	ArrayList<String> todoListData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do);
		
		readTodoListData();
		ListView todoList = (ListView) findViewById(R.id.todoList);
		todoListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoListData);
		todoList.setAdapter(todoListAdapter);
		setupToDoListLongClickListener(todoList);
		setupToDoListClickListener(todoList);
	}

	public void onAddToDoClick(View v) {
		EditText newToDo = (EditText) findViewById(R.id.newToDo);
		String newToDoItem = newToDo.getText().toString();
		todoListAdapter.add(newToDoItem);
		newToDo.getText().clear();
		writeTodoListData();
	}
	
	private void setupToDoListLongClickListener(ListView todoList) {
		todoList.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				todoListData.remove(pos);
				todoListAdapter.notifyDataSetInvalidated();
				writeTodoListData();
				return true;
			}
		});
	}
	
	private void setupToDoListClickListener(ListView todoList) {
		todoList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				Intent i = new Intent(ToDoActivity.this, EditToDoActivity.class);
				i.putExtra(EditToDoActivity.DATA, todoListData.get(pos));
				i.putExtra(EditToDoActivity.INDEX, pos);
				startActivityForResult(i, EDIT_TODO_REQUEST);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == EDIT_TODO_REQUEST && resultCode == RESULT_OK) {
			String newToDoData = data.getExtras().getString(EditToDoActivity.DATA);
			int todoIndex = data.getExtras().getInt(EditToDoActivity.INDEX);
			todoListData.set(todoIndex, newToDoData);
			todoListAdapter.notifyDataSetChanged();
			writeTodoListData();
		}
	};
	
	private void readTodoListData() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, FILE_NAME);
		try {
			todoListData = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			todoListData = new ArrayList<String>();
			e.printStackTrace();
		}
	}

	private void writeTodoListData() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, FILE_NAME);
		try {
			FileUtils.writeLines(todoFile, todoListData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
