package com.ischoolbar.programmer.entity.admin;

import org.springframework.stereotype.Component;

/**
 * 种植实体类
 * @author Administrator
 *
 */
@Component
public class Floor {
	private Long id;//种植id
	private String name;//种植名称
	private String remark;//种植备注
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
