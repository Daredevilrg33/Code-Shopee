package amit.techie33.shopbuycart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
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
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

@SuppressLint({ "HandlerLeak", "NewApi", "ViewHolder" })
public class Cart extends Activity implements OnClickListener,
		AnimationListener {

	TextView appname, name, amount, price, cartdate, quantity, cartopen,
			orderopen;
	Typeface font;
	LinearLayout header, couponview, tabs;
	Button applycoupon, ordernow;
	DisplayMetrics displaymetrics;
	int width, height;
	LinearLayout.LayoutParams latoutparams;
	String userid, selectedtab = "cart", couponamount, straddress,
			currentorderid;
	SharedPreferences pref;
	LinearLayout showorderbtn;
	EditText coupontxt;
	Editor editor;
	Dialog dialog, dialog1;
	double latitude, longitude;
	Animation animFadein;
	ImageView pdimageloader, HomeTab, CategoryTab, OfferTab, CartTab,
			AccountTab;
	Globals globals;

	CartAdapter Cartadapter;
	OrderAdapter Orderadapter;
	ListView list;
	DealAdapterList DealAdapterList;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cart);

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
		showorderbtn = (LinearLayout) findViewById(R.id.showorderbtn);
		appname = (TextView) findViewById(R.id.appname);
		HomeTab = (ImageView) findViewById(R.id.home);
		tabs = (LinearLayout) findViewById(R.id.tabs);
		ordernow = (Button) findViewById(R.id.ordernow);
		CategoryTab = (ImageView) findViewById(R.id.category);
		OfferTab = (ImageView) findViewById(R.id.offers);
		CartTab = (ImageView) findViewById(R.id.cart);
		AccountTab = (ImageView) findViewById(R.id.account);
		list = (ListView) findViewById(R.id.list);
		cartopen = (TextView) findViewById(R.id.cartopen);
		orderopen = (TextView) findViewById(R.id.ordersopen);
		appname.setTypeface(font);
		orderopen.setOnClickListener(this);
		cartopen.setOnClickListener(this);
		HomeTab.setOnClickListener(this);
		CategoryTab.setOnClickListener(this);
		OfferTab.setOnClickListener(this);
		CartTab.setOnClickListener(this);
		AccountTab.setOnClickListener(this);
		ordernow.setOnClickListener(this);

		animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.blink);
		userid = globals.getuserid();
		Loading();
		tabs.setVisibility(0);

	}

	private void Loading() {
		// TODO Auto-generated method stub
		switch (userid) {
		case "0":
			dialog = new Dialog(Cart.this);
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

					new Thread(null, CartList, "refresh_data").start();
				}
			}, 0);

			dialog.show();
			dialog.setCancelable(false);

			break;

		default:
			dialog = new Dialog(Cart.this);
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

					new Thread(null, CartList, "refresh_data").start();
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
		case R.id.ordersopen:

			cartopen.setTextColor(Color.parseColor("#F6A81A"));
			cartopen.setBackgroundColor(Color.TRANSPARENT);
			orderopen.setTextColor(Color.parseColor("#ffffff"));
			orderopen.setBackgroundResource(R.drawable.righttab);

			dialog = new Dialog(Cart.this);
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
					showorderbtn.setVisibility(8);
					new Thread(null, OrderList, "refresh_data").start();
				}
			}, 0);

			dialog.show();
			dialog.setCancelable(false);

			break;
		case R.id.cartopen:

			orderopen.setTextColor(Color.parseColor("#F6A81A"));
			orderopen.setBackgroundColor(Color.TRANSPARENT);
			cartopen.setTextColor(Color.parseColor("#ffffff"));
			cartopen.setBackgroundResource(R.drawable.lefttab);

			dialog = new Dialog(Cart.this);
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

					new Thread(null, CartList, "refresh_data").start();
				}
			}, 0);

			dialog.show();
			dialog.setCancelable(false);
			break;
		case R.id.ordernow:

			dialog1 = new Dialog(Cart.this);
			dialog1.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// setting custom layout to dialog
			dialog1.setContentView(R.layout.couponcode);

			coupontxt = (EditText) dialog1.findViewById(R.id.coupontxt);

			applycoupon = (Button) dialog1.findViewById(R.id.applycoupon);

			Button ordercoupon = (Button) dialog1
					.findViewById(R.id.ordercoupon);
			couponview = (LinearLayout) dialog1.findViewById(R.id.couponview);

			applycoupon.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					couponview.setVisibility(0);
				}
			});
			TextView confirm = (TextView) dialog1.findViewById(R.id.confirm);
			coupontxt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					coupontxt.setError(null);
				}
			});

			confirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (coupontxt.getText().toString().length() < 3) {

						coupontxt.setError("Sorry Invalid Coupon");
						coupontxt.setFocusable(true);

					} else {

						couponamount = coupontxt.getText().toString();

						new Thread(null, Couponcode, "refresh_data").start();

					}

				}
			});
			ordercoupon.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							dialog1.dismiss();
							addressview();
							// new Thread(null, OrderNow,
							// "refresh_data").start();
						}
					}, 0);
				}
			});

			dialog1.show();

			// hjgsahdgkhgasgkhsagfkhgkgfgkhasfgkhafga

			break;

		case R.id.home:
			dialog = new Dialog(Cart.this);
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

			dialog = new Dialog(Cart.this);
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
			tabs.setVisibility(8);
			selectedtab = "deal";
			HomeTab.setImageResource(R.drawable.home);
			CategoryTab.setImageResource(R.drawable.list);
			OfferTab.setImageResource(R.drawable.offer_active);
			CartTab.setImageResource(R.drawable.cart);
			AccountTab.setImageResource(R.drawable.account);
			list.setVisibility(0);
			DealAdapterList = new DealAdapterList(getApplicationContext(),
					globals.getDealList());
			list.setAdapter(DealAdapterList);
			break;
		case R.id.cart:
			tabs.setVisibility(0);
			selectedtab = "cart";
			HomeTab.setImageResource(R.drawable.home);
			CategoryTab.setImageResource(R.drawable.list);
			OfferTab.setImageResource(R.drawable.offer);
			CartTab.setImageResource(R.drawable.cart_active);
			AccountTab.setImageResource(R.drawable.account);
			recreate();
			break;
		case R.id.account:
			selectedtab = "account";
			dialog = new Dialog(Cart.this);
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

	protected void addressview() {
		// TODO Auto-generated method stub
		dialog = new Dialog(Cart.this);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// setting custom layout to dialog
		dialog.setContentView(R.layout.address);

		final EditText phone = (EditText) dialog.findViewById(R.id.phone);
		final EditText address = (EditText) dialog.findViewById(R.id.address);
		final TextView confirm = (TextView) dialog.findViewById(R.id.confirm);

		phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				phone.setError(null);
			}
		});

		address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				address.setError(null);
			}
		});
		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (phone.getText().toString().length() < 10) {

					phone.setError("Please Enter Valid Number");
					phone.setFocusable(true);

				} else if (address.getText().toString().length() < 10) {

					address.setError("Please Enter Correct address");
					address.setFocusable(true);

				} else {

					straddress = address.getText().toString() + "  "
							+ phone.getText().toString();

					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							dialog.dismiss();
							final ImageView truck = (ImageView) findViewById(R.id.truck);
							TranslateAnimation animation = new TranslateAnimation(
									0.0f, width, 0.0f, 0.0f); // new
																// TranslateAnimation(xFrom,xTo,
																// yFrom,yTo)
							animation.setDuration(7000);
							animation.setRepeatCount(1); // animation repeat
															// count
							animation.setRepeatMode(1);

							truck.startAnimation(animation);
							new Handler().postDelayed(new Runnable() {

								@Override
								public void run() {
									truck.setVisibility(8);
									dialog = new Dialog(Cart.this);
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
											showorderbtn.setVisibility(8);
											new Thread(null, OrderNow,
													"refresh_data").start();
										}

									}, 0);

									dialog.show();
									dialog.setCancelable(false);
								}
							}, 7000);

						}
					}, 0);

				}

			}
		});

		dialog.show();
	}

	Runnable OrderNow = new Runnable() {

		@Override
		public void run() {

			String orderid = createorder();
			String result = "";
			try {
				String lat = null, log = null;
				result = WebServiceHandler.OrderNow(getApplicationContext(),
						orderid, userid, straddress,  String.valueOf(globals.getDealList().size()), log);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			OrderHandler.sendMessage(msg);

		}
	};

	Handler OrderHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			String res = msg.obj.toString();

			if (res.equals("order place ")) {

				ArrayList<HashMap<String, String>> temp = new ArrayList<HashMap<String, String>>();
				globals.setCartList(temp);

				Cartadapter = new CartAdapter(getApplicationContext(),
						globals.getCartList());
				Cartadapter.notifyDataSetChanged();
				dialog.dismiss();
				dialog = new Dialog(Cart.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// setting custom layout to dialog
				dialog.setContentView(R.layout.messagedailog);

				final TextView okbtn = (TextView) dialog
						.findViewById(R.id.confirm);

				TextView message = (TextView) dialog.findViewById(R.id.message);
				message.setText(res);
				okbtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						cartopen.setTextColor(Color.parseColor("#F6A81A"));
						cartopen.setBackgroundColor(Color.TRANSPARENT);
						orderopen.setTextColor(Color.parseColor("#ffffff"));
						orderopen.setBackgroundResource(R.drawable.righttab);
						// TODO Auto-generated method stub
						new Thread(null, OrderList, "refresh_data").start();

					}
				});
				dialog.show();
			} else {

				dialog.dismiss();
				dialog = new Dialog(Cart.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// setting custom layout to dialog
				dialog.setContentView(R.layout.messagedailog);

				final TextView okbtn = (TextView) dialog
						.findViewById(R.id.confirm);

				TextView message = (TextView) dialog.findViewById(R.id.message);
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
		}
	};

	Runnable OrderList = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler.OrderList(getApplicationContext(),
						userid);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			OrderListHandler.sendMessage(msg);

		}
	};

	Handler OrderListHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			String res = msg.obj.toString();

			if (res.equals("sucess")) {
				dialog.dismiss();
				list.setVisibility(0);
				Orderadapter = new OrderAdapter(getApplicationContext(),
						globals.getOrderList());
				list.setAdapter(Orderadapter);

			} else {
				list.setVisibility(8);
				dialog.dismiss();
				dialog = new Dialog(Cart.this);
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
						dialog = new Dialog(Cart.this);
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

	Runnable CartList = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler.CartList(getApplicationContext(),
						userid);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			CartListHandler.sendMessage(msg);

		}
	};

	Handler CartListHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			final String res = msg.obj.toString();

			if (res.equals("sucess")) {
				dialog.dismiss();
				list.setVisibility(0);
				Cartadapter = new CartAdapter(getApplicationContext(),
						globals.getCartList());
				list.setAdapter(Cartadapter);

				if (globals.getCartList().size() > 0)
					showorderbtn.setVisibility(0);
			} else {
				dialog.dismiss();
				list.setVisibility(8);
				dialog = new Dialog(Cart.this);
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
						dialog = new Dialog(Cart.this);
						dialog.getWindow().setBackgroundDrawable(
								new ColorDrawable(
										android.graphics.Color.TRANSPARENT));
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						// setting custom layout to dialog
						dialog.setContentView(R.layout.messagedailog);
						TextView message = (TextView) dialog
								.findViewById(R.id.message);
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

			} else {
				dialog.dismiss();
				dialog = new Dialog(Cart.this);
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
						dialog = new Dialog(Cart.this);
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

	class OrderAdapter extends BaseAdapter {

		private Context context;
		LayoutInflater mInflater;
		String image_url;
		ArrayList<HashMap<String, String>> cartlist = new ArrayList<HashMap<String, String>>();
		TextView orders, items, date, status;

		public OrderAdapter(Context c,
				ArrayList<HashMap<String, String>> cartlist) {
			this.cartlist = cartlist;

			this.context = c;
			mInflater = LayoutInflater.from(context);
		}

		public View getGroupView(int i, boolean b, View view, GridView list) {
			// TODO Auto-generated method stub
			return null;
		}

		public int getCount() {
			return cartlist.size();

		}

		public Object getItem(int position) {
			return cartlist.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressWarnings("unused")
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			ViewHolder holder;
			HashMap<String, String> request = new HashMap<String, String>();
			request = cartlist.get(position);

			holder = new ViewHolder();

			convertView = (RelativeLayout) mInflater.inflate(

			R.layout.customorder, null);

			orders = (TextView) convertView.findViewById(R.id.order);
			amount = (TextView) convertView.findViewById(R.id.totalprice);
			items = (TextView) convertView.findViewById(R.id.items);
			date = (TextView) convertView.findViewById(R.id.date);
			status = (TextView) convertView.findViewById(R.id.status);

			orders.setTypeface(font);
			amount.setTypeface(font);
			items.setTypeface(font);
			date.setTypeface(font);
			status.setTypeface(font);

			holder = (ViewHolder) convertView.getTag();

			orders.setText("orderid : " + cartlist.get(position).get("orderid"));
			amount.setText(cartlist.get(position).get("totalprice"));
			items.setText("items : " + cartlist.get(position).get("items"));
			date.setText("deliverydate "
					+ cartlist.get(position).get("deliverydate"));
			status.setText(cartlist.get(position).get("status"));

			status.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					currentorderid = cartlist.get(position).get("orderid");
					dialog = new Dialog(Cart.this);
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
			if (cartlist.get(position).get("status").equals("confirm")) {

				ImageView sendorder = (ImageView) convertView
						.findViewById(R.id.sendorder);
				sendorder.setImageResource(R.drawable.confirmorder);

			}
			if (cartlist.get(position).get("status").equals("sendfordelivery")) {
				ImageView oredrcobfirm = (ImageView) convertView
						.findViewById(R.id.oredrcobfirm);

				ImageView sendorder = (ImageView) convertView
						.findViewById(R.id.sendorder);
				sendorder.setImageResource(R.drawable.confirmorder);
				oredrcobfirm.setImageResource(R.drawable.checked);

			} else if (cartlist.get(position).get("status").equals("cancel")) {

				ImageView oredrcobfirm = (ImageView) convertView
						.findViewById(R.id.oredrcobfirm);

				oredrcobfirm.setImageResource(R.drawable.cancelorder);

			}

			return convertView;
		}

		class ViewHolder {

		}

	}

	class CartAdapter extends BaseAdapter {

		private Context context;
		LayoutInflater mInflater;
		String image_url;
		ArrayList<HashMap<String, String>> cartlist = new ArrayList<HashMap<String, String>>();
		TextView name;
		ImageView productimage;

		public CartAdapter(Context c,
				ArrayList<HashMap<String, String>> cartlist) {
			this.cartlist = cartlist;

			this.context = c;
			mInflater = LayoutInflater.from(context);
		}

		public View getGroupView(int i, boolean b, View view, GridView list) {
			// TODO Auto-generated method stub
			return null;
		}

		public int getCount() {
			return cartlist.size();

		}

		public Object getItem(int position) {
			return cartlist.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressWarnings("unused")
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			ViewHolder holder;
			HashMap<String, String> request = new HashMap<String, String>();
			request = cartlist.get(position);

			holder = new ViewHolder();

			convertView = (RelativeLayout) mInflater.inflate(
					R.layout.customcart, null);

			name = (TextView) convertView.findViewById(R.id.name);
			amount = (TextView) convertView.findViewById(R.id.amount);
			price = (TextView) convertView.findViewById(R.id.price);
			cartdate = (TextView) convertView.findViewById(R.id.date);
			quantity = (TextView) convertView.findViewById(R.id.quantity);

			LinearLayout.LayoutParams latoutparams = new LinearLayout.LayoutParams(
					(height / 6), (height / 6));
			productimage = (ImageView) convertView
					.findViewById(R.id.productimage);
			name.setTypeface(font);

			holder = (ViewHolder) convertView.getTag();

			Picasso.with(context)
					.load("http://emerchantshop.com/shopbuycart_app/product_image/"
							+ cartlist.get(position).get("image"))
					.into(productimage);
			productimage.setLayoutParams(latoutparams);
			name.setText(cartlist.get(position).get("name"));
			amount.setText(String.valueOf(Integer.parseInt(cartlist.get(
					position).get("quantity"))
					* Integer.parseInt(cartlist.get(position).get(
							"discountprice"))));
			price.setText(cartlist.get(position).get("discountprice"));
			cartdate.setText(cartlist.get(position).get("cartdate"));
			quantity.setText(cartlist.get(position).get("quantity"));

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

	protected String createorder() {
		char[] chars = "123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		// TODO Auto-generated method stub
		return sb.toString();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

	Runnable search = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler.searchOrderlist(
						getApplicationContext(), currentorderid);

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
				dialog = new Dialog(Cart.this);
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
						dialog = new Dialog(Cart.this);
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

	Runnable Couponcode = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler.Couponcode(getApplicationContext(),
						couponamount, userid);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			CouponcodeHandler.sendMessage(msg);

		}
	};

	Handler CouponcodeHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			String res = msg.obj.toString();

			if (res.equals("sucess")) {
				applycoupon.setVisibility(8);
				couponview.setVisibility(8);

			} else {

				coupontxt.setError("! Sorry Invalid Coupon");
			}

		}
	};

}
