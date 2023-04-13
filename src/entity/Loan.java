package entity;

import java.util.Date;

public class Loan {
	private int loan_id;
	private int book_id;
	private int member_id;
	private Date date_out;
	private Date date_in;
	public int getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(int loan_id) {
		this.loan_id = loan_id;
	}
	public int getBook_id() {
		return book_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
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
	public Loan(int loan_id, int book_id, int member_id, Date date_out, Date date_in) {
		super();
		this.loan_id = loan_id;
		this.book_id = book_id;
		this.member_id = member_id;
		this.date_out = date_out;
		this.date_in = date_in;
	}
	public Loan(int book_id, int member_id, Date date_out, Date date_in) {
		super();
		this.book_id = book_id;
		this.member_id = member_id;
		this.date_out = date_out;
		this.date_in = date_in;
	}
	public Loan(int book_id, int member_id) {
		super();
		this.book_id = book_id;
		this.member_id = member_id;
	}
	public Loan() {
		super();
		this.loan_id = 0;
		this.book_id = 0;
		this.member_id = 0;
		this.date_out = new Date();
		this.date_in = new Date();
	}
	@Override
	public String toString() {
		return "Loan [loan_id=" + loan_id + ", book_id=" + book_id + ", member_id=" + member_id + ", date_out="
				+ date_out + ", date_in=" + date_in + "]";
	}
	
}
