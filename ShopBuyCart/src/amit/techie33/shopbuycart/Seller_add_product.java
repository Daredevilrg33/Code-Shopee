package amit.techie33.shopbuycart;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by om on 12/15/2015.
 */
public class Seller_add_product extends Activity {

    EditText name, about;
    static TextView country, city, birth;
    String gender;
    private File userfile = null;
    LinearLayout sex_tab;
    ImageView cover_pic;
    private Uri picUri;
    
    Button update;
    RequestParams params = new RequestParams();
    private static final int CAMERA_REQUEST = 1;
    private static final int GALLERY_REQUEST = 2;
    String pic_request;
    static UsefullData objUsefullData;
    String[] city_list;
    SaveData save_data;
    String[] looking_for_list = {"Chatting", "Fun", "Friends", "Hangout", "Relationships", "Dating", "Nothing", "Right Now", "Job", "Partner", "Socializing", "Help"};
    String[] country_name_list = {"Albania", "Algeria", "American Samoa",
            "Andorra", "Angola", "Anguilla", "Antigua And Barbuda",
            "Argentina", "Armenia", "Aruba", "Australia", "Austria",
            "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados",
            "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan",
            "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil",
            "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia",
            "Cameroon", "Canada", "Cape Verde", "Cayman Islands",
            "Central African Republic", "Chad", "Chile", "China",
            "Christmas Island", "Cocos (Keeling) Islands", "Colombia",
            "Comoros", "Republic of the Congo", "Cook Islands", "Costa Rica",
            "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark",
            "Djibouti", "Dominica", "Dominican Republic", "East Timor",
            "Ecuador", "Egypt", "El Salvador", "England", "Equatorial Guinea",
            "Estonia", "Ethiopia", "Falkland Islands", "Faroe Islands", "Fiji",
            "Finland", "France", "French Guiana", "French Polynesia", "Gabon",
            "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece",
            "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala",
            "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras",
            "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran",
            "Iraq", "Ireland", "Isle of Man", "Israel", "Italy", "Jamaica",
            "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait",
            "Kyrgyzstan", "Lao", "Latvia", "Lebanon", "Lesotho", "Liberia",
            "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia",
            "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta",
            "Marshall Islands", "Martinique", "Mauritania", "Mauritius",
            "Mayotte", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia",
            "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia",
            "Nauru", "Nepal", "Netherlands", "Netherlands Antilles",
            "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria",
            "Niue", "Norfolk Island", "Northern Ireland",
            "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau",
            "Panama", "Papua new Guinea", "Paraguay", "Peru", "Philippines",
            "Pitcairn Island", "Poland", "Portugal", "Puerto Rico", "Qatar",
            "Reunion", "Romania", "Russia", "Rwanda", "Saint Kitts And Nevis",
            "Saint Lucia", "Saint Vincent And The Grenadines", "Samoa",
            "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Scotland",
            "Senegal", "Serbia and Montenegro", "Seychelles", "Sierra Leone",
            "Singapore", "Slovak Republic", "Slovenia", "Solomon Islands",
            "Somalia", "South Africa",
            "South Georgia and the South Sandwich Islands", "Spain",
            "Sri Lanka", "St Helena", "St Pierre and Miquelon", "Sudan",
            "Suriname", "Svalbard And Jan Mayen Islands", "Swaziland",
            "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan",
            "Tanzania", "Thailand", "Togo", "Tokelau", "Tonga",
            "Trinidad And Tobago", "Tunisia", "Turkey", "Turkmenistan",
            "Turks And Caicos Islands", "Tuvalu", "Uganda", "Ukraine",
            "United Arab Emirates", "United States", "Uruguay", "Uzbekistan",
            "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)",
            "Virgin Islands (US)", "Wales", "Wallis And Futuna Islands",
            "Western Sahara", "Yemen", "Zambia", "Zimbabwe"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);


        save_data = new SaveData(Seller_add_product.this);
        objUsefullData = new UsefullData(Seller_add_product.this);
        name = (EditText) findViewById(R.id.editText_name);
        country = (TextView) findViewById(R.id.country);
        city = (TextView) findViewById(R.id.city_edit_profile);
         birth = (TextView) findViewById(R.id.birth_edit);
           about = (EditText) findViewById(R.id.about);
        cover_pic = (ImageView) findViewById(R.id.viewOne);
    

