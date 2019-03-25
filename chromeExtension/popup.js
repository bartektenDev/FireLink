
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

document.getElementById("sendDataBtn").addEventListener("click", sendData);

function sendData()
{
  var serverToken = "AAAA16-Iy60:APA91bFhoWONQAI1GjBo_CiRV8LqabW3SAa7U8wL66ST_q9z7tSKebfTtAtNTEv9R59_-3x9wNl94RlhQLTLgac0F7djxa3JMC7XVAkNI5vPkrW2p4sQNa9AScjNykU7lL6yCOgOzkWl";
  var deviceToken = "de8ssksk5ku:APA91bEXgTIkzS-L6TZuZ-ppXYXN0Knt4pRrEV646t11MHcgKo-_BiEa5PHxj9YvosWFImjlL8tEwvCwt6icZJDvKPVSOwDOQwWRkKeUdsicpj5g1xBrjnwc33ggdNicWJBgJ_1wcl7B";
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
    txt = "User cancelled the prompt.";
  } else {
    txt = "Hello " + person + "! How are you today?";
  }
}


function readTextFile(file)
{
    var rawFile = new XMLHttpRequest();
    rawFile.open("GET", file, false);
    rawFile.onreadystatechange = function ()
    {
        if(rawFile.readyState === 4)
        {
            if(rawFile.status === 200 || rawFile.status == 0)
            {
                var allText = rawFile.responseText;
                alert(allText);
            }
        }
    }
    rawFile.send(null);
}

function writeTextFile(file)
{

}
