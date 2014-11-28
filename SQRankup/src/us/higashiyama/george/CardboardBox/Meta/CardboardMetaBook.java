
package us.higashiyama.george.CardboardBox.Meta;

import java.io.Serializable;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class CardboardMetaBook implements CardboardItemMeta, Serializable {

	private static final long serialVersionUID = -2679865680908973363L;
	private int id;
	private String title;
	private String author;
	private List<String> pages;

	public CardboardMetaBook(ItemStack book) {

		BookMeta meta = (BookMeta) book.getItemMeta();
		this.id = book.getTypeId();
		this.title = meta.getTitle();
		this.author = meta.getAuthor();
		this.pages = meta.getPages();
	}

	public ItemMeta unbox() {

		BookMeta meta = (BookMeta) new ItemStack(this.id).getItemMeta();
		meta.setTitle(this.title);
		meta.setAuthor(this.author);
		meta.setPages(this.pages);
		return meta;
	}
}
