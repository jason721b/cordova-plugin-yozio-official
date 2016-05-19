"use strict";

var exec = require("cordova/exec");

/**
 * The Cordova plugin ID for this plugin.
 */
var PLUGIN_ID = "YozioPlugin";

/**
 * The plugin which will be exported and exposed in the global scope.
 */
var YozioPlugin = {};

/**
 * When your app being opened the 1st time, the function will be called to receive metadata.
 * 
 * @param [function] successCallback - The success callback for this asynchronous function; receives a metadata dictionary.
 * @param [function] failureCallback - The failure callback for this asynchronous function; receives an error string.
 */
YozioPlugin.onNewInstallMetadataCallback = function(successCallback, failureCallback) {
	exec(successCallback, failureCallback, PLUGIN_ID, "onNewInstallMetadataCallback", []);
} 

/**
 * When your app being opened through deeplink, the function will be called to receive metadata from deeplink.
 *
 * @param [function] successCallback - The success callback for this asynchronous function; receives a metadata dictionary.
 * @param [function] failureCallback - The failure callback for this asynchronous function; receives an error string.
 *
 */
YozioPlugin.onDeeplinkMetadataCallback = function(successCallback, failureCallback) {
	exec(successCallback, failureCallback, PLUGIN_ID, "onDeeplinkMetadataCallback", []);
}

/**
 * Get persisted install metdata.
 *
 * @param [function] successCallback - The success callback for this asynchronous function; receives a metadata dictionary.
 * @param [function] failureCallback - The failure callback for this asynchronous function; receives an error string.
 */
YozioPlugin.getInstallMetadata = function(successCallback, failureCallback) {
	exec(successCallback, failureCallback, PLUGIN_ID, "getInstallMetadata", []);
}

/**
 * Get the most recent persisted deeplink metadata.
 *
 * @param [function] successCallback - The success callback for this asynchronous function; receives a metadata dictionary.
 * @param [function] failureCallback - The failure callback for this asynchronous function; receives an error string.
 */
YozioPlugin.getLastDeeplinkMetadata = function(successCallback, failureCallback) {
	exec(successCallback, failureCallback, PLUGIN_ID, "getLastDeeplinkMetadata", []);
}

/**
 * Track standard signup event.
 *
 * @param [function] successCallback - The success callback for this asynchronous function.
 * @param [function] failureCallback - The failure callback for this asynchronous function; receives an error string.
 */
YozioPlugin.trackSignup = function(successCallback, failureCallback) {
	exec(successCallback, failureCallback, PLUGIN_ID, "trackSignup", []);
}

/**
 * Track payment event.
 *
 * @param [number] amount - The payment amount.
 * @param [function] successCallback - The success callback for this asynchronous function.
 * @param [function] failureCallback - The failure callback for this asynchronous function; receives an error string.
 */
YozioPlugin.trackPayment = function(amount, successCallback, failureCallback) {

	// Do validation before going over the native code bridge.
	if (typeof(amount) !== "number") {
		setTimeout(function () { failureCallback("An amount (number) is required."); }, 0);
		return;
	}

	exec(successCallback, failureCallback, PLUGIN_ID, "trackPayment", [amount]);
}

/**
 * Track custom event that you defined in Yozio console.
 *
 * @param [string] eventName - The name of the custom event to track.
 * @param [number] value - The optinal value to track with the event.
 * @param [function] successCallback - The success callback for this asynchronous function.
 * @param [function] failureCallback - The failure callback for this asynchronous function; receives an error string.
 */
YozioPlugin.trackCustomEvent = function(eventName, value, successCallback, failureCallback) {
	// Do validation before going over the native code bridge.
	if (typeof(eventName) !== "string") {
		setTimeout(function () { failureCallback("An event name (string) is required."); }, 0);
		return;
	}

	// If value wasn't provided, default it to zero.
	if (value == null) {
		value = 0;
	}

	// Do validation before going over the native code bridge.
	if (typeof(value) !== "number") {
		setTimeout(function () { failureCallback("Value must be null or a number."); }, 0);
		return;
	}

	exec(successCallback, failureCallback, PLUGIN_ID, "trackCustomEvent", [eventName, value]); 
}

module.exports = YozioPlugin;
