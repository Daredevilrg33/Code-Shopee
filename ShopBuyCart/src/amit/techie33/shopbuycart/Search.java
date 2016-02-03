package amit.techie33.shopbuycart;

import java.util.ArrayList;
import java.util.HashMap;

import amit.techie33.shopbuycart.Cart.AdapterList;
import amit.techie33.shopbuycart.Cart.AdapterList.ViewHolder;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class Search extends Activity implements OnClickListener {
	TextView close;
	Typeface font;
	String currentorderid;
	EditText searchtxt;
	Dialog dialog;
	ImageView pdimageloader;
	Animation animFadein;
	Globals globals;
	ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		list = (ListView) findViewById(R.id.listView1);
		globals = (Globals) getApplicationContext();
		searchtxt = (EditText) findViewById(R.id.search);
		close = (TextView) findViewById(R.id.close);
		font = Typeface.createFromAsset(getApplication().getAssets(),
				"Jacked_Font.ttf");
		close.setTypeface(font);
		animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.blink);

		close.setOnClickListener(this);

		searchtxt.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					// do something
					currentorderid = v.getText().toString();
					dialog = new Dialog(Search.this);
					dialog.getWindow().setBackgroundDrawable(
							new ColorDrawable(
									android.graphics.Color.TRANSPARENT));
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					// setting custom layout to dialog
					dialog.setContentView(R.layout.progressdialog);

					pdimageloader = (ImageView) dialog
							.findViewById(R.id.pdimageloader);

					pdimageloader.startAnimation(animFadein);
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {

							new Thread(null, search, "refresh_data").start();

						}
					}, 0);

					dialog.show();
					dialog.setCancelable(false);
				}
				return false;
			}

		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.close:
			startActivity(new Intent(getApplicationContext(), Home.class));
			finish();
			break;

		default:
			break;
		}
	}

	Runnable search = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler.searchOrderlist(
						getApplicationContext(), searchtxt.getText().toString());

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			searchHandler.sendMessage(msg);

		}
	};

	Handler searchHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			String res = msg.obj.toString();

			if (res.equals("sucess")) {
				dialog.dismiss();
				AdapterList subcatLists = new AdapterList(
						getApplicationContext(),
						globals.getSearchOrderListall());
				list.setAdapter(subcatLists);
				subcatLists.notifyDataSetChanged();

			} else {
				dialog.dismiss();
				dialog = new Dialog(Search.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// setting custom layout to dialog
				dialog.setContentView(R.layout.progressdialog);

				ImageView pdimageloader = (ImageView) dialog
						.findViewById(R.id.pdimageloader);

				pdimageloader.startAnimation(animFadein);
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {

						dialog.dismiss();
						dialog = new Dialog(Search.this);
						dialog.getWindow().setBackgroundDrawable(
								new ColorDrawable(
										android.graphics.Color.TRANSPARENT));
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						// setting custom layout to dialog
						dialog.setContentView(R.layout.messagedailog);

						final TextView okbtn = (TextView) dialog
								.findViewById(R.id.confirm);

						okbtn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
						dialog.show();

					}
				}, 2000);

				dialog.show();

			}

		}
	};

	public class AdapterList extends BaseAdapter {

		private Context context;
		LayoutInflater mInflater;
		TextView productname, size, quantity, price;
		CircularImageView subcatimage;
		ArrayList<HashMap<String, String>> customsubcat = new ArrayList<HashMap<String, String>>();

		public AdapterList(Context c,
				ArrayList<HashMap<String, String>> customsubcat) {
			this.customsubcat = customsubcat;

			this.context = c;
			mInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return customsubcat.size();

		}

		public Object getItem(int position) {
			return customsubcat.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressWarnings("unused")
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			ViewHolder holder;
			HashMap<String, String> request = new HashMap<String, String>();
			request = customsubcat.get(position);

			holder = new ViewHolder();

			convertView = (RelativeLayout) mInflater.inflate(
					R.layout.showorder, null);

			Button status = (Button) convertView.findViewById(R.id.status);
			productname = (TextView) convertView.findViewById(R.id.productname);
			size = (TextView) convertView.findViewById(R.id.size);
			quantity = (TextView) convertView.findViewById(R.id.quntity);
			price = (TextView) convertView.findViewById(R.id.price);
			productname.setTypeface(font);
			size.setTypeface(font);
			quantity.setTypeface(font);
			status.setVisibility(0);
			price.setTypeface(font);
			TextView addressorder = (TextView) convertView
					.findViewById(R.id.addressorder);

			LinearLayout addd = (LinearLayout) convertView
					.findViewById(R.id.addd);

			holder = (ViewHolder) convertView.getTag();

			addressorder.setText(customsubcat.get(position).get("address"));
			if (position > 0) {
				addd.setVisibility(8);
			}

			status.setText("CURRENT STATUS :  "
					+ customsubcat.get(position).get("status").toUpperCase());
			productname.setText("PRODUCT   NAME  : "
					+ customsubcat.get(position).get("name"));

			size.setText("PRODUCT   SIZE  : "
					+ customsubcat.get(position).get("size"));

			quantity.setText("PRODUCT   QUANTITY  : "
					+ customsubcat.get(position).get("quantity"));

			price.setText("PRODUCT   PRICE  : "
					+ customsubcat.get(position).get("discountprice"));

			return convertView;

		}

		class ViewHolder {

		}

	}

}
