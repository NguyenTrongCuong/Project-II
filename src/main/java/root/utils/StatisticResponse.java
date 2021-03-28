package root.utils;

import java.util.ArrayList;
import java.util.List;

public class StatisticResponse<T, U> {
	private String header = "";
	private List<U> labels = new ArrayList<>();
	private List<T> data = new ArrayList<>();
	private List<String> colors = new ArrayList<>();
	private String yAxisName = "";
	private String xAxisName = "";
	private int isEmpty = 1;
	
	public String getyAxisName() {
		return yAxisName;
	}

	public void setyAxisName(String yAxisName) {
		this.yAxisName = yAxisName;
	}

	public String getxAxisName() {
		return xAxisName;
	}

	public void setxAxisName(String xAxisName) {
		this.xAxisName = xAxisName;
	}

	public int getIsEmpty() {
		return isEmpty;
	}

	public void setIsEmpty(int isEmpty) {
		this.isEmpty = isEmpty;
	}

	public List<String> getColors() {
		return colors;
	}

	public void setColors(List<String> colors) {
		this.colors = colors;
	}

	public String getHeader() {
		return header;
	}
	
	public void setHeader(String header) {
		this.header = header;
	}
	
	public List<U> getLabels() {
		return labels;
	}

	public void setLabels(List<U> labels) {
		this.labels = labels;
	}

	public List<T> getData() {
		return data;
	}
	
	public void setData(List<T> data) {
		this.data = data;
	}
	
	

}
