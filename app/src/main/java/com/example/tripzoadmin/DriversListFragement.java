package com.example.tripzoadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DriversListFragement extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;

    AlertDialog.Builder builder;
    AlertDialog dialog;

    private ArrayList<DriverData> driverDataArrayList;

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
        View view = inflater.inflate(R.layout.fragement_drivers_list, container, false);

        builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child("Driver Information");
        driverDataArrayList = new ArrayList<>();

        listView = (ListView)view.findViewById(R.id.driver_list_view);
        progessActivate(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Bundle bundle = new Bundle();
                bundle.putBinder("driverProfileData", new ObjectWrapperForBinder(driverDataArrayList.get(position)));

                Intent intent = new Intent(getActivity().getApplicationContext(), DriverProfileActivity.class);
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
                driverDataArrayList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    DriverData driverData = dataSnapshot1.getValue(DriverData.class);
                    driverDataArrayList.add(driverData);
                }
                if(driverDataArrayList.size()>0){
                    listView.setAdapter(new CustomDriverListView());
                    progessActivate(false);
                }else{
                    progessActivate(false);
                    Toast.makeText(getActivity().getApplicationContext(), "No Driver Info in Database !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                progessActivate(false);
//                Toast.makeText(getActivity().getApplicationContext(), "No Driver Info in Database !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class CustomDriverListView extends BaseAdapter{

        @Override
        public int getCount() {
            return driverDataArrayList.size();
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
            View view = getLayoutInflater().inflate(R.layout.custom_driver_list, parent, false);
            TextView name = (TextView)view.findViewById(R.id.list_driver_name);
            TextView mobile = (TextView)view.findViewById(R.id.list_driver_mobile);

            String fN = driverDataArrayList.get(position).getFirstName();
            String lN = driverDataArrayList.get(position).getLastName();
            name.setText(fN+" "+lN);
            mobile.setText(driverDataArrayList.get(position).getMobile());
            return view;
        }
    }
}
