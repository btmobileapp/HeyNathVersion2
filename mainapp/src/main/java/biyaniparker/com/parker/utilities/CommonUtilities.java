package biyaniparker.com.parker.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import biyaniparker.com.parker.BuildConfig;
import biyaniparker.com.parker.database.ItemDAOCategory;
import biyaniparker.com.parker.database.ItemDAODispatch;
import biyaniparker.com.parker.database.ItemDAOOrder;
import biyaniparker.com.parker.database.ItemDAOPrice;
import biyaniparker.com.parker.database.ItemDAOProduct;
import biyaniparker.com.parker.database.ItemDAOSizeMaster;
import biyaniparker.com.parker.database.ItemDAOUser;

/**
 * Created by bt18 on 08/08/2016.
 */
public class CommonUtilities
{
    public final static String URL=

          //  "http://123.252.221.158:100/parkerservices/";
            //"http://192.168.73.100:100/parkerservices/";
          // "http://biyanitechnologies.com/parkerservices/";
          // "http://103.249.96.252/parkerservices/";
   // "http://btwebservices.biyanitechnologies.com/parkerservices/";
       //     "http://testing.biyanitechnologies.com/parkerservices/";
    // public final static String URL= "http://btwebservices.biyanitechnologies.com/sunanda/";
    //  "http://192.168.73.133/parker/";
     "http://btwebservices.biyanitechnologies.com/dealerapp/";
  //  "http://btwebservices.biyanitechnologies.com/dealerchoice/";

    public static int Height=800;
    public static int Width=600;

    

/*
    public final static String RESPONCE_OK="Success";
    public static String GodName="|| Yashwant Dugdh Prakriya Ltd. ||";
    public static String AdminShop="Warana Dudh";
    public static String AdminAdress=" Yashwant Group of Industries" +
            "" +
            ",Shirala , Sangli";

              public static String Slogan="";
    public static String AdminContact="Ph No. 91 2345 270 100 ";

*/
    public final static String RESPONCE_OK="Success";
    public static String GodName="|| Shree ||";
    public static String AdminShop="Rajashree Industries";
   // public static String AdminAdress="Jaysingpur Dist Kolhapur, Maharashtra 416101";
     public static String AdminAdress="3rd Lane,Jaysingpur-416101\n Dist Kolhapur, Maharashtra ";
            //"BLOCK NO.433/3C,GAT NO 24/3A, OPP.HOTEL SAPTAGIRI,JAYSINGPUR-416101";
    public static String Slogan="";
    public static String AdminContact=   "Ph No. 02322 225 073";
            //Rajshree "Ph No. 02322 225 073";
            //"Ph No. (02328)444444 ";
   // public static String AdminEmail="Email: choiceelectric14@gmail.com";
    public static Context context;


    public  static  boolean isDispatchEnable=false;
    public  static  boolean isMarkEnable=true;
    public static long getCurrentTime()
    {
        Calendar calendar=Calendar.getInstance();
        return calendar.getTimeInMillis();
    }


    public static void setHeightWidth(int val)
    {
            Height= (int) (val*8.7);
            Width= (int) (val*6.5);
    }

    public static long parseDate(String strDate)
    {
        try {

            return Long.valueOf(strDate.replace("/Date(", "").replace(")/", ""));
        }
        catch (Exception e)
        {

        }
        return 0;
    }

    public static void hideatInItInputBoard(Activity context)
    {
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static void hideSoftKeyBord(Activity context) {
        InputMethodManager inputManager = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = context.getCurrentFocus();
        if (v == null)
            return;


        inputManager.hideSoftInputFromWindow(v.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException
    {
        if ("content".equalsIgnoreCase(uri.getScheme()))
        {
            String[] projection = { "_data" };
            Cursor cursor = null;
            try
            {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    public static void alert(Context context,String str)
    {

        AlertDialog.Builder al=new AlertDialog.Builder(context);
        al.setMessage(str);
        al.setPositiveButton("Ok ",null);
        al.show();
    }


    public static void printLog(String str)
    {
        Log.d("perror", str);
    }


    public static String longToDate(long orderDate)
    {
        Date date=new Date(orderDate);

        SimpleDateFormat f=new SimpleDateFormat("dd/MM/yyyy");
        return f.format(date);
    }

    public static Uri getFileUri(Context context, String fileName) {
        File file = getFile(context, fileName);
        return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
    }

    public static File getFile(Context context, String fileName) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }

        File storageDir = context.getExternalFilesDir(null);
        return new File(storageDir, fileName);
    }

    public  static void  openPdf(Context context,String  path)
    {
        try
        {
            File file = new File(path);
            Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider",file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);

        }
        catch (Exception e)
        {

        }
    }

    public  static void  openPdf1(Context context,File path)
    {
        Uri photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", path);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(photoURI, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }

    public static  boolean emailWithAttachement(Context context, String emailaddress,
                                                String message,String subject,String filepath)
    {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        //Intent.Extra_e
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[]{ emailaddress});
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        emailIntent.setType("application/image");
        //  emailIntent.setType(Intent.EXTRA_STREAM);

        Uri uri = Uri.parse("file://" + filepath);
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(emailIntent);

        return true;
    }

    public static void deleteAll(Context context)
    {
        ItemDAOPrice itemDAOPrice=new ItemDAOPrice(context);
        itemDAOPrice.delete();
        ItemDAOCategory itemDAOCategory=new ItemDAOCategory(context);
        itemDAOCategory.delete();
        ItemDAOSizeMaster itemDAOSizeMaster=new ItemDAOSizeMaster(context);
        itemDAOSizeMaster.deleteAllSizes();
        itemDAOSizeMaster.deleteAllDetailsSizes();
        ItemDAODispatch itemDAODispatch=new ItemDAODispatch(context);
        itemDAODispatch.deleteDispatchDetail();

        ItemDAOUser itemDAOUser=new ItemDAOUser(context);
        itemDAOUser.deleteShop();
        itemDAOUser.deleteUser();;

        ItemDAOOrder itemDAOOrder=new ItemDAOOrder(context);
        itemDAOOrder.deleteAllOrder();

        ItemDAOProduct itemDAOProduct=new ItemDAOProduct(context);
        itemDAOProduct.delete();


    }
}
