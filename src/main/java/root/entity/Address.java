package root.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class Address implements Serializable {
	@Column(name="provinces")
	@NotBlank(message="Province is required")
	private String province;
	
	@Column(name="cities")
	@NotBlank(message="City is required")
	private String city;
	
	@Column(name="districts")
	@NotBlank(message="District is required")
	private String district;
	
	@Column(name="streets")
	@NotBlank(message="Street is required")
	private String street;
	
	@Column(name="locations")
	@NotBlank(message="Location is required")
	private String location;
	
	private static final long serialVersionUID = 1L;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
	

}
