#import <Cordova/CDVPlugin.h>

@interface YozioPlugin : CDVPlugin

- (void)onNewInstallMetadataCallback:(CDVInvokedUrlCommand *)command;

+ (void)onYozioSDKNewInstallMetadataCallback:(NSDictionary *)metadata;

- (void)onDeeplinkMetadataCallback:(CDVInvokedUrlCommand *)command;

+ (void)onYozioSDKDeeplinkMetadataCallback:(NSDictionary *)metadata;

- (void)getInstallMetadata:(CDVInvokedUrlCommand *)command;

- (void)getLastDeeplinkMetadata:(CDVInvokedUrlCommand *)command;

- (void)trackSignup:(CDVInvokedUrlCommand *)command;

- (void)trackPayment:(CDVInvokedUrlCommand *)command;

- (void)trackCustomEvent:(CDVInvokedUrlCommand *)command;

@end
