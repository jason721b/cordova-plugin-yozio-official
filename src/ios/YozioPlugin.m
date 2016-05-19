#import "Yozio.h"
#import "YozioPlugin.h"
#import <Cordova/CDVPlugin.h>
#import <Cordova/CDVCommandDelegateImpl.h>

@implementation YozioPlugin

CDVCommandDelegateImpl *newInstallCommandDelegateHandler = nil;
CDVInvokedUrlCommand *newInstallCommandHandler = nil;
CDVCommandDelegateImpl *deeplinkCommandDelegateHandler = nil;
CDVInvokedUrlCommand *deeplinkCommandHandler = nil;

- (void)onNewInstallMetadataCallback:(CDVInvokedUrlCommand *)command
{
    newInstallCommandDelegateHandler = self.commandDelegate;
    newInstallCommandHandler = command;
}

// called when Yozio SDK initialize, pass metadata back to javascript interface
+ (void)onYozioSDKNewInstallMetadataCallback:(NSDictionary *)metadata
{
    if (newInstallCommandHandler != nil && newInstallCommandHandler != nil) {
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:metadata];
        [newInstallCommandDelegateHandler sendPluginResult:pluginResult callbackId:newInstallCommandHandler.callbackId];
    }
}

- (void)onDeeplinkMetadataCallback:(CDVInvokedUrlCommand *)command
{
    deeplinkCommandDelegateHandler = self.commandDelegate;
    deeplinkCommandHandler = command;
}

// called when app opened through deeplink, pass metadata back to javascript interface
+ (void)onYozioSDKDeeplinkMetadataCallback:(NSDictionary *)metadata
{
    if (deeplinkCommandHandler != nil && deeplinkCommandHandler != nil) {
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:metadata];
        [deeplinkCommandDelegateHandler sendPluginResult:pluginResult callbackId:deeplinkCommandHandler.callbackId];
    }
}

- (void)getInstallMetadata:(CDVInvokedUrlCommand *)command
{
    NSDictionary *metadata = [Yozio getNewInstallMetaDataAsHash];
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:metadata];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)getLastDeeplinkMetadata:(CDVInvokedUrlCommand *)command
{
    NSDictionary *metadata = [Yozio getLastDeeplinkMetaDataAsHash];
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:metadata];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)trackSignup:(CDVInvokedUrlCommand *)command
{
	// Delegate to the Yozio SDK.
	[self.commandDelegate runInBackground:^{
		[Yozio trackSignup];
	}];

	CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
	[self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)trackPayment:(CDVInvokedUrlCommand *)command {

	// Ensure we have the correct number of arguments.
	if ([command.arguments count] != 1) {
		CDVPluginResult *res = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"An amount is required."];
		[self.commandDelegate sendPluginResult:res callbackId:command.callbackId];
		return;
	}

	// Obtain the arguments.
	NSNumber* amount = [command.arguments objectAtIndex:0];

	// Validate the arguments.
	if (!amount) {
		CDVPluginResult *res = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"An amount is required."];
		[self.commandDelegate sendPluginResult:res callbackId:command.callbackId];
		return;
	}

	// Delegate to the Yozio SDK.
	[self.commandDelegate runInBackground:^{
		[Yozio trackPayment:[amount doubleValue]];
	}];

	CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
	[self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];

}

- (void)trackCustomEvent:(CDVInvokedUrlCommand *)command
{
    if ([command.arguments count] != 2) {
        CDVPluginResult *res = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"An event name and amount are required."];
        [self.commandDelegate sendPluginResult:res callbackId:command.callbackId];
        return;
    }
    
    // Obtain the arguments.
    NSString* eventName = [command.arguments objectAtIndex:0];
    NSNumber* value = [command.arguments objectAtIndex:1];
    
    // Validate the arguments.
    if (!eventName) {
        CDVPluginResult *res = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"An event name is required."];
        [self.commandDelegate sendPluginResult:res callbackId:command.callbackId];
        return;
    }
    
    if (!value) {
        CDVPluginResult *res = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"A value is required."];
        [self.commandDelegate sendPluginResult:res callbackId:command.callbackId];
        return;
    }
    
    // Delegate to the Yozio SDK.
    [self.commandDelegate runInBackground:^{
        [Yozio trackCustomEventWithName:eventName andValue:[value doubleValue]];
    }];
    
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    
}

@end
