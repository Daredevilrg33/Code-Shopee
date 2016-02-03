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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import database.DatabaseHelper;

public class ProductDetail extends Activity implements OnClickListener,
		OnItemSelectedListener {

	DisplayMetrics displaymetrics;
	int width, height, val = 1;
	RelativeLayout.LayoutParams latoutparams;
	Globals globals;
	Animation animFadein;
	Typeface font;
	String userid, productid, qty = "", totalprice = "", strsize = "";
	Dialog dialog;
	ImageView fav, back, pdimageloader;
	Button addtocart;
	TextView name, oldprice, newprice, description, dealdetail, review,
			descriptiontext, dealdetailtext, appname, discount;
	ViewPager imagepager;
	Spinner size, Quantity;
	ImageAdapter imageadapter;
	ScrollView background;

	LinearLayout sizelist;
	ArrayList<String> quanty;
	DatabaseHelper db;
	RatingBar ratings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productdetail);
		db = new DatabaseHelper(getApplicationContext());
		displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		width = displaymetrics.widthPixels;
		height = displaymetrics.heightPixels;
		globals = (Globals) getApplicationContext();
		latoutparams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT, (width / 2) + 30);
		animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.blink);
		font = Typeface.createFromAsset(getApplication().getAssets(),
				"Jacked_Font.ttf");

		sizelist = (LinearLayout) findViewById(R.id.sizelist);
		name = (TextView) findViewById(R.id.name);
		ratings = (RatingBar) findViewById(R.id.rating);

		oldprice = (TextView) findViewById(R.id.oldprice);
		newprice = (TextView) findViewById(R.id.newprice);
		description = (TextView) findViewById(R.id.description);
		dealdetail = (TextView) findViewById(R.id.dealdetail);
		review = (TextView) findViewById(R.id.review);
		discount = (TextView) findViewById(R.id.discount);
		fav = (ImageView) findViewById(R.id.fav);
		appname = (TextView) findViewById(R.id.appname);
		descriptiontext = (TextView) findViewById(R.id.descriptiontext);
		dealdetailtext = (TextView) findViewById(R.id.dealtext);
		addtocart = (Button) findViewById(R.id.addtocart);
		imagepager = (ViewPager) findViewById(R.id.imagepager);
		back = (ImageView) findViewById(R.id.back);

		back.setOnClickListener(this);
		addtocart.setOnClickListener(this);
		appname.setTypeface(font);
		addtocart.setTypeface(font);
		discount.setTypeface(font);
		name.setTypeface(font);
		descriptiontext.setTypeface(font);
		dealdetailtext.setTypeface(font);
		review.setTypeface(font);

		size = (Spinner) findViewById(R.id.size);
		background = (ScrollView) findViewById(R.id.background);
		Quantity = (Spinner) findViewById(R.id.Quantity);

		ratings.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub

			}

		});

		fav = (ImageView) findViewById(R.id.fav);

		addtocart.setOnClickListener(this);
		fav.setOnClickListener(this);
		imagepager.setLayoutParams(latoutparams);

		userid = globals.getuserid();
		productid = getIntent().getStringExtra("productid");
		Loading();

		size.setOnItemSelectedListener(this);
		fav.setOnClickListener(this);

	}

	protected void Loadingsize(final int position) {
		dialog = new Dialog(ProductDetail.this);
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

				Quantity(position);

			}
		}, 0);

		dialog.show();
		dialog.setCancelable(false);
		// TODO Auto-generated method stub

	}

	private void Quantity(int pos) {
		// TODO Auto-generated method stub
		if (globals.getSizeList().get(pos).get("quantity") != null) {

			int val = Integer.parseInt(globals.getSizeList()
					.get(size.getSelectedItemPosition()).get("quantity"));
			quanty = new ArrayList<String>();
			strsize = globals.getSizeList().get(pos).get("size");

			for (int s = 0; s < val; s++) {
				quanty.add(String.valueOf(s + 1));
			}

			ArrayAdapter<String> quanty_list = new ArrayAdapter<String>(
					ProductDetail.this, android.R.layout.simple_spinner_item,
					quanty);
			quanty_list
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			dialog.dismiss();
			Quantity.setAdapter(quanty_list);

		}
	}

	private void Loading() {
		// TODO Auto-generated method stub
		switch (userid) {
		case "0":
			dialog = new Dialog(ProductDetail.this);
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

					new Thread(null, SearchProductDeatil, "refresh_data")
							.start();

				}
			}, 0);

			dialog.show();
			dialog.setCancelable(false);

			break;

		default:

			int chk = db.CheckFAV(productid);
			if (chk > 0) {
				fav.setImageResource(R.drawable.heart_fill);
			} else {
				fav.setImageResource(R.drawable.heart_active);
			}
			dialog = new Dialog(ProductDetail.this);
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

					new Thread(null, SearchProductDeatil, "refresh_data")
							.start();

				}
			}, 0);

			dialog.show();
			dialog.setCancelable(false);

			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.back:

			finish();

			break;

		case R.id.fav:
			if (userid == "0") {
				dialog = new Dialog(this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// setting custom layout to dialog
				dialog.setContentView(R.layout.messagedailog);

				final TextView okbtn = (TextView) dialog
						.findViewById(R.id.confirm);
				TextView message = (TextView) dialog.findViewById(R.id.message);

				message.setText("WITHOUT LOGIN !\n FOR FAVOURITE YOU HAVE TO LOGIN");
				message.setTypeface(font);
				okbtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						startActivity(new Intent(ProductDetail.this,
								Account.class));
						finish();
						overridePendingTransition(0, 0);
					}
				});
				dialog.show();

			} else {
				int chk = db.CheckFAV(productid);
				if (chk > 0) {
					fav.setImageResource(R.drawable.heart_active);
					db.UNFAV(productid);
				} else {
					db.INSERT_FAV(productid);
					fav.setImageResource(R.drawable.heart_fill);
				}
			}
			break;

		case R.id.addtocart:
			if (userid == "0") {
				dialog = new Dialog(this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// setting custom layout to dialog
				dialog.setContentView(R.layout.messagedailog);

				final TextView okbtn = (TextView) dialog
						.findViewById(R.id.confirm);
				TextView message = (TextView) dialog.findViewById(R.id.message);

				message.setText(" You Have To Login First !");
				okbtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						startActivity(new Intent(ProductDetail.this,
								Account.class));
						finish();
						overridePendingTransition(0, 0);
					}
				});
				dialog.show();

			} else {
				if (sizelist.getVisibility() == View.VISIBLE) {
					strsize = globals.getSize().get(
							size.getSelectedItemPosition());
					qty = String
							.valueOf(Quantity.getSelectedItemPosition() + 1);
					if (strsize.length() > 0 && qty.length() > 0) {

						new Thread(null, AddtoCart, "refresh_data").start();

					} else {
						dialog = new Dialog(this);
						dialog.getWindow().setBackgroundDrawable(
								new ColorDrawable(
										android.graphics.Color.TRANSPARENT));
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						// setting custom layout to dialog
						dialog.setContentView(R.layout.messagedailog);

						final TextView okbtn = (TextView) dialog
								.findViewById(R.id.confirm);
						TextView message = (TextView) dialog
								.findViewById(R.id.message);

						message.setText(" Sorry Fail to load size and Quantity !");
						okbtn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								dialog.dismiss();
							}
						});
						dialog.show();
					}
				} else {
					dialog = new Dialog(this);
					dialog.getWindow().setBackgroundDrawable(
							new ColorDrawable(
									android.graphics.Color.TRANSPARENT));
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					// setting custom layout to dialog
					dialog.setContentView(R.layout.messagedailog);

					final TextView okbtn = (TextView) dialog
							.findViewById(R.id.confirm);
					TextView message = (TextView) dialog
							.findViewById(R.id.message);

					message.setText(" Please Select the size and Quantity of product !");
					okbtn.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}

			break;
		default:
			break;
		}
	};

	Runnable AddtoCart = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler.AddtoCart(getApplicationContext(),
						productid, userid, qty, totalprice, strsize);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;

			AddtoCartHandler.sendMessage(msg);

		}
	};

	Handler AddtoCartHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			final String res = msg.obj.toString();

			if (res.equals("sucess")) {
				dialog.dismiss();

				startActivity(new Intent(getApplicationContext(), Cart.class));
				finish();

			} else {
				dialog.dismiss();
				dialog = new Dialog(ProductDetail.this);
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
						dialog = new Dialog(ProductDetail.this);
						dialog.getWindow().setBackgroundDrawable(
								new ColorDrawable(
										android.graphics.Color.TRANSPARENT));
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						// setting custom layout to dialog
						dialog.setContentView(R.layout.messagedailog);

						final TextView okbtn = (TextView) dialog
								.findViewById(R.id.confirm);
						TextView message = (TextView) dialog
								.findViewById(R.id.message);

						message.setText(res);
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

	public class ImageAdapter extends PagerAdapter {

		Context context;
		ArrayList<HashMap<String, String>> Deallist = new ArrayList<HashMap<String, String>>();
		LayoutInflater inflater;

		public ImageAdapter(Context c,
				ArrayList<HashMap<String, String>> deallist) {
			this.context = c;
			this.Deallist = deallist;
		}

		@Override
		public int getCount() {
			return this.Deallist.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == ((LinearLayout) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			ImageView dealimage;
			LinearLayout indicate;
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View viewLayout = inflater.inflate(R.layout.customdeal, container,
					false);

			dealimage = (ImageView) viewLayout.findViewById(R.id.dealimage);
			indicate = (LinearLayout) viewLayout.findViewById(R.id.indicate);

			dealimage.setScaleType(ScaleType.CENTER_INSIDE);
			Picasso.with(context)
					.load("http://emerchantshop.com/shopbuycart_app/product_image/"
							+ Deallist.get(position).get("image"))
					.into(dealimage);
			for (int indicator = 0; indicator < Deallist.size(); indicator++) {
				ImageView incatorimage = new ImageView(context);

				if (indicator == position) {
					incatorimage.setImageResource(R.drawable.fill);
				} else {
					incatorimage.setImageResource(R.drawable.count);
				}
				indicate.addView(incatorimage);
			}

			((ViewPager) container).addView(viewLayout);

			return viewLayout;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((LinearLayout) object);

		}

	}

	Runnable sizelistservice = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler.sizelist(getApplicationContext(),
						productid);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			SizeHandler.sendMessage(msg);

		}
	};

	Handler SizeHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			final String res = msg.obj.toString();

			if (res.equals("sucess")) {
				dialog.dismiss();
				sizelist.setVisibility(0);

				ArrayAdapter<String> size_list = new ArrayAdapter<String>(
						ProductDetail.this,
						android.R.layout.simple_spinner_item, globals.getSize());
				size_list
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				size.setAdapter(size_list);
				Loadingsize(0);

			} else {
				dialog.dismiss();
				dialog = new Dialog(ProductDetail.this);
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
						dialog = new Dialog(ProductDetail.this);
						dialog.getWindow().setBackgroundDrawable(
								new ColorDrawable(
										android.graphics.Color.TRANSPARENT));
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						// setting custom layout to dialog
						dialog.setContentView(R.layout.messagedailog);

						final TextView okbtn = (TextView) dialog
								.findViewById(R.id.confirm);
						TextView message = (TextView) dialog
								.findViewById(R.id.message);

						message.setText(res);
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
	Runnable SearchProductDeatil = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler.searchProduct(
						getApplicationContext(), productid);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			SearchProductDeatilHandler.sendMessage(msg);

		}
	};

	Handler SearchProductDeatilHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			String res = msg.obj.toString();

			if (res.equals("sucess")) {
				dialog.dismiss();
				imageadapter = new ImageAdapter(getApplicationContext(),
						globals.getProductImageList());
				imagepager.setAdapter(imageadapter);
				new Thread(null, sizelistservice, "refresh_data").start();
				detail();

			} else {
				dialog.dismiss();
				dialog = new Dialog(ProductDetail.this);
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
						dialog = new Dialog(ProductDetail.this);
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

	protected void detail() {
		// TODO Auto-generated method stub
		name.setText(globals.getSelectProductList().get(0).get("name"));

		oldprice.setText(globals.getSelectProductList().get(0).get("mrp"));
		oldprice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		oldprice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG
				| Paint.ANTI_ALIAS_FLAG);
		newprice.setText(globals.getSelectProductList().get(0)
				.get("discountprice"));
		description.setText(globals.getSelectProductList().get(0)
				.get("description"));
		dealdetail.setText("No Deal Available");
		review.setText("No Review Available");
		discount.setText(globals.getSelectProductList().get(0).get("discount")
				+ " % OFF");
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.size:
			Loadingsize(pos);

			break;

		default:
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}
