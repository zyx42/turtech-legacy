$(document).ready(function() {

    $('.quantity-right-plus').click(function(e) {
        e.preventDefault();
        var quantity = parseInt($('#qty').val());
        if (quantity >= 1) {
            $('#qty').val(quantity + 1);
        } else {
            $('#qty').val(1);
        }
    });

    $('.quantity-left-minus').click(function(e) {
        e.preventDefault();
        var quantity = parseInt($('#qty').val());
        if (quantity > 1) {
            $('#qty').val(quantity - 1);
        } else {
            $('#qty').val(1);
        }
    });
    
    $('.add-to-cart').click(function() {
        var qty = parseInt($('#qty').val());
        $("#addSuccess").css("display", "none");
        $("#notEnoughStock").css("display", "none");
        if (!loggedIn) {
            window.location.hash = 'signin';
            $('#accountControl').modal('show');
            $('#signin').tab('show');
            $('a[href="#signin"]').addClass('active');
        } else if (qty > inStock || qty + jointQty > inStock) {
            $("#notEnoughStock").css("display", "block");
        } else {
            var id = $(this).attr('id');
            var path = location.origin + '/turtech/shoppingCart/addItem?id=' + id + '&qty=' + qty;
            $.ajax({
                type: "GET",
                url: path,
                success: function() {
                    $("#addSuccess").css("display", "block");
                    jointQty += qty;
                }
            });
        }
    });
});