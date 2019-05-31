package CategoryCarousel;

import javafx.scene.image.Image;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Categories {
	private static final List<Category> categoryList = new LinkedList<>();
	static {
		categoryList.add(new Category("Bröd", "BREAD", ProductCategory.BREAD));
		categoryList.add(new Category("Kolhydrater", "POTATO_RICE", ProductCategory.POTATO_RICE, ProductCategory.PASTA));
		categoryList.add(new Category("Grönsaker", "VEGETABLE_FRUIT", ProductCategory.VEGETABLE_FRUIT, ProductCategory.ROOT_VEGETABLE, ProductCategory.CABBAGE, ProductCategory.POD));
		categoryList.add(new Category("Frukt & Bär", "FRUIT", ProductCategory.FRUIT, ProductCategory.CITRUS_FRUIT, ProductCategory.EXOTIC_FRUIT, ProductCategory.MELONS, ProductCategory.BERRY));
		categoryList.add(new Category("Örter", "HERB", ProductCategory.HERB));
		categoryList.add(new Category("Kött", "MEAT", ProductCategory.MEAT));
		categoryList.add(new Category("Fisk", "FISH", ProductCategory.FISH));
		categoryList.add(new Category("Mejeri", "DAIRIES", ProductCategory.DAIRIES));
		categoryList.add(new Category("Torra varor", "FLOUR_SUGAR_SALT", ProductCategory.FLOUR_SUGAR_SALT));
		categoryList.add(new Category("Nötter", "NUTS_AND_SEEDS", ProductCategory.NUTS_AND_SEEDS));
		categoryList.add(new Category("Varma drycker", "HOT_DRINKS", ProductCategory.HOT_DRINKS));
		categoryList.add(new Category("Kalla drycker", "COLD_DRINKS", ProductCategory.COLD_DRINKS));
		categoryList.add(new Category("Godis", "SWEET", ProductCategory.SWEET));
	};

	public static int size() {
		return categoryList.size();
	}
	public static Category get(int i) {
		return categoryList.get(i);
	}

	public static List<Category> values() {
		return categoryList;
	}

	public static int indexOf(Category c) {
		return categoryList.indexOf(c);
	}

	public static class Category {
		private final String name;
		private final String imageName;
		private final ProductCategory[] categories;

		private Category(String name, String imageName, ProductCategory... categories) {
			this.name = name;
			this.imageName = imageName;
			this.categories = categories;
		}

		public String getName() {
			return name;
		}
		public ProductCategory[] getCategories() {
			return categories;
		}

		public List<Product> getProducts() {
			List<Product> products = new LinkedList<>();

			for (ProductCategory pc : categories) {
				products.addAll(IMatDataHandler.getInstance().getProducts(pc));
			}

			return products;
		}

		public Image getImage(double width, double height) {
			File file = new File(IMatDataHandler.getInstance().imatDirectory() + "/category_icons/" + imageName + ".png");
			return new Image(file.toURI().toString(), width, height, true, true, true);
		}
	}
}