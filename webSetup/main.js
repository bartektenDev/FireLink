const electron = require('electron')
// Module to control application life.
const app = electron.app
// Module to create native browser window.
const BrowserWindow = electron.BrowserWindow

var exec = require('child_process').exec
function Callback(err, stdout, stderr) {
    if (err) {
        //console.log(`exec error: ${err}`);
        console.log('We ran into a problem! Make sure you are not already running the FireLink application! If you are, please close this window.');
        return;
    }else{
        console.log(`${stdout}`);
    }
}

res = exec('http-server -u 127.0.0.1 -p 5000', Callback);

const path = require('path')
const url = require('url')

// Keep a global reference of the window object, if you don't, the window will
// be closed automatically when the JavaScript object is garbage collected.
let mainWindow

function createWindow () {
  // Create the browser window.
  mainWindow = new BrowserWindow({width: 1200, height: 900})

  mainWindow.webContents.session.clearCache(function(){
  //some callback.
  });

  mainWindow.loadURL('127.0.0.1:5000')

  // Open the DevTools.
  mainWindow.webContents.openDevTools()

  mainWindow.webContents.on('did-fail-load', function() {
    console.log('failed');
    //retry until we load

  });

  // Emitted when the window is closed.
  mainWindow.on('closed', function () {
    res = exec('', Callback);
    // Dereference the window object, usually you would store windows
    // in an array if your app supports multi windows, this is the time
    // when you should delete the corresponding element.
    mainWindow = null
  })
}

// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.on('ready', createWindow)

// Quit when all windows are closed.
app.on('window-all-closed', function () {
  // On OS X it is common for applications and their menu bar
  // to stay active until the user quits explicitly with Cmd + Q
  if (process.platform !== 'darwin') {
    app.quit()
  }
})

app.on('activate', function () {
  // On OS X it's common to re-create a window in the app when the
  // dock icon is clicked and there are no other windows open.
  if (mainWindow === null) {
    createWindow()
  }
})
