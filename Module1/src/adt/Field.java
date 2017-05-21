package adt;

// Encapsulates: String, Integer, or Boolean
public class Field<T> {
	public T data;
	
	public Field(T data) {
		data(data);
	}
	
	
	
	public T data() {
		return data;
	}
	
	public T data(T data) {
		T get = this.data;
		this.data = data;
		return get;
	}
	
	@Override
	public String toString() {
		return data.toString();
	}
	
	public int hashCode(){
		return data != null ? data.hashCode() : 0;
	}
}
