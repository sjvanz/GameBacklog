package com.example.user.gamebacklog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Date;

public class UpdateActivity extends AppCompatActivity {
    public TextInputEditText mInputTitle;
    public TextInputEditText mInputPlatform;
    public TextInputEditText mInputNotes;
    public Spinner mInputStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.backloggame_update);

        mInputTitle = findViewById(R.id.inputTitle);
        mInputPlatform = findViewById(R.id.inputPlatform);
        mInputNotes = findViewById(R.id.inputNotes);
        mInputStatus = findViewById(R.id.inputStatus);
        final Backlog backlogGameUpdate = getIntent().getParcelableExtra(MainActivity.UPDATE_GAME);
        final Integer insertOrUpdate = getIntent().getIntExtra(MainActivity.INSERT_OR_UPDATE, MainActivity.TASK_UPDATE_BACKLOGGAMES);

        if (backlogGameUpdate != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.backlog_column, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mInputStatus.setAdapter(adapter);
            if (backlogGameUpdate.getStatus() != null) {
                int spinnerPosition = adapter.getPosition(backlogGameUpdate.getStatus());
                mInputStatus.setSelection(spinnerPosition);
            }

            mInputTitle.setText(backlogGameUpdate.getTitle());
            mInputPlatform.setText(backlogGameUpdate.getPlatform());
            mInputNotes.setText(backlogGameUpdate.getNotes());
        }

        FloatingActionButton saveBacklogGame = findViewById(R.id.saveBacklogGame);
        saveBacklogGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mInputTitle.getText().toString();
                String platform = mInputPlatform.getText().toString();
                String notes = mInputNotes.getText().toString();
                String status = mInputStatus.getSelectedItem().toString();

                if (!TextUtils.isEmpty(title)) {
                    backlogGameUpdate.setTitle(title);
                }
                if (!TextUtils.isEmpty(platform)) {
                    backlogGameUpdate.setPlatform(platform);
                }
                if (!TextUtils.isEmpty(notes)) {
                    backlogGameUpdate.setNotes(notes);
                }
                backlogGameUpdate.setStatus(status);
                backlogGameUpdate.setSaveDate(new Date());

                Intent resultIntent = new Intent();
                resultIntent.putExtra(MainActivity.UPDATE_GAME, backlogGameUpdate);
                resultIntent.putExtra(MainActivity.INSERT_OR_UPDATE, insertOrUpdate);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
