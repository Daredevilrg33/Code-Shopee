package amit.techie33.shopbuycart;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import database.DatabaseHelper;

@SuppressLint("NewApi")
public class AdminSubcat extends Activity implements OnClickListener,
		AnimationListener, OnItemClickListener {

	CatAdapter catadapter;
	Typeface font;
	LinearLayout footer;
	ViewPager categories;
	ImageView pdimageloader, HomeTab, back, CategoryTab, OfferTab, CartTab,
			AccountTab;
	Dialog dialog;
	Animation animFadein;
	String subcatid, newcatimage, userid, selectedtab = "category",
			tempcategory;
	Globals globals;
	private Uri mUri;
	private static final int IMAGE_GALLERY = 0, IMAGE_CAMERA = 1;
	private String selectedImagePath;
	Bitmap mBitmap;
	SubAdapterList subcatList;
	DisplayMetrics displaymetrics;
	int width, height;
	LinearLayout.LayoutParams latoutparams;
	DealAdapterList DealAdapterList;
	ListView list;
	int catposition = 0;
	ArrayList<HashMap<String, String>> customsubcat;
	HashMap<String, String> customsubcatmap;
	Boolean status = false;
	DatabaseHelper db;

	CircularImageView tempimage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminsubcat);
		footer = (LinearLayout) findViewById(R.id.footer);
		footer.setVisibility(8);
		db = new DatabaseHelper(getApplicationContext());
		displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		width = displaymetrics.widthPixels;
		height = displaymetrics.heightPixels;
		globals = (Globals) getApplicationContext();
		latoutparams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, (height / 3));
		animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.blink);
		font = Typeface.createFromAsset(getApplication().getAssets(),
				"Jacked_Font.ttf");

		catposition = globals.getcatposition();
		categories = (ViewPager) findViewById(R.id.categories);
		HomeTab = (ImageView) findViewById(R.id.home);
		CategoryTab = (ImageView) findViewById(R.id.category);
		OfferTab = (ImageView) findViewById(R.id.offers);
		CartTab = (ImageView) findViewById(R.id.cart);
		AccountTab = (ImageView) findViewById(R.id.account);

		list = (ListView) findViewById(R.id.list);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(AdminSubcat.this, AdminPage.class));
				finish();
				overridePendingTransition(0, 0);
			}
		});
		userid = globals.getuserid();

		HomeTab.setOnClickListener(this);
		CategoryTab.setOnClickListener(this);
		OfferTab.setOnClickListener(this);
		CartTab.setOnClickListener(this);
		AccountTab.setOnClickListener(this);
		// list.setOnItemClickListener(this);

		Fetchdetail();

	}

	Runnable Deletesubcategory = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler.deletesubcategory(
						getApplicationContext(), subcatid);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			DeletecategoryHandler.sendMessage(msg);

		}
	};

	Handler DeletecategoryHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			String res = msg.obj.toString();
			dialog.dismiss();

			dialog = new Dialog(AdminSubcat.this);
			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// setting custom layout to dialog
			dialog.setContentView(R.layout.messagedailog);

			TextView message = (TextView) dialog.findViewById(R.id.message);
			message.setText("Thanks for using Shopee");
			final TextView okbtn = (TextView) dialog.findViewById(R.id.confirm);

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
	};

	private void Fetchdetail() {
		// TODO Auto-generated method stub
		switch (userid) {

		case "0":
			dialog = new Dialog(AdminSubcat.this);
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

					new Thread(null, CategoryDeatil, "refresh_data").start();

				}
			}, 0);

			dialog.show();
			dialog.setCancelable(false);
			break;

		default:
			dialog = new Dialog(AdminSubcat.this);
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

					new Thread(null, CategoryDeatil, "refresh_data").start();

				}
			}, 0);

			dialog.show();
			dialog.setCancelable(false);
			break;
		}

	}

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
				new Thread(null, SubCategoryDeatil, "refresh_data").start();
			} else {
				dialog.dismiss();
				dialog = new Dialog(AdminSubcat.this);
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
						dialog = new Dialog(AdminSubcat.this);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		default:
			break;
		}

	}

	Runnable SubCategoryDeatil = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler
						.SubCategoryListing(getApplicationContext());

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			SubCategoryHandler.sendMessage(msg);

		}
	};

	Handler SubCategoryHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			String res = msg.obj.toString();

			if (res.equals("sucess")) {
				//
				// customsubcat = new ArrayList<HashMap<String, String>>();
				//
				// for (int s = 0; s < globals.getSubCategoryList().size(); s++)
				// {
				// if (globals
				// .getCategoryList()
				// .get(catposition)
				// .get("id")
				// .equals(globals.getSubCategoryList().get(s)
				// .get("catid"))) {
				//
				// customsubcatmap = new HashMap<String, String>();
				// customsubcatmap.put("id", globals.getSubCategoryList()
				// .get(s).get("id"));
				// customsubcatmap.put("image", globals
				// .getSubCategoryList().get(s).get("image"));
				// customsubcatmap.put("catid", globals
				// .getSubCategoryList().get(s).get("catid"));
				// customsubcatmap
				// .put("name", globals.getSubCategoryList()
				// .get(s).get("subcategory"));
				// customsubcat.add(customsubcatmap);
				//
				// }
				//
				// }
				dialog.dismiss();
				catadapter = new CatAdapter(getApplicationContext(),
						globals.getCategoryList());
				categories.setAdapter(catadapter);
				categories.setCurrentItem(catposition, true);

			} else {
				dialog.dismiss();
				dialog = new Dialog(AdminSubcat.this);
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
						dialog = new Dialog(AdminSubcat.this);
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

	public void onBackPressed() {
	};

	public class CatAdapter extends PagerAdapter {

		Context context;
		ArrayList<HashMap<String, String>> categorylist = new ArrayList<HashMap<String, String>>();
		LayoutInflater inflater;
		Globals global;

		public CatAdapter(Context c,
				ArrayList<HashMap<String, String>> categorylist) {
			this.context = c;
			this.categorylist = categorylist;
			global = (Globals) context;
		}

		@Override
		public int getCount() {
			return this.categorylist.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == ((RelativeLayout) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {

			ImageView catimage;
			ListView list;
			TextView catname;

			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View viewLayout = inflater.inflate(R.layout.customcategory,
					container, false);

			catimage = (ImageView) viewLayout.findViewById(R.id.catimage);
			catname = (TextView) viewLayout.findViewById(R.id.catname);
			list = (ListView) viewLayout.findViewById(R.id.listView1);

			customsubcat = new ArrayList<HashMap<String, String>>();

			// customsubcat = db.getallSubcategories(String.valueOf(position +
			// 1));
			for (int s = 0; s < global.getSubCategoryList().size(); s++) {
				if (categorylist
						.get(position)
						.get("id")
						.equals(global.getSubCategoryList().get(s).get("catid"))) {

					customsubcatmap = new HashMap<String, String>();
					customsubcatmap.put("id", global.getSubCategoryList()
							.get(s).get("id"));
					customsubcatmap.put("catid", global.getSubCategoryList()
							.get(s).get("catid"));
					customsubcatmap.put("catnames", global.getSubCategoryList()
							.get(s).get("category"));
					customsubcatmap.put("image", global.getSubCategoryList()
							.get(s).get("image"));
					customsubcatmap.put("name", global.getSubCategoryList()
							.get(s).get("subcategory"));
					customsubcat.add(customsubcatmap);

				}

			}
			subcatList = new SubAdapterList(getApplicationContext(),
					customsubcat);
			list.setAdapter(subcatList);
			subcatList.notifyDataSetChanged();
			Picasso.with(context)
					.load("http://emerchantshop.com/shopbuycart_app/cat_image/"
							+ categorylist.get(position).get("image"))
					.into(catimage);

			catname.setText(categorylist.get(position).get("catgeory"));
			catname.setTypeface(font);

			((ViewPager) container).addView(viewLayout);

			return viewLayout;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((RelativeLayout) object);

		}

	}

	public class SubAdapterList extends BaseAdapter {

		private Context context;
		LayoutInflater mInflater;
		TextView namesubcact;
		ImageView subcatimage;
		ArrayList<HashMap<String, String>> customsubcat = new ArrayList<HashMap<String, String>>();

		public SubAdapterList(Context c,
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

			convertView = (RelativeLayout) mInflater.inflate(R.layout.name,
					null);
			subcatimage = (ImageView) convertView
					.findViewById(R.id.subcatimage);
			namesubcact = (TextView) convertView.findViewById(R.id.name);
			namesubcact.setTypeface(font);

			holder = (ViewHolder) convertView.getTag();
			Picasso.with(context)
					.load("http://emerchantshop.com/shopbuycart_app/subcat_image/"
							+ customsubcat.get(position).get("image"))
					.into(subcatimage);

			namesubcact.setText(customsubcat.get(position).get("name"));

			namesubcact.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// Intent product = new Intent(AdminSubcat.this,
					// Product.class);
					// product.putExtra("subcatid", customsubcat.get(position)
					// .get("id"));
					// startActivity(product);
					subcatid = customsubcat.get(position).get("id");

					// TODO Auto-generated method stub

					if (globals.getchange().equals("offers")) {

						dialog = new Dialog(AdminSubcat.this);
						dialog.getWindow().setBackgroundDrawable(
								new ColorDrawable(
										android.graphics.Color.TRANSPARENT));
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						// setting custom layout to dialog
						dialog.setContentView(R.layout.adddeal);

						tempimage = (CircularImageView) dialog
								.findViewById(R.id.newimage);
						final EditText newname = (EditText) dialog
								.findViewById(R.id.dealdiscount);
						Button change = (Button) dialog
								.findViewById(R.id.change);
						change.setText("Add Offer");
						tempimage.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								getimage();
							}
						});
						change.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dialog.dismiss();
								tempcategory = newname.getText().toString();
								dialog = new Dialog(AdminSubcat.this);
								dialog.getWindow()
										.setBackgroundDrawable(
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

										new Thread(null, Adddeal,
												"refresh_data").start();
									}
								}, 0);

								dialog.show();
								dialog.setCancelable(false);
							}
						});

						dialog.show();

					}
					if (globals.getchange().equals("edit")) {

						dialog = new Dialog(AdminSubcat.this);
						dialog.getWindow().setBackgroundDrawable(
								new ColorDrawable(
										android.graphics.Color.TRANSPARENT));
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						// setting custom layout to dialog
						dialog.setContentView(R.layout.add_edit_cat);

						ImageView newimage = (ImageView) dialog
								.findViewById(R.id.newimage);
						EditText newname = (EditText) dialog
								.findViewById(R.id.newname);
						Button change = (Button) dialog
								.findViewById(R.id.change);

						Picasso.with(AdminSubcat.this)
								.load("http://emerchantshop.com/shopbuycart_app/subcat_image/"
										+ customsubcat.get(position).get(
												"image")).into(newimage);

						newname.setText(customsubcat.get(position).get("name"));

						change.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

							}
						});

						dialog.show();

					} else if (globals.getchange().equals("addoffers")) {
						Intent product = new Intent(AdminSubcat.this,
								Adminproductsview.class);
						product.putExtra("subcatid", customsubcat.get(position)
								.get("id"));
						startActivity(product);

					} else if (globals.getchange().equals("showproduct")) {
						Intent product = new Intent(AdminSubcat.this,
								Adminproductsview.class);
						product.putExtra("subcatid", customsubcat.get(position)
								.get("id"));
						startActivity(product);
					} else if (globals.getchange().equals("productdelete")) {

						Intent product = new Intent(AdminSubcat.this,
								Adminproductsview.class);
						product.putExtra("subcatid", customsubcat.get(position)
								.get("id"));
						startActivity(product);

					} else if (globals.getchange().equals("productadd")) {

						Intent product = new Intent(AdminSubcat.this,
								AddProduct.class);
						product.putExtra("subcatid", customsubcat.get(position)
								.get("id"));
						product.putExtra("catid", customsubcat.get(position)
								.get("catid"));
						product.putExtra("subcatnames",
								customsubcat.get(position).get("name"));
						product.putExtra("catnames", customsubcat.get(position)
								.get("subcatnames"));
						startActivity(product);
						overridePendingTransition(0, 0);
					} else if (globals.getchange().equals("delete")) {

						dialog = new Dialog(AdminSubcat.this);
						dialog.getWindow().setBackgroundDrawable(
								new ColorDrawable(
										android.graphics.Color.TRANSPARENT));
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						// setting custom layout to dialog
						dialog.setContentView(R.layout.messagedailog);

						TextView message = (TextView) dialog
								.findViewById(R.id.message);
						message.setText("Do You Want Delete Category ");
						TextView okbtn = (TextView) dialog
								.findViewById(R.id.confirm);

						okbtn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								// TODO Auto-generated method stub
								dialog.dismiss();

								dialog = new Dialog(AdminSubcat.this);
								dialog.getWindow()
										.setBackgroundDrawable(
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

										new Thread(null, Deletesubcategory,
												"refresh_data").start();
									}
								}, 0);

								dialog.show();
								dialog.setCancelable(false);

							}
						});
						dialog.show();

					}
				}
			});
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

	public Bitmap getBlurredBitmap(Bitmap original, int radius) {
		if (radius < 1)
			return null;

		int width = original.getWidth();
		int height = original.getHeight();
		int wm = width - 1;
		int hm = height - 1;
		int wh = width * height;
		int div = radius + radius + 1;
		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, p1, p2, yp, yi, yw;
		int vmin[] = new int[Math.max(width, height)];
		int vmax[] = new int[Math.max(width, height)];
		int dv[] = new int[256 * div];
		for (i = 0; i < 256 * div; i++)
			dv[i] = i / div;

		int[] blurredBitmap = new int[wh];
		original.getPixels(blurredBitmap, 0, width, 0, 0, width, height);

		yw = 0;
		yi = 0;

		for (y = 0; y < height; y++) {
			rsum = 0;
			gsum = 0;
			bsum = 0;
			for (i = -radius; i <= radius; i++) {
				p = blurredBitmap[yi + Math.min(wm, Math.max(i, 0))];
				rsum += (p & 0xff0000) >> 16;
				gsum += (p & 0x00ff00) >> 8;
				bsum += p & 0x0000ff;
			}
			for (x = 0; x < width; x++) {
				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];

				if (y == 0) {
					vmin[x] = Math.min(x + radius + 1, wm);
					vmax[x] = Math.max(x - radius, 0);
				}
				p1 = blurredBitmap[yw + vmin[x]];
				p2 = blurredBitmap[yw + vmax[x]];

				rsum += ((p1 & 0xff0000) - (p2 & 0xff0000)) >> 16;
				gsum += ((p1 & 0x00ff00) - (p2 & 0x00ff00)) >> 8;
				bsum += (p1 & 0x0000ff) - (p2 & 0x0000ff);
				yi++;
			}
			yw += width;
		}

		for (x = 0; x < width; x++) {
			rsum = gsum = bsum = 0;
			yp = -radius * width;
			for (i = -radius; i <= radius; i++) {
				yi = Math.max(0, yp) + x;
				rsum += r[yi];
				gsum += g[yi];
				bsum += b[yi];
				yp += width;
			}
			yi = x;
			for (y = 0; y < height; y++) {
				blurredBitmap[yi] = 0xff000000 | (dv[rsum] << 16)
						| (dv[gsum] << 8) | dv[bsum];
				if (x == 0) {
					vmin[y] = Math.min(y + radius + 1, hm) * width;
					vmax[y] = Math.max(y - radius, 0) * width;
				}
				p1 = x + vmin[y];
				p2 = x + vmax[y];

				rsum += r[p1] - r[p2];
				gsum += g[p1] - g[p2];
				bsum += b[p1] - b[p2];

				yi += width;
			}
		}

		return Bitmap.createBitmap(blurredBitmap, width, height,
				Bitmap.Config.RGB_565);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		// if (globals.getchange().equals("edit")) {
		//
		// dialog = new Dialog(AdminSubcat.this);
		// dialog.getWindow().setBackgroundDrawable(
		// new ColorDrawable(android.graphics.Color.TRANSPARENT));
		// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// // setting custom layout to dialog
		// dialog.setContentView(R.layout.add_edit_cat);
		//
		// ImageView newimage = (ImageView) dialog.findViewById(R.id.newimage);
		// EditText newname = (EditText) dialog.findViewById(R.id.newname);
		// Button change = (Button) dialog.findViewById(R.id.change);
		//
		// Picasso.with(AdminSubcat.this)
		// .load("http://emerchantshop.com/shopbuycart_app/subcat_image/"
		// + customsubcat.get(position).get("image"))
		// .into(newimage);
		//
		// newname.setText(customsubcat.get(position).get("name"));
		//
		// change.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		//
		// dialog.show();
		//
		// }
		// customsubcat.get(position).get("id");
		// startActivity(new Intent(AdminSubcat.this, Product.class));

	}

	private void getimage() {
		CharSequence[] items2 = { "Camera", "Gallery" };
		AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
		builder2.setTitle("upload your car image");
		builder2.setItems(items2, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				changeView(item);
			}
		});
		AlertDialog alert2 = builder2.create();
		alert2.show();

	}

	private void changeView(int a) {

		if (a == 0) {

			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			mUri = Uri.fromFile(new File(Environment
					.getExternalStorageDirectory(), "pic_"
					+ String.valueOf(System.currentTimeMillis()) + ".jpg"));

			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mUri);

			startActivityForResult(intent, IMAGE_CAMERA);

		} else if (a == 1) {
			Intent in = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

			startActivityForResult(in, IMAGE_GALLERY);

		}
	}

	@SuppressWarnings("unused")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which activity returned data to us
		switch (requestCode) {
		case IMAGE_GALLERY:
			// only proceed if the result was RESULT_OK
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();
				String[] projections = { MediaStore.Images.Media.DATA };

				Cursor cursor = getContentResolver().query(uri, projections,
						null, null, null);
				cursor.moveToFirst();

				int index = cursor.getColumnIndex(projections[0]);
				String filepath = cursor.getString(index);
				// Log.e("bbmmp when picture taken from Gallery", "" +
				// filepath);
				cursor.close();
				Bitmap bitmap = BitmapFactory.decodeFile(filepath);

				newcatimage = convertImageToString(bitmap);

				tempimage.setImageBitmap(bitmap);

			}

			break;
		case IMAGE_CAMERA:
			// only proceed if the result was RESULT_OK
			if (resultCode == RESULT_OK) {
				selectedImagePath = mUri.getPath();

				File f = new File(selectedImagePath);

				mBitmap = Utils.reduceImageSize(selectedImagePath);

				newcatimage = convertImageToString(mBitmap);

				tempimage.setImageBitmap(mBitmap);

			}

			break;

		default:
			break;
		}
	}

	public String convertImageToString(Bitmap bit) {

		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		bit.compress(Bitmap.CompressFormat.JPEG, 70, bout);
		byte[] imagearray = bout.toByteArray();
		String encoded = Base64.encodeBytes(imagearray);
		Log.i("encoded image ", "length is : " + encoded.length());
		return encoded;
	}

	Runnable Adddeal = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler.addnewdeal(getApplicationContext(),
						subcatid, tempcategory, newcatimage, "subcategory");

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			AdddealHandler.sendMessage(msg);

		}
	};

	Handler AdddealHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			String res = msg.obj.toString();
			dialog.dismiss();
			if (res.equals("sucess")) {
				dialog.dismiss();
				recreate();

			} else {

				dialog.dismiss();
				dialog = new Dialog(AdminSubcat.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// setting custom layout to dialog
				dialog.setContentView(R.layout.messagedailog);

				TextView message = (TextView) dialog.findViewById(R.id.message);
				message.setText(res);
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

		}
	};

}
