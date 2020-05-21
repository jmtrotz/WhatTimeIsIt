package com.jeffTrotz.whatTimeIsIt;

// Import packages
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Adapter for the RecyclerView that generates the cards to display the city name and time.
 * @author Jeffrey Trotz
 * @version 2.0
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    private LinkedHashMap<String, String> mData;  // Hash map containing the city name/time
    private LayoutInflater mInflater;   // Used to instantiate the contents of the layout XML file

    /**
     * Constructor
     * @param context Current state of the application
     * @param data  HashMap of city names/times
     */
    RecyclerViewAdapter(Context context, LinkedHashMap<String, String> data)
    {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    /**
     * Called when RecyclerView needs a new {@link RecyclerView.ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(RecyclerView.ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.     *
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(RecyclerView.ViewHolder, int)
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.recycler_view_row, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link RecyclerView.ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link android.widget.ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link RecyclerView.ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * Override {@link #onBindViewHolder(RecyclerView.ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item
     *               at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        // Keeps track of the current position in the Map
        int i = 0;

        // Iterates through the map and displays the data in the recycler view card
        for (Map.Entry<String, String> entry : mData.entrySet())
        {
            // If the position matches the current count, display the
            // key and value  from the map in their respective TextViews
            if (position == i)
            {
                holder.labelTV.setText(entry.getKey());
                holder.timeTV.setText(entry.getValue());
                break;
            }

            // Increase count
            i++;
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    // Stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView labelTV;   // TextView that displays the city name
        TextView timeTV;    // TextView that displays the city time

        /**
         * Constructor
         * @param itemView Used to locate view elements
         */
        ViewHolder(View itemView)
        {
            super(itemView);
            labelTV = itemView.findViewById(R.id.labelTV);
            timeTV = itemView.findViewById(R.id.timeTV);
        }
    }
}