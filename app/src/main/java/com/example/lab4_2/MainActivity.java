package com.example.lab4_2;

import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.util.SystemClock;
import androidx.media3.common.util.UnstableApi;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);
        db.deleteAllContacts();

        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));

        // Reading all contacts
        Log.e("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();
        List<String> contact_string = new ArrayList<>();
        for (Contact cn : contacts) {
            String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + ",Phone: " + cn.getPhoneNumber();
            // Writing Contacts to log
            Log.e("Name: ", log);
            contact_string.add(log);
        }
        ListView lvUser = (ListView) findViewById(R.id.lv_user);
        ArrayAdapter<String> userAdapter = new
                ArrayAdapter<String>(MainActivity.this, R.layout.item_contact,contact_string);
        lvUser.setAdapter(userAdapter);
        //Long click to delete a contact in listview
        lvUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle the long click event
                String selected_item = (String) parent.getAdapter().getItem(position);
                String id_selected_item = selected_item.split(" ")[1];
                if (db.deleteContact(Long.parseLong(id_selected_item)))
                {
                    userAdapter.remove(selected_item);
                    userAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Item " + position + " was deleted", Toast.LENGTH_SHORT).show();
                    return true;
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Item " + position + " was not deleted", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });
        // Click to get contact's info
        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected_item = (String) parent.getAdapter().getItem(position);
                String id_selected_item = selected_item.split(" ")[1];
                Contact contact = db.getContact(Integer.parseInt(id_selected_item));
                Toast.makeText(getApplicationContext(), "ToString function: " + contact.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // Click to change contact's phone number
//        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String selected_item = (String) parent.getAdapter().getItem(position);
//                String id_selected_item = selected_item.split(" ")[1];
//                selected_item = db.updateContactPhoneNumber(Long.parseLong(id_selected_item), "1111111111");
//                contact_string.set(position, selected_item);
//                userAdapter.notifyDataSetChanged();
//                Toast.makeText(getApplicationContext(), "Item " + position + " was updated", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}