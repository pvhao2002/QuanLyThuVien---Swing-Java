package entity;

public class Member {
	private int member_id;
	private String username;
	private String password;
	private String name;
	private int role;

	public Member(int member_id, String username, String password, String name, int role) {
		super();
		this.member_id = member_id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.role = role;
	}

	public Member() {
		super();
		this.member_id = 0;
		this.username = "";
		this.password = "";
		this.name = "";
		this.role = 0;
	}

	public Member(String username, String password, String name, int role) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.role = role;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return username;
	}

}
