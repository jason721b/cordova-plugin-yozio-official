package com.yozio;

import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.yozio.android.Yozio;
import com.yozio.android.interfaces.YozioMetaDataCallbackable;

public class YozioNewInstallMetaDataCallback implements YozioMetaDataCallbackable {

	public YozioNewInstallMetaDataCallback() {}
	
	public void onCallback(Context context, String targetActivityClassName, HashMap<String, Object> metaData) {
		
		/**
		 * To retrieve meta data passed by Yozio for new installs, you must implement this interface.
		 * 
		 * This example demonstrates how to 
		 * 1) retrieve meta data passed by Yozio for new installs, and 
		 * 2) launching of a new activity based on your configuration on yozio web console.
		 *
		 * Please note you must include the following lines in your Manifest file to configure this class properly.
		 * 
		 * See sample manifest config below.
		 *
		 * <application>
		 * ...
		 *    <meta-data android:name="YozioMetaDataCallback" android:value="com.yozio.yozio_android_sample_app.YozioMetaDataCallback" />
		 * ...
		 * </application>
		 */

		 // get the meta data from Yozio, so you can post it back to your server for tracking and segmentation
		Log.i("YozioMetaDataCallback", "Got meta data: " + metaData.toString());

		// notify YozioPlugin
		YozioPlugin.onNewInstallMetadataCallback(metaData);

		YozioPlugin.setIsOpenedByNewInstallCallback(true);

		// launching the activity with meta data using Yozio helper
		// You have to use Yozio.startActivityWithMetaData here, so the analytics will work properly.
		if (targetActivityClassName != null) {
			Yozio.startActivityWithMetaData(context, targetActivityClassName, metaData);	
		}

	}

}
