package com.example.lab4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity {

    public static final int REQUEST_READ_CONTACTS = 79;
    private ArrayList mobileArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            mobileArray = getAllContacts();
        } else {
            requestPermission();
        }

        ListView list = findViewById(R.id.contacts_list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId)
            {
                TextView item = (TextView) itemView;
                String[] parts = item.getText().toString().split("; ");
                String name = parts[0];
                String number = parts[1];

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:".concat(number)));
                startActivity(intent);
            }
        });

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mobileArray);
        list.setAdapter(adapter);
    }

    private void requestPermission() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_CONTACTS)) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_CONTACTS)) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mobileArray = getAllContacts();
            }
        }

    }

    private ArrayList getAllContacts() {
        ArrayList<String> nameList = new ArrayList<>();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur.moveToNext()) {
                int id_column = cur.getColumnIndex(ContactsContract.Contacts._ID);
                String id = cur.getString(id_column);
                int name_column = cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                String name = cur.getString(name_column);
                Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                while (phones.moveToNext()) {
                    int num_column = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String number = phones.getString(num_column);
                    nameList.add(name.concat("; ").concat(number));
                }
                phones.close();
            }
        }
        if (cur != null) {
            cur.close();
        }
        return nameList;
    }
}