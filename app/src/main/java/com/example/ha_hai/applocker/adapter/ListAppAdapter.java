package com.example.ha_hai.applocker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.example.ha_hai.applocker.R;
import com.example.ha_hai.applocker.fragment.ShowFragment;
import com.example.ha_hai.applocker.model.App;

import java.util.List;

/**
 * Created by ha_hai on 8/03/2018.
 */

public class ListAppAdapter extends RecyclerView.Adapter<ListAppAdapter.MyHolder> {

    Context mContext;
    List<App> mListApp;

    public ListAppAdapter(Context context, List<App> listApp) {
        this.mContext = context;
        this.mListApp = listApp;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_view, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.imgHinhAnh.setImageDrawable(mListApp.get(position).getIcon());
        holder.txtAppName.setText(mListApp.get(position).getName());
        holder.toggleButton.setChecked(mListApp.get(position).getState() == 1 ? true : false);
        if (mListApp.get(position).getState() == 0)
            holder.txtStateApp.setText("OFF");
        else
            holder.txtStateApp.setText("ON");
    }

    @Override
    public int getItemCount() {
        return mListApp.size();
    }

    public void setItems(List<App> apps) {
        this.mListApp = apps;
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView imgHinhAnh;
        TextView txtAppName, txtStateApp;
        ToggleButton toggleButton;

        public MyHolder(final View itemView) {
            super(itemView);

            imgHinhAnh = itemView.findViewById(R.id.imgHinhAnh);
            txtAppName = itemView.findViewById(R.id.txtAppName);
            txtStateApp = itemView.findViewById(R.id.txtStateApp);
            toggleButton = itemView.findViewById(R.id.toggleButton);

            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                    if (isChecked) {
                        txtStateApp.setText("ON");
                        mListApp.get(getAdapterPosition()).setState(1);
                    } else {
                        txtStateApp.setText("OFF");
                        mListApp.get(getAdapterPosition()).setState(0);
                    }
                    ShowFragment.mDBManager.update(mListApp.get(getAdapterPosition()));
                }
            });
        }
    }
}
