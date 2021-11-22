package com.example.taramatimer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    public void updateWithEntrainement(Entrainement entrainement){
        this.nameV.setText(entrainement.getName());
        this.totalTimeV.setText("Total time : " + entrainement.getTotalDurtion() + "s");
        this.iconV.setImageResource(entrainement.getIcon());

    }
}
