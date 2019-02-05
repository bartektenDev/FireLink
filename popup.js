document.addEventListener('DOMContentLoaded', function () {
      document.querySelector('button').addEventListener('click', main);
});
function main() {
    var source = document.getElementById('source').value;
    document.getElementById("result").innerHTML = source;
}

function whoop()
{
  //click the submit button within the form with the information

  document.getElementById("urlLogoDisplay").src = "assets/uploadframes/uploading/uploading.gif";
  setTimeout(function(){
    //uploading
    document.getElementById("urlLogoDisplay").src = "assets/uploadframes/uploading/frame_085_delay-0.03s.gif";
    //get Title of current site
    //get current site link

  }, 2200);
  setTimeout(function(){
    //upload complete
    document.getElementById("urlLogoDisplay").src = "assets/uploadframes/uploadcomplete/uploadcomplete.gif";
  }, 4000);
  setTimeout(function(){
    //wait showing its complet for three seconds and reset
    document.getElementById("urlLogoDisplay").src = "assets/uploadframes/uploadcomplete/frame_100_delay-0.78s.gif";
  }, 5100);
  setTimeout(function(){
    //back to idle and ready to firelink it!
    document.getElementById("urlLogoDisplay").src = "assets/uploadframes/idle/idle.gif";
  }, 8100);
}
