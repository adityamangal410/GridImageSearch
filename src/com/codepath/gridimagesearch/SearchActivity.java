package com.codepath.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	final static int GET_RESULT_TEXT = 0;
	String Site="";
	String ImageType="";
	String ImageSize="";
	String Color="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position, long rowId) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	public void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch = (Button) findViewById(R.id.btnSearch);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	  switch (item.getItemId()) {
	  case R.id.advanced:
		// In Activity
		  startActivityForResult(
		          new Intent(this, Advanced.class), GET_RESULT_TEXT);
	    break;
	  default:
	    break;
	  }
	  return true;
	}
	
	// Handle the result once the activity returns a result, display contact
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == 0) {
	      if (resultCode == RESULT_OK) {
	    	  Site = data.getStringExtra("site");
	    	  ImageSize = data.getStringExtra("size");
	    	  ImageType = data.getStringExtra("type");
	    	  Color = data.getStringExtra("color");
	      }
	    }
	  }
	
	public void onImageSearch(View v) {
		String query = etQuery.getText().toString();
		Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT).show();
		AsyncHttpClient client = new AsyncHttpClient();
		// https://ajax.googleapis.com/ajax/services/search/images?q=Android&v=1.0
		String url = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&start=0&v=1.0";
		
		if(!Site.isEmpty()){
			url = url+"&as_sitesearch="+Uri.encode(Site);
		}
		if(!ImageSize.isEmpty()){
			url = url+"&imgsz="+Uri.encode(ImageSize);
		}
		if(!ImageType.isEmpty()){
			url = url+"&imgtype="+Uri.encode(ImageType);
		}
		if(!Color.isEmpty()){
			url = url+"&imgcolor="+Uri.encode(Color);
		}
		
		url = url + "&q="+Uri.encode(query);
		Toast.makeText(this, url, Toast.LENGTH_LONG).show();
		
		client.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults = null;
				try {
					imageJsonResults = response.getJSONObject("responseData").getJSONArray(
							"results");
					imageResults.clear();
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
					Log.d("DEBUG", imageResults.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