         cover_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openImageIntent();
                userfile = objUsefullData
                        .createFile("userfile.png");
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, GALLERY_REQUEST);
                pic_request = "c";

            }
        });
      update = (Button) findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                params.put("chat_id", save_data.get(CHAT_ID));
//
//                params.put("name", name.getText().toString());
//                params.put("aboutme ", about.getText().toString());
//                params.put("gender", gender);
//                params.put("looking_for", looking.getText().toString());
//                params.put("city", city.getText().toString());
//                params.put("birthdate", birth.getText().toString());
//                params.put("anniversarydate", aniversary.getText().toString());
//                try {
//                    params.put("image", userfile);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                Edit_profile_process();

            }
        });


        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_dialog("Select Country");

            }
        });
        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(Seller_add_product.this.getFragmentManager(), "DatePicker");

            }
        });
          city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (country.getText().toString().equals("")) {
                    objUsefullData.showMsgOnUI("Please Select Country");
                } else {
                    params.put("country", country.getText().toString());
                    get_city_list();
                }

            }


        });


    }


    private void open_dialog(final String msg) {

        final Dialog dialog = new Dialog(Seller_add_product.this);
        dialog.setTitle(msg);
        dialog.setContentView(R.layout.categories);
        final ListView listCategories = (ListView) dialog
                .findViewById(R.id.listCategories);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    Seller_add_product.this, R.layout.list_category, R.id.textCat,
                    country_name_list);

            listCategories.setAdapter(adapter);
        
        listCategories
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        String cat = (String) listCategories
                                .getItemAtPosition(position);
                             country.setText(cat);
                        

                        dialog.dismiss();
                    }
                });
        dialog.show();

    }


    private void Edit_profile_process() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://minksos.com/beta/profile_update.php", params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        objUsefullData.showProgress("Update info ...", "");
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        objUsefullData.dismissProgress();
                        super.onFailure(error);
                    }

                    @Override
                    public void onFinish() {
                        objUsefullData.dismissProgress();

                        super.onFinish();
                    }

                    @Override
                    public void onSuccess(String response) {
                        try {
                            Log.e("abc", "======response=======" + response);
                            objUsefullData.dismissProgress();
                            parse_edit_profile(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }


    public void parse_edit_profile(String response)
    {


        Log.i(" Forgot Response-", response);
        try {
            JSONObject object = new JSONObject(response);

            String status = object.getString("status");
            if (status.equals("success")) {
                

            } else {

                Toast.makeText(getApplicationContext(),"Something Went Worng",Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        }

    }

    private void get_city_list() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://minksos.com/beta/city_list.php", params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        objUsefullData.showProgress("Checking cities", "");
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        objUsefullData.dismissProgress();
                        super.onFailure(error);
                    }

                    @Override
                    public void onFinish() {
                        objUsefullData.dismissProgress();

                        super.onFinish();
                    }

                    @Override
                    public void onSuccess(String response) {
                        try {
                            Log.e("abc", "======response=======" + response);

                            parse_city_list(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }


    public void parse_city_list(String response) {


        Log.i(" login Response-", response);
        try {
            JSONObject object = new JSONObject(response);

            String status = object.getString("status");
            if (status.equals("sucessfull")) {
                JSONArray jsonArray = object.getJSONArray("city_list");

                city_list = getStringArray(jsonArray);


                Log.i("city_list", "" + city_list);


                objUsefullData.dismissProgress();
                final Dialog dialog = new Dialog(Seller_add_product.this);
                dialog.setTitle("Select City");
                dialog.setContentView(R.layout.categories);
                final ListView listCategories = (ListView) dialog
                        .findViewById(R.id.listCategories);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        Seller_add_product.this, R.layout.list_category, R.id.textCat,
                        city_list);
                listCategories.setAdapter(adapter);
                listCategories
                        .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent,
                                                    View view, int position, long id) {
                                String cat = (String) listCategories
                                        .getItemAtPosition(position);
                                city.setText(cat);
                                dialog.dismiss();
                            }
                        });
                dialog.show();

            } else {
                objUsefullData.dismissProgress();
                objUsefullData.showMsgOnUI("No City Found");

            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

    }

    public static String[] getStringArray(JSONArray jsonArray) {
        String[] stringArray = null;
        int length = jsonArray.length();
        if (jsonArray != null) {
            stringArray = new String[length];
            for (int i = 0; i < length; i++) {
                stringArray[i] = jsonArray.optString(i);
            }
        }
        return stringArray;
    }

    private void performCrop() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 2);
            cropIntent.putExtra("aspectY", 2);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, 3);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            Toast toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


//    private void openImageIntent(){
//
//        // Determine Uri of camera image to save.
//        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "amfb" + File.separator);
//        root.mkdir();
//        final String fname = "img_" + System.currentTimeMillis() + ".jpg";
//        final File sdImageMainDirectory = new File(root, fname);
//        picUri = Uri.fromFile(sdImageMainDirectory);
//
//        // Camera.
//        final List<Intent> cameraIntents = new ArrayList<Intent>();
//        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        final PackageManager packageManager = getPackageManager();
//        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
//        for (ResolveInfo res : listCam){
//            final String packageName = res.activityInfo.packageName;
//            final Intent intent = new Intent(captureIntent);
//            userfile = objUsefullData
//                    .createFile("userfile.png");
//            intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                    Uri.fromFile(userfile));
//            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
//            intent.setPackage(packageName);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, PHOTO_CAPTURE);
//            cameraIntents.add(intent);
//        }
//
//        //FileSystem
//        final Intent galleryIntent = new Intent();
//        galleryIntent.setType("image/");
//        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
//        userfile = objUsefullData
//                .createFile("userfile.png");
//        // Chooser of filesystem options.
//        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Image");
//        // Add the camera options.
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
//        startActivityForResult(chooserIntent, PHOTO_PICKED);
//
//
//
//    }


    private void openImageIntent() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                Seller_add_product.this);
        builder.setTitle("Select image");

        builder.setSingleChoiceItems(new String[]{"Camera", "Gallery"}, -1,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent cameraIntent = new Intent(
                                    android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            try {
                                userfile = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");

                            }catch (Exception ex) {
                                // Error occurred while creating the File
                               ex.printStackTrace();
                            }
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(userfile));
                            startActivityForResult(cameraIntent, CAMERA_REQUEST);


                        } else {
                            userfile = objUsefullData
                                    .createFile("userfile.png");
                            Intent i = new Intent(
                                    Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, GALLERY_REQUEST);
                        }
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                if (userfile != null) {
                    Bitmap thumbnail = BitmapFactory.decodeFile(
                            userfile.getPath(), new BitmapFactory.Options());



                         if (pic_request.equals("c")) {
                        cover_pic.setImageBitmap(thumbnail);

                    }


                }

            } else if (requestCode == GALLERY_REQUEST) {

                String picturePath = getPath(data.getData());
                userfile = new File(picturePath);
                Bitmap imgBmp = BitmapFactory.decodeFile(picturePath);

                 if (pic_request.equals("c")) {
                    cover_pic.setImageBitmap(imgBmp);
                }

                reCreateFile(imgBmp);

            }
        }

    }

    

    public String getPath(Uri uri) {
        Uri selectedImage = uri;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        return picturePath;
    }

    private void reCreateFile(Bitmap _bitmapScaled) {
        Bitmap scaled = Bitmap
                .createScaledBitmap(_bitmapScaled, 100, 100, true);
        try {
            FileOutputStream out = new FileOutputStream(userfile);
            scaled.compress(Bitmap.CompressFormat.PNG, 90, out);
            UsefullData.Log("User File :: " + userfile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void populateSetDate(int year, int month, int day) {

        DecimalFormat formatter = new DecimalFormat("00");
        String aFormatted_day = formatter.format(day);
        String aFormatted_month = formatter.format(month);

        birth.setText(aFormatted_day + "-" + aFormatted_month + "-" + year);
    }

    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }


        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar userAge = new GregorianCalendar(year, month, day);
            Calendar minAdultAge = new GregorianCalendar();
            minAdultAge.add(Calendar.YEAR, -13);
            if (minAdultAge.before(userAge)) {
                objUsefullData.showMsgOnUI("Only 13+ Allowed");


            } else {
                populateSetDate(year, month + 1, day);
            }
        }
    }


    
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        String mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

}
