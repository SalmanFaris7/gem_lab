package org.tensorflow.lite.examples.objectdetection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adapterBrand extends RecyclerView.Adapter<adapterBrand.MyBrandViewHolder>{

    List<gem> gemList;
    Context context;

    public adapterBrand(List<gem> brandList, Context context) {
        this.gemList = brandList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyBrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_gem,parent,false);
        MyBrandViewHolder brandHolder = new MyBrandViewHolder(view);

        return brandHolder;
    }

    //bind values to the recycler view
    @Override
    public void onBindViewHolder(@NonNull adapterBrand.MyBrandViewHolder holder, @SuppressLint("RecyclerView") int position) {
        /*holder.brand_No.setText(gemList.get(position).getGemName());*/
        holder.gem_Name.setText(gemList.get(position).getGemName());
        holder.gemWeight.setText(gemList.get(position).getGemWeight());

        holder.gemEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gem gems = gemList.get((position));

                Intent i =new Intent(context,Home.class);
                i.putExtra("gemName", gems.gemName);
                i.putExtra("gemWeight", gems.gemWeight);
                i.putExtra("gemShape", gems.gemShape);
                i.putExtra("PricePerCarat", gems.perCarat);
                i.putExtra("gemPrice", gems.gemPrice);
                i.putExtra("image_path", gems.imagePath);
                context.startActivity(i);
            }
        });

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gem gems = gemList.get((position));

                Intent i =new Intent(context,View_Inventory.class);
                i.putExtra("gem_Name", gems.gemName);
                i.putExtra("gem_Weight", gems.gemWeight);
                context.startActivity(i);
            }
        });

    }

    //get no of items in the array brand_list
    @Override
    public int getItemCount() {
        return gemList.size();
    }

    //assign UI components(single_product.xml) to variables
    public class MyBrandViewHolder extends RecyclerView.ViewHolder{
        TextView gem_No, gem_Name,gemEdit,gemWeight;
        LinearLayout parentLayout;

        public MyBrandViewHolder(@NonNull View itemView) {
            super(itemView);
            /*gem_No = itemView.findViewById(R.id.brand_);*/
            gem_Name = itemView.findViewById(R.id.gem_name);
            gemWeight = itemView.findViewById(R.id.gem_weight);
            parentLayout = itemView.findViewById(R.id.singleGemLayout);
            gemEdit = itemView.findViewById(R.id.gemEdit);
        }
    }
}
