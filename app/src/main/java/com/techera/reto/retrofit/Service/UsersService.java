package com.techera.reto.retrofit.Service;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.techera.reto.PostsActivity;
import com.techera.reto.R;
import com.techera.reto.retrofit.Interface.UsersInterface;
import com.techera.reto.retrofit.Model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersService {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    UsersInterface usersInterface = retrofit.create(UsersInterface.class);

    public void getListUsers(View view) {
        Call<List<Users>> call = usersInterface.getUsers();
        TextView tvResponse = (TextView) view;

        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if (!response.isSuccessful()) {
                    tvResponse.setText("Codigo: " + response.code());
                    return;
                }
                List<Users> usersList = response.body();

                for (Users users: usersList) {
                    String content = "";
                    content += "id: " + users.getId() + "\n";
                    content += "userId: " + users.getName() + "\n";
                    content += "title: " + users.getUsername() + "\n";
                    content += "body: " + users.getEmail() + "\n\n";

                    tvResponse.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                return;
            }
        });
    }

    public void loadUsersItems(AutoCompleteTextView iUsers) {
        Call<List<Users>> call = usersInterface.getUsers();

        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<Users> usersList = response.body();
                String[] type = new String[usersList.size()+1];
                type[0] = "Todos los Usuarios";
                for (int i = 1; i < type.length; i++) {
                    type[i] = usersList.get(i-1).getId() + " - " + usersList.get(i-1).getUsername();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(iUsers.getContext(),  R.layout.drop_down_item,type);
                iUsers.setAdapter(adapter);
                iUsers.setText(adapter.getItem(0), false);
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                return;
            }
        });
    }
}
