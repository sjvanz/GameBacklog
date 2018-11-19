package com.example.user.gamebacklog;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BacklogAdapter.BacklogClickListener {
    private List<Backlog> mBacklogGameObjects;
    private BacklogAdapter mAdapter;

    private RecyclerView mBacklogRecyclerView;

    public static final int REQUESTCODE = 1234;
    public static final String UPDATE_GAME = "UPDATE_GAME";
    public static final String INSERT_OR_UPDATE = "INSERT_OR_UPDATE";

    public final static int TASK_GET_ALL_BACKLOGGAMES = 0;
    public final static int TASK_DELETE_BACKLOGGAMES = 1;
    public final static int TASK_UPDATE_BACKLOGGAMES = 2;
    public final static int TASK_INSERT_BACKLOGGAMES = 3;

    protected static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);
        new ReminderAsyncTask(TASK_GET_ALL_BACKLOGGAMES).execute();

        mBacklogGameObjects = new ArrayList<>();

        mBacklogRecyclerView = findViewById(R.id.backlogRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);

        mBacklogRecyclerView.setLayoutManager(mLayoutManager);

        updateUI();

        FloatingActionButton fab = findViewById(R.id.addBacklogGame);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                intent.putExtra(UPDATE_GAME, new Backlog());
                intent.putExtra(INSERT_OR_UPDATE, TASK_INSERT_BACKLOGGAMES);
                startActivityForResult(intent, REQUESTCODE);
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }


                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        int position = (viewHolder.getAdapterPosition());
                        new ReminderAsyncTask(TASK_DELETE_BACKLOGGAMES).execute(mBacklogGameObjects.get(position));
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mBacklogRecyclerView);
    }

    public void onBacklogDbUpdated(List list) {
        mBacklogGameObjects = list;
        updateUI();
    }

    public void updateUI() {
        if (mAdapter == null) {
            mAdapter = new BacklogAdapter(mBacklogGameObjects, this);
            mBacklogRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.swapList(mBacklogGameObjects);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTCODE && resultCode == RESULT_OK) {
            Backlog updatedBacklogGame = data.getParcelableExtra(UPDATE_GAME);
            Integer insertOrUpdate = data.getIntExtra(INSERT_OR_UPDATE, TASK_UPDATE_BACKLOGGAMES);
            new ReminderAsyncTask(insertOrUpdate).execute(updatedBacklogGame);
            updateUI();
        }
    }

    @Override
    public void backlogGameOnClick(int i) {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        intent.putExtra(UPDATE_GAME, mBacklogGameObjects.get(i));
        intent.putExtra(INSERT_OR_UPDATE, TASK_UPDATE_BACKLOGGAMES);
        startActivityForResult(intent, REQUESTCODE);
    }


    public class ReminderAsyncTask extends AsyncTask<Backlog, Void, List> {
        private int taskCode;

        public ReminderAsyncTask(int taskCode) {
            this.taskCode = taskCode;
        }

        @Override
        protected List doInBackground(Backlog... backlogGames) {
            switch (taskCode) {
                case MainActivity.TASK_DELETE_BACKLOGGAMES:
                    db.backlogGameDao().deleteBacklogGame(backlogGames[0]);
                    break;

                case MainActivity.TASK_UPDATE_BACKLOGGAMES:
                    db.backlogGameDao().updateBacklogGame(backlogGames[0]);
                    break;

                case MainActivity.TASK_INSERT_BACKLOGGAMES:
                    db.backlogGameDao().insertBacklogGame(backlogGames[0]);
                    break;
            }

            return db.backlogGameDao().getAllBacklogGames();
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            onBacklogDbUpdated(list);
        }
    }
}