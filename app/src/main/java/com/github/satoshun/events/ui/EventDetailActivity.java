package com.github.satoshun.events.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.github.satoshun.events.R;
import com.github.satoshun.events.databinding.ActivityEventDetailBinding;

import static com.github.satoshun.events.domain.Events.Event;

public class EventDetailActivity extends BaseActivity  {

    private final static String TAG = "EventDetailActivity";
    private final static String INTENT_EVENT = "INTENT_EVENT";

    public static Intent callingIntent(Context context, Event event) {
        Intent intent = new Intent(context, EventDetailActivity.class);
        intent.putExtra(INTENT_EVENT, event);

        return intent;
    }

    private ActivityEventDetailBinding binding;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = bindContentView(this, R.layout.activity_event_detail);
        event = (Event) getIntent().getSerializableExtra(INTENT_EVENT);
        if (event == null) {
            Log.e(TAG, "invalid data");
            return;
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(event.title());

        binding.setEvent(event);
        binding.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(event.url()));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
