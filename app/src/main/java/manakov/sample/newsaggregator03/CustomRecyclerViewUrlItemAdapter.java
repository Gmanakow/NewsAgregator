package manakov.sample.newsaggregator03;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CustomRecyclerViewUrlItemAdapter extends RecyclerView.Adapter<CustomRecyclerViewUrlItemAdapter.ViewHolder> {
    private ArrayList<UrlItem> list;
    private View.OnClickListener onClickListener;

    public CustomRecyclerViewUrlItemAdapter(ArrayList<UrlItem> list){
        this.list = list;
    }

    @Override
    public CustomRecyclerViewUrlItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.url_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomRecyclerViewUrlItemAdapter.ViewHolder viewHolder, int position){
        viewHolder.titleView.setText( list.get(position).getTitle() );
        viewHolder.urlView  .setText( list.get(position).getUrl()   );
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        this.onClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titleView;
        public TextView urlView;

        public ViewHolder(View itemView){
            super(itemView);

            titleView = itemView.findViewById( R.id.urlItemTitleView );
            urlView   = itemView.findViewById( R.id.urlItemUrlView   );

            itemView.setTag(this);
            itemView.setOnClickListener(onClickListener);
        }
    }

    public void setList(ArrayList<UrlItem> list){
        this.list = list;
    }
}
