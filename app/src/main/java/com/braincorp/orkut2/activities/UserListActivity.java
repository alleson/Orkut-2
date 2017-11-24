package com.braincorp.orkut2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.braincorp.orkut2.R;
import com.braincorp.orkut2.adapters.UserAdapter;
import com.braincorp.orkut2.database.Sql;
import com.braincorp.orkut2.database.UserDao;
import com.braincorp.orkut2.listeners.OnItemClickListener;
import com.braincorp.orkut2.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserListActivity extends AppCompatActivity implements OnItemClickListener {

    public static final String EXTRA_SHOW_FRIENDS = "show_friends";
    public static final String EXTRA_USER = "user";

    public static Intent getIntent(Context context, boolean showFriends, User user) {
        Intent intent = new Intent(context, UserListActivity.class);
        intent.putExtra(EXTRA_SHOW_FRIENDS, showFriends);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }

    private boolean showFriends;
    private RecyclerView recyclerView;
    private User user;
    private UserDao database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setActionBar();
        database = UserDao.getInstance(this);
        bindViews();
        parseIntent();
        fillRecyclerView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        String sql = String.format(Locale.getDefault(), Sql.ALL_OTHER_USERS, this.user.getId());
        List<User> users = database.select(sql);
        User user = null;
        for (int i = 0; i < users.size(); i++) {
            if (i == position) {
                user = users.get(i);
                break;
            }
        }
        if (user != null) {
            Intent intent = UserDetailsActivity.getIntent(this, this.user, user);
            startActivity(intent);
        }
    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void bindViews() {
        recyclerView = findViewById(R.id.recyclerViewFriendsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void fillRecyclerView() {
        List<User> data;
        if (showFriends) {
            data = new ArrayList<>(); // TODO: implement show friends flow
        } else {
            String sql = String.format(Locale.getDefault(), Sql.ALL_OTHER_USERS, user.getId());
            data = database.select(sql);
        }
        UserAdapter adapter = new UserAdapter(this, data, this);
        recyclerView.setAdapter(adapter);
    }

    private void parseIntent() {
        showFriends = getIntent().getBooleanExtra(EXTRA_SHOW_FRIENDS, false);
        user = getIntent().getParcelableExtra(EXTRA_USER);
    }

}