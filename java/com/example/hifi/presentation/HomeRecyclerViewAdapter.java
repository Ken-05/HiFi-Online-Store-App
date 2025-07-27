package com.example.hifi.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hifi.R;
import com.example.hifi.business.AccessProducts;
import com.example.hifi.objects.Product;

import android.content.res.Resources;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    int pos = 1;
    Context context;
    AccessProducts accessProducts;
    Resources resources;

    public HomeRecyclerViewAdapter(Context context, AccessProducts accessProducts,
                                   RecyclerViewInterface recyclerViewInterface, Resources resources){
        this.context = context;
        this.accessProducts = accessProducts;
        this.recyclerViewInterface = recyclerViewInterface;
        this.resources = resources;
    }

    @NonNull
    @Override
    public HomeRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This is where i give a look to my rows (inflate the layout)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row,parent,false);
        return new HomeRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerViewAdapter.MyViewHolder holder, int position) {
        //assign values to the views created in recycler_view_row layout file
        // based on position of recycler view
        Product bindProduct = accessProducts.getProductById(""+pos);
        if (bindProduct == null) return;
        holder.myButton.setText(bindProduct.getProductName());
        String productImageName = bindProduct.getProductImageName();
        int productImageResource = resources.getIdentifier(productImageName, "drawable", context.getPackageName());
        holder.myImage.setImageResource(productImageResource);
        pos++;
    }

    ////////////////////////////////////////////////////
    // Change later to size of database
    ////////////////////////////////////////////////////
    @Override
    public int getItemCount() {
        // the number of items that are displayed
        return 20;//change later to size of database
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // grab views from recycler_view_row layout file
        //kinda like in the onCreate method
        ImageView myImage;
        Button myButton;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            myImage = itemView.findViewById(R.id.imageView);
            myButton = itemView.findViewById(R.id.button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int adapterPosition = getAdapterPosition();

                        if(adapterPosition != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onClick(adapterPosition+1);
                        }

                    }
                }
            });

            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int adapterPosition = getAdapterPosition();

                        if(adapterPosition != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onClick(adapterPosition+1);
                        }

                    }
                }
            });
        }
    }
}
