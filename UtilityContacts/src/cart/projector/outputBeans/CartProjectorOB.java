package cart.projector.outputBeans;

import java.util.List;



public class CartProjectorOB {

	boolean productAlreadyInCart;
	private String numberOfItemsInCart;
	private List<CartItem> cartItems;

	
	public List<CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	public String getNumberOfItemsInCart() {
		return numberOfItemsInCart;
	}
	public void setNumberOfItemsInCart(String numberOfItemsInCart) {
		this.numberOfItemsInCart = numberOfItemsInCart;
	} 

	public boolean isProductAlreadyInCart() {
		return productAlreadyInCart;
	}

	public void setProductAlreadyInCart(boolean productAlreadyInCart) {
		this.productAlreadyInCart = productAlreadyInCart;
	}
}
