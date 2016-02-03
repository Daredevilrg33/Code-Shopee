package amit.techie33.shopbuycart;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;

public class Globals extends Application {

	int catposition = 0;
	private ArrayList<HashMap<String, String>> devicelist = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> deallist = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> categorylist = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> subcategorylist = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> productlist = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> selectproductlist = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> productimage = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> sizelist = new ArrayList<HashMap<String, String>>();
	private ArrayList<String> listsize = new ArrayList<String>();
	private ArrayList<HashMap<String, String>> cartlist = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> orderlist = new ArrayList<HashMap<String, String>>();
	private String userid, username;
	private String change,ord;
	private ArrayList<HashMap<String, String>> orderlistall = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> orderlistall2 = new ArrayList<HashMap<String, String>>();

	private ArrayList<HashMap<String, String>> favproductlist = new ArrayList<HashMap<String, String>>();
	private String productid;
	private String adminpass;
	private String adminemail;

	public ArrayList<HashMap<String, String>> getDeviceDetail() {
		// TODO Auto-generated method stub
		return devicelist;
	}

	public void setDeviceDetail(ArrayList<HashMap<String, String>> devicelist) {
		// TODO Auto-generated method stub
		this.devicelist = devicelist;
	}

	public ArrayList<HashMap<String, String>> getDealList() {
		// TODO Auto-generated method stub
		return deallist;
	}

	public void setDealList(ArrayList<HashMap<String, String>> deallist) {
		// TODO Auto-generated method stub
		this.deallist = deallist;
	}

	public ArrayList<HashMap<String, String>> getCategoryList() {
		// TODO Auto-generated method stub
		return categorylist;
	}

	public void setCategoryList(ArrayList<HashMap<String, String>> categorylist) {
		// TODO Auto-generated method stub
		this.categorylist = categorylist;
	}

	public ArrayList<HashMap<String, String>> getSubCategoryList() {
		// TODO Auto-generated method stub
		return subcategorylist;
	}

	public void setSubCategoryList(
			ArrayList<HashMap<String, String>> subcategorylist) {
		// TODO Auto-generated method stub
		this.subcategorylist = subcategorylist;
	}

	public int getcatposition() {
		// TODO Auto-generated method stub
		return catposition;
	}

	public void setcatposition(int catposition) {
		// TODO Auto-generated method stub
		this.catposition = catposition;
	}

	public ArrayList<HashMap<String, String>> getProductList() {
		// TODO Auto-generated method stub
		return productlist;
	}

	public void setProductList(ArrayList<HashMap<String, String>> productlist) {
		// TODO Auto-generated method stub
		this.productlist = productlist;
	}

	public ArrayList<HashMap<String, String>> getSelectProductList() {
		// TODO Auto-generated method stub
		return selectproductlist;
	}

	public void setSelectProductList(
			ArrayList<HashMap<String, String>> selectproductlist) {
		// TODO Auto-generated method stub
		this.selectproductlist = selectproductlist;
	}

	public ArrayList<HashMap<String, String>> getProductImageList() {
		// TODO Auto-generated method stub
		return productimage;
	}

	public void setProductImageList(
			ArrayList<HashMap<String, String>> productimage) {
		// TODO Auto-generated method stub
		this.productimage = productimage;
	}

	public ArrayList<HashMap<String, String>> getSizeList() {
		// TODO Auto-generated method stub
		return sizelist;
	}

	public void setSizeList(ArrayList<HashMap<String, String>> sizelist) {
		// TODO Auto-generated method stub
		this.sizelist = sizelist;
	}

	public ArrayList<String> getSize() {
		// TODO Auto-generated method stub
		return listsize;
	}

	public void setSize(ArrayList<String> listsize) {
		// TODO Auto-generated method stub
		this.listsize = listsize;
	}

	public ArrayList<HashMap<String, String>> getCartList() {
		// TODO Auto-generated method stub
		return cartlist;
	}

	public void setCartList(ArrayList<HashMap<String, String>> cartlist) {
		// TODO Auto-generated method stub
		this.cartlist = cartlist;
	}

	public ArrayList<HashMap<String, String>> getOrderList() {
		// TODO Auto-generated method stub
		return orderlist;
	}

	public void setOrderList(ArrayList<HashMap<String, String>> orderlist) {
		// TODO Auto-generated method stub
		this.orderlist = orderlist;
	}

	public String getuserid() {
		// TODO Auto-generated method stub
		return userid;
	}

	public void setuserid(String userid) {
		// TODO Auto-generated method stub
		this.userid = userid;
	}

	public String getusername() {
		// TODO Auto-generated method stub
		return username;
	}

	public void setusername(String username) {
		// TODO Auto-generated method stub
		this.username = username;
	}

	public String getchange() {
		// TODO Auto-generated method stub
		return change;
	}

	public void setchange(String change) {
		// TODO Auto-generated method stub
		this.change = change;
	}

	public ArrayList<HashMap<String, String>> getOrderListall() {
		// TODO Auto-generated method stub
		return orderlistall;
	}

	public void setOrderListall(ArrayList<HashMap<String, String>> orderlistall) {
		// TODO Auto-generated method stub
		this.orderlistall = orderlistall;
	}

	public ArrayList<HashMap<String, String>> getSearchOrderListall() {
		// TODO Auto-generated method stub
		return orderlistall2;
	}

	public void setSearchOrderListall(
			ArrayList<HashMap<String, String>> orderlistall2) {
		// TODO Auto-generated method stub
		this.orderlistall2 = orderlistall2;
	}

	public ArrayList<HashMap<String, String>> getFavProductList() {
		// TODO Auto-generated method stub
		return favproductlist;
	}

	public void setFavProductList(
			ArrayList<HashMap<String, String>> favproductlist) {
		// TODO Auto-generated method stub
		this.favproductlist = favproductlist;
	}

	public String get_globalsearch() {
		// TODO Auto-generated method stub
		return ord;
	}
	public void set_globalsearch(String ord) {
		// TODO Auto-generated method stub
		this.ord = ord;
	}

	public String getpro_id() {
		// TODO Auto-generated method stub
		return productid;
	}
	public void setpro_id(String productid) {
		// TODO Auto-generated method stub
		this.productid = productid;
	}

	public void setadminemail(String adminemail) {
		// TODO Auto-generated method stub
		this.adminemail =adminemail;
	}

	public void setadminpassword(String adminpass) {
		// TODO Auto-generated method stub
		this.adminpass =adminpass;
	}
	public String getadminemail() {
		// TODO Auto-generated method stub
		return adminemail;
	}

	public String getadminpassword() {
		// TODO Auto-generated method stub
		return adminpass;
	}
}
