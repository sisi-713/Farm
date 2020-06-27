package com.ischoolbar.programmer.entity.admin;

import org.springframework.stereotype.Component;

/**
 * 土地实体类
 * @author Administrator
 *
 */
@Component
public class Room {
	private Long id;//土地id
	private String photo;//土地图片
	private String sn;//土地编号
	private Long roomTypeId;//房型id
	private Long floorId;//所属种植id
	private int status;//房型状态，0：可入住,1:已入住,2:打扫中
	private String remark;//房型备注
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Long getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(Long roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public Long getFloorId() {
		return floorId;
	}
	public void setFloorId(Long floorId) {
		this.floorId = floorId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
}
