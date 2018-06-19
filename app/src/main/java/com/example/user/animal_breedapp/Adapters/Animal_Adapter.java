package com.example.user.animal_breedapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.animal_breedapp.DetailAnimal;
import com.example.user.animal_breedapp.Models.Animal;
import com.example.user.animal_breedapp.R;

import java.util.List;

import static com.example.user.animal_breedapp.DetailCategory.image_re;
import static com.example.user.animal_breedapp.DetailCategory.name_re;

/**
 * Created by ${Mina} on 18/06/2018.
 */

public class Animal_Adapter extends RecyclerView.Adapter<Animal_Adapter.viewHolder> {
    public static int pos;
    public static int x;
    public Animal obj;
    List<Animal> m;
    private Context mcontext;

    public Animal_Adapter(List<Animal> m, Context mcontext) {
        this.m = m;
        this.mcontext = mcontext;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_row, parent, false);
        viewHolder viewHolder = new viewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, int position) {
        obj = m.get(position);
        holder.t.setText(obj.getName_an());


    }

    @Override
    public int getItemCount() {
        return m.size();
    }

    class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView t;

        public viewHolder(View itemView) {
            super(itemView);
            t = itemView.findViewById(R.id.animal_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            pos = getLayoutPosition();
            Intent intent = new Intent(v.getContext(), DetailAnimal.class);
            intent.putExtra("position", getLayoutPosition());
            intent.putExtra("name_ani", m.get(pos).getName_an());
            intent.putExtra("cat_na", name_re);
            intent.putExtra("cat_im", image_re);

            itemView.getContext().startActivity(intent);


        }
    }


}
