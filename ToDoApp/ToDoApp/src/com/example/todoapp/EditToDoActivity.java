package com.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditToDoActivity extends Activity {
	final static String DATA = "Data";
	final static String INDEX = "Index";
	
	EditText todo; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_to_do);
		todo = (EditText) findViewById(R.id.editTodo);
		String todoData = getIntent().getStringExtra(DATA);
		todo.setText(todoData);
	}

	public void onSaveClicked(View v) {
		Intent i = new Intent();
		i.putExtra(DATA, todo.getText().toString());
		i.putExtra(INDEX, getIntent().getIntExtra(INDEX, 0));
		setResult(RESULT_OK, i);
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_to_do, menu);
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
