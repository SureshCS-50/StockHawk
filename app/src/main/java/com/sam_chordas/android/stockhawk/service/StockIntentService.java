package com.sam_chordas.android.stockhawk.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.TaskParams;
import com.sam_chordas.android.stockhawk.utils.Constants;

/**
 * Created by sam_chordas on 10/1/15.
 */
public class StockIntentService extends IntentService {

    public StockIntentService() {
        super(StockIntentService.class.getName());
    }

    public StockIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Log.d(StockIntentService.class.getSimpleName(), "Stock Intent Service");
            StockTaskService stockTaskService = new StockTaskService(this);
            Bundle args = new Bundle();
            if (intent.getStringExtra(Constants.KEY_TAG).equals("add")) {
                args.putString(Constants.KEY_SYMBOL, intent.getStringExtra(Constants.KEY_SYMBOL));
            }
            // We can call OnRunTask from the intent service to force it to run immediately instead of
            // scheduling a task.
            stockTaskService.onRunTask(new TaskParams(intent.getStringExtra(Constants.KEY_TAG), args));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
