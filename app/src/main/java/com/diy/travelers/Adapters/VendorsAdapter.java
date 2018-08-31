package com.diy.travelers.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diy.travelers.Models.UserBean;
import com.diy.travelers.R;

import java.util.ArrayList;

/**
 * Created by Abhinav on 8/20/2018.
 */

public class VendorsAdapter extends RecyclerView.Adapter<VendorsAdapter.ViewHolder> {
    Context context;
    ArrayList<UserBean> userBeanArrayList;
    UserBean[] userBean;

    public VendorsAdapter(Context context, ArrayList<UserBean> userBeanArrayList) {
        this.context = context;
        this.userBeanArrayList = userBeanArrayList;
    }

    public VendorsAdapter(Context context, UserBean[] userBean) {
        this.context=context;
        this.userBean=userBean;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.vendors_list_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.companyName.setText(userBean[position].getCompanyName());
        holder.comapnyEmail.setText("Email: "+userBean[position].getPrimaryEmail());
        holder.companyPhone.setText("Contact: "+userBean[position].getPhone());
        holder.ownerName.setText("Owner Name: "+userBean[position].getFullName());
    }

    @Override
    public int getItemCount() {
        return userBean.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView companyName, ownerName, companyPhone, comapnyEmail;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.vendorListCard);
            comapnyEmail=itemView.findViewById(R.id.companyEmail);
            companyName=itemView.findViewById(R.id.companyName);
            companyPhone=itemView.findViewById(R.id.companyPhone);
            ownerName=itemView.findViewById(R.id.companyOwnerName);
        }
    }
}
