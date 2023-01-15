package com.techera.reto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.textfield.TextInputLayout;
import com.techera.reto.retrofit.Model.Posts;
import com.techera.reto.retrofit.Service.PostsService;
import com.techera.reto.retrofit.Service.UsersService;


public class PostsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnFocusChangeListener {
    // Componentes
    private AutoCompleteTextView iUsers;
    private TextInputLayout iTitle, iBody, iIdPost;
    private Button btnRegister, btnEliminar;
    private ListView lvPosts;
    // Services
    private PostsService postsService;
    private UsersService usersService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        initUI();
    }

    private void initUI() {
        // Services
        postsService = new PostsService();
        usersService = new UsersService();
        // Componentes
        iUsers = findViewById(R.id.iUsers);
        iIdPost = (TextInputLayout) findViewById(R.id.iIdPost);
        iTitle = (TextInputLayout) findViewById(R.id.iTitle);
        iBody = (TextInputLayout) findViewById(R.id.iBody);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        lvPosts = (ListView) findViewById(R.id.lvPosts);

        usersService.loadUsersItems(iUsers);
        postsService.getListPostsByUser(null, lvPosts);

        iUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                postsService.getListPostsByUser((i == 0)?null :i, lvPosts);
                limpiarCampos();
            }
        });

        lvPosts.setOnItemClickListener(this);
        iIdPost.getEditText().setOnFocusChangeListener(this);
        btnRegister.setOnClickListener(view -> registerPost(view));
        btnEliminar.setOnClickListener(view -> deletePost(view));
    }

    private void registerPost(View view) {
        // Importante: el recurso no se actualizará realmente en el servidor, pero se falsificará.
        try {
            Posts post = new Posts();
            post.setUserId(Integer.parseInt(iUsers.getText().toString().split("-")[0].trim()));
            post.setTitle(iTitle.getEditText().getText().toString());
            post.setBody(iBody.getEditText().getText().toString());
            if (!btnRegister.getText().toString().equals("Registrar")) {
                post.setId(Integer.parseInt(iIdPost.getEditText().getText().toString()));
            }

            postsService.savePost(post, view);
            postsService.getListPostsByUser(post.getUserId(), lvPosts);
            limpiarCampos();
        } catch (Exception e) {
            Log.i("Exception", e.getMessage());
        }
    }

    private void deletePost(View view) {
        // Importante: el recurso no se actualizará realmente en el servidor, pero se falsificará.
        Posts post = new Posts();
        post.setId(Integer.parseInt(iIdPost.getEditText().getText().toString()));

        postsService.deletePost(post, view);
        postsService.getListPostsByUser(null, lvPosts);
        limpiarCampos();
    }


    private void limpiarCampos() {
        iIdPost.getEditText().setText("");
        iIdPost.setVisibility(View.GONE);
        iTitle.getEditText().setText("");
        iBody.getEditText().setText("");
        btnRegister.setText("Registrar");
        btnEliminar.setEnabled(false);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Posts post = (Posts) adapterView.getAdapter().getItem(i);
        mostrarPost(post);
    }

    public void mostrarPost(Posts post) {
        if (post == null) { return; }
        iUsers.setText(iUsers.getAdapter().getItem(post.getUserId()).toString(),false);
        iIdPost.setVisibility(View.VISIBLE);
        iIdPost.getEditText().setText(post.getId().toString());
        /*postsService.getListPostsByUser(post.getUserId(), lvPosts);*/
        iTitle.getEditText().setText(post.getTitle());
        iBody.getEditText().setText(post.getBody());
        btnRegister.setText("Modificar");
        btnEliminar.setEnabled(true);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus){
            try {
                Integer idPost = Integer.parseInt(iIdPost.getEditText().getText().toString());
                Posts post = null;
                Log.i("Post", String.valueOf(lvPosts.getAdapter().getCount()));
                for (int i = 0; i < lvPosts.getAdapter().getCount(); i++) {
                    Posts p = (Posts) lvPosts.getAdapter().getItem(i);
                    if (p.getId() == idPost) {
                        post = (Posts) lvPosts.getAdapter().getItem(i);
                        break;
                    }
                }
                Log.i("Post", String.valueOf(post));
                mostrarPost(post);
            } catch (Exception e) {
                Log.i("Post", e.getMessage());
                limpiarCampos();
                iUsers.setText(iUsers.getAdapter().getItem(0).toString(),false);
                postsService.getListPostsByUser(null, lvPosts);
            }
        } else {
            postsService.getListPostsByUser(null, lvPosts);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}