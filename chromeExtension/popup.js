
var currentTabUrl, currentTabTitle;

var port = chrome.extension.connect({
     name: "Data Pass Communication"
});
port.postMessage("Hi Background. Please send me data. Thank you. LOL.");
port.onMessage.addListener(function(webData) {
     console.log("Recieved Data: " + webData);
     currentTabTitle = webData.titleText;
     currentTabUrl = webData.urlText;
     document.getElementById("titleText").innerHTML = currentTabTitle;
     document.getElementById("urlLink").innerHTML = currentTabUrl;
});

document.getElementById("sendDataBtn").addEventListener("click", checkKey);

function checkKey()   {
    chrome.storage.sync.get(/* String or Array */["targetDeviceKey"], function(items){
      //  items = [ { "yourBody": "myBody" } ]
      if(items.targetDeviceKey != null){
          var deviceKey = items.targetDeviceKey;
          if(deviceKey.length == 152){
              sendData(deviceKey);
          }else{
              alert("Doesn't look like this is a valid device key. Please try a different one.");
          }
      }else{
          alert("Doesn't look like this is a valid device key. Please try a different one.");
      }
    });
}

function sendData(keyData)   {
    var serverToken = "AAAA16-Iy60:APA91bFhoWONQAI1GjBo_CiRV8LqabW3SAa7U8wL66ST_q9z7tSKebfTtAtNTEv9R59_-3x9wNl94RlhQLTLgac0F7djxa3JMC7XVAkNI5vPkrW2p4sQNa9AScjNykU7lL6yCOgOzkWl";
    var deviceToken = keyData;
    var titleToDevice = currentTabTitle;
    var contentToDevice = currentTabUrl;

    var http = new XMLHttpRequest();
    var url = 'https://fcm.googleapis.com/fcm/send';
    var params = "{\r\n \"to\": \"" + deviceToken.toString() + "\",\r\n \"data\": {\r\n \"title\": \"" + titleToDevice.toString() + "\",\r\n \"content\" : \"" + contentToDevice.toString() + "\",\r\n \"imageUrl\": \"http:\/\/h5.4j.com\/thumb\/Ninja-Run.jpg\"\r\n }\r\n}";
    http.open('POST', url, true);

    //Send the proper header information along with the request
    http.setRequestHeader('Content-type', 'application/json');
    http.setRequestHeader('Authorization', 'key=' + serverToken.toString());

    http.onreadystatechange = function() {//Call a function when the state changes.
      if(http.readyState == 4 && http.status == 200) {
        //alert("Success!");
      }else{
        //alert("Fail");
      }
    }
    http.send(params);
    //click the submit button within the form with the information
    document.getElementById("urlLogoDisplay").src = "assets/progress.gif";
    setTimeout(function(){
      //uploading
      document.getElementById("urlLogoDisplay").src = "assets/uploadframes/idle/idle.gif";
      //get Title of current site
      //get current site link
    }, 3800);
}

function settingsDeviceTarget() {
    var targetdevice = null;

    var person = prompt("Please enter the device key to send links to: ", targetdevice);

    if (person == null || person == "") {
      //txt = "User cancelled the prompt.";
    } else {
      //txt = "Hello " + person + "! How are you today?";
    }
}

function readKey()
{
    chrome.storage.sync.get(/* String or Array */["targetDeviceKey"], function(items){
      //  items = [ { "yourBody": "myBody" } ]
      if(items.targetDeviceKey == null){
          //alert("No key saved yet.");
      }else{
          //alert("Read Successfully! :" + items.targetDeviceKey);
          document.getElementById("keyData").value = items.targetDeviceKey;
      }
    });
}

document.getElementById("writekeybtn").addEventListener("click", writeKey);

function writeKey()
{
    var key = document.getElementById("keyData").value;
    chrome.storage.sync.set({ "targetDeviceKey": key }, function(){
        alert("Saved Successfully!");
    });
}

readKey();
