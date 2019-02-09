console.log('background running');

chrome.browserAction.onClicked.addListener(buttonClicked);

function buttonClicked(tab) {
  // let msg = {
  //   txt: "Hello World!"
  // }
  // chrome.tabs.sendMessage(tab.id, msg);
}

chrome.extension.onConnect.addListener(function(port) {
    console.log("Connecting...");
    port.onMessage.addListener(function(msg) {
        chrome.tabs.query({'active': true, 'lastFocusedWindow': true}, function (tabs) {
            let webData = {
              titleText: tabs[0].title,
              urlText: tabs[0].url
            }
            console.log("Current Tab Title: " + webData.titleText);
            console.log("Current Tab Url: " + webData.urlText);
            port.postMessage(webData);
        });
    });
})
