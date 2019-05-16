package com.example.tripzoadmin;

import android.app.Dialog;
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

public class RideDetailsFragement extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;

    AlertDialog.Builder builder;
    AlertDialog dialog;

    private ArrayList<RideData> rideDataArrayList;

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
        View view = inflater.inflate(R.layout.fragement_ride_details, container, false);

        builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child("Ride Information");

        rideDataArrayList = new ArrayList<>();


        listView = (ListView) view.findViewById(R.id.ride_list_view);


        progessActivate(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.custom_dialog);

                ImageView closeDialogBtn = (ImageView) dialog.findViewById(R.id.close_dialog);

                TextView pickupName = (TextView)dialog.findViewById(R.id.pickup_name_show);
                TextView pickupTime = (TextView)dialog.findViewById(R.id.pickup_time_show);
                TextView dropName = (TextView)dialog.findViewById(R.id.drop_name_show);
                TextView dropTime = (TextView)dialog.findViewById(R.id.drop_time_show);
                TextView cusName = (TextView)dialog.findViewById(R.id.cus_name_dialog_show);
                TextView cusMobile = (TextView)dialog.findViewById(R.id.cus_mobile_dialog_show);
                TextView otp = (TextView)dialog.findViewById(R.id.otp_show);
                TextView fare = (TextView)dialog.findViewById(R.id.fare_show);
                TextView driverName = (TextView)dialog.findViewById(R.id.driver_name_dialog_show);
                TextView driverMobile = (TextView)dialog.findViewById(R.id.driver_mobile_dialog_show);
                TextView vNumber = (TextView)dialog.findViewById(R.id.vehicle_number_dialog_show);
                TextView ratings = (TextView)dialog.findViewById(R.id.ratings_dialog_show);

                RideData data = rideDataArrayList.get(position);
                if(data != null){
                    pickupName.setText(data.getPickupLocation().toUpperCase());
                    dropName.setText(data.getDropLocation().toUpperCase());
                    pickupTime.setText(data.getPickupTime());
                    dropTime.setText(data.getDropTime());
                    cusName.setText(data.getCustomerName());
                    cusMobile.setText(data.getCustomerMobile());
                    otp.setText(data.getOTPGenerated()+"");
                    fare.setText(data.getTotalFare()+"");
                    driverName.setText(data.getDriverName());
                    driverMobile.setText(data.getDriverMobile());
                    vNumber.setText(data.getDriverVehicleNumber());
                    ratings.setText(data.getRideRatings()+"/5");
                }

                closeDialogBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
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
                rideDataArrayList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    RideData rideData = dataSnapshot1.getValue(RideData.class);
                    rideDataArrayList.add(rideData);
                }
                if(rideDataArrayList.size()>0){
                    listView.setAdapter(new CustomListViewRide());
                    progessActivate(false);
                }else{
                    progessActivate(false);
                    Toast.makeText(getActivity().getApplicationContext(), "No Ride Info in Database !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                progessActivate(false);
//                Toast.makeText(getActivity().getApplicationContext(), "No Driver Info in Database !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class CustomListViewRide extends BaseAdapter {

        @Override
        public int getCount() {
            return rideDataArrayList.size();
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
            View view = getLayoutInflater().inflate(R.layout.custom_ride_list, parent, false);
            TextView ridePickupAlphabet = (TextView)view.findViewById(R.id.ride_pickup_alphabet);
            TextView rideDropAlphabet = (TextView)view.findViewById(R.id.ride_drop_alpha);
            ridePickupAlphabet.setText(rideDataArrayList.get(position).getPickupLocation().substring(0,1));
            rideDropAlphabet.setText(rideDataArrayList.get(position).getDropLocation().substring(0,1));
            return view;
        }
    }
}
