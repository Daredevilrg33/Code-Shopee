package amit.techie33.shopbuycart;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

@SuppressLint({ "HandlerLeak", "NewApi", "ViewHolder" })
public class Home extends Activity implements OnClickListener,
		AnimationListener {

	TextView appname, search, categoryname;
	Typeface font;
	ViewPager viewPager;
	LinearLayout header;
	ProductAdapter ProductAdapter;
	DisplayMetrics displaymetrics;
	int width, height;
	LinearLayout.LayoutParams latoutparams;
	DealAdapter dealadapter;
	String userid = "0", selectedtab = "home";
	SharedPreferences pref;
	ImageView fav;
	Editor editor;
	Dialog dialog;
	int val = 1;
	Animation animFadein;
	ImageView pdimageloader, HomeTab, CategoryTab, OfferTab, CartTab,
			AccountTab;
	Globals globals;
	GridView categorylist;
	CategoryAdapter Categoryadapter;
	ListView list;
	DealAdapterList DealAdapterList;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		fav = (ImageView) findViewById(R.id.fav);

		displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		width = displaymetrics.widthPixels;
		height = displaymetrics.heightPixels;
		globals = (Globals) getApplicationContext();
		latoutparams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, (height / 3));
		font = Typeface.createFromAsset(getApplication().getAssets(),
				"Jacked_Font.ttf");

		header = (LinearLayout) findViewById(R.id.header);
		categorylist = (GridView) findViewById(R.id.categorylist);
		viewPager = (ViewPager) findViewById(R.id.deal);
		search = (TextView) findViewById(R.id.search);
		appname = (TextView) findViewById(R.id.appname);
		HomeTab = (ImageView) findViewById(R.id.home);
		CategoryTab = (ImageView) findViewById(R.id.category);
		OfferTab = (ImageView) findViewById(R.id.offers);
		CartTab = (ImageView) findViewById(R.id.cart);
		AccountTab = (ImageView) findViewById(R.id.account);
		list = (ListView) findViewById(R.id.listview);
		appname.setTypeface(font);
		viewPager.setLayoutParams(latoutparams);
		search.setOnClickListener(this);
		HomeTab.setOnClickListener(this);
		CategoryTab.setOnClickListener(this);
		OfferTab.setOnClickListener(this);
		CartTab.setOnClickListener(this);
		AccountTab.setOnClickListener(this);
		userid = globals.getuserid();

		fav.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog = new Dialog(Home.this);
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

						new Thread(null, productlisting, "refresh_data")
								.start();

					}
				}, 0);

				dialog.show();
				dialog.setCancelable(false);

			}
		});

		animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.blink);
		Loading();

		categorylist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				globals.setcatposition(position);
				startActivity(new Intent(getApplicationContext(),
						Category.class));
			}
		});
		setListViewHeightBasedOnChildren(categorylist);
	}

	private void setListViewHeightBasedOnChildren(GridView list) {
		// TODO Auto-generated method stub

		CategoryAdapter listAdapter = (CategoryAdapter) list.getAdapter();
		if (listAdapter == null)
			return;

		int desiredWidth = MeasureSpec.makeMeasureSpec(list.getWidth(),
				MeasureSpec.UNSPECIFIED);
		int totalHeight = 0;
		View view = null;

		for (int i = 0; i < listAdapter.getCount(); i++) {
			view = listAdapter.getView(i, view, list);

			if (i == 0)
				view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
						LayoutParams.MATCH_PARENT));

			view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
			totalHeight += view.getMeasuredHeight();

		}

		ViewGroup.LayoutParams params = list.getLayoutParams();
		params.height = totalHeight
				+ ((list.getHeight()) * (listAdapter.getCount()));

		list.setLayoutParams(params);
		list.requestLayout();
	}

	private void Loading() {
		// TODO Auto-generated method stub
		switch (userid) {
		case "0":
			dialog = new Dialog(Home.this);
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

					new Thread(null, DealDeatil, "refresh_data").start();
				}
			}, 0);

			dialog.show();
			dialog.setCancelable(false);

			break;

		default:
			dialog = new Dialog(Home.this);
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

					new Thread(null, DealDeatil, "refresh_data").start();
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
		case R.id.search:
			startActivity(new Intent(getApplicationContext(), Search.class));
			finish();
			break;
		case R.id.home:
			selectedtab = "home";

			recreate();
			break;
		case R.id.category:

			dialog = new Dialog(Home.this);
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
		case R.id.offers:
			selectedtab = "deal";
			HomeTab.setImageResource(R.drawable.home);
			CategoryTab.setImageResource(R.drawable.list);
			OfferTab.setImageResource(R.drawable.offer_active);
			CartTab.setImageResource(R.drawable.cart);
			AccountTab.setImageResource(R.drawable.account);
			header.setVisibility(8);
			list.setVisibility(0);
			viewPager.setVisibility(8);
			categorylist.setVisibility(8);
			DealAdapterList = new DealAdapterList(getApplicationContext(),
					globals.getDealList());
			list.setAdapter(DealAdapterList);
			break;
		case R.id.cart:
			if (userid != "0") {
				dialog = new Dialog(Home.this);
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
				dialog = new Dialog(Home.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// setting custom layout to dialog
				dialog.setContentView(R.layout.messagedailog);

				final TextView okbtn = (TextView) dialog
						.findViewById(R.id.confirm);
				TextView message = (TextView) dialog.findViewById(R.id.message);

				message.setText("You Have to Login First");
				startActivity(new Intent(getApplicationContext(), Account.class));
				overridePendingTransition(0, 0);
				finish();
				okbtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				dialog.show();
			}
			break;
		case R.id.account:
			selectedtab = "account";
			dialog = new Dialog(Home.this);
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
	}

	Runnable DealDeatil = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler.DealListing(getApplicationContext());

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			DealListingHandler.sendMessage(msg);

		}
	};

	Handler DealListingHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			String res = msg.obj.toString();

			if (res.equals("sucess")) {
				dialog.dismiss();
				dealadapter = new DealAdapter(getApplicationContext(),
						globals.getDealList());
				viewPager.setAdapter(dealadapter);
				new Thread(null, CategoryDeatil, "refresh_data").start();
			} else {
				dialog.dismiss();
				dialog = new Dialog(Home.this);
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
						dialog = new Dialog(Home.this);
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

	Runnable CategoryDeatil = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler
						.CategoryListing(getApplicationContext());

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			CategoryHandler.sendMessage(msg);

		}
	};

	Handler CategoryHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			String res = msg.obj.toString();

			if (res.equals("sucess")) {
				dialog.dismiss();
				Categoryadapter = new CategoryAdapter(getApplicationContext(),
						globals.getCategoryList());
				categorylist.setAdapter(Categoryadapter);

			} else {
				dialog.dismiss();
				dialog = new Dialog(Home.this);
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
						dialog = new Dialog(Home.this);
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

	public class DealAdapter extends PagerAdapter {

		Context context;
		ArrayList<HashMap<String, String>> Deallist = new ArrayList<HashMap<String, String>>();
		LayoutInflater inflater;

		public DealAdapter(Context c,
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
		public Object instantiateItem(ViewGroup container, final int position) {

			ImageView dealimage;
			LinearLayout indicate;
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View viewLayout = inflater.inflate(R.layout.customdeal, container,
					false);

			dealimage = (ImageView) viewLayout.findViewById(R.id.dealimage);
			indicate = (LinearLayout) viewLayout.findViewById(R.id.indicate);

			dealimage.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (globals.getDealList().get(position).get("dealtype")
							.equals("category")) {

						for (int tempcat = 0; tempcat < globals
								.getCategoryList().size(); tempcat++) {

							if (globals
									.getCategoryList()
									.get(tempcat)
									.get("id")
									.equals(globals.getDealList().get(position)
											.get("typeid"))) {

								globals.setcatposition(tempcat);
								break;

							}

						}

						startActivity(new Intent(getApplicationContext(),
								Category.class));
						finish();
						overridePendingTransition(0, 0);
					} else if (globals.getDealList().get(position)
							.get("dealtype").equals("subcategory")) {
						Intent product = new Intent(getApplicationContext(),
								Product.class);
						product.putExtra(
								"subcatid",
								globals.getDealList().get(position)
										.get("typeid"));
						product.putExtra(
								"catid",
								globals.getSubCategoryList().get(position).get("catid"));
						
						startActivity(product);
						overridePendingTransition(0, 0);

					} else if (globals.getDealList().get(position)
							.get("dealtype").equals("product")) {

						Intent select = new Intent(getApplicationContext(),
								ProductDetail.class);
						// TODO Auto-generated method stub
						select.putExtra(
								"productid",
								globals.getDealList().get(position)
										.get("typeid"));
						startActivity(select);
						overridePendingTransition(0, 0);
					}
					// TODO Auto-generated method stub

				}
			});
			Picasso.with(context)
					.load("http://emerchantshop.com/shopbuycart_app/deal_image/"
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

	class CategoryAdapter extends BaseAdapter {

		private Context context;
		LayoutInflater mInflater;
		ImageView catgeoryimage;
		String image_url;
		ArrayList<HashMap<String, String>> categorieslist = new ArrayList<HashMap<String, String>>();

		public CategoryAdapter(Context c,
				ArrayList<HashMap<String, String>> categorylist) {
			this.categorieslist = categorylist;

			this.context = c;
			mInflater = LayoutInflater.from(context);
		}

		public View getGroupView(int i, boolean b, View view, GridView list) {
			// TODO Auto-generated method stub
			return null;
		}

		public int getCount() {
			return categorieslist.size();

		}

		public Object getItem(int position) {
			return categorieslist.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressWarnings("unused")
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			ViewHolder holder;
			HashMap<String, String> request = new HashMap<String, String>();
			request = categorieslist.get(position);

			holder = new ViewHolder();

			convertView = (LinearLayout) mInflater.inflate(R.layout.customgrid,
					null);

			categoryname = (TextView) convertView
					.findViewById(R.id.categoryname);

			LinearLayout.LayoutParams latoutparams = new LinearLayout.LayoutParams(
					(height / 3), (height / 3));
			catgeoryimage = (ImageView) convertView
					.findViewById(R.id.catgeoryimage);
			categoryname.setTypeface(font);

			holder = (ViewHolder) convertView.getTag();

			Picasso.with(context)
					.load("http://emerchantshop.com/shopbuycart_app/cat_image/"
							+ categorieslist.get(position).get("image"))
					.into(catgeoryimage);
			catgeoryimage.setLayoutParams(latoutparams);
			categoryname.setText(categorieslist.get(position).get("catgeory"));

			return convertView;
		}

		class ViewHolder {

		}

	}

	class DealAdapterList extends BaseAdapter {

		private Context context;
		LayoutInflater mInflater;
		ImageView dealimage;
		String image_url;
		ArrayList<HashMap<String, String>> Deallist = new ArrayList<HashMap<String, String>>();

		public DealAdapterList(Context c,
				ArrayList<HashMap<String, String>> categorylist) {
			this.Deallist = categorylist;

			this.context = c;
			mInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return Deallist.size();

		}

		public Object getItem(int position) {
			return Deallist.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressWarnings("unused")
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			ViewHolder holder;
			HashMap<String, String> request = new HashMap<String, String>();
			request = Deallist.get(position);

			// convertView=null;
			holder = new ViewHolder();

			convertView = (LinearLayout) mInflater.inflate(R.layout.customdeal,
					null);

			dealimage = (ImageView) convertView.findViewById(R.id.dealimage);
			dealimage.setLayoutParams(latoutparams);
			holder = (ViewHolder) convertView.getTag();

			Picasso.with(context)
					.load("http://emerchantshop.com/shopbuycart_app/deal_image/"
							+ Deallist.get(position).get("image"))
					.into(dealimage);

			return convertView;
		}

		class ViewHolder {

		}

	}

	Runnable productlisting = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler
						.ProductFavListing(getApplicationContext());

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

				header.setVisibility(8);
				list.setVisibility(8);
				viewPager.setVisibility(8);
				categorylist.setVisibility(0);
				ProductAdapter = new ProductAdapter(getApplicationContext(),
						globals.getFavProductList());
				categorylist.setAdapter(ProductAdapter);
				ProductAdapter.notifyDataSetChanged();

			} else {
				dialog.dismiss();
				dialog = new Dialog(Home.this);
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
						dialog = new Dialog(Home.this);
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

			ImageView productimage = (ImageView) convertView
					.findViewById(R.id.productimage);
			TextView productname = (TextView) convertView
					.findViewById(R.id.name);
			TextView oldprice = (TextView) convertView
					.findViewById(R.id.oldprice);
			TextView newprice = (TextView) convertView
					.findViewById(R.id.newprice);
			TextView discountprice = (TextView) convertView
					.findViewById(R.id.discount);
			rl = (LinearLayout) convertView.findViewById(R.id.rl);
			if (val == 1) {
				RelativeLayout.LayoutParams latoutparams = new RelativeLayout.LayoutParams(
						LinearLayout.LayoutParams.FILL_PARENT, (height / 3));
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
			oldprice.setPaintFlags(oldprice.getPaintFlags()
					& (~Paint.STRIKE_THRU_TEXT_FLAG));
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
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		if (animation == animFadein) {

		}

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}
}
