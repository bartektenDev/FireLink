chrome.browserAction.onClicked.addListener(function (tab) {
  chrome.tabs.create({url: 'popup.html'})
  var title = findTitle();
  chrome.runtime.sendMessage(title);
  alert(chrome.runtime.getURL(tab.id));
})

function findTitle()
{
  var tab = tabs[0];
  var resultTitle = document.title;
  var resultFireLink = tab.url;
  return resultFireLink;
}

chrome.runtime.onMessage.addListener(function(title,
  sender, sendResponse)  {
  if(title.length > 0)
  {
    chrome.pageAction.show(sender.tab.id);
  }
  sendResponse();
});
