$(document).ready(function() {0
    
    $('.quantity-right-plus').click(function(e) {
        e.preventDefault();
        var id = this.id.substring(1);
        var quantity = parseInt($('#qty' + id).val());
        if (quantity >= 1) {
            $('#qty' + id).val(quantity + 1);
            var qty = $('#qty' + id).val()
            var path = location.origin + '/turtech/shoppingCart/updateCartItem?id=' + id + '&qty=' + qty;
            $.ajax({
                type: "GET",
                url: path,
                success: function() {
                    location.reload();
                }
            });
        } else {
            $('#qty' + id).val(1);
            var path = location.origin + '/turtech/shoppingCart/updateCartItem?id=' + id + '&qty=' + 1;
            $.ajax({
                type: "GET",
                url: path,
                success: function() {
                    location.reload();
                }
            });
        }
    });

    $('.quantity-left-minus').click(function(e) {
        e.preventDefault();
        var id = this.id.substring(1);
        var quantity = parseInt($('#qty' + id).val());
        if (quantity > 1) {
            $('#qty' + id).val(quantity - 1);
            var qty = $('#qty' + id).val()
            var path = location.origin + '/turtech/shoppingCart/updateCartItem?id=' + id + '&qty=' + qty;
            $.ajax({
                type: "GET",
                url: path,
                success: function() {
                    location.reload();
                }
            });
        } else {
            $('#qty' + id).val(1);
            var path = location.origin + '/turtech/shoppingCart/updateCartItem?id=' + id + '&qty=' + 1;
            $.ajax({
                type: "GET",
                url: path,
                success: function() {
                    location.reload();
                }
            });
        }
    });
});