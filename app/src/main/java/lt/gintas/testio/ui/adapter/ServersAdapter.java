package lt.gintas.testio.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lt.gintas.testio.R;
import lt.gintas.testio.core.model.ServerItem;

/**
 * Created by Gintautas on 2016-12-14.
 */

public class ServersAdapter extends RecyclerView.Adapter<ServersAdapter.ViewHolder> {
    private List<ServerItem> serverItems;

    public ServersAdapter(List<ServerItem> serverItems) {
        this.serverItems = serverItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_server, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ServerItem serverItem = serverItems.get(position);
        holder.name.setText(serverItem.getName());
        holder.distance.setText(serverItem.getDistance() + " km");
        holder.line.setVisibility(position + 1 < getItemCount() ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return serverItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView distance;
        View line;
        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            distance = itemView.findViewById(R.id.distance);
            line = itemView.findViewById(R.id.line);
        }
    }

}