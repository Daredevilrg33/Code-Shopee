package amit.techie33.shopbuycart;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import database.DatabaseHelper;

public class AddProduct extends Activity implements OnClickListener {

	DisplayMetrics displaymetrics;
	int width, height, val = 1;
	RelativeLayout.LayoutParams latoutparams;
	Globals globals;
	Animation animFadein;
	Typeface font;
	String userid, totalprice = "", strname = "", strcatid = "",
			strsubcatid = "", strcatname = "", strsubcatname = "",
			strdiscount = "", strmrp = "", strdealid = "", strdescription = "",
			strrating = "4", strp1 = "", strp2 = "", strp3 = "", strp4 = "",
			strp5 = "";
	Dialog dialog;
	ImageView fav, back, pdimageloader, photo1, photo2, photo3, photo4, photo5;
	Button addtocart;
	EditText name, oldprice, description, discount;
	TextView newprice, dealdetail, review, descriptiontext, dealdetailtext,
			appname;
	ImageView imagepager;
	Spinner size, Quantity;
	ScrollView background;

	LinearLayout sizelist;
	String photo = "0";
	Bitmap p1, p2, p3, p4, p5;
	ArrayList<String> quanty;
	DatabaseHelper db;
	RatingBar ratings;
	ArrayAdapter<String> subcat_list;
	ArrayList<String> subcat;
	private Uri mUri;
	private static final int IMAGE_GALLERY = 8, IMAGE_CAMERA = 9;
	private String selectedImagePath, productid, strsize, strquanty;
	Bitmap mBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addproduct);
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
		name = (EditText) findViewById(R.id.name);
		ratings = (RatingBar) findViewById(R.id.rating);

		oldprice = (EditText) findViewById(R.id.oldprice);
		newprice = (TextView) findViewById(R.id.newprice);
		description = (EditText) findViewById(R.id.description);
		dealdetail = (EditText) findViewById(R.id.dealdetail);
		review = (TextView) findViewById(R.id.review);
		discount = (EditText) findViewById(R.id.discount);
		fav = (ImageView) findViewById(R.id.fav);

		photo1 = (ImageView) findViewById(R.id.photo1);
		photo2 = (ImageView) findViewById(R.id.photo2);
		photo3 = (ImageView) findViewById(R.id.photo3);
		photo4 = (ImageView) findViewById(R.id.photo4);
		photo5 = (ImageView) findViewById(R.id.photo5);
		appname = (TextView) findViewById(R.id.newproduct);
		descriptiontext = (TextView) findViewById(R.id.descriptiontext);
		dealdetailtext = (TextView) findViewById(R.id.dealtext);
		addtocart = (Button) findViewById(R.id.addproduct);
		imagepager = (ImageView) findViewById(R.id.imagepager);
		back = (ImageView) findViewById(R.id.back);
		size = (Spinner) findViewById(R.id.size);
		background = (ScrollView) findViewById(R.id.background);
		// Quantity = (Spinner) findViewById(R.id.Quantity);
		// size.setOnItemSelectedListener(this);
		addtocart.setOnClickListener(this);
		back.setOnClickListener(this);
		addtocart.setOnClickListener(this);
		photo1.setOnClickListener(this);
		photo2.setOnClickListener(this);
		photo3.setOnClickListener(this);
		photo4.setOnClickListener(this);
		photo5.setOnClickListener(this);

		appname.setTypeface(font);
		addtocart.setTypeface(font);
		discount.setTypeface(font);
		name.setTypeface(font);
		descriptiontext.setTypeface(font);
		dealdetailtext.setTypeface(font);
		review.setTypeface(font);

		// imagepager.setLayoutParams(latoutparams);

		// dialog = new Dialog(AddProduct.this);
		// dialog.getWindow().setBackgroundDrawable(
		// new ColorDrawable(android.graphics.Color.TRANSPARENT));
		// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// // setting custom layout to dialog
		// dialog.setContentView(R.layout.progressdialog);
		//
		// pdimageloader = (ImageView) dialog.findViewById(R.id.pdimageloader);
		//
		// pdimageloader.startAnimation(animFadein);
		// new Handler().postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		//
		// new Thread(null, SubCategoryDeatil, "refresh_data").start();
		//
		// }
		// }, 0);
		//
		// dialog.show();
		// dialog.setCancelable(false);
		//
		// Loading();
		// Quantity(0);

	}

	// Runnable SubCategoryDeatil = new Runnable() {
	//
	// @Override
	// public void run() {
	//
	// String result = "";
	// try {
	//
	// result = WebServiceHandler
	// .SubCategoryListing(getApplicationContext());
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// Message msg = new Message();
	// msg.obj = result;
	// SubCategoryHandler.sendMessage(msg);
	//
	// }
	// };
	//
	// Handler SubCategoryHandler = new Handler() {
	//
	// @Override
	// public void handleMessage(Message msg) {
	//
	// String res = msg.obj.toString();
	//
	// if (res.equals("sucess")) {
	// dialog.dismiss();
	// Loading();
	// Quantity(0);
	// } else {
	// dialog.dismiss();
	//
	// dialog = new Dialog(AddProduct.this);
	// dialog.getWindow().setBackgroundDrawable(
	// new ColorDrawable(android.graphics.Color.TRANSPARENT));
	// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	// // setting custom layout to dialog
	// dialog.setContentView(R.layout.messagedailog);
	//
	// final TextView okbtn = (TextView) dialog
	// .findViewById(R.id.confirm);
	//
	// final TextView message = (TextView) dialog
	// .findViewById(R.id.message);
	//
	// message.setText("Unable Load Categories and Sub Categories Server Error Try After Some Time !");
	// okbtn.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// dialog.dismiss();
	// finish();
	// }
	// });
	// dialog.show();
	//
	// }
	//
	// }
	// };

	// private void Quantity(int pos) {
	// // TODO Auto-generated method stub
	// subcat = new ArrayList<String>();
	//
	// subcat = db.getSubcategories(String.valueOf(pos + 1));
	//
	// subcat_list = new ArrayAdapter<String>(AddProduct.this,
	// android.R.layout.simple_spinner_item, subcat);
	// subcat_list
	// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	//
	// Quantity.setAdapter(subcat_list);
	// subcat_list.notifyDataSetChanged();
	//
	// }

	// private void Loading() {
	//
	// ArrayList<String> cat = new ArrayList<String>();
	//
	// cat = db.getcategories();
	//
	// ArrayAdapter<String> cat_list = new ArrayAdapter<String>(
	// AddProduct.this, android.R.layout.simple_spinner_item, cat);
	// cat_list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	//
	// size.setAdapter(cat_list);
	//
	// }

	public String convertImageToString(Bitmap bit) {

		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		bit.compress(Bitmap.CompressFormat.JPEG, 70, bout);
		byte[] imagearray = bout.toByteArray();
		String encoded = Base64.encodeBytes(imagearray);
		Log.i("encoded image ", "length is : " + encoded.length());
		return encoded;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.photo1:
			photo = "1";

			getimage();
			break;
		case R.id.photo2:
			photo = "2";
			getimage();
			break;
		case R.id.photo3:
			photo = "3";
			getimage();
			break;
		case R.id.photo4:
			photo = "4";
			getimage();
			break;
		case R.id.photo5:
			photo = "5";
			getimage();
			break;
		case R.id.back:
			startActivity(new Intent(getApplicationContext(), AdminPage.class));
			finish();

			break;

		case R.id.addproduct:
			if (name.getText().toString().trim().length() < 2) {

				name.setError("Name not less than 2 charaters");
				name.setFocusable(true);

			} else if (oldprice.getText().toString().trim().length() < 0) {
				oldprice.setError("Name not less than 2 charaters");
				oldprice.setFocusable(true);

			} else {
				dialog = new Dialog(this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// setting custom layout to dialog
				dialog.setContentView(R.layout.messagedailog);

				final TextView okbtn = (TextView) dialog
						.findViewById(R.id.confirm);
				TextView message = (TextView) dialog.findViewById(R.id.message);

				message.setText(" Confirm to Add New Product  !");
				okbtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						dialog.dismiss();

						dialog = new Dialog(AddProduct.this);
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
								dialog.dismiss();
								dialog = new Dialog(AddProduct.this);
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

										new Thread(null, AddNewPRoduct,
												"refresh_data").start();
									}
								}, 0);

								dialog.show();
								dialog.setCancelable(false);
							}
						}, 0);

						dialog.show();
						dialog.setCancelable(false);

					}
				});
				dialog.show();
			}
			break;
		default:
			break;
		}
	};

	private void getimage() {
		CharSequence[] items2 = { "Camera", "Gallery" };
		AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
		builder2.setTitle("upload your car image");
		builder2.setItems(items2, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int val) {
				changeView(val);
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

				if (photo.equals("1")) {
					p1 = bitmap;
					strp1 = convertImageToString(p1);

					photo1.setImageBitmap(p1);
					break;
				} else if (photo.equals("2")) {
					p2 = bitmap;
					strp2 = convertImageToString(p2);

					photo2.setImageBitmap(p2);
					break;
				} else if (photo.equals("3")) {
					p3 = bitmap;
					strp3 = convertImageToString(p3);

					photo3.setImageBitmap(p3);
					break;
				} else if (photo.equals("4")) {
					p4 = bitmap;
					strp4 = convertImageToString(p4);

					photo4.setImageBitmap(p4);
					break;
				} else if (photo.equals("5")) {
					p5 = bitmap;
					strp5 = convertImageToString(p5);

					photo5.setImageBitmap(p5);
					break;
				}

			}

			break;
		case IMAGE_CAMERA:
			// only proceed if the result was RESULT_OK
			if (resultCode == RESULT_OK) {
				selectedImagePath = mUri.getPath();

				File f = new File(selectedImagePath);
				if (f.exists()) {
					// f.mkdirs();

					mBitmap = Utils.reduceImageSize(selectedImagePath);
				}
				if (photo.equals("1")) {
					p1 = mBitmap;
					strp1 = convertImageToString(p1);

					photo1.setImageBitmap(p1);
					break;
				} else if (photo.equals("2")) {
					p2 = mBitmap;
					strp2 = convertImageToString(p2);

					photo2.setImageBitmap(p2);
					break;
				} else if (photo.equals("3")) {
					p3 = mBitmap;
					strp3 = convertImageToString(p3);

					photo3.setImageBitmap(p3);
					break;
				} else if (photo.equals("4")) {
					p4 = mBitmap;
					strp4 = convertImageToString(p4);

					photo4.setImageBitmap(p4);
					break;
				} else if (photo.equals("5")) {
					p5 = mBitmap;
					strp5 = convertImageToString(p5);

					photo5.setImageBitmap(p5);
					break;
				}
			}

			break;

		default:
			break;
		}

	}

	Runnable AddNewPRoduct = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {
				strcatid = getIntent().getStringExtra("catid");
				strcatname = getIntent().getStringExtra("catnames");
				strsubcatname = getIntent().getStringExtra("subcatnames");
				strsubcatid = getIntent().getStringExtra("subcatid");
				strname = name.getText().toString();
				strdescription = description.getText().toString();
				strdiscount = discount.getText().toString();
				strdealid = "";
				strmrp = oldprice.getText().toString();
				strrating = "4";

				result = WebServiceHandler.AddNewPRoduct(
						getApplicationContext(), strname, strcatid,
						strsubcatid, strcatname, strsubcatname, strdiscount,
						strmrp, strdealid, strdescription, strrating, strp1,
						strp2, strp3, strp4, strp5);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			AddNewPRoductHandler.sendMessage(msg);

		}
	};

	Handler AddNewPRoductHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			final String res = msg.obj.toString();

			dialog.dismiss();
			if (res.equals("sucess")) {

				addsize();

			} else if (res.equals("Product Already in Product List")) {
				addsize();
			} else {

				dialog = new Dialog(AddProduct.this);
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
						recreate();
					}
				});
				dialog.show();

			}

		}

	};

	private void addsize() {
		dialog = new Dialog(AddProduct.this);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// setting custom layout to dialog
		dialog.setContentView(R.layout.addsize);

		Button cancel = (Button) dialog.findViewById(R.id.cancel);

		Button add = (Button) dialog.findViewById(R.id.add);
		final EditText size = (EditText) dialog.findViewById(R.id.size);
		final EditText qnty = (EditText) dialog.findViewById(R.id.quantity);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				dialog.dismiss();
				dialog = new Dialog(AddProduct.this);
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

						strsize = size.getText().toString();
						strquanty = qnty.getText().toString();
						new Thread(null, Addsize, "refresh_data").start();
					}
				}, 0);

				dialog.show();
				dialog.setCancelable(false);
			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				dialog = new Dialog(AddProduct.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// setting custom layout to dialog
				dialog.setContentView(R.layout.messagedailog);

				final TextView okbtn = (TextView) dialog
						.findViewById(R.id.confirm);
				TextView message = (TextView) dialog.findViewById(R.id.message);

				message.setText("Product Sucessfully Added ");
				okbtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						startActivity(new Intent(getApplicationContext(),
								AdminPage.class));
						finish();
					}
				});
				dialog.show();
			}
		});
		dialog.show();
	}

	Runnable Addsize = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				productid = globals.getpro_id();
				result = WebServiceHandler.AddSizePRoduct(
						getApplicationContext(), productid, strsize, strquanty);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			AddsizeHandler.sendMessage(msg);

		}
	};

	Handler AddsizeHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			final String res = msg.obj.toString();

			dialog.dismiss();

			addsize();

		}
	};
	/*
	 * @Override public void onItemSelected(AdapterView<?> parent, View v, int
	 * pos, long id) { // TODO Auto-generated method stub switch (v.getId()) {
	 * case R.id.size:
	 * 
	 * Quantity(pos + 1); subcat_list.notifyDataSetChanged(); break;
	 * 
	 * default: break; } }
	 */

	// @Override
	// public void onNothingSelected(AdapterView<?> parent) {
	// // TODO Auto-generated method stub
	//
	// }

}
