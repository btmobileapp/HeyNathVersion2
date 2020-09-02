package biyaniparker.com.parker.bal;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.json.JSONObject;

import biyaniparker.com.parker.utilities.CommonUtilities;
import biyaniparker.com.parker.utilities.DeviceUuidFactory;
import biyaniparker.com.parker.utilities.DownloadUtility;
import biyaniparker.com.parker.utilities.UserUtilities;
import biyaniparker.com.parker.utilities.serverutilities.AsyncUtilities;
import biyaniparker.com.parker.view.login.LoginActivity;

/**
 * Created by bt18 on 08/08/2016.
 */
public class ModuleLogin implements DownloadUtility
{
    Context context;
    public ModuleLogin(Context context)
    {
        this.context=context;
    }


    // Login function from server
    public void performLogin(String username, String pass)
    {
        String deviceId = "";
        try {
            deviceId = new DeviceUuidFactory(context).getDeviceUuid().toString();
        }
        catch (Exception ex){}
        // requset code ->   1
        String url=CommonUtilities.URL+"UtilService.svc/getUserDetail?username="+username+"&password="+pass+"&ClientId=1&DeviceID="+deviceId;
        AsyncUtilities serverAsync=new AsyncUtilities(context,false, url ,"",1,this);
        serverAsync.execute();

    }

    public  void loginInBackground()
    {

        String deviceId = "";
        try {
            deviceId = new DeviceUuidFactory(context).getDeviceUuid().toString();
        }
        catch (Exception ex){}
            // requset code ->   1
            String url=CommonUtilities.URL+"UtilService.svc/getUserDetail?username="+ UserUtilities.getUserName(context)+"&password="+UserUtilities.getUserPassword(context)+"&ClientId=1&DeviceID="+deviceId;
            AsyncUtilities serverAsync=new AsyncUtilities(context,false, url ,"",11,this);
            serverAsync.hideDialouge();
            serverAsync.execute();

    }

    @Override
    public void onComplete(String str, int requestCode,int responseCode)
    {
      //  Toast.makeText(context, ""+str, Toast.LENGTH_SHORT).show();
       // CommonUtilities.alert(context,str);
        if(requestCode==1)
        {
            DownloadUtility downloadUtility = (DownloadUtility) context;
            if (responseCode == 200)
            {
                if (parseUserData(str))
                {
                    try {
                        JSONObject j=new JSONObject(str);
                        int userid=j.getInt("UserId");
                        if(userid==-33)
                        {
                            downloadUtility.onComplete("LoginNotApproved", 1, responseCode);
                            return;
                        }
                        if(userid==-66)
                        {
                            downloadUtility.onComplete("AllreadyLogin", 1, responseCode);
                            return;
                        }
                        if(UserUtilities.getDeleteStatus(context))
                        {
                            downloadUtility.onComplete("LoginFailed", 1, responseCode);
                            return;
                        }
                    }
                    catch (Exception ex)
                    {}
                    downloadUtility.onComplete("LoginSuceess", 1, responseCode);

                }
                else
                {
                    downloadUtility.onComplete("LoginFailed", 1, responseCode);
                }
            } else
            {
                downloadUtility.onComplete("Server Communication Failed", 1, responseCode);
            }
        }
        if(requestCode==11)
        {
            if (responseCode == 200)
            {
                parseUserData1(str);
            }
        }
    }

    private boolean parseUserData(String str) {

        try
        {
            JSONObject jsonObject=new JSONObject(str);
            if( jsonObject.getInt("UserId")!=0)
            {
                SharedPreferences sh=context.getSharedPreferences("UserBean",context.MODE_PRIVATE);
                SharedPreferences.Editor edit=sh.edit();
                edit. putString("UserBean", str);
                edit.commit();
                return true;
            }


        }
        catch (Exception e)
        {

        }
        return false;
    }
    private boolean parseUserData1(String str) {

        try
        {
            JSONObject jsonObject=new JSONObject(str);
            if(   true)//jsonObject.getInt("UserId"))
            {
                SharedPreferences sh=context.getSharedPreferences("UserBean",context.MODE_PRIVATE);
                SharedPreferences.Editor edit=sh.edit();
                edit. putString("UserBean", str);
                edit.commit();
                return true;
            }


        }
        catch (Exception e)
        {

        }
        return false;
    }
}
