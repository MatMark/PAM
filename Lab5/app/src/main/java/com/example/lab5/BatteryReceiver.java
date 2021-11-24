package com.example.lab5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {

    private boolean isCharging = false;
    private boolean isFull = false;
    private int percent = -1;
    private float temp = -1;
    private float voltage = -1;
    private int chargePlug = -1;

    @Override
    public void onReceive(Context context, Intent intent) {
        showChanges(context, intent);
        saveValues(intent);
    }

    private void showChanges(Context context, Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        if (isCharging != (status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL)) {
            showToast(context, "isCharging", String.valueOf(status == BatteryManager.BATTERY_STATUS_CHARGING
                    || status == BatteryManager.BATTERY_STATUS_FULL));
        }
        if (isFull != (status == BatteryManager.BATTERY_STATUS_FULL)) {
            showToast(context, "isFull", String.valueOf(status == BatteryManager.BATTERY_STATUS_FULL));
        }
        if (chargePlug != intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)) {
            if (intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) == 1) {
                showToast(context, "chargePlug", "AC charging");
            } else if (intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) == 2) {
                showToast(context, "chargePlug", "USB charging");
            }
        }
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        if (percent != Math.round(level * 100 / (float) scale)) {
            showToast(context, "percent", String.valueOf(Math.round(level * 100 / (float) scale)));
        }
        if (temp != intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10.f) {
            showToast(context, "temp", String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10.f));
        }
        if (voltage != intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) / 1000.f) {
            showToast(context, "voltage", String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) / 1000.f));
        }
    }

    private void saveValues(Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL;
        isFull = status == BatteryManager.BATTERY_STATUS_FULL;

        chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        percent = Math.round(level * 100 / (float) scale);
        temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10.f;
        voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) / 1000.f;
    }

    private void showToast(Context context, String type, String value) {
        Toast toast = Toast.makeText(context.getApplicationContext(),
                String.format("%s: %s", type, value),
                Toast.LENGTH_SHORT);
        toast.show();
    }
}
