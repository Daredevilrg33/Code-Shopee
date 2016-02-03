package amit.techie33.shopbuycart;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Validations {
	static Bitmap bmImg;
	public static Boolean checkNetwork(Context activity) {
		  ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		  NetworkInfo netInfo = cm.getActiveNetworkInfo();
		  if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			  	return true;
		  }
		  else if(netInfo != null && (netInfo.getState()==NetworkInfo.State.DISCONNECTED || netInfo.getState()==NetworkInfo.State.DISCONNECTING || netInfo.getState()==NetworkInfo.State.SUSPENDED || netInfo.getState()==NetworkInfo.State.UNKNOWN)){
			  	return false;
		  }
		  else{
			  	return false;
		  }
	}
	
	public static boolean checkEmail(String inputMail) {   
	    Pattern pattern= Pattern.compile("^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+");
	    return pattern.matcher(inputMail).matches();
	}
	
	public static Bitmap downloadFile(String fileUrl) {
		  URL myFileUrl = null;
		  try {
		   myFileUrl = new URL(fileUrl);
		  } catch (MalformedURLException e) {
		   
		   e.printStackTrace();
		  }
		  try {
		   HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
		   conn.setDoInput(true);
		   conn.connect();
		   
		   int iW = 210;
		         int iH = 210;
		        
		   BitmapFactory.Options options = new BitmapFactory.Options();
		   InputStream is = conn.getInputStream();
		   bmImg = BitmapFactory.decodeStream(is);
		   bmImg = Bitmap.createScaledBitmap(bmImg, iW, iH, true);
		   //logo.setImageBitmap(bmImg);
		  

		  } catch (IOException e) {
		   
		   e.printStackTrace();
		  }
		  return bmImg;
		  
		 }
}
