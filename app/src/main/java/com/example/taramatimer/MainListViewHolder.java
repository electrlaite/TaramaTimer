package com.example.taramatimer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.mtrl_list_item_text) TextView nameV;
    @BindView(R.id.mtrl_list_item_icon) ImageView iconV;
    @BindView(R.id.mtrl_list_item_secondary_text) TextView totalTimeV;


    public MainListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateWithEntrainement(Entrainement entrainement, Context context){
        this.nameV.setText(entrainement.getName());
        this.totalTimeV.setText("Total time : " + entrainement.getTotalDurtion() + "s" + entrainement.getUid());
        this.iconV.setImageResource(entrainement.getIcon());
        this.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, timerActivity.class).putExtra(timerActivity.KEY_ID_ENTRAINEMENT, entrainement.getUid());
            context.startActivity(intent);
        });
        itemView.findViewById(R.id.mtrl_list_item_secondary_icon).setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEntrainement.class).putExtra(AddEntrainement.KEY_ENTRAINEMENT_ID, entrainement.getUid());
            context.startActivity(intent);
        });
        this.itemView.setBackgroundColor(entrainement.getColor());
        if(Color.red(entrainement.getColor()) < 100 && Color.blue(entrainement.getColor()) < 100 && Color.green(entrainement.getColor()) < 100){
            this.nameV.setTextColor(Color.WHITE);
            this.totalTimeV.setTextColor(Color.WHITE);
            this.iconV.setColorFilter(Color.WHITE);
            ImageView im = this.itemView.findViewById(R.id.mtrl_list_item_secondary_icon);
            im.setColorFilter(Color.WHITE);
        }
        else{
            this.nameV.setTextColor(Color.BLACK);
            this.totalTimeV.setTextColor(Color.BLACK);
            this.iconV.setColorFilter(Color.BLACK);
            ImageView im = this.itemView.findViewById(R.id.mtrl_list_item_secondary_icon);
            im.setColorFilter(Color.BLACK);
        }
    }
}
