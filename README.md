# BentoBox

Sample Bento with the following features:

* Declarative programming using Builder pattern
* Retrofit for managing Aria config requests
* Model objects with Gson annotations + GsonConverterFactory
* EventBus for creating observable events
* Activity lifecycle out-of-the-box

## Frameworks
* [Retrofit + Gson Converter][RTF]
* [Gson][GSN]
* [EventBus][EVB]

[RTF]: <https://square.github.io/retrofit>
[GSN]: <https://github.com/google/gson>
[EVB]: <http://greenrobot.org/eventbus>

## Usage
#### Bento Builder
Builder pattern allows for methods to be chained depending on the type of functionality being requested. Not all methods are required. For example when the app launches in Activity.onCreate you may want to register the app, load the app config, and trigger a track event to capture onCreate: 
```java
BentoBox bento = BentoBox.Builder()
		.registerApplication(getApplication())
		.withAppId("CoolApp.cc")
		.trackOnCreate("My Activity Data")
		.onConfigReady(config -> {
			Log.i("MAIN", "Config was loaded");
		})
		.onError(error -> {
			Log.i("MAIN", error.getMessage());
		})
		.build();
```
Method handlers associated with the request can be chained, for example, *onConfigReady*, *onError*, to receive asynchronous callbacks with the expected payload.

Instances can be reused once created. For example, track an event when a floating action button is clicked:
```java
floatingActionButton.setOnClickListener(view -> {
	bento.trackEvent("My Event Data");
});
```

#### Observing Events
This implementation uses EventBus to safely manage events. Events can execute on background threads, main thread, or guarantee to run on the same thread that created the event. Ex: *ThreadMode.BACKGROUND*, *ThreadMode.MAIN*, *ThreadMode.POSTING*, *ThreadMode.ASYNC*

Any Object may subscribe to events using standard EventBus annotations, for example:
```java
@Subscribe(threadMode = ThreadMode.MAIN)
public void onBentoErrorEvent(BentoError error) {
	Log.i("MAIN", "onBentoErrorEvent: " + error.getMessage());
}
```
An Object must register itself with EventBus in order to start receving Observable events.
```java
EventBus.getDefault().register(this);
```
Each Observer, for example, *ComscoreSubscriber* can use EventBus annotations to register and subscribe for observable events.

Events can be triggered by any other Object, i.e., BentoBox, MediaPlayer, Activity, as long as it is done via EventBus. For example:
```java
EventBus.getDefault().post(new BentoBox.Error("error message"));
```

You may want to wrap *post()* calls inside the BentoBox top level object to provide a single entry point or leave the implementation open by asking non-Bento objects to use EventBus directly.

#### Retrofit Requests
When a request is fetched each model object is automatically converted from a json response to a model object using Retrofit's *GsonConverterFactory*.

The set up is straight forward but you must guarantee the payload contract by setting up a *Call<T>* for each api method. For example:
```java
Call<AriaConfig> requestAppConfig(
	@Query("appId") String appId,
	@Query("v") String version
);
```
*AriaConfig* is the model object containing Gson annotations.

## Installation
#### Clone the repo
```java
git clone https://github.com/rgr-myrg/bento-box-android
cd bento-box-android/BentoBox
```
#### Build with gradle
```java
./gradlew assembleDebug
```
#### Install app on device
```java
adb install -r app/build/outputs/apk/app-debug.apk
```
Optionally, you can import the project on **Android Studio** and run the app.

### Version
0.1

### License
[MIT License] [MIT]

[MIT]: <https://opensource.org/licenses/MIT>

