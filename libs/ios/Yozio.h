//
//  Yozio.h
//
//  Copyright (c) 2015 Yozio. All rights reserved.
//
//  This file is part of the Yozio SDK.
//
//  By using the Yozio SDK in your software, you agree to the Yozio
//  terms of service which can be found at http://yozio.com/terms.


#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

#define YOZIO_OPEN_URL_TYPE_YOZIO_NEW_INSTALL        0
#define YOZIO_OPEN_URL_TYPE_YOZIO_DEEPLINK           1
#define YOZIO_OPEN_URL_TYPE_OTHER                   10

@interface Yozio : NSObject

/**
 * Configures the Yozio SDK. Must be called when your app is launched and before any other method.
 * You can find your app key and secret key in console.yozio.com > Your app > SDK.
 *
 * @param appKey - Application specific key provided by Yozio.
 * @param secretKey - Application specific secret key provided by Yozio.
 * @param callback - callback instance to handle meta data passed by new install or nil
 *
 */
+ (void)initializeWithAppKey:(NSString *)appKey
                   secretKey:(NSString *)secretKey
  newInstallMetaDataCallback:(void(^)(NSDictionary *))callback;

/**
 * This function will deliver new install meta data if using SFSafariViewController for 100% 
 * matching in safari, track deeplink that happened on Yozio super link.
 * It should be called inside the app delegate openURL and continueUserActivity function.
 *
 * @param url - NSURL object passed by openURL function
 * @return url type
 *     There are 3 types defined:
 *     YOZIO_OPEN_URL_TYPE_YOZIO_NEW_INSTALL - yozio internal builded url to dilver new install
 *     YOZIO_OPEN_URL_TYPE_YOZIO_DEEPLINK - url that have yozio builded meta data
 *     YOZIO_OPEN_URL_TYPE_OTHER   - other urls that not releated with Yozio
 */
+ (int)handleOpenURL:(NSURL *)url;

/**
 * Get meta data from deeplink url, and filter out Yozio internal parameters
 *
 * @param url - NSURL object passed by app delegate
 *
 * @return MetaData passed by deepLink url
 */
+ (NSDictionary *)getMetaDataFromDeeplink:(NSURL *)url;

/** 
 * Populates dictionary with meta data received from Universal Link url.
 *
 * @param url - The url from which the app was opened
 * @param associatedDomains - The array of domains that are configured by Yozio to use Universal Link eg:(r.yoz.io, r.custom_domain.com)
 * @return Dictionary of Metadata passed by Universal Link url.
 *
 */
+ (void) handleDeeplinkURL:(NSURL *)url
     withAssociatedDomains:(NSArray *) associatedDomains
  deeplinkMetaDataCallback:(void (^)(NSDictionary *))callback;

/**
 * When the a new install is detected, Yozio will try to find the Yozio short link user clicked
 * before he/she downloaded app. If the link has any meta data attached, Yozio SDK will
 * try to return these meta data through the newInstallMetadataCallback.
 *
 * These meta data are also stored in a file locally, and you can access them any time by calling 
 * the following 2 functions.
 */
+ (NSDictionary *)getNewInstallMetaDataAsHash;
+ (NSString *)getNewInstallMetaDataAsUrlParameterString;

/**
 * After you called [Yozio trackDeeplink:url] to let Yozio store the deeplink meta data in local,
 * then you can access them by calling the following 2 functions.
 */
+ (NSDictionary *)getLastDeeplinkMetaDataAsHash;
+ (NSString *)getLastDeeplinkMetaDataAsUrlParameterString;

/**
 * Tracks standard signup event.
 */
+ (void)trackSignup;

/**
 * Tracks payment event.
 *
 * @param amount - the payment amount.
 */
+ (void)trackPayment:(double)amount;

/**
 * Tracks custom downstream event.
 *
 * @param name - the event name.
 * @param value - the event value.
 */
+ (void)trackCustomEventWithName:(NSString *)name
                        andValue:(double)value;

@end

