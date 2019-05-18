package manakov.sample.newsaggregator03;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CustomRecyclerViewRssItemAdapter extends RecyclerView.Adapter<CustomRecyclerViewRssItemAdapter.ViewHolder> {
    private ArrayList<RssItem> list;
    private View.OnClickListener onClickListener;

    public CustomRecyclerViewRssItemAdapter(ArrayList<RssItem> list){
        this.list = list;
    }

    @Override
    public CustomRecyclerViewRssItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rss_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomRecyclerViewRssItemAdapter.ViewHolder viewHolder, int position){
        viewHolder.titleTextView      .setText( list.get(position).getTitle()           );
        viewHolder.dateTextView       .setText( list.get(position).getDate()            );
        viewHolder.linkTextView       .setText( list.get(position).getLink()            );
        viewHolder.descriptionTextView.setText( list.get(position).getDescription()     );
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        this.onClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titleTextView;
        public TextView dateTextView;
        public TextView linkTextView;
        public TextView descriptionTextView;

        public ViewHolder(View itemView){
            super(itemView);

            titleTextView       = itemView.findViewById(R.id.titleTextView);
            dateTextView        = itemView.findViewById(R.id.dateTextView);
            linkTextView        = itemView.findViewById(R.id.linkTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);

            itemView.setTag(this);
            itemView.setOnClickListener(onClickListener);
        }
    }

    public void setList(ArrayList<RssItem> list){
        this.list = list;
    }
}
