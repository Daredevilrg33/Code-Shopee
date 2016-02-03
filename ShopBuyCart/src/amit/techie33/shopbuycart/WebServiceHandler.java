package amit.techie33.shopbuycart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;
import database.DatabaseHelper;

@SuppressLint("SimpleDateFormat")
public class WebServiceHandler {
	static Globals global;
	static ArrayList<HashMap<String, String>> devicelist, deallist, sizelist,
			searchorderlistall, orderlist, selectproductlist, productimage,
			favproductlist, productlist, orderlistall, categorylist, cartlist,
			subcategorylist;
	static HashMap<String, String> devicemap, dealmap, categorymap, productmap,
			ordermapp, ordermap, cartmap, sizemap, subcategorymap;
	private static ArrayList<HashMap<String, String>> loginlist;
	static DatabaseHelper db;

	public static String DeviceRegister(Context c, String deviceid,
			String devicetype, String accesstoken) {
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/register.php?";
		global = (Globals) c.getApplicationContext();
		devicelist = new ArrayList<HashMap<String, String>>();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "register"));
			namepairs.add(new BasicNameValuePair("devicetoken", accesstoken));
			namepairs.add(new BasicNameValuePair("deviceid", deviceid));
			namepairs.add(new BasicNameValuePair("devicetype", devicetype));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				devicemap = new HashMap<String, String>();

				String device = job.getString("device");
				if (device.equals("register")
						|| device.equals("already registered")) {

					result = device;

					JSONObject object = job.getJSONObject("deatil");

					String id = object.getString("id");
					String device_token = object.getString("device_token");
					String device_id = object.getString("device_id");
					String device_type = object.getString("device_type");

					String login_type = object.getString("login_type");

					devicemap.put("id", id);
					devicemap.put("deviceid", device_id);
					devicemap.put("devicetoken", device_token);
					devicemap.put("devicetype", device_type);
					devicemap.put("logintype", login_type);

					devicelist.add(devicemap);

					global.setDeviceDetail(devicelist);

					result = "sucess";
				} else if (device.equals("error")) {
					global.setDeviceDetail(devicelist);
					result = "error";
				} else {
					global.setDeviceDetail(devicelist);
					result = job.getString("device");
				}

			} catch (Exception e) {
				e.printStackTrace();
				global.setDeviceDetail(devicelist);
				result = "SEVER ERROR";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			global.setDeviceDetail(devicelist);
			result = "SEVER ERROR";
		}// catch

		return result;
	}

	public static String DealListing(Context c) {
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/deal.php?";
		global = (Globals) c.getApplicationContext();
		deallist = new ArrayList<HashMap<String, String>>();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "listing"));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				String deal = job.getString("deal");
				if (deal.equals("deals")) {

					result = "sucess";
					JSONArray arry = job.getJSONArray("detail");

					for (int i = 0; i < arry.length(); i++) {

						dealmap = new HashMap<String, String>();

						JSONObject object = arry.getJSONObject(i);

						String id = object.getString("id");
						String typeid = object.getString("typeid");
						String dealtype = object.getString("deal_type");
						String image = object.getString("image");
						String discount = object.getString("discount");
						String date = object.getString("date");
						String weekly = object.getString("weekly");
						String dealday = object.getString("deal_day");

						dealmap.put("id", id);
						dealmap.put("typeid", typeid);
						dealmap.put("dealtype", dealtype);
						dealmap.put("image", image);
						dealmap.put("discount", discount);
						dealmap.put("date", date);
						dealmap.put("weekly", weekly);
						dealmap.put("dealday", dealday);

						deallist.add(dealmap);
					}
					global.setDealList(deallist);

				} else if (deal.equals("error")) {
					global.setDealList(deallist);
					result = "Server error";
				} else {

					result = job.getString("device");
					global.setDealList(deallist);
				}

			} catch (Exception e) {
				e.printStackTrace();
				global.setDealList(deallist);
				result = "Server error";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			global.setDealList(deallist);
			result = "Server error";
		}// catch

		return result;
	}

	public static String CategoryListing(Context c) {
		// TODO Auto-generated method stub
		String result = "";
		String respage = "";

		String url = "http://jobsforyouonly.com/shopby/category.php";
		global = (Globals) c.getApplicationContext();
		categorylist = new ArrayList<HashMap<String, String>>();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
//			namepairs.add(new BasicNameValuePair("action", "listing"));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage  = dhc.execute(postMethod, res);
				
				System.out.println("respage--->"+respage);

				JSONObject job = new JSONObject(respage);

				String deal = job.getString("categories");
				if (deal.equals("categories")) {

					result = "sucess";
					JSONArray arry = job.getJSONArray("detail");

					for (int i = 0; i < arry.length(); i++) {

						categorymap = new HashMap<String, String>();

						JSONObject object = arry.getJSONObject(i);

						String id = object.getString("id");
						String category = object.getString("category");
						String image = object.getString("image");
						String dealid = object.getString("deal_id");

						categorymap.put("id", id);
						categorymap.put("catgeory", category);
						categorymap.put("image", image);
						categorymap.put("dealid", dealid);

						categorylist.add(categorymap);
					}
					global.setCategoryList(categorylist);

				} else if (deal.equals("error")) {
					global.setCategoryList(categorylist);
					result = "server error";
				} else {
					global.setCategoryList(categorylist);
					result = job.getString("device");
				}

			} catch (Exception e) {
				e.printStackTrace();
				global.setCategoryList(categorylist);
				result = "server error";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			global.setCategoryList(categorylist);
			result = "server error";
		}// catch

		return result;
	}

	public static String SubCategoryListing(Context c) {
		// TODO Auto-generated method stub
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/subcategory.php?";
		global = (Globals) c.getApplicationContext();
		db = new DatabaseHelper(c);
		subcategorylist = new ArrayList<HashMap<String, String>>();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "allsubcategory"));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				String deal = job.getString("subcategories");
				if (deal.equals("subcategories")) {

					result = "sucess";
					JSONArray arry = job.getJSONArray("detail");

					for (int i = 0; i < arry.length(); i++) {

						subcategorymap = new HashMap<String, String>();

						JSONObject object = arry.getJSONObject(i);

						String id = object.getString("id");
						String catid = object.getString("cat_id");
						String category = object.getString("category");
						String subcategory = object.getString("subcategory");
						String image = object.getString("image");
						String dealid = object.getString("deal_id");

						subcategorymap.put("id", id);
						subcategorymap.put("catid", catid);
						subcategorymap.put("subcategory", subcategory);
						subcategorymap.put("catgeory", category);
						subcategorymap.put("image", image);
						subcategorymap.put("dealid", dealid);

						int ss = db.CHECKSUBCAT(id);
						if (ss == 0) {
							db.INSERT_SUB(catid, id, category, subcategory,
									image);
						} else {
						}
						subcategorylist.add(subcategorymap);
					}
					global.setSubCategoryList(subcategorylist);

				} else if (deal.equals("error")) {
					global.setSubCategoryList(subcategorylist);
					result = "server error";
				} else {
					global.setSubCategoryList(subcategorylist);
					result = job.getString("device");
				}

			} catch (Exception e) {
				e.printStackTrace();
				global.setSubCategoryList(subcategorylist);
				result = "server error";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			global.setSubCategoryList(subcategorylist);
			result = "server error";
		}// catch

		return result;
	}

	public static String ProductListing(Context c, String subcatid,String catid,String lat,String log) {
		// TODO Auto-generated method stub
		String result = "";
		String respage = "";

		String url = "http://jobsforyouonly.com/shopby/product_list.php";
		global = (Globals) c.getApplicationContext();
		productlist = new ArrayList<HashMap<String, String>>();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("catid", catid));
			namepairs.add(new BasicNameValuePair("scatid", subcatid));
			namepairs.add(new BasicNameValuePair("lat", lat));
			namepairs.add(new BasicNameValuePair("lng", log));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				String deal = job.getString("product");
				if (deal.equals("product")) {

					result = "sucess";
					JSONArray arry = job.getJSONArray("detail");

					for (int i = 0; i < arry.length(); i++) {

						productmap = new HashMap<String, String>();

						JSONObject object = arry.getJSONObject(i);

						JSONObject pro = object.getJSONObject("productdetail");

						productmap.put("id", String.valueOf(pro.get("id")));
						productmap.put("name", String.valueOf(pro.get("name")));
						productmap.put("catid",
								String.valueOf(pro.get("cat_id")));
						productmap.put("subcatid",
								String.valueOf(pro.get("subcat_id")));
						productmap.put("category",
								String.valueOf(pro.get("catgeory")));
						productmap.put("subcategory",
								String.valueOf(pro.get("subcategory")));
						productmap.put("dealid",
								String.valueOf(pro.get("deal_id")));
						productmap.put("outofstock",
								String.valueOf(pro.get("out_of_stock")));
						productmap.put("description",
								String.valueOf(pro.get("description")));
						productmap.put("rating",
								String.valueOf(pro.get("rating")));
						productmap.put("type", String.valueOf(pro.get("type")));
						productmap.put("mrp", String.valueOf(pro.get("mrp")));
						productmap.put("discount",
								String.valueOf(pro.get("discount")));
						productmap.put("discountprice",
								String.valueOf(pro.get("discountprice")));

						JSONObject proimg = object.getJSONObject("images");

						productmap.put("image",
								String.valueOf(proimg.get("image_1")));
						productmap.put("image3",
								String.valueOf(proimg.get("image_3")));
						productmap.put("image4",
								String.valueOf(proimg.get("image_4")));
						productmap.put("image5",
								String.valueOf(proimg.get("image_5")));

						productlist.add(productmap);

					}
					global.setProductList(productlist);

				} else if (deal.equals("error")) {

					global.setProductList(productlist);
					result = "server error";
				} else {

					global.setProductList(productlist);
					result = job.getString("device");
				}

			} catch (Exception e) {
				global.setProductList(productlist);
				result = "server error";
				e.printStackTrace();
			}//

		} catch (Exception e) {
			global.setProductList(productlist);
			result = "server error";
			e.printStackTrace();
		}// catch

		return result;
	}

	public static String searchProduct(Context c, String productid) {
		// TODO Auto-generated method stub
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/product.php?";
		global = (Globals) c.getApplicationContext();
		selectproductlist = new ArrayList<HashMap<String, String>>();
		productimage = new ArrayList<HashMap<String, String>>();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "search"));
			namepairs.add(new BasicNameValuePair("productid", productid));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				String deal = job.getString("product");
				if (deal.equals("product")) {

					result = "sucess";
					JSONArray arry = job.getJSONArray("detail");

					for (int i = 0; i < arry.length(); i++) {

						productmap = new HashMap<String, String>();

						JSONObject object = arry.getJSONObject(i);

						JSONObject pro = object.getJSONObject("productdetail");

						productmap.put("id", String.valueOf(pro.get("id")));
						productmap.put("name", String.valueOf(pro.get("name")));
						productmap.put("catid",
								String.valueOf(pro.get("cat_id")));
						productmap.put("subcatid",
								String.valueOf(pro.get("subcat_id")));
						productmap.put("category",
								String.valueOf(pro.get("catgeory")));
						productmap.put("subcategory",
								String.valueOf(pro.get("subcategory")));
						productmap.put("dealid",
								String.valueOf(pro.get("deal_id")));
						productmap.put("outofstock",
								String.valueOf(pro.get("out_of_stock")));
						productmap.put("description",
								String.valueOf(pro.get("description")));
						productmap.put("rating",
								String.valueOf(pro.get("rating")));
						productmap.put("type", String.valueOf(pro.get("type")));
						productmap.put("mrp", String.valueOf(pro.get("mrp")));
						productmap.put("discount",
								String.valueOf(pro.get("discount")));
						productmap.put("discountprice",
								String.valueOf(pro.get("discountprice")));

						JSONObject proimg = object.getJSONObject("images");
						HashMap<String, String> imagerproductmap = new HashMap<String, String>();

						imagerproductmap.put("image",
								String.valueOf(proimg.get("image_1")));
						productimage.add(imagerproductmap);
						imagerproductmap = new HashMap<String, String>();
						imagerproductmap.put("image",
								String.valueOf(proimg.get("image_3")));
						productimage.add(imagerproductmap);
						imagerproductmap = new HashMap<String, String>();
						imagerproductmap.put("image",
								String.valueOf(proimg.get("image_4")));
						productimage.add(imagerproductmap);
						imagerproductmap = new HashMap<String, String>();
						imagerproductmap.put("image",
								String.valueOf(proimg.get("image_5")));
						productimage.add(imagerproductmap);

						selectproductlist.add(productmap);

						productimage.add(imagerproductmap);

					}
					global.setSelectProductList(selectproductlist);
					global.setProductImageList(productimage);

				} else if (deal.equals("error")) {
					global.setSelectProductList(selectproductlist);
					global.setProductImageList(productimage);
					result = "server error";
				} else {
					global.setSelectProductList(selectproductlist);
					global.setProductImageList(productimage);
					result = job.getString("device");
				}

			} catch (Exception e) {
				e.printStackTrace();
				global.setSelectProductList(selectproductlist);
				global.setProductImageList(productimage);
				result = "server error";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			global.setSelectProductList(selectproductlist);
			global.setProductImageList(productimage);
			result = "server error";
		}// catch

		return result;
	}

	public static String sizelist(Context c, String productid) {
		// TODO Auto-generated method stub
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/product.php?";
		global = (Globals) c.getApplicationContext();
		sizelist = new ArrayList<HashMap<String, String>>();
		ArrayList<String> listsize = new ArrayList<String>();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "size"));
			namepairs.add(new BasicNameValuePair("productid", productid));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				String deal = job.getString("size");
				if (deal.equals("size")) {

					result = "sucess";
					JSONArray arry = job.getJSONArray("detail");

					for (int i = 0; i < arry.length(); i++) {

						sizemap = new HashMap<String, String>();

						JSONObject object = arry.getJSONObject(i);

						String id = object.getString("id");
						String size = object.getString("size");
						String quantity = object.getString("quantity");

						sizemap.put("id", id);
						sizemap.put("size", size);
						sizemap.put("quantity", quantity);

						sizelist.add(sizemap);
						listsize.add(size);
					}
					global.setSizeList(sizelist);
					global.setSize(listsize);

				} else if (deal.equals("error")) {
					global.setSizeList(sizelist);
					global.setSize(listsize);
					result = "server error";
				} else {
					global.setSizeList(sizelist);
					global.setSize(listsize);
					result = job.getString("device");
				}

			} catch (Exception e) {
				e.printStackTrace();
				global.setSizeList(sizelist);
				global.setSize(listsize);
				result = "server error";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			global.setSizeList(sizelist);
			global.setSize(listsize);
			result = "server error";
		}// catch

		return result;
	}

	public static String AddtoCart(Context c, String productid, String userid,
			String strquantity, String totalprice, String strsize) {
		// TODO Auto-generated method stub
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/cart.php?";
		global = (Globals) c.getApplicationContext();
		sizelist = new ArrayList<HashMap<String, String>>();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "addtocart"));
			namepairs.add(new BasicNameValuePair("productid", productid));
			namepairs.add(new BasicNameValuePair("userid", userid));
			namepairs.add(new BasicNameValuePair("size", strsize));
			namepairs.add(new BasicNameValuePair("quantity", strquantity));
			namepairs.add(new BasicNameValuePair("total", totalprice));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				String deal = job.getString("cart");
				if (deal.equals("cart")) {

					// String detail = job.getString("detail");

					result = "sucess";
				} else {

					result = job.getString("cart");
				}

			} catch (Exception e) {
				e.printStackTrace();
				result = "Server Error";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			result = "Server Error";
		}// catch

		return result;
	}

	public static String CartList(Context c, String userid) {
		// TODO Auto-generated method stub
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/cart.php?";
		global = (Globals) c.getApplicationContext();
		cartlist = new ArrayList<HashMap<String, String>>();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "listing"));
			namepairs.add(new BasicNameValuePair("userid", userid));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				String deal = job.getString("cart");
				if (deal.equals("cart")) {

					result = "sucess";
					JSONArray arry = job.getJSONArray("detail");

					for (int i = 0; i < arry.length(); i++) {

						cartmap = new HashMap<String, String>();

						JSONObject object = arry.getJSONObject(i);

						String id = object.getString("id");
						String productid = object.getString("productid");
						String quantity = object.getString("quantity");
						String cartdate = object.getString("cart_date");
						String discountprice = object
								.getString("discount_price");
						String mrp = object.getString("mrp");
						String size = object.getString("size");
						String discount = object.getString("discount");
						String image = object.getString("image");
						String name = object.getString("name");
						String status = object.getString("status");

						cartmap.put("id", id);
						cartmap.put("productid", productid);
						cartmap.put("quantity", quantity);
						cartmap.put("cartdate", cartdate);
						cartmap.put("discountprice", discountprice);
						cartmap.put("discount", discount);
						cartmap.put("image", image);
						cartmap.put("mrp", mrp);
						cartmap.put("name", name);
						cartmap.put("size", size);
						cartmap.put("status", status);

						cartlist.add(cartmap);
					}
					global.setCartList(cartlist);

				} else if (deal.equals("error")) {
					global.setCartList(cartlist);
					result = "error";
				} else {
					global.setCartList(cartlist);
					result = job.getString("cart");
				}

			} catch (Exception e) {
				e.printStackTrace();
				global.setCartList(cartlist);
				result = "error";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			global.setCartList(cartlist);
			result = "error";
		}// catch

		return result;
	}

	public static String OrderNow(Context c, String orderid, String userid,
			String track, String total, String log) {
		// TODO Auto-generated method stub
		String result = "";
		String respage = "";

		String url = "http://jobsforyouonly.com/shopby/order.php";
		global = (Globals) c.getApplicationContext();
		cartlist = new ArrayList<HashMap<String, String>>();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
//			namepairs.add(new BasicNameValuePair("total", "order"));
			namepairs.add(new BasicNameValuePair("uid", userid));
			namepairs.add(new BasicNameValuePair("ono", orderid));
			namepairs.add(new BasicNameValuePair("total", total));
			namepairs.add(new BasicNameValuePair("track", track));
//			namepairs.add(new BasicNameValuePair("longitude", log));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);
					System.out.println("respage-------------"+respage);
				String deal = job.getString("order");
				if (deal.equals("order")) {

					result = job.getString("detail");

				} else {

					result = job.getString("order");
				}

			} catch (Exception e) {
				e.printStackTrace();
				result = "serever error";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			result = "serever error";
		}// catch

		return result;
	}

	public static String OrderList(Context c, String userid) {
		// TODO Auto-generated method stub
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/order.php?";
		global = (Globals) c.getApplicationContext();
		orderlist = new ArrayList<HashMap<String, String>>();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "listing"));
			namepairs.add(new BasicNameValuePair("userid", userid));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				String deal = job.getString("order");
				if (deal.equals("order")) {

					result = "sucess";
					JSONArray arry = job.getJSONArray("detail");

					for (int i = 0; i < arry.length(); i++) {

						ordermap = new HashMap<String, String>();

						JSONObject object = arry.getJSONObject(i);

						String id = object.getString("id");
						String productid = object.getString("productid");
						String quantity = object.getString("quantity");
						String deliverydate = object.getString("delivery_date");
						String discountprice = object
								.getString("discount_price");
						String mrp = object.getString("mrp");
						String size = object.getString("size");
						String discount = object.getString("discount");
						String image = object.getString("image");
						String name = object.getString("name");
						String items = object.getString("items");
						String totalprice = object.getString("totalprice");
						String status = object.getString("status");
						String address = object.getString("address");

						String orderid = object.getString("orderid");

						ordermap.put("id", id);
						ordermap.put("items", items);
						ordermap.put("address", address);
						ordermap.put("orderid", orderid);
						ordermap.put("totalprice", totalprice);
						ordermap.put("productid", productid);
						ordermap.put("quantity", quantity);
						ordermap.put("deliverydate", deliverydate);
						ordermap.put("discountprice", discountprice);
						ordermap.put("discount", discount);
						ordermap.put("image", image);
						ordermap.put("mrp", mrp);
						ordermap.put("name", name);
						ordermap.put("size", size);
						ordermap.put("status", status);

						orderlist.add(ordermap);
					}
					global.setOrderList(orderlist);

				} else if (deal.equals("error")) {
					global.setOrderList(orderlist);
					result = "error";
				} else {
					global.setOrderList(orderlist);
					result = job.getString("cart");
				}

			} catch (Exception e) {
				e.printStackTrace();
				global.setOrderList(orderlist);
				result = "error";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			global.setOrderList(orderlist);
			result = "error";
		}// catch

		return result;
	}

	public static String RegisterService(Context c, String username,
			String stremail, String strpassword, String strphone,
			String stranswer, String straddress, double latitude,
			double longitude) {
		String result = "";
		String respage = "";
		TelephonyManager mngr = (TelephonyManager) c
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceid = mngr.getDeviceId();
		String devicetype = android.os.Build.MODEL + " "
				+ android.os.Build.DISPLAY;
		String accesstoken = mngr.getLine1Number();

		String url = "http://jobsforyouonly.com/shopby/user_sign_up.php?";
		global = (Globals) c.getApplicationContext();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
//			namepairs.add(new BasicNameValuePair("action", "userregister"));
			namepairs.add(new BasicNameValuePair("dtoken", accesstoken));
//			namepairs.add(new BasicNameValuePair("deviceid", deviceid));
			namepairs.add(new BasicNameValuePair("dtype", devicetype));
			namepairs.add(new BasicNameValuePair("uname", username));
			namepairs.add(new BasicNameValuePair("ans", stranswer));
			namepairs.add(new BasicNameValuePair("que",
					"what is your pet name ?"));

			namepairs.add(new BasicNameValuePair("lng", String
					.valueOf(longitude)));
			namepairs.add(new BasicNameValuePair("lat", String
					.valueOf(latitude)));
//			namepairs.add(new BasicNameValuePair("address", straddress));
			namepairs.add(new BasicNameValuePair("phone", strphone));
//			namepairs.add(new BasicNameValuePair("status", "user"));

			namepairs.add(new BasicNameValuePair("mail", stremail));
			namepairs.add(new BasicNameValuePair("pass", strpassword));
//			namepairs.add(new BasicNameValuePair("status", "user"));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);
				
//				System.out.println("--------respage---------"+respage);

				JSONObject job = new JSONObject(respage);

				String device = job.getString("status");
				if (device.equals("successfull")) {

					String Log_ID = job.getJSONObject("result").getString("Log_ID");
					String Username = job.getJSONObject("result").getString("Username");
					String Email = job.getJSONObject("result").getString("Email");
					String Password = job.getJSONObject("result").getString("Password");
					String Phone = job.getJSONObject("result").getString("Phone");
					
					String Device_Type = job.getJSONObject("result").getString("Device_Type");
					String Lat = job.getJSONObject("result").getString("Lat");
					String Lng = job.getJSONObject("result").getString("Lng");
					String Added_Date = job.getJSONObject("result").getString("Added_Date");
					
					
					result = "sucess";
//
//					JSONObject object = job.getJSONObject("deatil");
//
//					String id = object.getString("id");
//
					global.setuserid(Log_ID);
					global.setusername(Username);

				} 
				
//				else if (device.equals("exist")) {
//					global.setuserid("0");
//					result = "already registered";
//				} else if (device.equals("error")) {
//					global.setuserid("0");
//					result = "Login error";
//				} 
//					else {
//					global.setuserid("0");
//					result = job.getString("device");
//				}

			} catch (Exception e) {
				e.printStackTrace();
				global.setuserid("0");
				result = "Register error";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			global.setuserid("0");
			result = "Login error";
		}// catch

		return result;
	}

	public static String UserLogin(Context c, String stremail,
			String strpassword, String address, double latitude,
			double longitude) {
		String result = "";
		String respage = "";

		String url = "http://jobsforyouonly.com/shopby/user_login.php?";
		global = (Globals) c.getApplicationContext();
		TelephonyManager mngr = (TelephonyManager) c
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceid = mngr.getDeviceId();
		String devicetype = android.os.Build.MODEL + " "
				+ android.os.Build.DISPLAY;
		String accesstoken = mngr.getLine1Number();

		loginlist = new ArrayList<HashMap<String, String>>();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
//			namepairs.add(new BasicNameValuePair("action", "login"));
//			namepairs.add(new BasicNameValuePair("devicetoken", accesstoken));
//			namepairs.add(new BasicNameValuePair("deviceid", deviceid));
//			namepairs.add(new BasicNameValuePair("devicetype", devicetype));
			namepairs.add(new BasicNameValuePair("uname", stremail));
			namepairs.add(new BasicNameValuePair("pass", strpassword));
//			namepairs.add(new BasicNameValuePair("logintype", "user"));
//			namepairs.add(new BasicNameValuePair("longitude", String
//					.valueOf(longitude)));
//			namepairs.add(new BasicNameValuePair("latitude", String
//					.valueOf(latitude)));
//			namepairs.add(new BasicNameValuePair("address", address));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);
				
				System.out.println("--------respage-----------"+respage);

				JSONObject job = new JSONObject(respage);

				devicemap = new HashMap<String, String>();

				String device = job.getString("status");
				if (device.equals("exist")) {

					result = "sucess";
					
					String Log_ID = job.getJSONObject("result").getString("Log_ID");
					String Username = job.getJSONObject("result").getString("Username");
					String Email = job.getJSONObject("result").getString("Email");
					String Password = job.getJSONObject("result").getString("Password");
					String Phone = job.getJSONObject("result").getString("Phone");
					
					String Device_Type = job.getJSONObject("result").getString("Device_Type");
					String Lat = job.getJSONObject("result").getString("Lat");
					String Lng = job.getJSONObject("result").getString("Lng");
					String Added_Date = job.getJSONObject("result").getString("Added_Date");

////					JSONObject object = job.getJSONObject("detail");
////
////					String id = object.getString("id");
////					String status = object.getString("status");
////					if (status.equals("admin")) {
////						result = "admin";
//						global.setuserid(id);
//						global.setadminemail(object.getString("email"));
//						global.setadminpassword(object.getString("password"));
//
//						global.setusername(object.getString("name"));
//					}

					global.setuserid(Log_ID);
					global.setusername(Username);

//				} else if (device.equals("error")) {
//					global.setuserid("0");
//					result = "Login error";
				}
					else {
					global.setuserid("0");
					result = job.getString("device");
				}
			

			} catch (Exception e) {
				e.printStackTrace();
				global.setuserid("0");
				result = "Login error";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			global.setuserid("0");
			result = "Login error";
		}// catch

		return result;
		
	}

	public static String AddNewPRoduct(Context c, String strname,
			String strcatid, String strsubcatid, String strcatname,
			String strsubcatname, String strdiscount, String strmrp,
			String strdealid, String strdescription, String strrating,
			String strp1, String strp2, String strp3, String strp4, String strp5) {
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/product.php?";
		global = (Globals) c.getApplicationContext();
		TelephonyManager mngr = (TelephonyManager) c
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceid = mngr.getDeviceId();
		String devicetype = android.os.Build.MODEL + " "
				+ android.os.Build.DISPLAY;
		String accesstoken = mngr.getLine1Number();

		loginlist = new ArrayList<HashMap<String, String>>();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "addproduct"));

			namepairs.add(new BasicNameValuePair("catid", strcatid));
			namepairs.add(new BasicNameValuePair("name", strname));
			namepairs.add(new BasicNameValuePair("subcatid", strsubcatid));
			namepairs.add(new BasicNameValuePair("category", strcatname));
			namepairs.add(new BasicNameValuePair("subcategory", strsubcatname));
			namepairs.add(new BasicNameValuePair("discount", strdiscount));
			namepairs.add(new BasicNameValuePair("dealid", ""));
			namepairs
					.add(new BasicNameValuePair("description", strdescription));
			namepairs.add(new BasicNameValuePair("rating", "4"));
			namepairs.add(new BasicNameValuePair("mrp", strmrp));
			namepairs.add(new BasicNameValuePair("photo1", strp1));
			namepairs.add(new BasicNameValuePair("photo2", strp2));
			namepairs.add(new BasicNameValuePair("photo3", strp3));
			namepairs.add(new BasicNameValuePair("photo4", strp4));
			namepairs.add(new BasicNameValuePair("photo5", strp5));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				String device = job.getString("size");
				if (device.equals("added")) {

					result = "sucess";
					String productid = job.getString("detail");
					global.setpro_id(productid);

				} else if (device.equals("Product Already in Product List")) {
					result = "Product Already in Product List";

					String productid = job.getString("detail");
					global.setpro_id(productid);

				} else {

					result = job.getString("size");
				}

			} catch (Exception e) {
				e.printStackTrace();
				result = "Server error";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			result = "Network error";
		}// catch

		return result;
	}

	public static String addnewcategory(Context c, String tempcategory,
			String catimage) {
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/category.php?";
		global = (Globals) c.getApplicationContext();

		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "insert"));
			namepairs.add(new BasicNameValuePair("image", catimage));
			namepairs.add(new BasicNameValuePair("category", tempcategory));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				String device = job.getString("category");
				if (device.equals("category added")) {

					result = "sucess";

				} else {

					result = job.getString("category");
				}

			} catch (Exception e) {
				e.printStackTrace();
				result = "!Sorry cannot complete your request now\n Please Try Again Later";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			result = "!Sorry cannot complete your request now\n Please Try Again Later";
		}// catch

		return result;
	}

	public static String addnewsubcategory(Context c, String catid,
			String tempcategory, String newsubcatimage) {
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/subcategory.php?";
		global = (Globals) c.getApplicationContext();

		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "insert"));
			namepairs.add(new BasicNameValuePair("image", newsubcatimage));
			namepairs.add(new BasicNameValuePair("catid", catid));
			namepairs.add(new BasicNameValuePair("subcategory", tempcategory));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				String device = job.getString("subcategory");
				if (device.equals("Subcategory Added")) {

					result = "sucess";

				} else {

					result = job.getString("subcategory");
				}

			} catch (Exception e) {
				e.printStackTrace();
				result = "! Sorry \ncannot complete your request now\n Please Try Again Later";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			result = "! Sorry \ncannot complete your request now\n Please Try Again Later";
		}// catch

		return result;
	}

	public static String sendcoupon(Context c, String discountamounttxt,
			String emailaddresstxt, String couponcodetxt) {
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/order.php?";
		global = (Globals) c.getApplicationContext();

		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "coupan"));
			namepairs.add(new BasicNameValuePair("coupancode", couponcodetxt));
			namepairs.add(new BasicNameValuePair("amount", discountamounttxt));
			namepairs.add(new BasicNameValuePair("email", emailaddresstxt));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				result = "Thanks For Using ShopBuyCart";

			} catch (Exception e) {
				e.printStackTrace();
				result = "! Sorry \ncannot complete your request now\n Please Try Again Later";
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "! Sorry \ncannot complete your request now\n Please Try Again Later";
		}// catch

		return result;
	}

	public static String deletecategory(Context c, String catid) {
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/category.php?";
		global = (Globals) c.getApplicationContext();

		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "delete"));
			namepairs.add(new BasicNameValuePair("categoryid", catid));

			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				result = "Thanks For Using ShopBuyCart";

			} catch (Exception e) {
				e.printStackTrace();
				result = "! Sorry \ncannot complete your request now\n Please Try Again Later";
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "! Sorry \ncannot complete your request now\n Please Try Again Later";
		}// catch

		return result;
	}

	public static String deletesubcategory(Context c, String subcatid) {
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/subcategory.php?";
		global = (Globals) c.getApplicationContext();

		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "delete"));
			namepairs.add(new BasicNameValuePair("subcatid", subcatid));

			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				result = "Thanks For Using ShopBuyCart";

			} catch (Exception e) {
				e.printStackTrace();
				result = "! Sorry \ncannot complete your request now\n Please Try Again Later";
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "! Sorry \ncannot complete your request now\n Please Try Again Later";
		}// catch

		return result;
	}

	public static String deleteproduct(Context c, String productid) {
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/product.php?";
		global = (Globals) c.getApplicationContext();

		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "delete"));
			namepairs.add(new BasicNameValuePair("productid", productid));

			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				result = "Thanks For Using ShopBuyCart";

			} catch (Exception e) {
				e.printStackTrace();
				result = "! Sorry \ncannot complete your request now\n Please Try Again Later";
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "! Sorry \ncannot complete your request now\n Please Try Again Later";
		}// catch

		return result;
	}

	public static String Orderlist(Context c) {
		// TODO Auto-generated method stub
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/order.php?";
		global = (Globals) c.getApplicationContext();
		cartlist = new ArrayList<HashMap<String, String>>();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "allorder"));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				String deal = job.getString("order");
				if (deal.equals("order")) {

					result = "sucess";
					JSONArray arry = job.getJSONArray("detail");
					orderlistall = new ArrayList<HashMap<String, String>>();
					for (int i = 0; i < arry.length(); i++) {

						ordermapp = new HashMap<String, String>();

						JSONObject object = arry.getJSONObject(i);

						String id = object.getString("id");

						String status = object.getString("status");
						String orderid = object.getString("orderid");

						ordermapp.put("id", id);

						ordermapp.put("orderid", orderid);

						ordermapp.put("status", status);

						orderlistall.add(ordermapp);
					}
					global.setOrderListall(orderlistall);

				} else if (deal.equals("no product ")) {

					global.setOrderListall(orderlist);
					result = "No Order Placed";
				} else if (deal.equals("error")) {
					global.setOrderListall(orderlist);
					result = "error";
				} else {
					global.setOrderListall(orderlist);
					result = job.getString("cart");
				}

			} catch (Exception e) {
				e.printStackTrace();
				result = "!sorry \n Cannot Complete Your request now ";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			result = "!sorry \n Cannot Complete Your request now ";
		}// catch

		return result;
	}

	public static String updateOrderlist(Context c, String orderid,
			String currentstatus) {
		// TODO Auto-generated method stub
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/order.php?";
		global = (Globals) c.getApplicationContext();
		cartlist = new ArrayList<HashMap<String, String>>();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "update"));
			namepairs.add(new BasicNameValuePair("orderid", orderid));
			namepairs.add(new BasicNameValuePair("status", currentstatus));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				result = "sucess";
			} catch (Exception e) {
				e.printStackTrace();
				result = "!sorry \n Cannot Complete Your request now ";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			result = "!sorry \n Cannot Complete Your request now ";
		}// catch

		return result;
	}

