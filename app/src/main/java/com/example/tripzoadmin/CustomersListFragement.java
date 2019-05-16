package com.example.tripzoadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomersListFragement extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;

    AlertDialog.Builder builder;
    AlertDialog dialog;

    private ArrayList<CustomerData> customerDataArrayList;

    private ListView listView;

    private void progessActivate(boolean key){

        if(key){
            dialog.show();
        }else{
            dialog.cancel();
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_customers_list, container, false);

        builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child("Customer Information");
        customerDataArrayList = new ArrayList<>();

        listView = (ListView)view.findViewById(R.id.customer_list_view);
        progessActivate(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Bundle bundle = new Bundle();
                bundle.putBinder("customerProfileData", new ObjectWrapperForBinder(customerDataArrayList.get(position)));

                Intent intent = new Intent(getActivity().getApplicationContext(), CustomerProfileActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                customerDataArrayList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    CustomerData customerData = dataSnapshot1.getValue(CustomerData.class);
                    customerDataArrayList.add(customerData);
                }
                if(customerDataArrayList.size()>0){
                    listView.setAdapter(new CustomCustomerListView());
                    progessActivate(false);
                }else{
                    progessActivate(false);
                    Toast.makeText(getActivity().getApplicationContext(), "No Customer Info in Database !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                progessActivate(false);
//                Toast.makeText(getActivity().getApplicationContext(), "No Customer Info in Database !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class CustomCustomerListView extends BaseAdapter {

        @Override
        public int getCount() {
            return customerDataArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.custom_customer_list, parent, false);

            TextView name = (TextView)view.findViewById(R.id.list_cus_name);
            TextView mobile = (TextView)view.findViewById(R.id.list_cus_mobile);

            String fN = customerDataArrayList.get(position).getFirstName();
            String lN = customerDataArrayList.get(position).getLastName();
            name.setText(fN+" "+lN);
            mobile.setText(customerDataArrayList.get(position).getMobile());

            return view;
        }
    }
}
