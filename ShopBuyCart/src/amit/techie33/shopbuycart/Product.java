package amit.techie33.shopbuycart;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Product extends Activity implements OnClickListener {

	DisplayMetrics displaymetrics;
	int width, height, val = 1;
	RelativeLayout.LayoutParams latoutparams;
	Globals globals;
	Animation animFadein;
	Typeface font;
	TextView appname;
	String userid ="0" , subcatid = "",catid="";
	GridView productgrid;
	ListView productlist;
	Dialog dialog;
	GPSTracker gps;
	ImageView pdimageloader, back, list, table, HomeTab, CategoryTab, OfferTab,
			CartTab, AccountTab;
	ProductAdapter ProductAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product);

		gps = new GPSTracker(getApplicationContext());
		displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		width = displaymetrics.widthPixels;
		height = displaymetrics.heightPixels;
		globals = (Globals) getApplicationContext();
		latoutparams = new RelativeLayout.LayoutParams((width / 2) - 10,
				(width / 2) + 30);
		animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.blink);
		font = Typeface.createFromAsset(getApplication().getAssets(),
				"Jacked_Font.ttf");
		appname = (TextView) findViewById(R.id.appname);

		list = (ImageView) findViewById(R.id.list);
		productlist = (ListView) findViewById(R.id.listproduct);
		table = (ImageView) findViewById(R.id.table);
		globals = (Globals) getApplicationContext();
		subcatid = getIntent().getStringExtra("subcatid");
		catid = getIntent().getStringExtra("catid");
		productgrid = (GridView) findViewById(R.id.gridproduct);
		back = (ImageView) findViewById(R.id.back);
		HomeTab = (ImageView) findViewById(R.id.home);
		CategoryTab = (ImageView) findViewById(R.id.category);
		OfferTab = (ImageView) findViewById(R.id.offers);
		CartTab = (ImageView) findViewById(R.id.cart);
		AccountTab = (ImageView) findViewById(R.id.account);
		appname.setTypeface(font);
		userid = globals.getuserid();

		HomeTab.setOnClickListener(this);
		CategoryTab.setOnClickListener(this);
		OfferTab.setOnClickListener(this);
		CartTab.setOnClickListener(this);
		AccountTab.setOnClickListener(this);
		list.setOnClickListener(this);
		table.setOnClickListener(this);
		back.setOnClickListener(this);

		Fetchdetail();
	}

	private void Fetchdetail() {
		// TODO Auto-generated method stub
		switch (userid) {
		case "0":
			dialog = new Dialog(Product.this);
			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// setting custom layout to dialog
			dialog.setContentView(R.layout.progressdialog);

			pdimageloader = (ImageView) dialog.findViewById(R.id.pdimageloader);

			pdimageloader.startAnimation(animFadein);
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {

					new Thread(null, productlisting, "refresh_data").start();

				}
			}, 0);

			dialog.show();
			dialog.setCancelable(false);
			break;

		default:
			dialog = new Dialog(Product.this);
			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// setting custom layout to dialog
			dialog.setContentView(R.layout.progressdialog);

			pdimageloader = (ImageView) dialog.findViewById(R.id.pdimageloader);

			pdimageloader.startAnimation(animFadein);
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {

					new Thread(null, productlisting, "refresh_data").start();

				}
			}, 0);

			dialog.show();
			dialog.setCancelable(false);
			break;
		}
	}

	Runnable productlisting = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler.ProductListing(
						getApplicationContext(), subcatid,catid,String.valueOf(gps.latitude),String.valueOf(gps.longitude));

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			productlistingHandler.sendMessage(msg);

		}
	};

	Handler productlistingHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			String res = msg.obj.toString();

			if (res.equals("sucess")) {
				dialog.dismiss();
				ProductAdapter = new ProductAdapter(getApplicationContext(),
						globals.getProductList());
				productgrid.setAdapter(ProductAdapter);

			} else {
				dialog.dismiss();
				dialog = new Dialog(Product.this);
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
						dialog = new Dialog(Product.this);
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

	class ProductAdapter extends BaseAdapter {

		Context context;
		LayoutInflater mInflater;
		ImageView productimage;
		String image_url;
		ArrayList<HashMap<String, String>> productlist = new ArrayList<HashMap<String, String>>();
		TextView productname, oldprice, newprice, discountprice;
		LinearLayout rl;

		public ProductAdapter(Context c,
				ArrayList<HashMap<String, String>> categorylist) {
			this.productlist = categorylist;

			this.context = c;
			mInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return productlist.size();

		}

		public Object getItem(int position) {
			return productlist.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressWarnings("unused")
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			ViewHolder holder;
			HashMap<String, String> request = new HashMap<String, String>();
			request = productlist.get(position);

			// convertView=null;
			holder = new ViewHolder();

			convertView = (RelativeLayout) mInflater.inflate(
					R.layout.gridproduct, null);

			productimage = (ImageView) convertView
					.findViewById(R.id.productimage);
			productname = (TextView) convertView.findViewById(R.id.name);
			oldprice = (TextView) convertView.findViewById(R.id.oldprice);
			newprice = (TextView) convertView.findViewById(R.id.newprice);
			discountprice = (TextView) convertView.findViewById(R.id.discount);
			rl = (LinearLayout) convertView.findViewById(R.id.rl);
			if (val == 1) {
				rl.setLayoutParams(latoutparams);
			} else {
				RelativeLayout.LayoutParams latoutparams1 = new RelativeLayout.LayoutParams(
						(width - 10), width - 10);
				rl.setLayoutParams(latoutparams1);
			}
			holder = (ViewHolder) convertView.getTag();

			Picasso.with(context)
					.load("http://emerchantshop.com/shopbuycart_app/product_image/"
							+ productlist.get(position).get("image"))
					.into(productimage);

			productname.setTypeface(font);
			oldprice.setTypeface(font);
			newprice.setTypeface(font);
			discountprice.setTypeface(font);
			productname.setText(productlist.get(position).get("name"));
			oldprice.setText(productlist.get(position).get("discountprice"));
			oldprice.setPaintFlags(oldprice.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
			newprice.setText(productlist.get(position).get("mrp"));
			discountprice.setText(productlist.get(position).get("discount")
					+ " %");

			productimage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent select = new Intent(getApplicationContext(),
							ProductDetail.class);
					// TODO Auto-generated method stub
					select.putExtra("productid",
							productlist.get(position).get("id"));
					startActivity(select);
				}
			});
			return convertView;
		}

		class ViewHolder {

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.back:
			finish();

			break;
		case R.id.list:
			val = 0;

			productlist.setVisibility(0);
			productgrid.setVisibility(8);
			list.setImageResource(R.drawable.list_active);
			table.setImageResource(R.drawable.grid);

			ProductAdapter = new ProductAdapter(getApplicationContext(),
					globals.getProductList());
			productlist.setAdapter(ProductAdapter);
			break;
		case R.id.table:
			val = 1;
			list.setImageResource(R.drawable.list);
			table.setImageResource(R.drawable.grid_active);
			productlist.setVisibility(8);
			productgrid.setVisibility(0);

			ProductAdapter = new ProductAdapter(getApplicationContext(),
					globals.getProductList());
			productgrid.setAdapter(ProductAdapter);
			break;

		case R.id.home:
			dialog = new Dialog(Product.this);
			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// setting custom layout to dialog
			dialog.setContentView(R.layout.progressdialog);

			pdimageloader = (ImageView) dialog.findViewById(R.id.pdimageloader);

			pdimageloader.startAnimation(animFadein);
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					dialog.dismiss();
					globals.setcatposition(0);
					startActivity(new Intent(getApplicationContext(),
							Home.class));

					overridePendingTransition(0, 0);
				}
			}, 0);

			dialog.show();
			dialog.setCancelable(false);

			break;
		case R.id.category:

			dialog = new Dialog(Product.this);
			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// setting custom layout to dialog
			dialog.setContentView(R.layout.progressdialog);

			pdimageloader = (ImageView) dialog.findViewById(R.id.pdimageloader);

			pdimageloader.startAnimation(animFadein);
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					dialog.dismiss();
					globals.setcatposition(0);
					startActivity(new Intent(getApplicationContext(),
							Category.class));
					finish();
					overridePendingTransition(0, 0);
				}
			}, 0);

			dialog.show();
			dialog.setCancelable(false);

			break;

		case R.id.cart:
			if (userid != "0") {
				dialog = new Dialog(Product.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// setting custom layout to dialog
				dialog.setContentView(R.layout.progressdialog);

				pdimageloader = (ImageView) dialog
						.findViewById(R.id.pdimageloader);

				pdimageloader.startAnimation(animFadein);
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						dialog.dismiss();
						globals.setcatposition(0);
						startActivity(new Intent(getApplicationContext(),
								Cart.class));
						finish();
						overridePendingTransition(0, 0);
					}
				}, 0);

				dialog.show();
				dialog.setCancelable(false);
			} else {
				dialog = new Dialog(Product.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// setting custom layout to dialog
				dialog.setContentView(R.layout.messagedailog);

				final TextView okbtn = (TextView) dialog
						.findViewById(R.id.confirm);
				TextView message = (TextView) dialog.findViewById(R.id.message);

				message.setText("You Have to Login First");
				okbtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						recreate();
					}
				});
				dialog.show();
			}
			break;
		case R.id.account:

			dialog = new Dialog(Product.this);
			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// setting custom layout to dialog
			dialog.setContentView(R.layout.progressdialog);

			pdimageloader = (ImageView) dialog.findViewById(R.id.pdimageloader);

			pdimageloader.startAnimation(animFadein);
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					dialog.dismiss();
					globals.setcatposition(0);
					startActivity(new Intent(getApplicationContext(),
							Account.class));

					overridePendingTransition(0, 0);
				}
			}, 0);

			dialog.show();
			dialog.setCancelable(false);
			break;
		default:
			break;
		}
	};

}
