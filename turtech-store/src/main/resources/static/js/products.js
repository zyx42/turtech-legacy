$('.add-to-cart').click(function() {
    /*<![CDATA[*/
    var loggedIn = /*[[${#httpServletRequest.getUserPrincipal()!=null}]]*/ 'someone';
    /*]]*/
    console.log(loggedIn);
    if (!loggedIn) {
        window.location.hash = 'signin';
        $('#accountControl').modal('show');
        $('#signin').tab('show');
        $('a[href="#signin"]').addClass('active');
    } else {
        var id = $(this).attr('id');
        var path = location.origin + '/turtech/shoppingCart/addItem?id=' + id + '&qty=1';
        $.ajax({
            type: "GET",
            url: path,
            success: function() {
                alert("Item added!");
            }
        });
    }
});