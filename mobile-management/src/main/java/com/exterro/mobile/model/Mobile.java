/**
 * @Placed com.exterro.mobile.model
 */
package com.exterro.mobile.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * @author mrangasamy
 *
 * @date 12-Aug-2024
 */
@Entity
public class Mobile {
	@Id
	@GeneratedValue
	private int id;
	@Column(unique = true)
	private String imei;
	private String brand;
	private String model;
	private double cost;
	public Mobile(int id, String imei, String brand, String model, double cost) {
		super();
		this.id = id;
		this.imei = imei;
		this.brand = brand;
		this.model = model;
		this.cost = cost;
	}
	public Mobile() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	@Override
	public String toString() {
		return "Mobile [id=" + id + ", imei=" + imei + ", brand=" + brand + ", model=" + model + ", cost=" + cost + "]";
	}
	
}
