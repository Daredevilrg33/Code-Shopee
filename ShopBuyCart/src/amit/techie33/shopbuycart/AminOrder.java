package amit.techie33.shopbuycart;

import java.util.ArrayList;
import java.util.HashMap;

import amit.techie33.shopbuycart.Cart.AdapterList.ViewHolder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import database.DatabaseHelper;

@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class AminOrder extends Activity implements AnimationListener,
		OnItemClickListener {

	Typeface font;
	ImageView pdimageloader, HomeTab, CategoryTab, back, OfferTab, CartTab,
			AccountTab;
	Dialog dialog;
	Animation animFadein;
	String userid, selectedtab = "category", orderid, currentstatus;
	Globals globals;
	SubAdapterList subcatList;
	TextView newproduct;
	DisplayMetrics displaymetrics;
	int width, height;
	LinearLayout.LayoutParams latoutparams;
	ArrayList<HashMap<String, String>> customsubcat;
	DatabaseHelper db;
	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admincatlist);
		db = new DatabaseHelper(getApplicationContext());
		displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

		newproduct = (TextView) findViewById(R.id.newproduct);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(AminOrder.this, AdminPage.class));
				finish();
				overridePendingTransition(0, 0);
			}
		});
		width = displaymetrics.widthPixels;
		height = displaymetrics.heightPixels;
		globals = (Globals) getApplicationContext();
		latoutparams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, (height / 3));
		animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.blink);
		font = Typeface.createFromAsset(getApplication().getAssets(),
				"Jacked_Font.ttf");
		newproduct.setTypeface(font);
		newproduct.setText("ORDER LIST");
		list = (ListView) findViewById(R.id.list);

		userid = globals.getuserid();

		/*
		 * list.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long id) { if (globals.getchange().equals("edit" )) {
		 * 
		 * dialog = new Dialog(AminOrder.this); dialog.getWindow
		 * ().setBackgroundDrawable( new ColorDrawable( android.graphics.Color
		 * .TRANSPARENT)); dialog.requestWindowFeature
		 * (Window.FEATURE_NO_TITLE); // setting custom layout to dialog dialog
		 * .setContentView(R.layout.add_edit_cat );
		 * 
		 * ImageView newimage = (ImageView) dialog .findViewById(R.id.newimage);
		 * EditText newname = (EditText) dialog .findViewById(R.id.newname);
		 * Button change = (Button) dialog.findViewById(R.id.change);
		 * 
		 * Picasso.with(AminOrder.this) .load(
		 * "http://emerchantshop.com/shopbuycart_app/subcat_image/" +
		 * globals.getCategoryList().get( position)
		 * .get("image")).into(newimage);
		 * 
		 * newname.setText(globals. getCategoryList().get(position)
		 * .get("category"));
		 * 
		 * change.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub
		 * 
		 * } });
		 * 
		 * dialog.show();
		 * 
		 * } else if (globals.getchange().equals ("subcatadd")) {
		 * 
		 * dialog = new Dialog(AminOrder.this); dialog.getWindow
		 * ().setBackgroundDrawable( new ColorDrawable( android.graphics.Color
		 * .TRANSPARENT)); dialog.requestWindowFeature
		 * (Window.FEATURE_NO_TITLE); // setting custom layout to dialog dialog
		 * .setContentView(R.layout.add_edit_cat );
		 * 
		 * TextView selectcategoryname = (TextView) dialog .findViewById(R.
		 * id.selectcategoryname);
		 * 
		 * selectcategoryname.setVisibility(0 );
		 * selectcategoryname.setText(globals .getCategoryList()
		 * .get(position).get("category")); ImageView newimage = (ImageView)
		 * dialog .findViewById(R.id.newimage); EditText newname = (EditText)
		 * dialog .findViewById(R.id.newname); Button change = (Button)
		 * dialog.findViewById(R.id.change); change
		 * .setText("Add Sub Category"); change.setOnClickListener(new
		 * OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub
		 * 
		 * } });
		 * 
		 * dialog.show();
		 * 
		 * } else {
		 * 
		 * } // TODO Auto-generated method stub // Intent product = new
		 * Intent(AdminCatList.this, // Product.class); //
		 * product.putExtra("catid", // categories .get(position).get("id")); //
		 * startActivity(product);
		 * 
		 * } });
		 */
		Fetchdetail();
	}

	private void Fetchdetail() {
		// TODO Auto-generated method stub
		switch (userid) {

		case "0":
			dialog = new Dialog(AminOrder.this);
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

					new Thread(null, Orderlist, "refresh_data").start();

				}
			}, 0);

			dialog.show();
			dialog.setCancelable(false);
			break;

		default:
			dialog = new Dialog(AminOrder.this);
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

					new Thread(null, Orderlist, "refresh_data").start();

				}
			}, 0);

			dialog.show();
			dialog.setCancelable(false);
			break;
		}

	}

	Runnable Orderlist = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler.Orderlist(getApplicationContext());

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			OrderlistHandler.sendMessage(msg);

		}
	};

	Handler OrderlistHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			String res = msg.obj.toString();

			if (res.equals("sucess")) {

				if (globals.getOrderListall().size() > 0) {
					dialog.dismiss();
					subcatList = new SubAdapterList(getApplicationContext(),
							globals.getOrderListall());
					list.setAdapter(subcatList);
					subcatList.notifyDataSetChanged();

				}

			} else {
				dialog.dismiss();
				dialog = new Dialog(AminOrder.this);
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
						dialog = new Dialog(AminOrder.this);
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

	public class SubAdapterList extends BaseAdapter {

		private Context context;
		LayoutInflater mInflater;
		TextView namesubcact, confirm, delete, send;
		CircularImageView subcatimage;
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

			convertView = (RelativeLayout) mInflater.inflate(
					R.layout.statuasorder, null);

			namesubcact = (TextView) convertView.findViewById(R.id.name);
			confirm = (TextView) convertView.findViewById(R.id.confirm);
			delete = (TextView) convertView.findViewById(R.id.delete);
			send = (TextView) convertView.findViewById(R.id.send);
			TextView statustxt = (TextView) convertView
					.findViewById(R.id.statustxt);
			namesubcact.setTypeface(font);

			holder = (ViewHolder) convertView.getTag();
			currentstatus = "";

			statustxt.setText("CURRENT STATUS : "
					+ customsubcat.get(position).get("status"));
			namesubcact.setText("O_ID : "
					+ customsubcat.get(position).get("orderid"));
			if (customsubcat.get(position).get("status")
					.equalsIgnoreCase("placeorder")) {

				confirm.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						confirm.setEnabled(false);
						currentstatus = "confirm";
						orderid = customsubcat.get(position).get("orderid");
						dialog = new Dialog(AminOrder.this);
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

								new Thread(null, update, "refresh_data")
										.start();

							}
						}, 0);

						dialog.show();
						dialog.setCancelable(false);

					}
				});
				send.setEnabled(false);
				delete.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						currentstatus = "cancel";
						orderid = customsubcat.get(position).get("orderid");
						dialog = new Dialog(AminOrder.this);
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

								new Thread(null, update, "refresh_data")
										.start();

							}
						}, 0);

						dialog.show();
						dialog.setCancelable(false);

					}
				});
			} else if (customsubcat.get(position).get("status")
					.equalsIgnoreCase("cancel")) {

				confirm.setEnabled(false);
				send.setEnabled(false);
				delete.setEnabled(false);
			} else {
				send.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						delete.setEnabled(false);
						confirm.setEnabled(false);
						send.setEnabled(false);
						currentstatus = "sendfordelivery";
						orderid = customsubcat.get(position).get("orderid");
						dialog = new Dialog(AminOrder.this);
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

								new Thread(null, update, "refresh_data")
										.start();

							}
						}, 0);

						dialog.show();
						dialog.setCancelable(false);

					}
				});
				delete.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						currentstatus = "cancel";
						orderid = customsubcat.get(position).get("orderid");
						dialog = new Dialog(AminOrder.this);
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

								new Thread(null, update, "refresh_data")
										.start();

							}
						}, 0);

						dialog.show();
						dialog.setCancelable(false);

					}
				});

			}

			namesubcact.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					orderid = customsubcat.get(position).get("orderid");
					dialog = new Dialog(AminOrder.this);
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
		/*
		 * customsubcat.get(position).get("id"); startActivity(new
		 * Intent(AdminCatList.this, Product.class));
		 */

	}

	Runnable search = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler.searchOrderlist(
						getApplicationContext(), orderid);

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
				dialog = new Dialog(AminOrder.this);
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
						dialog = new Dialog(AminOrder.this);
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
	Runnable update = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler.updateOrderlist(
						getApplicationContext(), orderid, currentstatus);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			updateHandler.sendMessage(msg);

		}
	};

	Handler updateHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			String res = msg.obj.toString();

			if (res.equals("sucess")) {
				dialog.dismiss();
				recreate();

			} else {
				dialog.dismiss();
				dialog = new Dialog(AminOrder.this);
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
						dialog = new Dialog(AminOrder.this);
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

			productname = (TextView) convertView.findViewById(R.id.productname);
			size = (TextView) convertView.findViewById(R.id.size);
			quantity = (TextView) convertView.findViewById(R.id.quntity);
			price = (TextView) convertView.findViewById(R.id.price);
			productname.setTypeface(font);
			size.setTypeface(font);
			quantity.setTypeface(font);
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
