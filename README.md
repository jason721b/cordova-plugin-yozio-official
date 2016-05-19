# Official Cordova plugin for Yozio

This is a Cordova plugin which is supported officially by Yozio, it's used to wrap up the Yozio SDKs for iOS and Android. 

Yozio is a mobile growth platform, you can find more here: <https://yozio.com>.

This project is developed based on <https://github.com/Justin-Credible/cordova-plugin-yozio>, thanks for Justin's work.

Some of the interfaces of this plugin are different from Justin's.

## Installation

### Parameters

* YOZIO_APP_KEY - You can find the app key on Yozio Web Console Settings page. 
* YOZIO_SECRET_KEY - You can find the app key on Yozio Web Console Settings page. 
* URI_SCHEME - The custom uri scheme that could be used to deeplink into your app.
* YOZIO_UNIVERSAL_LINK_DOMAIN - The domain you're using for Universal link, if you're not using any custom domain, then specify r.yoz.io

###Example

    cordova plugin add cordova-plugin-yozio --variable YOZIO_APP_KEY=your_app_key --variable YOZIO_SECRET_KEY=your_secret_key --variable URI_SCHEME=your_custom_scheme --variable YOZIO_DOMAIN=r.company.com
   
## Usage

This plugin is available via a global variable named `YozioPlugin`. It exposes the following methods.

### onNewInstallMetadataCallback

When your app being opened the 1st time, the function will be called to receive metadata.

Method Signature:

onNewInstallMetadataCallback(successCallback, failureCallback)

Example Usage:

    YozioPlugin.onNewInstallMetadataCallback(function(metadata) {
        console.log("Recevied metadata: " + JSON.stringify(metadata));
    }, function(message) {
        console.log("onNewInstallMetadataCallback failed: " + message);
    });

### onDeeplinkMetadataCallback

When your app being opened through deeplink, the function will be called to receive metadata from deeplink.

Method Signature:

  onDeeplinkMetadataCallback(successCallback, failureCallback)
 
Example Usage:

    YozioPlugin.onDeeplinkMetadataCallback(function(metadata) {
        console.log("Recevied metadata: " + JSON.stringify(metadata));
    }, function(message) {
        console.log("onDeeplinkMetadataCallback failed: " + message);
    });

### getInstallMetadata

After Yozio SDK gets back metadata, the metadata will be persisted locally, you can use this method to get it.

Method Signature:

  getInstallMetadata(scuccessCallback, failureCallback)

Example Usage:

    YozioPlugin.getInstallMetadata(function(metadata) {
        console.log("Get persisited install metadata: " + JSON.stringify(metadata));
    }, function(message) {
        console.log("getMetadata failed: " + message);
    });

### getLastDeeplinkMetadata

The most recent deeplink metadata will be persisted locally too, you can use this method to get it.

Method Signature:

  getLastDeeplinkMetadata(scuccessCallback, failureCallback)
  
Example Usage:
   
    YozioPlugin.getLastDeeplinkMetadata(function(metadata) {
        console.log("Get last persisted deeplink metadata: " + JSON.stringify(metadata));
    }, function(message) {
        console.log("getLastDeeplinkMetadata failed: " + message);
    });

### trackSignup

Track standard signup event, this event is predefined by Yozio.

Method Signature:

  trackSignup(scuccessCallback, failureCallback)
  
Example Usage:

    YozioPlugin.trackSignup();

### trackPayment

Track payment event, this event is predefined by Yozio.

Method Signature:

  trackPayment(amount, successCallback, failureCallback)
  
Example Usage:
   
    YozioPlugin.trackPayment(50.0);

### trackCustomEvent

Track custom event that you defined in Yozio console.

Method Signature:

  trackCustomEvent(eventName, value, successCallback, failureCallback)

Example Usage:
   
    YozioPlugin.trackCustomEvent('coupon', 10, function() {
        console.log("trackCustomEvent successed");
    }, function(message) {
        console.log("trackCustomEvent coupon failed: " + message);
    });




