package entity;

import java.util.Date;

public class DetailLoan {
	private int book_id;
	private int loan_id;
	private String title;
	private String author;
	private String category;
	private int publication_year;
	private Date date_out;
	private Date date_in;

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

	public Date getDate_out() {
		return date_out;
	}

	public void setDate_out(Date date_out) {
		this.date_out = date_out;
	}

	public Date getDate_in() {
		return date_in;
	}

	public void setDate_in(Date date_in) {
		this.date_in = date_in;
	}

	public int getLoan_id() {
		return loan_id;
	}

	public void setLoan_id(int loan_id) {
		this.loan_id = loan_id;
	}

	public DetailLoan(int book_id, int loan_id, String title, String author, String category, int publication_year,
			Date date_out, Date date_in) {
		super();
		this.book_id = book_id;
		this.loan_id = loan_id;
		this.title = title;
		this.author = author;
		this.category = category;
		this.publication_year = publication_year;
		this.date_out = date_out;
		this.date_in = date_in;
	}

	@Override
	public String toString() {
		return "DetailLoan [book_id=" + book_id + ", loan_id=" + loan_id + ", title=" + title + ", author=" + author
				+ ", category=" + category + ", publication_year=" + publication_year + ", date_out=" + date_out
				+ ", date_in=" + date_in + "]";
	}

}
