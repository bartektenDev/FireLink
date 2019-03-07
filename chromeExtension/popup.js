
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
  var serverToken = "AAAA16-Iy60:APA91bHw6cYpz8coElaSpv_y4WeIlXq_BVeAJ65BJOm1nb2PVQV310BUN_Ng4mnMqftT7XbGTCGtwOrLSIhYQ1lhi7wAm24d5xOa1qYbRVQhX-JqxpODlL1GsHTzGMzcy01HMp__C3-v";
  var deviceToken = "cBsqDUmGj-Q:APA91bEvBVf6yOeoVmUXLDWo2CT7QnQL8tZHj1h2bK_qXAbjOD5HX2tiLcYwaXw186SgUD0edm9LtZWdu5L_QuQ7vy0SYb5MrOOMQo8kRpxHkie4bBGCKE5PJzBGBB1_2e7KnxovjP--";
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
