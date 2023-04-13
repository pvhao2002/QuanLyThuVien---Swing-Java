package entity;

public class Book {
	private int book_id;
	private String title;
	private String author;
	private String category;
	private int publication_year;

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPublication_year() {
		return publication_year;
	}

	public void setPublication_year(int publication_year) {
		this.publication_year = publication_year;
	}

	public Book(int book_id, String title, String author, String category, int publication_year) {
		super();
		this.book_id = book_id;
		this.title = title;
		this.author = author;
		this.category = category;
		this.publication_year = publication_year;
	}

	public Book() {
		super();
		this.book_id = 0;
		this.title = "";
		this.author = "";
		this.category = "";
		this.publication_year = 0;
	}

	public Book(String title, String author, String category, int publication_year) {
		super();
		this.title = title;
		this.author = author;
		this.category = category;
		this.publication_year = publication_year;
	}

	@Override
	public String toString() {
		return title;
	}

}
