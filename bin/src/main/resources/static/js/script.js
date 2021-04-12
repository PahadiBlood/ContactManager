const toggleSidebar = () => {

  if ($('.sidebar').is(':visible')) {
    $('.sidebar').hide();
    $('.content').css("margin-left", "0%");
  } else {
    $('.sidebar').show();
    $('.content').css("margin-left", "22%");
  }
};


function deleteContact(cid, currentPage, name) {

  swal({
    title: "Delete " + name + "?",
    text: "Do you want to delete this contact?",
    icon: "warning",
    buttons: true,
    dangerMode: true,
  })
    .then((willDelete) => {

      if (willDelete) {
        url = "/user/delete/" + currentPage + "/" + cid;
        $.ajax({
          url: url,
          method: "POST",
          success: function () {
            $("#" + cid).remove();
          }
        })
      }
    });
}


function deleteContactProfile(cid, name) {

  swal({
    title: "Delete " + name + "?",
    text: "Do you want to delete this contact?",
    icon: "warning",
    buttons: true,
    dangerMode: true,
  })
    .then((willDelete) => {

      if (willDelete) {
        url = "/user/delete/" + cid;
        $.ajax({
          url: url,
          method: "POST",
          success: function () {
            location.reload();
          }
        })
      }
    });
}

function submitForm() {
  document.forms["myform"].submit();
}


//search contacts 

const search = () => {

  let query = $("#search-input").val();
  if (query == '') {
    $('.show-result').hide();
  } else {
    let url = `/user/search/${query}`;

    fetch(url)
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        if (Object.keys(data).length) {
d
          let text = `<div class='list-group'>`;
          data.forEach((contact) => {
            text += `<a href='/user/contact/profile/${contact.cid}' class='list-group-item list-group-item-action'>${contact.name}</a>`;
          });

          text += `</div>`;

          $('.show-result').html(text);
          $('.show-result').show();
        } else {
let text = `
<div class='list-group'>
<a list-group-item list-group-item-action'>Contact not found.</a>
</div>
`;
          $('.show-result').html(text);
          $('.show-result').show();
        }
      });
  }
}