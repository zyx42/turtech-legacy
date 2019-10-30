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
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
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
	@Size(min = 2, max = 32, message = "{validation.product.name.Size}")
	@NotBlank(message = "{validation.product.name.NotBlank}")
	private String name;

	/**
	 * A name of the manufacturer of the product.
	 */
	@Column(name = "manufacturer")
	@Size(min = 2, max = 32, message = "{validation.product.manufacturer.Size}")
	@NotBlank(message = "{validation.product.manufacturer.NotBlank}")
	private String manufacturer;

	/**
	 * A date on which the product was manufactured.
	 */
	@Column(name = "manufacture_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "{validation.product.manufactureDate.NotNull}")
	@PastOrPresent(message = "{validation.product.manufactureDate.PastOrPresent}")
	private LocalDate manufactureDate;

	/**
	 * A category to which the product belongs. Currently those are: laptops,
	 * cellphones or tablets.
	 */
	@Column(name = "category")
	@NotNull(message = "{validation.product.category.NotNull}")
	private String category;

	/**
	 * A condition in which the product is. Currently those are: new,
	 * old, refurbished.
	 */
	@Column(name = "condition")
	@NotNull(message = "{validation.product.condition.NotNull}")
	private String condition;

	/**
	 * An approximate weight of the product during shipping.
	 */
	@Column(name = "shipping_weight")
	@Min(value = 0, message = "{validation.product.shippingWeight.Min}")
	@NotNull(message = "{validation.product.shippingWeight.NotNull}")
	private double shippingWeight;

	/**
	 * A general price of the product, which is charged from a regular customer.
	 */
	@Column(name = "list_price")
	@Min(value = 0, message = "{validation.product.listPrice.Min}")
	@Digits(integer = 8, fraction = 2, message = "{validation.product.listPrice.Digits}")
	@NotNull(message = "{validation.product.listPrice.NotNull}")
	private BigDecimal listPrice;

	/**
	 * A special price of the product, which is charged under special circumstances,
	 * like a sail, promo code or anything of sorts.
	 */
	@Column(name = "our_price")
	@Min(value = 0, message = "{validation.product.listPrice.Min}")
	@Digits(integer = 8, fraction = 2, message = "{validation.product.listPrice.Digits}")
	@NotNull(message = "{validation.product.listPrice.NotNull}")
	private BigDecimal ourPrice;

	/**
	 * A general description of the product.
	 */
	@Column(name = "description", columnDefinition = "text")
	@NotBlank(message = "{validation.product.description.NotBlank}")
	private String description;

	/**
	 * A short summary of technical specifications of the product.
	 */
	@Column(name = "specifications", columnDefinition = "text")
	@NotBlank(message = "{validation.product.specifications.NotBlank}")
	private String specifications;

	/**
	 * A number of the product items left in stock.
	 */
	@Column(name = "in_stock_number")
	@Min(value = 0, message = "{validation.product.inStockNumber.Min}")
	@NotNull(message = "{validation.product.inStockNumber.NotNull}")
	private int inStockNumber;

	/**
	 * A boolean which specifies if the product was discontinued or not.
	 */
	@Column(name = "discontinued")
	@NotNull(message = "{validation.product.discontinued.NotNull}")
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