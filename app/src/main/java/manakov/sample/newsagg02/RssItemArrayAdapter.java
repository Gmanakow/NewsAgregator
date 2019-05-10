package manakov.sample.newsagg02;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RssItemArrayAdapter extends ArrayAdapter<RssItem> {
    private Context context;
    private RssItemPack items = new RssItemPack();

    public RssItemArrayAdapter(@NonNull Context context, RssItemPack items){
        super(context, 0, items.getList());
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View viewItem = convertView;
        if (viewItem == null) {
            viewItem = LayoutInflater.from(context).inflate(R.layout.list_item_rss, parent, false);
        }

        RssItem item = items.get(position);
        TextView textView = viewItem.findViewById(R.id.textField);
        textView.setText(item.getTitle());
        return viewItem;
    }

}
