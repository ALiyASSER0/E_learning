//package com.example.learning.Splash
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import com.example.learning.R
//
//class DetailsNurse : Fragment() {
//    // TODO: Rename and change types of parameters
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_details_nurse, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//    }
//}
//<?xml version="1.0" encoding="utf-8"?>
//<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
//xmlns:tools="http://schemas.android.com/tools"
//xmlns:app="http://schemas.android.com/apk/res-auto"
//android:layout_width="match_parent"
//android:layout_height="match_parent"
//tools:context=".UI.nurse.DetailsNurse">
//
//
//
//
//<LinearLayout
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:orientation="horizontal"
//android:padding="16dp"
//tools:ignore="UseCompoundDrawables">
//
//<ImageView
//android:layout_width="36dp"
//android:layout_height="30dp"
//android:src="@drawable/baseline_arrow_back_24"
//android:layout_gravity="center_vertical"
//android:layout_marginEnd="8dp"
//android:contentDescription="@string/back_button"
//android:clickable="true"
//android:onClick="onBackPressed"/>
//
//<TextView
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_weight="1"
//android:style="@style/TextAppearance.AppCompat.Headline"
//android:text="Case Details"
//android:textStyle="bold"
//android:layout_gravity="center"
///>
//
//
//</LinearLayout>
//
//<LinearLayout
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:orientation="horizontal">
//
//
//<androidx.cardview.widget.CardView
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginLeft="16dp"
//app:cardCornerRadius="8dp"
//app:cardBackgroundColor="#22C7B8">
//
//<TextView
//android:layout_width="72dp"
//android:layout_height="wrap_content"
//android:padding="8dp"
//android:text="  Case"
//android:textColor="@android:color/white"
//android:textSize="16sp"
//android:textStyle="normal" />
//
//</androidx.cardview.widget.CardView>
//
//
//<androidx.cardview.widget.CardView
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginLeft="16dp"
//app:cardCornerRadius="8dp"
//app:cardBackgroundColor="#fdfcfb">
//
//<TextView
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:padding="8dp"
//android:text="Medical record"
//android:textColor="@android:color/black"
//android:textSize="16sp"
//android:textStyle="normal" />
//
//</androidx.cardview.widget.CardView>
//
//
//<androidx.cardview.widget.CardView
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginLeft="126dp"
//app:cardCornerRadius="8dp"
//app:cardBackgroundColor="#d9d9d9">
//
//<ImageView
//android:id="@+id/icon"
//android:layout_width="24dp"
//android:layout_height="15dp"
//android:src="@drawable/baseline_circle_24" />
//
//<ImageButton
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:background="?attr/actionBarPopupTheme"
//android:padding="8dp"
//android:src="@drawable/baseline_assignment_ind_24"
//android:contentDescription="Description of the image button"
//tools:ignore="TouchTargetSizeCheck" />
//
//</androidx.cardview.widget.CardView>
//
//
//
//
//</LinearLayout>
//
//<androidx.cardview.widget.CardView
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:layout_margin="8dp"
//app:cardCornerRadius="8dp">
//
//<LinearLayout
//android:layout_width="match_parent"
//android:layout_height="match_parent"
//android:orientation="vertical"
//android:padding="16dp">
//
//<TextView
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:style="@style/TextAppearance.AppCompat.Subheading"
//android:text="Patient Information : "
//android:textSize="16sp"
//android:textStyle="bold"
///>
//<Space
//android:layout_width="match_parent"
//android:layout_height="8dp" />
//<LinearLayout
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:orientation="horizontal">
//
//<TextView
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:layout_weight="1"
//android:text="Patient Name: " />
//
//<TextView
//android:id="@+id/patient_name"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text='@{stringValue != null ? stringValue.trim() : ""}' />
//
//</LinearLayout>
//<Space
//android:layout_width="match_parent"
//android:layout_height="8dp" />
//<LinearLayout
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:orientation="horizontal">
//
//<TextView
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:layout_weight="1"
//android:text="Age: " />
//
//<TextView
//android:id="@+id/patient_age"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text='@{stringValue != null ? stringValue.trim() : ""}' />
//
//</LinearLayout>
//<Space
//android:layout_width="match_parent"
//android:layout_height="8dp" />
//<LinearLayout
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:orientation="horizontal">
//
//<TextView
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:layout_weight="1"
//android:text="Phone Number: " />
//
//<TextView
//android:id="@+id/patient_phone"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text='@{stringValue != null ? stringValue.trim() : ""}' />
//
//</LinearLayout>
//<Space
//android:layout_width="match_parent"
//android:layout_height="8dp" />
//<LinearLayout
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:orientation="horizontal">
//
//<TextView
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:layout_weight="1"
//android:text="Date: " />
//
//<TextView
//android:id="@+id/case_date"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text='@{stringValue != null ? stringValue.trim() : ""}' />
//
//</LinearLayout>
//<Space
//android:layout_width="match_parent"
//android:layout_height="8dp" />
//<!-- Doctor -->
//<LinearLayout
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:orientation="horizontal">
//
//<TextView
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:layout_weight="1"
//android:text="Doctor: " />
//
//<TextView
//android:id="@+id/doctor_name"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text='@{stringValue != null ? stringValue.trim() : ""}' />
//
//</LinearLayout>
//
//<Space
//android:layout_width="match_parent"
//android:layout_height="8dp" />
//<!-- Nurse -->
//<LinearLayout
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:orientation="horizontal">
//
//<TextView
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:layout_weight="1"
//android:text="Nurse: " />
//
//<TextView
//android:id="@+id/nurse_name"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text='@{stringValue != null ? stringValue.trim() : ""}' />
//
//</LinearLayout>
//<Space
//android:layout_width="match_parent"
//android:layout_height="8dp" />
//<!-- Status -->
//<LinearLayout
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:orientation="horizontal">
//
//<TextView
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:layout_weight="1"
//android:text="Status: " />
//
//<TextView
//android:id="@+id/case_status"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text='@{stringValue != null ? stringValue.trim() : ""}' />
//
//</LinearLayout>
//<Space
//android:layout_width="match_parent"
//android:layout_height="8dp" />
//<!-- Case Description -->
//<LinearLayout
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:orientation="horizontal">
//
//<TextView
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:layout_weight="1"
//android:text="Case Description: " />
//
//<TextView
//android:id="@+id/case_description"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text='@{stringValue != null ? stringValue.trim() : ""}' />
//
//</LinearLayout>
//
//
//
//
//</LinearLayout>
//
//</androidx.cardview.widget.CardView>
//
//</FrameLayout>
