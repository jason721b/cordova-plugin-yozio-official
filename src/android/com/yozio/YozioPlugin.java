package com.yozio;

import android.content.Intent;

import com.yozio.android.Yozio;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class YozioPlugin extends CordovaPlugin {

	private static CallbackContext newInstallCallbackContext = null;
	private static CallbackContext deeplinkCallbackContext = null;

    private static boolean isOpenedByNewInstallCallback = false;

    public static void setIsOpenedByNewInstallCallback(boolean value) {
        isOpenedByNewInstallCallback = value;
    }

	@Override
	protected void pluginInitialize() {

		Yozio.YOZIO_ENABLE_LOGGING = true;
		Yozio.YOZIO_READ_TIMEOUT = 7000;

		Yozio.initialize(cordova.getActivity());
	}

	@Override
	public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
		if ("onNewInstallMetadataCallback".equals(action)) {

			YozioPlugin.newInstallCallbackContext = callbackContext;
			return true;

		} else if ("onDeeplinkMetadataCallback".equals(action)) {

			YozioPlugin.deeplinkCallbackContext = callbackContext;
			return true;

		} else if ("getInstallMetadata".equals(action)) {

			this.getInstallMetadata(callbackContext);
			return true;

		} else if ("getLastDeeplinkMetadata".equals(action)) {

			this.getLastDeeplinkMetadata(callbackContext);
			return true;

		} else if ("trackSignup".equals(action)) {

			cordova.getThreadPool().execute(new Runnable() {
				public void run() {
					YozioPlugin.this.trackSignup(callbackContext);
				}
			});
			return true;

		} else if ("trackPayment".equals(action)) {

			cordova.getThreadPool().execute(new Runnable() {
				public void run() {
					YozioPlugin.this.trackPayment(args, callbackContext);
				}
			});
			return true;

		} else if ("trackCustomEvent".equals(action)) {

			cordova.getThreadPool().execute(new Runnable() {
				public void run() {
					YozioPlugin.this.trackCustomEvent(args, callbackContext);
				}
			});

			return true;
		}
		return false;  // Returning false results in a "MethodNotFound" error.
	}

	public static void onNewInstallMetadataCallback(HashMap<String, Object> metadata) {
		if (newInstallCallbackContext != null) {
			JSONObject metadataObj = new JSONObject(metadata);
			PluginResult result = new PluginResult(PluginResult.Status.OK, metadataObj);
			result.setKeepCallback(true);
			newInstallCallbackContext.sendPluginResult(result);
		}
	}

	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		HashMap<String, Object> metadata = Yozio.getMetaData(intent);
		if (!isOpenedByNewInstallCallback) {
			isOpenedByNewInstallCallback = false;
			this.onDeeplinkMetadataCallback(metadata);
		}
	}

	private void onDeeplinkMetadataCallback(HashMap<String, Object> metadata) {

		if (deeplinkCallbackContext != null) {

			JSONObject metadataObj = new JSONObject(metadata);
			PluginResult result = new PluginResult(PluginResult.Status.OK, metadataObj);
			result.setKeepCallback(true);
			deeplinkCallbackContext.sendPluginResult(result);
		}
	}

	private void getInstallMetadata(final CallbackContext callbackContext) {

		HashMap<String, Object> metadata = Yozio.getInstallMetaDataAsHash(cordova.getActivity().getApplicationContext());
		callbackContext.success(new JSONObject(metadata));
	}

	private void getLastDeeplinkMetadata(final CallbackContext callbackContext) {

		HashMap<String, Object> metadata = Yozio.getLastDeeplinkMetaDataAsHash(cordova.getActivity().getApplicationContext());
		callbackContext.success(new JSONObject(metadata));
	}

	private void trackSignup(final CallbackContext callbackContext) {

		Yozio.trackSignUp(cordova.getActivity().getApplicationContext());
		callbackContext.success();
	}

	private void trackPayment(JSONArray args, final CallbackContext callbackContext) {

		try {
			// Ensure we have the correct number of arguments.
			if (args.length() != 1) {
				callbackContext.error("An amount is required.");
				return;
			}

			// Obtain the arguments.
			double amount = args.getDouble(0);

			Yozio.trackPayment(cordova.getActivity().getApplicationContext(), amount);

			callbackContext.success();

		} catch (JSONException exception) {
			callbackContext.error("YozioPlugin uncaught exception: " + exception.getMessage());
		}
	}

	private void trackCustomEvent(JSONArray args, final CallbackContext callbackContext) {
		try {
			// Ensure we have the correct number of arguments.
			if (args.length() != 2) {
				callbackContext.error("An event name and amount are required.");
				return;
			}

			// Obtain the arguments.
			String eventName = args.getString(0);
			double value = args.getDouble(1);

			// Validate the arguments.

			if (eventName == null || eventName.equals("")) {
				callbackContext.error("An event name is required.");
			}

			Yozio.trackCustomEvent(cordova.getActivity().getApplicationContext(), eventName, value);

			callbackContext.success();

		} catch (JSONException exception) {
				callbackContext.error("YozioPlugin uncaught exception: " + exception.getMessage());
		}
	}

}
