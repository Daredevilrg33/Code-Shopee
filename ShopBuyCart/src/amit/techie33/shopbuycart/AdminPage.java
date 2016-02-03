package amit.techie33.shopbuycart;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AdminPage extends Activity implements OnClickListener {
	ImageView offer, choose, pdimageloader;
	CircularImageView tempimage;
	TextView appname, showall, choosetext, cat, subcat, product, deal, coupan,
			orders, add, delete;
	Typeface font;
	Globals globals;

	String choosevalue = "", tempcategory, newcatimage = "", couponcodetxt,
			discountamounttxt, emailaddresstxt;
	LinearLayout options;
	SharedPreferences autologin;
	Editor editor;
	boolean check = false;
	ImageView logout;
	Dialog dialog;
	private Animation animFadein;
	private Uri mUri;
	private static final int IMAGE_GALLERY = 0, IMAGE_CAMERA = 1;
	private String selectedImagePath;
	Bitmap mBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminpage);
		globals = (Globals) getApplicationContext();
		autologin = getSharedPreferences("shopbuycart", 0);
		editor = autologin.edit();

		font = Typeface.createFromAsset(getApplication().getAssets(),
				"Jacked_Font.ttf");

		animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.blink);
		appname = (TextView) findViewById(R.id.appname);
		logout = (ImageView) findViewById(R.id.logout);
		cat = (TextView) findViewById(R.id.cat);
		choose = (ImageView) findViewById(R.id.choose);
		subcat = (TextView) findViewById(R.id.subcat);
		product = (TextView) findViewById(R.id.product);
		deal = (TextView) findViewById(R.id.deals);
		orders = (TextView) findViewById(R.id.orders);
		coupan = (TextView) findViewById(R.id.coupan);

		showall = (TextView) findViewById(R.id.showall);
		add = (TextView) findViewById(R.id.add);
		delete = (TextView) findViewById(R.id.delete);
		choosetext = (TextView) findViewById(R.id.choosetext);
		options = (LinearLayout) findViewById(R.id.options);

		appname.setTypeface(font);
		appname.setText("WELCOME ADMIN " + globals.getusername().toUpperCase());

		appname.setOnClickListener(this);
		logout.setOnClickListener(this);
		choose.setOnClickListener(this);
		cat.setOnClickListener(this);
		subcat.setOnClickListener(this);
		product.setOnClickListener(this);
		deal.setOnClickListener(this);
		orders.setOnClickListener(this);
		coupan.setOnClickListener(this);
		showall.setOnClickListener(this);
		add.setOnClickListener(this);
		delete.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.delete:

			globals.setchange("delete");
			switch (choosevalue) {

			case "category":
				startActivity(new Intent(AdminPage.this, AdminCatList.class));
				finish();
				overridePendingTransition(0, 0);
				break;
			case "offers":

				globals.setchange("addoffers");
				startActivity(new Intent(AdminPage.this, AdminSubcat.class));
				finish();
				overridePendingTransition(0, 0);

			case "subcategory":
				startActivity(new Intent(AdminPage.this, AdminSubcat.class));
				finish();
				overridePendingTransition(0, 0);

				break;
			case "product":
				globals.setchange("productdelete");
				startActivity(new Intent(AdminPage.this, AdminSubcat.class));
				finish();
				overridePendingTransition(0, 0);
				break;
			default:
				break;
			}

			break;
		case R.id.showall:
			globals.setchange("show");
			switch (choosevalue) {

			case "category":
				startActivity(new Intent(AdminPage.this, AdminCatList.class));
				finish();
				overridePendingTransition(0, 0);
				break;
			case "offers":

				globals.setchange("offers");
				startActivity(new Intent(AdminPage.this, AdminCatList.class));
				finish();
				overridePendingTransition(0, 0);
				break;

			case "subcategory":
				startActivity(new Intent(AdminPage.this, AdminSubcat.class));
				finish();
				overridePendingTransition(0, 0);

				break;
			case "product":
				globals.setchange("showproduct");
				startActivity(new Intent(AdminPage.this, AdminSubcat.class));
				finish();
				overridePendingTransition(0, 0);

				break;
			default:
				break;
			}
			break;

		case R.id.add:
			globals.setchange("add");
			switch (choosevalue) {
			case "category":

				dialog = new Dialog(AdminPage.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// setting custom layout to dialog
				dialog.setContentView(R.layout.add_edit_cat);

				tempimage = (CircularImageView) dialog
						.findViewById(R.id.newimage);
				final EditText newname = (EditText) dialog
						.findViewById(R.id.newname);
				Button change = (Button) dialog.findViewById(R.id.change);

				change.setText("Add Category");
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
						dialog = new Dialog(AdminPage.this);
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

								new Thread(null, Addnewcategory, "refresh_data")
										.start();
							}
						}, 0);

						dialog.show();
						dialog.setCancelable(false);
					}
				});

				dialog.show();
				break;
			case "offers":

				globals.setchange("offers");
				startActivity(new Intent(AdminPage.this, AdminSubcat.class));
				finish();
				overridePendingTransition(0, 0);
				break;

			case "subcategory":

				globals.setchange("subcatadd");
				startActivity(new Intent(AdminPage.this, AdminCatList.class));
				finish();
				overridePendingTransition(0, 0);
				break;
			case "product":

				globals.setchange("productadd");
				startActivity(new Intent(AdminPage.this, AdminSubcat.class));
				finish();
				overridePendingTransition(0, 0);

				break;
			default:
				break;
			}

			break;
		case R.id.logout:

			editor.putString("email", "");
			editor.putString("password", "");

			editor.commit();
			globals.setuserid("0");
			startActivity(new Intent(AdminPage.this, Account.class));
			finish();
			overridePendingTransition(0, 0);

			break;
		case R.id.appname:
			coupan.setBackgroundColor(Color.TRANSPARENT);
			deal.setBackgroundColor(Color.TRANSPARENT);
			cat.setBackgroundColor(Color.TRANSPARENT);
			subcat.setBackgroundColor(Color.TRANSPARENT);
			product.setBackgroundColor(Color.TRANSPARENT);
			orders.setBackgroundColor(Color.TRANSPARENT);
			appname.setText("WELCOME ADMIN "
					+ globals.getusername().toUpperCase());
			choosevalue = "";
			choose.setVisibility(8);
			options.setVisibility(8);
			choosetext.setVisibility(0);
			break;
		case R.id.cat:
			cat.setBackgroundColor(Color.parseColor("#66F6A81A"));
			coupan.setBackgroundColor(Color.TRANSPARENT);
			deal.setBackgroundColor(Color.TRANSPARENT);

			subcat.setBackgroundColor(Color.TRANSPARENT);
			product.setBackgroundColor(Color.TRANSPARENT);
			orders.setBackgroundColor(Color.TRANSPARENT);
			choosetext.setVisibility(8);
			choose.setVisibility(0);
			options.setVisibility(8);
			appname.setText("CATEGORIES");
			choosevalue = "category";
			break;
		case R.id.subcat:

			subcat.setBackgroundColor(Color.parseColor("#66F6A81A"));
			coupan.setBackgroundColor(Color.TRANSPARENT);
			deal.setBackgroundColor(Color.TRANSPARENT);
			cat.setBackgroundColor(Color.TRANSPARENT);

			product.setBackgroundColor(Color.TRANSPARENT);
			orders.setBackgroundColor(Color.TRANSPARENT);
			choosetext.setVisibility(8);
			choose.setVisibility(0);
			options.setVisibility(8);
			appname.setText("CATEGORIES");
			choosevalue = "subcategory";
			break;
		case R.id.product:

			product.setBackgroundColor(Color.parseColor("#66F6A81A"));
			coupan.setBackgroundColor(Color.TRANSPARENT);
			deal.setBackgroundColor(Color.TRANSPARENT);
			cat.setBackgroundColor(Color.TRANSPARENT);
			subcat.setBackgroundColor(Color.TRANSPARENT);

			orders.setBackgroundColor(Color.TRANSPARENT);
			choosetext.setVisibility(8);
			choose.setVisibility(0);
			options.setVisibility(8);
			appname.setText("PRODUCT");
			choosevalue = "product";
			break;
		case R.id.deals:
			deal.setBackgroundColor(Color.parseColor("#66F6A81A"));
			coupan.setBackgroundColor(Color.TRANSPARENT);

			cat.setBackgroundColor(Color.TRANSPARENT);
			subcat.setBackgroundColor(Color.TRANSPARENT);
			product.setBackgroundColor(Color.TRANSPARENT);
			orders.setBackgroundColor(Color.TRANSPARENT);
			choosetext.setVisibility(8);
			choose.setVisibility(0);
			options.setVisibility(8);
			appname.setText("DEALS");
			choosevalue = "offers";

			showall.setText("C");
			add.setText("SC");
			delete.setText("P");

			break;
		case R.id.choose:

			if (choosevalue.equals("coupan")) {
				String possible = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

				for (int i = 0; i < 5; i++) {
					couponcodetxt += possible.charAt((int) Math.floor(Math
							.random() * possible.length()));
				}
				dialog = new Dialog(AdminPage.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// setting custom layout to dialog
				dialog.setContentView(R.layout.test);
				TextView coupancode = (TextView) dialog
						.findViewById(R.id.coupancode);
				final EditText discountamount = (EditText) dialog
						.findViewById(R.id.discountamount);
				final EditText emailaddress = (EditText) dialog
						.findViewById(R.id.emailaddress);
				coupancode.setText("Coupon Code : " + couponcodetxt);

				discountamount.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						discountamount.setError(null);
					}
				});
				emailaddress.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						emailaddress.setError(null);
					}
				});
				LinearLayout send = (LinearLayout) dialog
						.findViewById(R.id.send);
				send.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						if (discountamount.getText().length() < 1) {

							discountamount.setError("Enter The Valid Amount");

						} else if (emailaddress.getText().length() < 4) {

							emailaddress.setError("Enter The Valid Email");

						} else {

							dialog.dismiss();
							discountamounttxt = discountamount.getText()
									.toString();
							emailaddresstxt = emailaddress.getText().toString();
							// TODO Auto-generated method stub
							dialog.dismiss();

							dialog = new Dialog(AdminPage.this);
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

									new Thread(null, SendCoponToEmail,
											"refresh_data").start();
								}
							}, 0);

							dialog.show();
							dialog.setCancelable(false);

						}

					}
				});

				dialog.show();
			} else if (choosevalue.equals("orders")) {
				startActivity(new Intent(AdminPage.this, AminOrder.class));
				finish();
				overridePendingTransition(0, 0);
			} else {

				if (check) {

					options.setVisibility(8);
					check = false;
				} else {

					options.setVisibility(0);
					check = true;
				}
			}
			break;
		case R.id.coupan:
			coupan.setBackgroundColor(Color.parseColor("#66F6A81A"));

			deal.setBackgroundColor(Color.TRANSPARENT);
			cat.setBackgroundColor(Color.TRANSPARENT);
			subcat.setBackgroundColor(Color.TRANSPARENT);
			product.setBackgroundColor(Color.TRANSPARENT);
			orders.setBackgroundColor(Color.TRANSPARENT);
			choosetext.setVisibility(8);
			choose.setVisibility(0);
			options.setVisibility(8);
			appname.setText("COUPAN");
			choosevalue = "coupan";

			break;
		case R.id.orders:
			orders.setBackgroundColor(Color.parseColor("#66F6A81A"));
			coupan.setBackgroundColor(Color.TRANSPARENT);
			deal.setBackgroundColor(Color.TRANSPARENT);
			cat.setBackgroundColor(Color.TRANSPARENT);
			subcat.setBackgroundColor(Color.TRANSPARENT);
			product.setBackgroundColor(Color.TRANSPARENT);
			choosetext.setVisibility(8);
			choose.setVisibility(0);
			options.setVisibility(8);
			appname.setText("ORDERS");
			choosevalue = "orders";

			break;

		default:
			break;
		}

	}

	Runnable SendCoponToEmail = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler.sendcoupon(getApplicationContext(),
						discountamounttxt, emailaddresstxt, couponcodetxt);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			SendCoponToEmailHandler.sendMessage(msg);

		}
	};

	Handler SendCoponToEmailHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			String res = msg.obj.toString();
			dialog.dismiss();
			if (res.equals("sucess")) {

				recreate();

			} else {

				dialog.dismiss();
				dialog = new Dialog(AdminPage.this);
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

	Runnable Addnewcategory = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler.addnewcategory(
						getApplicationContext(), tempcategory, newcatimage);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			AddnewcategoryHandler.sendMessage(msg);

		}
	};

	public void onBackPressed() {

	};

	Handler AddnewcategoryHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			String res = msg.obj.toString();
			dialog.dismiss();
			if (res.equals("sucess")) {

				recreate();

			} else {

				dialog.dismiss();
				dialog = new Dialog(AdminPage.this);
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

}
