# FireLink
<img src="images/web_hi_res_512.png" width="100px" height="100px"/>

**Status**: Beta Stage 6

**What is it?** This is a chrome extension for sharing links immediately to the
FireLink app.

**Preview Demo**

<img src="images/ext1.png" width="auto" height="auto"/>
<img src="images/ext2.png" width="auto" height="auto"/>

Solarized dark             |  Solarized Ocean
:-------------------------:|:-------------------------:
![](images/phonepreview.jpg)  |  ![](images/previewautolaunch.jpg)

<img src="images/phonepreview.jpg" width="auto" height="800px"/>                  
<img src="images/previewautolaunch.jpg" width="auto" height="800px"/>

**How to set it up yourself!** 

1. Open your git folder and open git bash and enter the following:

```sh
git clone https://github.com/bartektenDev/FireLink.git
```

2. Now open chrome and copy and paste the following:

```sh
chrome://extensions/
```
3. Click **Load Unpacked** then find the FireLink folder in your git folder and
select it.

4. Now launch Android studio and in the FireLink folder open androidApplication/FireLink.
After everything finishes loading, connect your device and click the play (debug) icon.
This will install FireLink onto your device. After the app launches, in Android Studio
click on the logcat option on the bottom of the window and in the filter box enter: token.
Wait 10-30 seconds and in the box a refresh token should show up. This token is specific 
to your device and everytime you uninstall and reinstall the application you will recieve
a new token and this step will have to be repeated. Highlight only the token. It will 
look like this:

```sh
refresh token: zFbjNWjsdu0:APz91bHsBir0dnnhUSsUqw8LWa79mA8O78q4tgvuBfUrl-9snpbv0-rd-kxe3ObFqrZkkf_AHjjGnmZvAyIowmqes9SJ7umWE3pnnBeCD26w0vPmFTzpiueHu7nfZAWX7HDLVH6p6upB
```

We only need the value so copy that for the next step.

5. In the root folder of FireLink open the popup.js file. Scroll down till you see the
variable: deviceToken. In the quotations paste the token you copied. Here's an example
how it should look.

```sh
function sendData()
{
  var serverToken = "AAAA16-Iy60:APA291bHw6cYpz8coEl2aSpv_y4WeIlXq_BVeAJ65BJOm1nb2PVQV310BUN_Ng4mnMqftT7XbGTCGtwOrLSIhYQ1lhi7wAm24d5x3Oa1qYbRVQhX-JqxpODlL1GsHTzGMzcy01HMp__C3-v";
  var deviceToken = "zFbjNWjsdu0:APz91bHsBir0dnnhUSsUqw8LWa79mA8O78q4tgvuBfUrl-9snpbv0-rd-kxe3ObFqrZkkf_AHjjGnmZvAyIowmqes9SJ7umWE3pnnBeCD26w0vPmFTzpiueHu7nfZAWX7HDLVH6p6upB";
  var titleToDevice = currentTabTitle;
  var contentToDevice = currentTabUrl;

  var http = new XMLHttpRequest();
  var url = 'https://fcm.googleapis.com/fcm/send';
  var params = "{\r\n \"to\": \"" + deviceToken.toString() + "\",\r\n \"data\": {\r\n \"title\": \"" + titleToDevice.toString() + "\",\r\n \"content\" : \"" + contentToDevice.toString() + "\",\r\n \"imageUrl\": \"http:\/\/h5.4j.com\/thumb\/Ninja-Run.jpg\"\r\n }\r\n}";
  http.open('POST', url, true);
  ...
```

6. Then save the file and you're all set! Now just open the chrome://extensions/ tab again 
and on FireLink click the refresh icon to make sure everything is set. Now go to any 
websites and click on the FireLink extension. You will see the title of the website 
along with the link below it. Just click "send" and you will instantly recieve the 
notification on your device!
</p2>
<p3>
**What's being worked on?**
- [ ] Working on making an easy set up for Windows/Mac and Android/iOS. 
- [ ] Setup localhost on port 5000 the token retriever
- [X] QR Scanner in FireLink app
- [X] Read QR Code in FireLink app
- [ ] Send "friend request" to QR Code from setup on main PC
- [ ] Retrieve "friend request" from FireLink app
- [ ] Read FROM token in the notification from the app. Both parties need to accept the
"friend request" otherwise if it is denied from the computer setup, then the FireLink
application will cancel and not allow the request to be made or completed.
</p3>
