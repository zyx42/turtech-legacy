package org.eugenarium.admin.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Class {@code Product} represents a product which is offered in the webstore.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Entity
@Data
@Table(name = "product", schema = "turtech")
@EntityListeners(AuditingEntityListener.class)
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	/**
	 * A name of the product, which in case of electronics usually is a
	 * model and a mark.
	 */
	@Column(name = "name")
	private String name;

	/**
	 * A name of the manufacturer of the product.
	 */
	@Column(name = "manufacturer")
	private String manufacturer;

	/**
	 * A date on which the product was manufactured.
	 */
	@Column(name = "manufacture_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate manufactureDate;

	/**
	 * A category to which the product belongs. Currently those are: laptops,
	 * cellphones or tablets.
	 */
	@Column(name = "category")
	private String category;

	/**
	 * A condition in which the product is. Currently those are: new,
	 * old, refurbished.
	 */
	@Column(name = "condition")
	private String condition;

	/**
	 * An approximate weight of the product during shipping.
	 */
	@Column(name = "shipping_weight")
	private double shippingWeight;

	/**
	 * A general price of the product, which is charged from a regular customer.
	 */
	@Column(name = "list_price")
	private double listPrice;

	/**
	 * A special price of the product, which is charged under special circumstances,
	 * like a sail, promo code or anything of sorts.
	 */
	@Column(name = "our_price")
	private double ourPrice;

	/**
	 * A general description of the product.
	 */
	@Column(name = "description", columnDefinition = "text")
	private String description;

	/**
	 * A short summary of technical specifications of the product.
	 */
	@Column(name = "specifications", columnDefinition = "text")
	private String specifications;

	/**
	 * A number of the product items left in stock.
	 */
	@Column(name = "in_stock_number")
	private int inStockNumber;

	/**
	 * A boolean which specifies if the product was discontinued or not.
	 */
	@Column(name = "discontinued")
	private boolean discontinued = false;

	/**
	 * A general image of the product.
	 */
	@Transient
	@JsonIgnore
	private MultipartFile productImage;

	/**
	 * A ProductToCartItem is basically the way to implement a table of the
	 * relationship between products and cart items, considering that even though
	 * one cart item is always a particular product, many users can have the same
	 * product in their shopping carts leading to a OneToMany(Product to CartItem's)
	 * relationship.
	 */
	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private List<CartItem> cartItemList;

	/**
	 * A list of reviews which were left by different users.
	 */
	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private List<UserReview> userReviewList;

	/**
	 * An auditing field, which specifies the date on which the product was added.
	 * It's filled automatically.
	 */
	@Column(name = "created_date", nullable = false, updatable = false)
	@CreatedDate
	@JsonIgnore
	private LocalDateTime createdDate;

	/**
	 * An auditing field, which specifies the person responsible for adding the product.
	 * It's filled automatically
	 */
	@Column(name = "created_by", nullable = false, updatable = false)
	@CreatedBy
	@JsonIgnore
	private String createdBy;

	/**
	 * An auditing field, which specified the date on which the product was last modified.
	 * It's filled automatically
	 */
	@Column(name = "last_modified_date", nullable = false)
	@LastModifiedDate
	@JsonIgnore
	private LocalDateTime lastModifiedDate;

	/**
	 * An auditing field, which specified the person responsible for the last changes
	 * to the product information. It's filled automatically.
	 */
	@Column(name = "last_modified_by", nullable = false)
	@LastModifiedBy
	@JsonIgnore
	private String lastModifiedBy;

	/**
	 * Returns the hash code value for this Product. It uses a prime number,
	 * <tt>id</tt> and <tt>name</tt> as means to calculate the hash code.
	 *
	 * @return the hash code value for this Product
	 *
	 * @see Object#hashCode()
	 * @see Object#equals(Object)
	 */
	@Override
	public int hashCode() {
		return 7 * (int) id.longValue() * name.hashCode();
	}

	/**
	 * Indicates whether some other object is &quot;equal to&quot; this Product.
	 * This method can return <tt>true</tt> <i>only</i> if the specified object is
	 * also a Product and it has the same <tt>id</id> and <tt>name</tt>.
	 * 
	 * @param obj the reference object with which to compare.
	 * @return <code>true</code> only if the specified object is also a Product. and
	 *         it it has the same <tt>id</id> and <tt>name</tt>.
	 * @see Object#equals(Object)
	 * @see Object#hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Product))
			return false;
		Product other = (Product) obj;
		return id.equals(other.getId()) && name.equals(other.getName());
	}
}