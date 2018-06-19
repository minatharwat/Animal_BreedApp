package com.example.user.animal_breedapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.user.animal_breedapp.DetailCategory;
import com.example.user.animal_breedapp.Models.CategoreItem;
import com.example.user.animal_breedapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ${Mina} on 18/06/2018.
 */

public class Categorie_RecycleAdapter extends RecyclerView.Adapter<Categorie_RecycleAdapter.viewHolder> {
    public static int pos;
    public static int x;
    public CategoreItem obj;
    List<CategoreItem> m;
    private Context mcontext;

    public Categorie_RecycleAdapter(List<CategoreItem> m, Context mcontext) {
        this.m = m;
        this.mcontext = mcontext;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorie_item, parent, false);
        viewHolder viewHolder = new viewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, int position) {
        obj = m.get(position);
        //  if (x==0) {
        Picasso.with(mcontext).load(obj.getCategorie_image()).error(R.drawable.no_img).placeholder(R.drawable.no_img).into(holder.imageView);
        /*}else {
        Picasso.with(mcontext)
                .load(obj.getCategorie_image())
                .placeholder(R.drawable.no_img)
                .error(android.R.drawable.stat_notify_error)
                .networkPolicy(NetworkPolicy.OFFLINE)//user this for offline support
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        // Try again online if cache failed
                        Picasso.with(mcontext)
                                .load(Uri.parse(obj.getCategorie_image()))
                                .placeholder(R.drawable.no_img)
                                .error(R.drawable.no_img)
                                .into(holder.imageView);
                    }
                });
        }*/
    }

    @Override
    public int getItemCount() {
        return m.size();
    }

    class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;

        public viewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_cat);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            pos = getLayoutPosition();
            Intent intent = new Intent(v.getContext(), DetailCategory.class);
            intent.putExtra("name_c", m.get(pos).getName());
            intent.putExtra("image_c", m.get(pos).getCategorie_image());
            itemView.getContext().startActivity(intent);


        }
    }


}