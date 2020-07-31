package biyaniparker.com.parker.beans;

public class ProductDetailsBean {
    public String imageUrl;

    public ProductDetailsBean(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
