package root.utils;

public class Notification<T, U> {
	private T content;
	
	private U type;

	public Notification(T content, U type) {
		super();
		this.content = content;
		this.type = type;
	}

	public U getType() {
		return type;
	}

	public void setType(U type) {
		this.type = type;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}
	
	

}
