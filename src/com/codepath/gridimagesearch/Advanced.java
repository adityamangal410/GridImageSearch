package com.codepath.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

public class Advanced extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advanced);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.advanced, menu);
		return true;
	}

	public void onAdvancedSearch(View v) {
		EditText etSiteFilter = (EditText)findViewById(R.id.etSiteFilter);
		AutoCompleteTextView actvImageSize = (AutoCompleteTextView)findViewById(R.id.actvImageSize);
		AutoCompleteTextView actvImageType = (AutoCompleteTextView)findViewById(R.id.actvImageType);
		AutoCompleteTextView actvColorFilter = (AutoCompleteTextView)findViewById(R.id.actvColorFilter);
		
		String site = etSiteFilter.getText().toString();
		String size = actvImageSize.getText().toString();
		String type = actvImageType.getText().toString();
		String color = actvColorFilter.getText().toString();
		
		Intent i = new Intent();
		i.putExtra("site", site);
		i.putExtra("size", size);
		i.putExtra("type", type);
		i.putExtra("color", color);
		setResult(RESULT_OK, i);
		  finish();
	}
}
