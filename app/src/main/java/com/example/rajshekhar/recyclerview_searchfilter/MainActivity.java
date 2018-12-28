package com.example.rajshekhar.recyclerview_searchfilter;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rajshekhar.recyclerview_searchfilter.bean.Movie;
import com.example.rajshekhar.recyclerview_searchfilter.http.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private List<Movie> movieList;
    private RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        movieData();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter = new MovieAdapter();
        recyclerView.setAdapter(movieAdapter);
        movieList = new ArrayList<>();


    }

    private void movieData() {
        ApiClient.getInstance().getMovie().enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                movieList = response.body();
                String[] heroes = new String[movieList.size()];
                movieAdapter.setMovieList(getApplicationContext(), movieList);

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < movieList.size(); i++) {
                    heroes[i] = movieList.get(i).getTitle();
                    Log.e("LISTING", movieList.get(i).getTitle());
                }


            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.e("TAG", "Response = " + t.toString());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                movieAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                movieAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.action_search){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        if(!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}