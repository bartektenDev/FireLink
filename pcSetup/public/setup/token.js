MsgElem = document.getElementById("msg")
TokenElem = document.getElementById("token")
NotisElem = document.getElementById("notis")
ErrElem = document.getElementById("err")
QRTokenElem = document.getElementById("tokenImage1")
// Initialize Firebase
// TODO: Replace with your project's customized code snippet
var config = {
  apiKey: "AIzaSyCD8zerHteGZqqsgxp6q86Ervw2gB1-0sI",
  authDomain: "firelink-8b41e.firebaseapp.com",
  databaseURL: "https://firelink-8b41e.firebaseio.com",
  storageBucket: "firelink-8b41e.appspot.com",
  messagingSenderId: "926362946477"
};
firebase.initializeApp(config);

const messaging = firebase.messaging();
messaging
    .requestPermission()
    .then(function () {
        MsgElem.innerHTML = "Notification permission granted."
        console.log("Notification permission granted.");

        // get the token in the form of promise
        return messaging.getToken()
    })
    .then(function(token) {
        TokenElem.innerHTML = token
        QRTokenElem.src = 'https://api.qrserver.com/v1/create-qr-code/?data=' + token + '&amp;size=150x150'
    })
    .catch(function (err) {
        ErrElem.innerHTML =  ErrElem.innerHTML + "; " + err
        console.log("Unable to get permission to notify.", err);
    });

messaging.onMessage(function(payload) {
    console.log("Message received. ", payload);
    NotisElem.innerHTML = NotisElem.innerHTML + JSON.stringify(payload)
});
