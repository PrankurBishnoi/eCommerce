package com.prankur.eCommerce.models.product;

import com.prankur.eCommerce.models.users.Customer;

import javax.persistence.*;

@Entity
public class ProductReview
{
//    @EmbeddedId
//    private ProductReviewIdCompositKey id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String review;
    @Column(nullable = false)
    private int rating;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @MapsId("productId")
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @MapsId("customerId")
    private Customer customer;

    public ProductReview(String review, int rating) {
        this.review = review;
        this.rating = rating;
    }

//    public ProductReview(String review, int rating, Product product, Customer customer) {
//        this.review = review;
//        this.rating = rating;
//        this.product = product;
//        this.customer = customer;
//    }



//        public ProductReview(ProductReviewIdCompositKey id, String review, int rating) {
//        this.id = id;
//        this.review = review;
//        this.rating = rating;
//    }

//    public ProductReview(ProductReviewIdCompositKey id, String review, int rating, Product product, Customer customer) {
//        this.id = id;
//        this.review = review;
//        this.rating = rating;
////        this.product = product;
////        this.customer = customer;
//    }

//    public ProductReviewIdCompositKey getId() {
//        return id;
//    }
//
//    public void setId(ProductReviewIdCompositKey id) {
//        this.id = id;
//    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "ProductReview{" +
                "id=" + id +
                ", review='" + review + '\'' +
                ", rating=" + rating +
                ", product=" + product +
                ", customer=" + customer +
                '}';
    }

    public ProductReview() {
    }
}
