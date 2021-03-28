package root.utils;

import java.util.ArrayList;
import java.util.List;

public class SearchResponse<T> {
	private List<T> result = new ArrayList<>();

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}
	
	

}
