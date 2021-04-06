const toggleSidebar=()=>{

if($('.sidebar').is(':visible')){
$('.sidebar').hide();
$('.content').css("margin-left","0%");
}else{
$('.sidebar').show();
$('.content').css("margin-left","22%");
}
};


function deleteContact(cid,currentPage,name){

swal({
  title: "Delete "+name+"?",
  text: "Do you want to delete this contact?",
  icon: "warning",
  buttons: true,
  dangerMode: true,
})
.then((willDelete) => {

  if (willDelete) {
  url="/user/delete/"+currentPage+"/"+cid;
  $.ajax({
  url:url,
  method:"POST",
  success:function(){
  $("#"+cid).remove();
  }
  })
  }
});
}


function deleteContactProfile(cid,name){

swal({
  title: "Delete "+name+"?",
  text: "Do you want to delete this contact?",
  icon: "warning",
  buttons: true,
  dangerMode: true,
})
.then((willDelete) => {

  if (willDelete) {
  url="/user/delete/"+cid;
  $.ajax({
  url:url,
  method:"POST",
  success:function(){
  location.reload();
  }
  })
  }
});
}