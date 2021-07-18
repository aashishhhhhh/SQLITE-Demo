package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_add,btn_viewAll;
    EditText et_name,et_age;
    Switch sw_activeCustomer;
    ListView lv_customerList;
    ArrayAdapter customerArrayAdapter;
    DatabaseHelper databaseHelper ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add=(Button)findViewById(R.id.btn_Add);
        btn_viewAll=(Button)findViewById(R.id.btn_viewall);
        et_name=(EditText)findViewById(R.id.et_Name);
        et_age=(EditText)findViewById(R.id.etAge);
        sw_activeCustomer=(Switch)findViewById(R.id.sw_cutomer);
        lv_customerList=(ListView)findViewById(R.id.lv_customer);
         databaseHelper = new DatabaseHelper(MainActivity.this);
        ShowCustomerOnListView(databaseHelper);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomerModel customerModel;
                try {
                     customerModel = new CustomerModel(-1,et_name.getText().toString(),Integer.parseInt(et_age.getText().toString()),sw_activeCustomer.isChecked());
                    Toast.makeText(MainActivity.this,customerModel.toString(),Toast.LENGTH_SHORT).show();

                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this,"Error Creating customer",Toast.LENGTH_SHORT).show();
                    customerModel = new CustomerModel(-1,"error",0,false);
                }

                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                boolean b = databaseHelper.addOne(customerModel);

                Toast.makeText(MainActivity.this,"Success="+b,Toast.LENGTH_SHORT).show();
                ShowCustomerOnListView(databaseHelper);

            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

                ShowCustomerOnListView(databaseHelper);
                //Toast.makeText(MainActivity.this,everyone.toString(),Toast.LENGTH_SHORT).show();
            }
        });

     /*   lv_customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModel clickedCustomer = (CustomerModel) parent.getItemAtPosition(position);
                databaseHelper.deleteOne(clickedCustomer);
                ShowCustomerOnListView(databaseHelper);
                Toast.makeText(getApplicationContext(),"Deleted: "+clickedCustomer.toString(),Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    private void ShowCustomerOnListView(DatabaseHelper databaseHelper2) {
        customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, databaseHelper2.getEveryone());
        lv_customerList.setAdapter(customerArrayAdapter);
    }
}