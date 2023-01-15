package com.techera.reto.retrofit.Service;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.techera.reto.retrofit.Interface.PostsInterface;
import com.techera.reto.retrofit.Model.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostsService {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    PostsInterface postsInterface = retrofit.create(PostsInterface.class);

    public void getListPostsByUser(Integer userId, ListView lvPosts) {
        Call<List<Posts>> call;
        call = (userId != null)?  postsInterface.getPostsByUserId(userId): postsInterface.getPosts();

        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<Posts> postsList = response.body();
                ArrayAdapter<Posts> adapter = new ArrayAdapter<Posts>(lvPosts.getContext(), android.R.layout.simple_list_item_1, postsList);
                lvPosts.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                return;
            }
        });
    }

    public void savePost(Posts post, View view) {
        Call<Posts> call;
        call = (post.getId() == null)? postsInterface.savePosts(post) : postsInterface.updatePosts(post.getId(), post);

        call.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(view.getContext(), "No se pudo guardar.", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(view.getContext(), "Post "+((post.getId() == null)? "Registrado":"Editado") + ".", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
        });
    }

    public void deletePost(Posts post, View view) {
        Call<Void> call = postsInterface.deletePosts(post.getId());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(view.getContext(), "No se pudo eliminar.", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(view.getContext(), "Post Eliminado.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
        });
    }
}
