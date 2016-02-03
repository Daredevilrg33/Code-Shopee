package amit.techie33.shopbuycart;

import java.util.ArrayList;

import android.content.Context;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
    private ArrayList<String> resultList;
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String LOG_TAG = "ExampleApp";
    private LayoutInflater mInflater;
	private Geocoder mGeocoder;
	private StringBuilder mSb = new StringBuilder();
    private static final String API_KEY = "AIzaSyALSq1vM64PvFrl3TDpD9VTIysax0Eubos";

    
    public AutoCompleteAdapter(final Context context) {
		super(context, -1);
		mInflater = LayoutInflater.from(context);
		mGeocoder = new Geocoder(context);
	}
 
    
   

	@Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocomplete results.

                   /*resultList = CreateInukshk.autocomplete(constraint
                            .toString());*/

                    // Assign the data to the FilterResults
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint,
                    FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }
    
}