//	public static String searchOrderlist(Context c, String orderid) {
//		// TODO Auto-generated method stub
//		String result = "";
//		String respage = "";
//
//		String url = "http://jobsforyouonly.com/shopby/search.php";
//		global = (Globals) c.getApplicationContext();
//		cartlist = new ArrayList<HashMap<String, String>>();
//		try {
//			DefaultHttpClient dhc = new DefaultHttpClient();
//			ResponseHandler<String> res = new BasicResponseHandler();
//
//			HttpPost postMethod = new HttpPost(url);
//
//			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
////			namepairs.add(new BasicNameValuePair("action", "search"));
//			namepairs.add(new BasicNameValuePair("keyword", orderid));
//			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));
//
//			try {
//				respage = dhc.execute(postMethod, res);
//				System.out.println("----respage--->"+respage );
//				JSONObject job = new JSONObject(respage);
//
//				String deal = job.getString("order");
//				if (deal.equals("order")) {
//
//					result = "sucess";
//					JSONArray arry = job.getJSONArray("detail");
//					searchorderlistall = new ArrayList<HashMap<String, String>>();
//					for (int i = 0; i < arry.length(); i++) {
//
//						ordermapp = new HashMap<String, String>();
//
//						JSONObject object = arry.getJSONObject(i);
//
//						String id = object.getString("id");
//						String productid = object.getString("productid");
//						String quantity = object.getString("quantity");
//						String deliverydate = object.getString("delivery_date");
//						String discountprice = object
//								.getString("discount_price");
//						String mrp = object.getString("mrp");
//						String size = object.getString("size");
//						String discount = object.getString("discount");
//						String image = object.getString("image");
//						String name = object.getString("name");
//						String items = object.getString("items");
//						String totalprice = object.getString("totalprice");
//						String status = object.getString("status");
//						String orderids = object.getString("orderid");
//						String address = object.getString("address");
//
//						ordermapp.put("id", id);
//						ordermapp.put("items", items);
//						ordermapp.put("orderid", orderids);
//						ordermapp.put("address", address);
//						ordermapp.put("totalprice", totalprice);
//						ordermapp.put("productid", productid);
//						ordermapp.put("quantity", quantity);
//						ordermapp.put("deliverydate", deliverydate);
//						ordermapp.put("discountprice", discountprice);
//						ordermapp.put("discount", discount);
//						ordermapp.put("image", image);
//						ordermapp.put("mrp", mrp);
//						ordermapp.put("name", name);
//						ordermapp.put("size", size);
//						ordermapp.put("status", status);
//
//						searchorderlistall.add(ordermapp);
//					}
//					global.setSearchOrderListall(searchorderlistall);
//
//				} else if (deal.equals("error")) {
//					global.setSearchOrderListall(orderlist);
//					result = "error";
//				} else {
//					global.setSearchOrderListall(orderlist);
//					result = job.getString("cart");
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//				result = "!sorry \n Cannot Complete Your request now ";
//			}//
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			result = "!sorry \n Cannot Complete Your request now ";
//		}// catch
//
//		return result;
//	}
	
	public static String searchOrderlist(Context c, String orderid) {
		// TODO Auto-generated method stub
		String result = "";
		String respage = "";

		String url = "http://jobsforyouonly.com/shopby/search.php";
		global = (Globals) c.getApplicationContext();
		cartlist = new ArrayList<HashMap<String, String>>();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
//			namepairs.add(new BasicNameValuePair("action", "search"));
			namepairs.add(new BasicNameValuePair("keyword", orderid));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);
				System.out.println("----respage--->"+respage );
				JSONObject job = new JSONObject(respage);

				String deal = job.getString("order");
				if (deal.equals("order")) {

					result = "sucess";
					JSONArray arry = job.getJSONArray("detail");
					searchorderlistall = new ArrayList<HashMap<String, String>>();
					for (int i = 0; i < arry.length(); i++) {

						ordermapp = new HashMap<String, String>();

						JSONObject object = arry.getJSONObject(i);

						String id = object.getString("id");
						String productid = object.getString("productid");
						String quantity = object.getString("quantity");
						String deliverydate = object.getString("delivery_date");
						String discountprice = object
								.getString("discount_price");
						String mrp = object.getString("mrp");
						String size = object.getString("size");
						String discount = object.getString("discount");
						String image = object.getString("image");
						String name = object.getString("name");
						String items = object.getString("items");
						String totalprice = object.getString("totalprice");
						String status = object.getString("status");
						String orderids = object.getString("orderid");
						String address = object.getString("address");

						ordermapp.put("id", id);
						ordermapp.put("items", items);
						ordermapp.put("orderid", orderids);
						ordermapp.put("address", address);
						ordermapp.put("totalprice", totalprice);
						ordermapp.put("productid", productid);
						ordermapp.put("quantity", quantity);
						ordermapp.put("deliverydate", deliverydate);
						ordermapp.put("discountprice", discountprice);
						ordermapp.put("discount", discount);
						ordermapp.put("image", image);
						ordermapp.put("mrp", mrp);
						ordermapp.put("name", name);
						ordermapp.put("size", size);
						ordermapp.put("status", status);

						searchorderlistall.add(ordermapp);
					}
					global.setSearchOrderListall(searchorderlistall);

				} else if (deal.equals("error")) {
					global.setSearchOrderListall(orderlist);
					result = "error";
				} else {
					global.setSearchOrderListall(orderlist);
					result = job.getString("cart");
				}

			} catch (Exception e) {
				e.printStackTrace();
				result = "!sorry \n Cannot Complete Your request now ";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			result = "!sorry \n Cannot Complete Your request now ";
		}// catch

		return result;
	}

	public static String addnewdeal(Context c, String catid,
			String tempcategory, String newcatimage, String cat) {
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/deal.php?";
		global = (Globals) c.getApplicationContext();

		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "insert"));
			namepairs.add(new BasicNameValuePair("image", newcatimage));
			namepairs.add(new BasicNameValuePair("typeid", catid));
			namepairs.add(new BasicNameValuePair("deal_type", cat));
			namepairs.add(new BasicNameValuePair("discount", tempcategory));
			namepairs.add(new BasicNameValuePair("weekly", ""));
			namepairs.add(new BasicNameValuePair("status", "runing"));
			namepairs.add(new BasicNameValuePair("deal_day", ""));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				String device = job.getString("deal");
				if (device.equals("Deal Added ")) {

					result = "sucess";

				} else {

					result = job.getString("subcategory");
				}

			} catch (Exception e) {
				e.printStackTrace();
				result = "! Sorry \ncannot complete your request now\n Please Try Again Later";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			result = "! Sorry \ncannot complete your request now\n Please Try Again Later";
		}// catch

		return result;
	}

	public static String Couponcode(Context c, String couponamount,
			String userid) {
		// TODO Auto-generated method stub
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/order.php?";
		global = (Globals) c.getApplicationContext();
		cartlist = new ArrayList<HashMap<String, String>>();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "applycoupon"));
			namepairs.add(new BasicNameValuePair("coupancode", couponamount));
			namepairs.add(new BasicNameValuePair("userid", userid));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);
				String ress = job.getString("coupan");
				if (ress.equals("success")) {
					result = "sucess";
				} else {
					result = "Sorry Inavalid";

				}
			} catch (Exception e) {
				e.printStackTrace();
				result = "!sorry \n Cannot Complete Your request now ";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			result = "!sorry \n Cannot Complete Your request now ";
		}// catch

		return result;
	}

	public static String ProductFavListing(Context c) {
		// TODO Auto-generated method stub
		String result = "";
		String respage = "";
		db = new DatabaseHelper(c);

		String url = "http://emerchantshop.com/shopbuycart_app/product.php?";
		global = (Globals) c.getApplicationContext();
		favproductlist = new ArrayList<HashMap<String, String>>();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "allproduct"));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				String deal = job.getString("product");
				if (deal.equals("product")) {

					result = "sucess";
					JSONArray arry = job.getJSONArray("detail");

					for (int i = 0; i < arry.length(); i++) {

						productmap = new HashMap<String, String>();

						JSONObject object = arry.getJSONObject(i);

						JSONObject pro = object.getJSONObject("productdetail");
						int chk = db.CheckFAV(String.valueOf(pro.get("id")));
						if (chk > 0) {
							productmap.put("id", String.valueOf(pro.get("id")));

							productmap.put("name",
									String.valueOf(pro.get("name")));
							productmap.put("catid",
									String.valueOf(pro.get("cat_id")));
							productmap.put("subcatid",
									String.valueOf(pro.get("subcat_id")));
							productmap.put("category",
									String.valueOf(pro.get("catgeory")));
							productmap.put("subcategory",
									String.valueOf(pro.get("subcategory")));
							productmap.put("dealid",
									String.valueOf(pro.get("deal_id")));
							productmap.put("outofstock",
									String.valueOf(pro.get("out_of_stock")));
							productmap.put("description",
									String.valueOf(pro.get("description")));
							productmap.put("rating",
									String.valueOf(pro.get("rating")));
							productmap.put("type",
									String.valueOf(pro.get("type")));
							productmap.put("mrp",
									String.valueOf(pro.get("mrp")));
							productmap.put("discount",
									String.valueOf(pro.get("discount")));
							productmap.put("discountprice",
									String.valueOf(pro.get("discountprice")));

							JSONObject proimg = object.getJSONObject("images");

							productmap.put("image",
									String.valueOf(proimg.get("image_1")));
							productmap.put("image3",
									String.valueOf(proimg.get("image_3")));
							productmap.put("image4",
									String.valueOf(proimg.get("image_4")));
							productmap.put("image5",
									String.valueOf(proimg.get("image_5")));

							favproductlist.add(productmap);
						}
					}
					global.setFavProductList(favproductlist);

				} else if (deal.equals("error")) {

					global.setFavProductList(favproductlist);
					result = "server error";
				} else {

					global.setFavProductList(favproductlist);
					result = job.getString("device");
				}

			} catch (Exception e) {
				global.setFavProductList(favproductlist);
				result = "server error";
				e.printStackTrace();
			}//

		} catch (Exception e) {
			global.setFavProductList(favproductlist);
			result = "server error";
			e.printStackTrace();
		}// catch

		return result;
	}

	public static String AminLogin(Context c) {
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/register.php?";
		global = (Globals) c.getApplicationContext();
		TelephonyManager mngr = (TelephonyManager) c
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceid = mngr.getDeviceId();
		String devicetype = android.os.Build.MODEL + " "
				+ android.os.Build.DISPLAY;
		String accesstoken = mngr.getLine1Number();

		loginlist = new ArrayList<HashMap<String, String>>();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "check"));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

				JSONObject job = new JSONObject(respage);

				devicemap = new HashMap<String, String>();

				String device = job.getString("device");
				if (device.equals("login")) {

					result = "sucess";

					JSONObject object = job.getJSONObject("detail");

					String id = object.getString("id");
					String status = object.getString("status");
					if (status.equals("admin")) {
						result = "admin";
						global.setuserid(id);
						global.setadminemail(object.getString("email"));
						global.setadminpassword(object.getString("password"));
						global.setusername(object.getString("name"));
					}

					global.setuserid(id);
					global.setusername(object.getString("name"));

				} else if (device.equals("error")) {
					global.setuserid("0");
					result = "Login error";
				} else {
					global.setuserid("0");
					result = job.getString("device");
				}

			} catch (Exception e) {
				e.printStackTrace();
				global.setuserid("0");
				result = "Login error";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			global.setuserid("0");
			result = "Login error";
		}// catch

		return result;
	}

	public static String AddSizePRoduct(Context c, String productid,
			String strsize, String strquanty) {
		String result = "";
		String respage = "";

		String url = "http://emerchantshop.com/shopbuycart_app/size.php?";
		global = (Globals) c.getApplicationContext();

		loginlist = new ArrayList<HashMap<String, String>>();
		try {
			DefaultHttpClient dhc = new DefaultHttpClient();
			ResponseHandler<String> res = new BasicResponseHandler();

			HttpPost postMethod = new HttpPost(url);

			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			namepairs.add(new BasicNameValuePair("action", "insert"));

			namepairs.add(new BasicNameValuePair("productid", productid));
			namepairs.add(new BasicNameValuePair("size", strsize));
			namepairs.add(new BasicNameValuePair("quantity", strquanty));
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs, "UTF-8"));

			try {
				respage = dhc.execute(postMethod, res);

			} catch (Exception e) {
				e.printStackTrace();
				result = "Server error";
			}//

		} catch (Exception e) {
			e.printStackTrace();
			result = "Network error";
		}// catch

		return result;
	}
}
