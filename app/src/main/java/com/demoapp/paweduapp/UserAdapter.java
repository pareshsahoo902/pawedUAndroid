package com.demoapp.paweduapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demoapp.paweduapp.APIModels.UserModel;
import com.demoapp.paweduapp.Profile.UserProfile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UserModel> userModelList = new ArrayList<>();
    private Context mContext;

    public UserAdapter(List<UserModel> userModelList, Context mContext) {
        this.userModelList = userModelList;
        this.mContext = mContext;
    }

    public void updateUserList(List<UserModel> users) {
        userModelList.clear();
        userModelList.addAll(users);
        notifyDataSetChanged();

    }

    @NonNull

    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {

        UserModel currentModel = userModelList.get(position);
        holder.name.setText(currentModel.getName());
        holder.breed.setText(currentModel.getBreed());

        Picasso.get().load(currentModel.getImageUrl())
                .error(R.drawable.dogdemo).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), UserProfile.class).putExtra("UserId",currentModel.getId()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView name, breed;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.name);
            breed = itemView.findViewById(R.id.breed);

        }
    }
}
