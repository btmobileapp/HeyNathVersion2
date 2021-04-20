package com.bt.heynath;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

public class DeviceUuidFactory {

	protected static final String PREFS_FILE = "device_id.xml";
	protected static final String PREFS_DEVICE_ID = "device_id";
	protected static volatile UUID uuid;

	public DeviceUuidFactory(Context context)
	{
		if (uuid == null)
		{
			synchronized (DeviceUuidFactory.class)
			{
				if (uuid == null)
				{
					final SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0);
					final String id = prefs.getString(PREFS_DEVICE_ID, null);
					if (id != null)
					{
						// Use the ids previously computed and stored in the
						// prefs file
						uuid = UUID.fromString(id);
					} else {
						final String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
						// Use the Android ID unless it's broken, in which case
						// fallback on deviceId,
						// unless it's not available, then fallback on a random
						// number which we store to a prefs file
						try {
							if (!"9774d56d682e549c".equals(androidId))
							{
								uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
							}
							else
							{
								final String deviceId = (((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());
								uuid = deviceId != null ? UUID
										.nameUUIDFromBytes(deviceId
												.getBytes("utf8")) : UUID
										.randomUUID();
							}
						} catch (UnsupportedEncodingException e) {
							throw new RuntimeException(e);
						}
						// Write the value out to the prefs file
						prefs.edit()
								.putString(PREFS_DEVICE_ID, uuid.toString())
								.commit();
					}
				}
			}
		}
	}


	/**
	 * Returns a unique UUID for the current android device. As with all UUIDs,
	 * this unique ID is "very highly likely" to be unique across all Android
	 * devices. Much more so than ANDROID_ID is.
	 *
	 * The UUID is generated by using ANDROID_ID as the base key if appropriate,
	 * falling back on TelephonyManager.getDeviceID() if ANDROID_ID is known to
	 * be incorrect, and finally falling back on a random UUID that's persisted
	 * to SharedPreferences if getDeviceID() does not return a usable value.
	 *
	 * In some rare circumstances, this ID may change. In particular, if the
	 * device is factory reset a new device ID may be generated. In addition, if
	 * a user upgrades their phone from certain buggy implementations of Android
	 * 2.2 to a newer, non-buggy version of Android, the device ID may change.
	 * Or, if a user uninstalls your app on a device that has neither a proper
	 * Android ID nor a Device ID, this ID may change on reinstallation.
	 *
	 * Note that if the code falls back on using TelephonyManager.getDeviceId(),
	 * the resulting ID will NOT change after a factory reset. Something to be
	 * aware of.
	 *
	 * Works ar ound a bug in Android 2.2 for many devices when using ANDROID_ID
	 * directly.
	 *
	 *  @ see   http ://code.google.com/p/android/issues/detail?id=10603
	 *
	 * @return a UUID that may be used to uniquely identify your device for most
	 *         purposes.
	 */
	public UUID getDeviceUuid() {
		return uuid;
	}



	static public String getIMENumber(Context context)
	{
		try {
			if (Build.VERSION.SDK_INT >= 23) {
				TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
				String imeiNumber1 = tm.getDeviceId(1); //(API level 23)
				String imeiNumber2 = tm.getDeviceId(2);

				return imeiNumber1 + "_" + imeiNumber2;
			}


			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			//return  telephonyManager.getDeviceId();
		/*if (android.os.Build.VERSION.SDK_INT >= 26) {
			return  telephonyManager.getImeid();
		}
		else*/
			{
				return telephonyManager.getDeviceId();
			}
		}
		catch(Exception  ex){
			return "";
		}
	}

	static  public String getSimNumber(Context context)
	{
		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
				SubscriptionManager subscriptionManager = SubscriptionManager.from(context);
				List<SubscriptionInfo> subsInfoList = subscriptionManager.getActiveSubscriptionInfoList();

				//	Log.d("Test", "Current list = " + subsInfoList);
				TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

				String mString = "";
				for (SubscriptionInfo subscriptionInfo : subsInfoList) {
					String number = subscriptionInfo.getNumber();
					if (mString.equalsIgnoreCase(""))
						mString = number;
					else if (number != null && number.equalsIgnoreCase(""))
						mString = mString + "_" + number;

					//telephonyManager.getSimSerialNumber(subscriptionInfo.getSubscriptionId())
				}


				return mString;
			} else {

				TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
				//telephonyManager.getLineNumber();
				return telephonyManager.getLine1Number();
			}
		}
		catch(Exception m)
		{

		}
		return "";
	}

	/*
        public static String getDeviceUniqueID(Context activity)
        {
            String device_unique_id = Secure.getString(activity.getContentResolver(),
                    Secure.ANDROID_ID);
            return device_unique_id;
        }
    */
	public static String getExtraInfo(Context context)
	{
		String etcInfo="";
		TelephonyManager tm = (TelephonyManager)
				context.getSystemService(Context.TELEPHONY_SERVICE);
		try
		{
			etcInfo="Software_Version-"+ tm.getDeviceSoftwareVersion();
			etcInfo=etcInfo+","+ tm.getSimOperatorName();
		}
		catch (Exception e)
		{}

		try
		{
			String simID = tm.getSimSerialNumber();
			String telNumber = tm.getLine1Number();
			etcInfo = etcInfo + ",SimSerialNumber-" + simID;
			etcInfo = etcInfo + ",SimNumber-" + telNumber;
		}
		catch (Exception ex){}

		return  etcInfo;
	}
}