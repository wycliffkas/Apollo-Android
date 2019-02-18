package com.example.javadevelopersnairobi.apollo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
	private String title_response,title2_response,
			title3_response,title4_response,description_response,
			description2_response,description3_response,description4_response;
	private TextView title,description,title2,description2,title3,description3,description4,title4;



	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		title = findViewById(R.id.text_view_title);
		description = findViewById(R.id.text_view_description);
		title2 = findViewById(R.id.text_view_title2);
		description2 = findViewById(R.id.text_view_description2);
		title3 = findViewById(R.id.text_view_title3);
		description3 = findViewById(R.id.text_view_description3);
		description4 = findViewById(R.id.text_view_description4);
		title4 = findViewById(R.id.text_view_title4);

		getPosts();
	}

	private void getPosts(){

		MyApolloClient.getMyApolloClient().query(
			AllPostsQuery.builder().build()).enqueue(new ApolloCall.Callback<AllPostsQuery.Data>() {

			@Override
			public void onResponse(@NotNull Response<AllPostsQuery.Data> response) {

				title_response = response.data().allPosts.get(0).title();
				title2_response = response.data().allPosts.get(1).title();
				title3_response = response.data().allPosts.get(2).title();
				title4_response = response.data().allPosts.get(3).title();

				description_response = response.data().allPosts.get(0).description();
				description2_response = response.data().allPosts.get(1).description();
				description3_response = response.data().allPosts.get(2).description();
				description4_response = response.data().allPosts.get(3).description();

				MainActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						title.setText(title_response);
						title2.setText(title2_response);
						title3.setText(title3_response);
						title4.setText(title4_response);

						description.setText(description_response);
						description2.setText(description2_response);
						description3.setText(description3_response);
						description4.setText(description4_response);
					}
				});
			}

			@Override
			public void onFailure(@NotNull ApolloException e) {

			}
		});
	}

	public void submitPost(View v){
		EditText mtitle = findViewById(R.id.editText_title);
		mtitle.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
		EditText mdescription = findViewById(R.id.editText_description);
		mdescription.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

		MyApolloClient.getMyApolloClient().mutate(
				NewPostMutation.builder()
				.title(mtitle.getText().toString())
				.description(mdescription.getText().toString())
				.build()
		).enqueue(new ApolloCall.Callback<NewPostMutation.Data>() {
			@Override
			public void onResponse(@NotNull Response<NewPostMutation.Data> response) {
				MainActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(MainActivity.this, "Successfully Added ", Toast.LENGTH_SHORT).show();
					}
				});
			}

			@Override
			public void onFailure(@NotNull ApolloException e) {

			}
		});

	}
}
